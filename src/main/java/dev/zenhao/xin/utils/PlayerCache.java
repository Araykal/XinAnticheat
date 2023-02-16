/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package dev.zenhao.xin.utils;

import dev.zenhao.xin.utils.PlayerCacheManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;

public class PlayerCache {
    private final List<Location> locList = new ArrayList<Location>();
    private final HashMap<Location, Integer> locCacheMap = new HashMap();

    public PlayerCache(String playerName) {
        PlayerCacheManager.putCache(playerName, this);
    }

    public void addLoc(Location loc) {
        if (this.locCacheMap.containsKey(loc)) {
            this.locCacheMap.put(loc, this.locCacheMap.get(loc) + 1);
        } else {
            this.locCacheMap.put(loc, 0);
        }
        if (this.locList.size() >= 5) {
            this.locCacheMap.remove(this.locList.get(0));
            this.locList.remove(0);
        }
        if (!this.locList.contains(loc)) {
            this.locList.add(loc);
        }
    }

    public int sum() {
        int sum = 0;
        for (Location loc : this.locCacheMap.keySet()) {
            sum += this.locCacheMap.get(loc).intValue();
        }
        return sum + 1;
    }

    public List<Location> getLocList() {
        return this.locList;
    }
}

