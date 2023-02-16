/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package dev.zenhao.xin.listeners.movement;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.CSpeed;
import dev.zenhao.xin.utils.FlagUtil;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class BoatSpeed
implements Listener {
    Main main;

    public BoatSpeed(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        double Offset = 0.0;
        if (!(event.getFrom().getY() > event.getTo().getY())) {
            Offset = CSpeed.offset(event.getFrom(), event.getTo());
        }
        if (event.getPlayer().isInsideVehicle() && Offset >= 6.1 && event.getTo().getY() - event.getFrom().getY() < 1.0) {
            FlagUtil.flagNoEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation());
        }
    }
}

