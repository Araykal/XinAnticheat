/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.utils;

import dev.zenhao.xin.utils.PlayerCache;
import java.util.HashMap;

public class PlayerCacheManager {
    private static final HashMap<String, PlayerCache> cacheMap = new HashMap();

    public static PlayerCache getCacheByName(String playerName) {
        return cacheMap.get(playerName) == null ? new PlayerCache(playerName) : cacheMap.get(playerName);
    }

    public static void putCache(String playerName, PlayerCache cache) {
        cacheMap.put(playerName, cache);
    }

    public static void removeCache(String playerName) {
        cacheMap.remove(playerName);
    }
}

