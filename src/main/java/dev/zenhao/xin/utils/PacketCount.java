/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.entity.Player
 */
package dev.zenhao.xin.utils;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.entity.Player;

public class PacketCount {
    public static HashMap<Player, Integer> countPos = new HashMap();
    public static HashMap<Player, Integer> countPosLook = new HashMap();

    public static void IncreaseCountPos(Player player) {
        if (countPos.containsKey(player)) {
            int countPosing = countPos.get(player);
            countPos.put(player, ++countPosing);
        } else {
            countPos.put(player, 1);
        }
    }

    public static void IncreaseCountPosLook(Player player) {
        if (countPosLook.containsKey(player)) {
            int countPosing = countPosLook.get(player);
            countPosLook.put(player, ++countPosing);
        } else {
            countPosLook.put(player, 1);
        }
    }

    public static synchronized void PacketCounts() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    countPos.clear();
                    countPosLook.clear();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 1000L);
    }
}

