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
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.inventory;

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
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class InventoryClickCount
implements Listener {
    public static ConcurrentHashMap<Player, Integer> packetCount = new ConcurrentHashMap();
    public static CopyOnWriteArrayList<Player> playerList = new CopyOnWriteArrayList();
    public int clickID;
    public Main main;
    public int val;

    public InventoryClickCount(Main main) {
        this.main = main;
        this.val = main.getConfigInt("AntiCheat.ClickPacketCount");
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
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.WINDOW_CLICK}){

            public void onPacketReceiving(PacketEvent event) {
                try {
                    InventoryClickCount.IncreaseCountPos(event.getPlayer());
                    if (packetCount.get(event.getPlayer()) >= InventoryClickCount.this.val * 2) {
                        event.setCancelled(true);
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

    @EventHandler
    public void onLog(PlayerQuitEvent event) {
        try {
            packetCount.remove(event.getPlayer());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

