/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  com.comphenix.protocol.injector.server.TemporaryPlayer
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.timer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.injector.server.TemporaryPlayer;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TimerC
implements Listener {
    public static final ConcurrentHashMap<UUID, Long> LAST_PACKET_TIME = new ConcurrentHashMap();
    public static final ConcurrentHashMap<UUID, Double> PACKET_BALANCE = new ConcurrentHashMap();
    public static int val;
    Main main;

    public TimerC(Main main) {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketSending(PacketEvent event) {
                TimerC.this.onSending(event);
            }

            public void onPacketReceiving(PacketEvent event) {
                TimerC.this.onReceiving(event);
            }
        });
        this.main = main;
        val = 500;
    }

    public static void compensate(Player player) {
        UUID uuid = player.getUniqueId();
        double packetBalance = PACKET_BALANCE.getOrDefault(uuid, 0.0);
        PACKET_BALANCE.put(uuid, packetBalance - 150.0);
    }

    public void onSending(PacketEvent event) {
        Player player = event.getPlayer();
        if (player instanceof TemporaryPlayer) {
            return;
        }
        Main.sendToMainThread(() -> TimerC.compensate(player));
    }

    public void onReceiving(PacketEvent event) {
        try {
            if (event.isCancelled() || FlagUtil.lowTPS(8)) {
                return;
            }
            CraftPlayer player = (CraftPlayer)event.getPlayer();
            UUID uuid = player.getUniqueId();
            long packetTimeNow = System.currentTimeMillis();
            long lastPacketTime = LAST_PACKET_TIME.getOrDefault(uuid, packetTimeNow - 50L);
            double packetBalance = PACKET_BALANCE.getOrDefault(uuid, 0.0);
            long rate = packetTimeNow - lastPacketTime;
            packetBalance += 50.0;
            packetBalance -= (double)rate;
            int triggerBalance = 55;
            int minimumClamp = val;
            if (packetBalance >= 55.0) {
                packetBalance = -27.5;
                FlagUtil.flagWithEvent(player, player.getLocation(), event, true);
            } else if (packetBalance < (double)(-1 * minimumClamp)) {
                packetBalance = -1 * minimumClamp;
            }
            LAST_PACKET_TIME.put(uuid, packetTimeNow);
            PACKET_BALANCE.put(uuid, packetBalance);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

