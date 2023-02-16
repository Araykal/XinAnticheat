/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.PacketType$Play$Server
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
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
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TimerB
implements Listener {
    public static TimerB INSTANCE;
    private final ConcurrentHashMap<UUID, Long> lastTime = new ConcurrentHashMap();
    private final ConcurrentHashMap<UUID, Double> balance = new ConcurrentHashMap();
    public Main main;
    public int cfg;

    public TimerB(Main main) {
        this.main = main;
        INSTANCE = this;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                TimerB.this.onReceiving(event);
            }
        });
        this.cfg = main.getConfigInt("AntiCheat.FlagLevel");
    }

    public void onReceiving(PacketEvent event) {
        try {
            Player player = event.getPlayer();
            UUID uuid = player.getUniqueId();
            if (this.main.getConfigBoolean("AntiCheat.AntiFastContracting")) {
                if (event.getPacket().getType().equals((Object)PacketType.Play.Client.POSITION) || event.getPacket().getType().equals((Object)PacketType.Play.Client.POSITION_LOOK)) {
                    long time = System.currentTimeMillis();
                    long lastTime = this.lastTime.getOrDefault(uuid, 0L) != 0L ? this.lastTime.getOrDefault(uuid, 0L) : time - 50L;
                    this.lastTime.put(uuid, time);
                    long rate = time - lastTime;
                    double balanceOrDefault = this.balance.getOrDefault(uuid, 0.0);
                    this.balance.put(uuid, balanceOrDefault + 50.0);
                    balanceOrDefault = this.balance.getOrDefault(uuid, 0.0);
                    this.balance.put(uuid, balanceOrDefault - (double)rate);
                    if (this.balance.getOrDefault(uuid, 0.0) >= (double)this.cfg) {
                        FlagUtil.flagWithEvent((CraftPlayer)player, player.getLocation(), event, false);
                        this.balance.put(uuid, 0.0);
                    }
                } else if (event.getPacket().getType().equals((Object)PacketType.Play.Server.POSITION)) {
                    double balanceOrDefault = this.balance.getOrDefault(uuid, 0.0);
                    this.balance.put(uuid, balanceOrDefault - 50.0);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

