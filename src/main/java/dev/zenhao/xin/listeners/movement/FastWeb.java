/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.PacketType
 *  com.comphenix.protocol.PacketType$Play$Client
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  com.comphenix.protocol.events.ListenerPriority
 *  com.comphenix.protocol.events.PacketAdapter
 *  com.comphenix.protocol.events.PacketEvent
 *  com.comphenix.protocol.events.PacketListener
 *  org.bukkit.Bukkit
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.event.EventHandler
 *  org.bukkit.event.Listener
 *  org.bukkit.event.block.BlockPlaceEvent
 *  org.bukkit.event.player.PlayerInteractEvent
 *  org.bukkit.event.player.PlayerMoveEvent
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.movement;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import dev.zenhao.xin.utils.RandomUtil;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class FastWeb
implements Listener {
    public static FastWeb INSTANCE;
    public static ConcurrentHashMap<Player, Integer> placeCount;
    public static ConcurrentHashMap<String, Integer> targetMap;
    public static ConcurrentHashMap<Player, Integer> countLook;
    public static ConcurrentHashMap<Player, Integer> countPos;
    public static CopyOnWriteArrayList<String> playerList;
    public static CopyOnWriteArrayList<String> strictList;
    public static int click;
    public static int eat;
    Main main;

    public FastWeb(Main main) {
        this.main = main;
        INSTANCE = this;
        targetMap.clear();
        playerList.clear();
        strictList.clear();
        countPos.clear();
        countLook.clear();
        eat = 25;
        click = 55;
        FastWeb.PacketCounts();
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    placeCount.clear();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 3000L);
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.USE_ITEM}){

            public void onPacketReceiving(PacketEvent event) {
                placeCount.forEach((p, c) -> {
                    if (p.getName().equals(event.getPlayer().getName()) && c >= 6) {
                        event.setCancelled(true);
                    }
                });
                playerList.forEach(player -> {
                    if (event.getPlayer().getName().equals(player)) {
                        event.setCancelled(true);
                    }
                });
            }
        });
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                FastWeb.IncreaseCount(event.getPlayer());
                strictList.forEach(player -> {
                    if (event.getPlayer().getName().equals(player) && countPos.get(event.getPlayer()) >= 18) {
                        FlagUtil.flagWithEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation(), event, true);
                    }
                });
            }
        });
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}){

            public void onPacketReceiving(PacketEvent event) {
                FastWeb.IncreaseCountLook(event.getPlayer());
                strictList.forEach(player -> {
                    if (event.getPlayer().getName().equals(player) && countLook.get(event.getPlayer()) >= 18) {
                        FlagUtil.flagWithEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation(), event, true);
                    }
                });
            }
        });
    }

    public static synchronized void PacketCounts() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    countPos.clear();
                    countLook.clear();
                    strictList.clear();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 500L);
    }

    public static void IncreaseCount(Player player) {
        try {
            if (countPos.containsKey(player)) {
                int counting = countPos.get(player);
                countPos.put(player, ++counting);
            } else {
                countPos.put(player, 1);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void IncreaseCountLook(Player player) {
        try {
            if (countLook.containsKey(player)) {
                int counting = countLook.get(player);
                countLook.put(player, ++counting);
            } else {
                countLook.put(player, 1);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void checkInvalidSpeed(PlayerMoveEvent event, Player player, double x, double y, double z, double yStrict) {
        double max;
        Block down = player.getLocation().clone().subtract(0.0, -0.05, 0.0).getBlock();
        if (y > 0.0 && (player.getLocation().add(0.0, -1.0, 0.0).getBlock().getType().equals((Object)Material.LEGACY_WEB) || player.getLocation().add(0.0, 1.0, 0.0).getBlock().getType().equals((Object)Material.LEGACY_WEB) || player.getLocation().getBlock().getType().equals((Object)Material.LEGACY_WEB)) && y - (max = 0.04) > 0.0) {
            FlagUtil.flagNoEvent((CraftPlayer)player, player.getLocation().getX(), event.getFrom().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            FastWeb.onAdd(player);
            return;
        }
        if (y < 0.0 && (player.getLocation().add(0.0, y, 0.0).getBlock().getType().equals((Object)Material.LEGACY_WEB) || player.getLocation().getBlock().getType().equals((Object)Material.LEGACY_WEB))) {
            max = 0.08;
            if (Math.abs(y) > max + 6.0E-9) {
                FlagUtil.flagNoEvent((CraftPlayer)player, player.getLocation().getX() - x, event.getFrom().getY(), player.getLocation().getZ() - z, player.getLocation().getYaw(), player.getLocation().getPitch());
                FastWeb.onAdd(player);
                return;
            }
        }
        if (down.getType().equals((Object)Material.LEGACY_WEB) && !player.getLocation().getBlock().getType().equals((Object)Material.LEGACY_WEB)) {
            FlagUtil.flagNoEvent((CraftPlayer)player, player.getLocation().getX(), event.getTo().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
            FastWeb.onAdd(player);
            return;
        }
        if (player.getLocation().getY() < yStrict && player.getLocation().getBlock().getType().equals((Object)Material.LEGACY_WEB)) {
            if (!strictList.contains(player.getName())) {
                strictList.add(player.getName());
            }
            return;
        }
        targetMap.remove(player.getName());
        playerList.remove(player.getName());
        strictList.remove(player.getName());
    }

    public static void onAdd(Player player) {
        if (!targetMap.containsKey(player.getName())) {
            targetMap.put(player.getName(), 0);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (this.main.getConfigBoolean("AntiCheat.ThreadAsync")) {
            Bukkit.getScheduler().runTaskLaterAsynchronously((Plugin)this.main, () -> FastWeb.checkInvalidSpeed(event, event.getPlayer(), event.getTo().getX() - event.getFrom().getX(), event.getTo().getY() - event.getFrom().getY(), event.getTo().getZ() - event.getFrom().getZ(), event.getFrom().getY()), 2L);
        } else {
            FastWeb.checkInvalidSpeed(event, event.getPlayer(), event.getTo().getX() - event.getFrom().getX(), event.getTo().getY() - event.getFrom().getY(), event.getTo().getZ() - event.getFrom().getZ(), event.getFrom().getY());
        }
    }

    public static void IncreasePacket(Player player) {
        if (placeCount.containsKey(player)) {
            int countPosing = placeCount.get(player);
            placeCount.put(player, ++countPosing);
        } else {
            placeCount.put(player, 1);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if (event.getBlockPlaced().getType().equals((Object)Material.LEGACY_WEB)) {
            FastWeb.IncreasePacket(event.getPlayer());
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        try {
            targetMap.forEach((player, a) -> {
                if (player.equals(event.getPlayer().getName()) && (event.getItem() != null && event.getItem().getType().equals((Object)Material.GOLDEN_APPLE) || event.getItem().getType().equals((Object)Material.CHORUS_FRUIT)) && (a = Integer.valueOf(RandomUtil.nextInt(0, 100))) <= eat) {
                    if (!playerList.contains(player)) {
                        playerList.add((String)player);
                    }
                    event.setCancelled(true);
                }
            });
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    static {
        placeCount = new ConcurrentHashMap();
        targetMap = new ConcurrentHashMap();
        countLook = new ConcurrentHashMap();
        countPos = new ConcurrentHashMap();
        playerList = new CopyOnWriteArrayList();
        strictList = new CopyOnWriteArrayList();
    }
}

