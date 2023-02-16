/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.world.ChunkLoadEvent
 */
package dev.zenhao.xin.listeners.misc;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.manager.ViolationManager;
import dev.zenhao.xin.utils.FlagUtil;
import java.util.Comparator;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkLoadLimit
extends ViolationManager
implements Listener {
    public Main main;

    public ChunkLoadLimit(Main main) {
        super(1, 30);
        this.main = main;
    }

    @EventHandler(priority=EventPriority.HIGH)
    public void onChunkGenerated(ChunkLoadEvent event) {
        Location location = event.getChunk().getBlock(7, 0, 7).getLocation();
        event.getChunk().getWorld().getPlayers().stream().filter(p -> location.getWorld().getName().equals(p.getLocation().getWorld().getName())).min(Comparator.comparingDouble(player -> this.manhattanDistance(player.getLocation().getX(), location.getX(), player.getLocation().getZ(), location.getZ()))).ifPresent(player -> {
            if (player.isGliding() || player.isInsideVehicle()) {
                this.increment(player.getUniqueId());
                int limit = this.main.getConfig().getInt("ChunkLoadStrict.chunkVL");
                if ((float)this.getVLS(player.getUniqueId()) > (float)limit * 1.5f) {
                    Main.sendToMainThread(new Runnable(){
                        final /* synthetic */ Player val$player;
                        {
                            this.val$player = player;
                        }

                        @Override
                        public void run() {
                            FlagUtil.flagNoEvent(this.val$player, this.val$player.getLocation());
                        }
                    });
                }
            }
        });
    }

    public double manhattanDistance(double x1, double x2, double z1, double z2) {
        return Math.abs(x2 - x1) + Math.abs(z2 - z1);
    }
}

