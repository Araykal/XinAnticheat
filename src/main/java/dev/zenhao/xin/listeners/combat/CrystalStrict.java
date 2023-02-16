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
 *  org.bukkit.entity.EnderCrystal
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
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
import dev.zenhao.xin.manager.ViolationManager;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;

public class CrystalStrict
extends ViolationManager
implements Listener {
    public static ConcurrentHashMap<String, Long> playerMap = new ConcurrentHashMap();
    public static int strictDelay;
    public Main main;
    public CraftPlayer player;

    public CrystalStrict(Main main) {
        super(1, 20);
        this.main = main;
        strictDelay = 25;
        playerMap.clear();
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.USE_ENTITY}){

            public void onPacketReceiving(PacketEvent event) {
                try {
                    CrystalStrict.this.player = (CraftPlayer)event.getPlayer();
                    CrystalStrict.this.increment(CrystalStrict.this.player.getUniqueId());
                    if (CrystalStrict.this.getVLS(CrystalStrict.this.player.getUniqueId()) >= strictDelay) {
                        event.setCancelled(true);
                    }
                    CrystalStrict.this.onStrict(event);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        });
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof EnderCrystal && this.player != null && !playerMap.containsKey(this.player.getName())) {
                playerMap.put(this.player.getName(), System.currentTimeMillis());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void onStrict(PacketEvent event) {
        if (this.player.equals((Object)event.getPlayer())) {
            playerMap.forEach((players, now) -> {
                if (this.player.getName().equals(players)) {
                    if (System.currentTimeMillis() - now <= 22L) {
                        event.setCancelled(true);
                    } else {
                        playerMap.remove(players);
                        event.setCancelled(false);
                    }
                }
            });
        }
    }
}

