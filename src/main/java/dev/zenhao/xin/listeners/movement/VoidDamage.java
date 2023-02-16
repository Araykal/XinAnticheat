/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.World$Environment
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package dev.zenhao.xin.listeners.movement;

import dev.zenhao.xin.Main;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidDamage
implements Listener {
    Main main;

    public VoidDamage(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getWorld().getEnvironment().equals((Object)World.Environment.NETHER) && event.getPlayer().getLocation().getY() >= 127.0) {
            event.getPlayer().teleport(new Location(event.getPlayer().getWorld(), event.getPlayer().getLocation().getX(), 121.0, event.getPlayer().getLocation().getZ()));
        }
    }
}

