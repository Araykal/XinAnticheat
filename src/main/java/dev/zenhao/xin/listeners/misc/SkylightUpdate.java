/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 */
package dev.zenhao.xin.listeners.misc;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.PlayerCache;
import dev.zenhao.xin.utils.PlayerCacheManager;
import dev.zenhao.xin.utils.RandomUtil;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SkylightUpdate
implements Listener {
    public static int count = 12;
    private static List<Integer> idList;
    public Main main;

    public SkylightUpdate(Main main) {
        this.main = main;
        idList = main.getConfig().getIntegerList("DontCheckBlockId");
    }

    private boolean check(Player player, PlayerCache cache, Block block) {
        String playerName = player.getName();
        cache.addLoc(block.getLocation());
        if (cache.getLocList().contains(block.getLocation())) {
            int nowCount = cache.sum();
            if (nowCount >= count) {
                PlayerCacheManager.removeCache(playerName);
                return true;
            }
            return false;
        }
        return false;
    }

    @Deprecated
    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getPlayer().getLocation().getY() >= 128.0 && event.getPlayer().getWorld().getName().equals("world")) {
            PlayerCache cache;
            Player player = event.getPlayer();
            Block block = event.getBlockPlaced();
            if (!block.getType().name().endsWith("SHULKER_BOX") && !idList.contains(block.getType()) && this.check(player, cache = PlayerCacheManager.getCacheByName(player.getName()), block)) {
                event.setCancelled(true);
                event.getPlayer().teleport(event.getPlayer().getLocation().getY() >= 100.0 ? event.getPlayer().getLocation().add(0.0, -99.0, 0.0) : event.getPlayer().getLocation().add(0.0, RandomUtil.nextDouble(-1.0, -100.0), 0.0));
            }
        }
    }
}

