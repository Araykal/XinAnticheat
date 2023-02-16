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
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.EntityType
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.event.entity.PlayerDeathEvent
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
import dev.zenhao.xin.utils.FlagUtil;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class BowDamage
implements Listener {
    public static CopyOnWriteArrayList<Entity> target = new CopyOnWriteArrayList();
    CraftPlayer player;
    Main main;

    public BowDamage(Main main) {
        this.main = main;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                BowDamage.this.player = (CraftPlayer)event.getPlayer();
                target.forEach(p -> {
                    if (p.equals(BowDamage.this.player)) {
                        FlagUtil.flagWithEvent(BowDamage.this.player, BowDamage.this.player.getLocation(), event, true);
                    }
                });
            }
        });
    }

    @EventHandler
    public void onDead(PlayerDeathEvent event) {
        target.forEach(entity -> {
            if (event.getEntity().equals(entity)) {
                target.remove(event.getEntity());
            }
        });
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getEntityType().equals((Object)EntityType.PLAYER) && event.getDamager().getName().equals(this.player.getName()) && event.getDamager() instanceof Player && (this.player.getInventory().getItemInMainHand().getType().equals((Object)Material.BOW) || this.player.getInventory().getItemInOffHand().getType().equals((Object)Material.BOW)) && event.getDamage() > 50.0) {
            ((Player)event.getDamager()).setHealth(0.0);
            event.setCancelled(true);
            if (!target.contains(event.getDamager())) {
                target.add(event.getDamager());
            }
        }
    }
}

