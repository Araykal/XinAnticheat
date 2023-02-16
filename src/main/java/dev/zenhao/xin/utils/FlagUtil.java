/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.events.PacketEvent
 *  org.bukkit.Location
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package dev.zenhao.xin.utils;

import com.comphenix.protocol.events.PacketEvent;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.Utils;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FlagUtil {
    public static void flagWithEvent(CraftPlayer player, Location location, PacketEvent event, boolean cancel) {
        event.setCancelled(cancel);
        Main.sendToMainThread(() -> player.teleport(location));
    }

    public static void flagNoEvent(CraftPlayer player, Location location) {
        Main.sendToMainThread(() -> player.teleport(location));
    }

    public static void flagNoEvent(Player player, Location location) {
        Main.sendToMainThread(() -> player.teleport(location));
    }

    public static void flagNoEvent(CraftPlayer player, double x, double y, double z) {
        Main.sendToMainThread(() -> player.teleport(new Location(player.getWorld(), x, y, z)));
    }

    public static void flagNoEvent(CraftPlayer player, double x, double y, double z, float yaw, float pitch) {
        Main.sendToMainThread(() -> player.teleport(new Location(player.getWorld(), x, y, z, yaw, pitch)));
    }

    public static boolean lowTPS(int tps) {
        return Utils.getTps() <= (double)tps;
    }
}

