/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.entity.EntityToggleGlideEvent
 */
package dev.zenhao.xin.listeners.movement;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.manager.ViolationManager;
import dev.zenhao.xin.utils.FlagUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

public class PacketElytraFly
extends ViolationManager
implements Listener {
    Main main;

    public PacketElytraFly(Main main) {
        super(1, 3);
        this.main = main;
    }

    @EventHandler
    public void onToggle(EntityToggleGlideEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player)event.getEntity();
            this.increment(player.getUniqueId());
            if (this.getVLS(player.getUniqueId()) > 25) {
                FlagUtil.flagNoEvent(player, player.getLocation());
            }
        }
    }
}

