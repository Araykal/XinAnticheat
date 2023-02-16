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
 *  com.comphenix.protocol.events.PacketContainer
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.GameMode
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PacketFlyCheck
implements Listener {
    public static PacketFlyCheck INSTANCE;
    public int tp;
    public Main main;

    public PacketFlyCheck(Main main) {
        this.main = main;
        this.tp = 150;
        INSTANCE = this;
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGH, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                PacketFlyCheck.this.onPacket(event);
            }
        });
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGH, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}){

            public void onPacketReceiving(PacketEvent event) {
                PacketFlyCheck.this.onPacket(event);
            }
        });
    }

    public void onPacket(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        double x = (Double)packet.getDoubles().read(0);
        double y = (Double)packet.getDoubles().read(1);
        double z = (Double)packet.getDoubles().read(2);
        float yaw = ((Float)packet.getFloat().read(0)).floatValue();
        float pitch = ((Float)packet.getFloat().read(1)).floatValue();
        try {
            if (this.main.getConfigBoolean("AntiCheat.PacketFly") && !event.getPlayer().getGameMode().equals((Object)GameMode.CREATIVE) && !event.getPlayer().isInsideVehicle() && !event.getPlayer().isGliding()) {
                if (Math.abs(pitch) > 90.0f) {
                    FlagUtil.flagWithEvent((CraftPlayer)player, player.getLocation(), event, false);
                    return;
                }
                if (y <= -8.0) {
                    if (player.getInventory().getItemInMainHand().getType().equals((Object)Material.CHORUS_FRUIT) || player.getInventory().getItemInOffHand().getType().equals((Object)Material.CHORUS_FRUIT)) {
                        return;
                    }
                    FlagUtil.flagWithEvent((CraftPlayer)player, player.getLocation(), event, true);
                    return;
                }
                Location previous = player.getLocation().clone();
                previous.setY(0.0);
                Location current = new Location(previous.getWorld(), x, 0.0, z, yaw, pitch);
                double distanceHorizontal = previous.distanceSquared(current);
                double distanceVertical = y - player.getLocation().getY();
                double maxDistanceHorizontal = this.tp;
                if (distanceHorizontal > maxDistanceHorizontal && !player.isGliding() && !player.isInsideVehicle()) {
                    FlagUtil.flagWithEvent((CraftPlayer)player, player.getLocation(), event, true);
                    return;
                }
                if ((distanceVertical < -150.0 || distanceVertical >= 300.0) && !player.isGliding() && !player.isInsideVehicle()) {
                    if (distanceVertical < -150.0 && (player.getInventory().getItemInMainHand().getType().equals((Object)Material.CHORUS_FRUIT) || player.getInventory().getItemInOffHand().getType().equals((Object)Material.CHORUS_FRUIT))) {
                        return;
                    }
                    FlagUtil.flagWithEvent((CraftPlayer)player, player.getLocation(), event, true);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

