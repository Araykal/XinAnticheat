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
 *  org.bukkit.Material
 *  org.bukkit.entity.EnderCrystal
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.zenhao.xin.Main;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Material;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class AttackFrequency
implements Listener {
    public static ConcurrentHashMap<Player, Integer> packetCount = new ConcurrentHashMap();
    Main main;

    public AttackFrequency(final Main main) {
        this.main = main;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    if (packetCount != null) {
                        packetCount.clear();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 1000L);
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.USE_ENTITY}){

            public void onPacketReceiving(PacketEvent event) {
                try {
                    Entity entity = (Entity)event.getPacket().getEntityModifier(event).read(0);
                    AttackFrequency.IncreaseCountPos(event.getPlayer());
                    if (!(entity instanceof EnderCrystal)) {
                        if (event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.END_PORTAL) && (float)packetCount.get(event.getPlayer()).intValue() >= (float)main.getConfigInt("AntiCheat.AttackMaxPacket") / 2.0f) {
                            event.setCancelled(true);
                            return;
                        }
                        if (packetCount.get(event.getPlayer()) >= main.getConfigInt("AntiCheat.AttackMaxPacket")) {
                            event.setCancelled(true);
                        }
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        });
    }

    public static void IncreaseCountPos(Player player) {
        if (packetCount.containsKey(player)) {
            int packetCounting = packetCount.get(player);
            packetCount.put(player, ++packetCounting);
        } else {
            packetCount.put(player, 1);
        }
    }
}

