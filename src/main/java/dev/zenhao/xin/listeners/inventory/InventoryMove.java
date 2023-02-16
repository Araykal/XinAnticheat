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
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.inventory.InventoryClickEvent
 *  org.bukkit.event.player.PlayerMoveEvent
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class InventoryMove
implements Listener {
    public static ConcurrentHashMap<String, Double> clickList = new ConcurrentHashMap();
    public static CopyOnWriteArrayList<String> strictList = new CopyOnWriteArrayList();
    CraftPlayer player;
    Main main;

    public InventoryMove(final Main main) {
        this.main = main;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.WINDOW_CLICK}){

            public void onPacketReceiving(PacketEvent event) {
                if (main.getConfigBoolean("AntiCheat.InventoryMove")) {
                    InventoryMove.this.player = (CraftPlayer)event.getPlayer();
                    if (!clickList.containsKey(InventoryMove.this.player.getName())) {
                        clickList.put(InventoryMove.this.player.getName(), InventoryMove.this.player.getHealth());
                    }
                }
            }
        });
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (this.main.getConfigBoolean("AntiCheat.InventoryMove")) {
            strictList.forEach(p -> {
                if (event.getWhoClicked().getName().equals(p)) {
                    event.setCancelled(true);
                }
            });
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        this.onInvalidClick(event, event.getPlayer().getHealth());
    }

    public void onInvalidClick(PlayerMoveEvent event, double dmg) {
        if (this.main.getConfigBoolean("AntiCheat.InventoryMove")) {
            clickList.forEach((p, damage) -> {
                if ((event.getTo().getX() - event.getFrom().getX() != 0.0 || event.getFrom().getY() - event.getTo().getY() != 0.0 || event.getFrom().getZ() - event.getFrom().getZ() != 0.0) && damage != dmg) {
                    if (!strictList.contains(p)) {
                        strictList.add((String)p);
                        return;
                    }
                    strictList.remove(p);
                    clickList.remove(p);
                }
            });
        }
    }
}

