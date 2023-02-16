/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.entity.EnderCrystal
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityDamageByEntityEvent
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.potion.PotionEffectType
 */
package dev.zenhao.xin.listeners.combat;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.PlayerUtil;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffectType;

public class AntiWeakness
implements Listener {
    public static ConcurrentHashMap<Player, Integer> packetList = new ConcurrentHashMap();
    public static CopyOnWriteArrayList<String> playerTarget = new CopyOnWriteArrayList();
    public Main main;

    public AntiWeakness(Main main) {
        this.main = main;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    packetList.clear();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 1000L);
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.HELD_ITEM_SLOT}){

            public void onPacketReceiving(PacketEvent event) {
                try {
                    if (PlayerUtil.hasEffect(event.getPlayer(), PotionEffectType.WEAKNESS) && !PlayerUtil.hasEffect(event.getPlayer(), PotionEffectType.INCREASE_DAMAGE)) {
                        AntiWeakness.IncreasePacket(event.getPlayer());
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        });
        ProtocolLibrary.getProtocolManager().addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.USE_ENTITY}){

            public void onPacketReceiving(PacketEvent event) {
                try {
                    playerTarget.forEach(p -> {
                        if (p.equals(event.getPlayer().getName()) && packetList.get(event.getPlayer()) >= 4) {
                            event.setCancelled(true);
                            return;
                        }
                        playerTarget.remove(p);
                    });
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        });
    }

    public static void IncreasePacket(Player player) {
        if (packetList.containsKey(player)) {
            int countPosing = packetList.get(player);
            packetList.put(player, ++countPosing);
        } else {
            packetList.put(player, 1);
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event) {
        try {
            if (event.getEntity() instanceof EnderCrystal && PlayerUtil.hasEffect((Player)event.getDamager(), PotionEffectType.WEAKNESS) && !PlayerUtil.hasEffect((Player)event.getDamager(), PotionEffectType.INCREASE_DAMAGE) && !playerTarget.contains(event.getDamager().getName())) {
                playerTarget.add(event.getDamager().getName());
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

