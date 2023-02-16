/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.EventPriority
 *  org.bukkit.event.Listener
 *  org.bukkit.event.player.PlayerQuitEvent
 *  org.bukkit.event.player.PlayerSwapHandItemsEvent
 */
package dev.zenhao.xin.listeners.player;

import dev.zenhao.xin.Main;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class OffhandCrash
implements Listener {
    public static HashMap<String, Cool> s = new HashMap();
    Main main;

    public OffhandCrash(Main main) {
        this.main = main;
        OffhandCrash.ClearCheck();
    }

    public static synchronized void ClearCheck() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    OffhandCrash.s.forEach((s, cool) -> cool.clearClick());
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 1000L);
    }

    @EventHandler(priority=EventPriority.MONITOR)
    public void onSwap(PlayerSwapHandItemsEvent e) {
        Player p = e.getPlayer();
        Cool cool = s.get(p.getName());
        if (cool == null) {
            cool = new Cool();
            s.put(p.getName(), cool);
        }
        cool.addClick();
        if (cool.getClick() > 8) {
            cool.clearClick();
            p.kickPlayer("OFF Hand Packet Large!");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        try {
            OffhandCrash.s.forEach((s, cool) -> cool.clearClick());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static class Cool {
        private int click;

        public int getClick() {
            return this.click;
        }

        public void addClick() {
            ++this.click;
        }

        public void clearClick() {
            this.click = 0;
        }
    }
}

