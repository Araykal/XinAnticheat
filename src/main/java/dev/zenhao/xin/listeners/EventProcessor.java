/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerVelocityEvent
 *  org.bukkit.util.Vector
 */
package dev.zenhao.xin.listeners;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.listeners.exploit.LagInventory;
import dev.zenhao.xin.utils.User;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;

public class EventProcessor
implements Listener {
    @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
    public void onPlayerVelocity(PlayerVelocityEvent event) {
        Player player = event.getPlayer();
        Vector velocity = event.getVelocity();
        User user = Main.INSTANCE.getUserManager().getUser(player.getUniqueId());
        user.getVelocityTracker().registerVelocity(velocity);
        if (!user.getMovementManager().onGround) {
            return;
        }
        user.getMovementManager().velocityExpectedMotionY = velocity.getY();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        try {
            LagInventory.playerStrict.remove(event.getPlayer().getName());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

