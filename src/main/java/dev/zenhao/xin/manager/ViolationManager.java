/*
 * Decompiled with CFR 0.152.
 */
package dev.zenhao.xin.manager;

import dev.zenhao.xin.Main;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ViolationManager {
    private final ConcurrentHashMap<UUID, Integer> map;
    private final int addAmount;
    private final int reduceAmount;

    public ViolationManager(int addAmount, int reduceAmount) {
        this.addAmount = addAmount;
        this.reduceAmount = reduceAmount;
        this.map = new ConcurrentHashMap();
        ((Main)Main.getPlugin(Main.class)).registerViolationManager(this);
    }

    public void decrementAll() {
        this.map.forEach((key, val) -> {
            if (val <= 0) {
                this.map.remove(key);
                return;
            }
            this.map.replace((UUID)key, val - this.reduceAmount);
        });
    }

    public void increment(UUID uuid) {
        if (!this.map.containsKey(uuid)) {
            this.map.put(uuid, 0);
        } else {
            this.map.replace(uuid, this.map.get(uuid) + this.addAmount);
        }
    }

    public int getVLS(UUID id) {
        return this.map.getOrDefault(id, -1);
    }

    public void remove(UUID id) {
        this.map.remove(id);
    }
}

