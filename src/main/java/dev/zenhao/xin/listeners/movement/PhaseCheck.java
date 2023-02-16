/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Material
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerMoveEvent
 */
package dev.zenhao.xin.listeners.movement;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PhaseCheck
implements Listener {
    Main main;

    public PhaseCheck(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPhasing(PlayerMoveEvent event) {
        try {
            if ((event.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.OBSIDIAN) && event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.OBSIDIAN) || event.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.BEDROCK) && event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.AIR) || event.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.BEDROCK) && event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.BEDROCK) || event.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.BEDROCK) && event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.OBSIDIAN) || event.getPlayer().getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.OBSIDIAN) && event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.BEDROCK) || event.getPlayer().getLocation().getBlock().getType().equals((Object)Material.BEDROCK)) && event.getPlayer().getLocation().getY() <= 30.0) {
                FlagUtil.flagNoEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation().add(0.0, 2.0, 0.0));
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

