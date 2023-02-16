/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.md_5.bungee.api.ChatColor
 *  net.md_5.bungee.api.chat.BaseComponent
 *  net.md_5.bungee.api.chat.ClickEvent
 *  net.md_5.bungee.api.chat.ClickEvent$Action
 *  net.md_5.bungee.api.chat.ComponentBuilder
 *  net.md_5.bungee.api.chat.HoverEvent
 *  net.md_5.bungee.api.chat.HoverEvent$Action
 *  net.md_5.bungee.api.chat.TextComponent
 *  net.minecraft.server.v1_12_R1.EntityLiving
 *  net.minecraft.server.v1_12_R1.EntityPlayer
 *  net.minecraft.server.v1_12_R1.Packet
 *  net.minecraft.server.v1_12_R1.PacketPlayInUseItem
 *  net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.Chunk
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.Particle
 *  org.bukkit.block.Block
 *  org.bukkit.command.CommandSender
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.entity.Player
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.utils;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.ChatUtils;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.EntityLiving;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayInUseItem;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Utils {
    public static Main plugin;

    public Utils(Main plugin) {
        Utils.plugin = plugin;
    }

    public static double getTps() {
        return Math.round(Bukkit.getServer().getTPS()[0]);
    }

    public static org.bukkit.ChatColor getTPSColor(String input) {
        if (!input.equals("*20")) {
            double i = Double.parseDouble(input);
            if (i >= 18.0) {
                return org.bukkit.ChatColor.GREEN;
            }
            return i >= 13.0 ? org.bukkit.ChatColor.YELLOW : org.bukkit.ChatColor.RED;
        }
        return org.bukkit.ChatColor.GREEN;
    }

    public static String Prefix() {
        return "[&d&lMelon&r&b&lCore&r] ";
    }

    public static String noPersmission() {
        return ChatUtils.TranslateToColorChat(Utils.Prefix() + "&c&l\u4f60\u4e0d\u80fd\u8fd9\u4e48\u505a!");
    }

    public static void genParticle(Player player) {
        for (int i = 0; i < 10000; ++i) {
            player.spawnParticle(Particle.EXPLOSION_HUGE, player.getLocation(), Integer.MAX_VALUE, 1.0, 1.0, 1.0);
            player.spawnParticle(Particle.EXPLOSION_LARGE, player.getLocation(), Integer.MAX_VALUE, 1.0, 1.0, 1.0);
            player.spawnParticle(Particle.TOTEM, player.getLocation(), Integer.MAX_VALUE, 1.0, 1.0, 1.0);
            player.spawnParticle(Particle.NOTE, player.getLocation(), Integer.MAX_VALUE, 1.0, 1.0, 1.0);
        }
    }

    public void fakePacket(Player p) {
        EntityPlayer nmsPlayer = ((CraftPlayer)p).getHandle();
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving((EntityLiving)nmsPlayer);
        PacketPlayInUseItem packet2 = new PacketPlayInUseItem();
        nmsPlayer.playerConnection.sendPacket((Packet)packet);
        nmsPlayer.playerConnection.sendPacket((Packet)packet2);
    }

    public static boolean isHoveringOverWater(Location player, int blocks) {
        for (int i = player.getBlockY(); i > player.getBlockY() - blocks; --i) {
            Block newloc = new Location(player.getWorld(), (double)player.getBlockX(), (double)i, (double)player.getBlockZ()).getBlock();
            if (newloc.getType() == Material.AIR) continue;
            return newloc.isLiquid();
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player) {
        return Utils.isHoveringOverWater(player, 25);
    }

    public static void sendMessage(Player player, String string) {
        player.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)string));
    }

    public static void sendMessage(CommandSender sender, String string) {
        sender.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)string));
    }

    public static void kickPlayer(Player player, String string) {
        player.kickPlayer(org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)string));
    }

    public static void teleportPlayer(Player player, int x, int y, int z) {
        player.teleport(new Location(player.getWorld(), (double)x, (double)y, (double)z));
    }

    public static void teleportPlayer(Player player, double x, double y, double z) {
        player.teleport(new Location(player.getWorld(), x, y, z));
    }

    public static void sendClickableMessage(Player player, String message, String hoverText, String cmd, ClickEvent.Action action) {
        TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes((char)'&', (String)message));
        msg.setClickEvent(new ClickEvent(action, cmd));
        msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.translateAlternateColorCodes((char)'&', (String)hoverText)).create()));
        player.spigot().sendMessage((BaseComponent)msg);
    }

    public static List<String> runSysCommand(String command) {
        try {
            String line;
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            ArrayList<String> output = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                output.add(line);
            }
            reader.close();
            return output;
        }
        catch (IOException ignored) {
            return null;
        }
    }

    public static void sendOpMessage(String message) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (!online.isOp()) continue;
            online.sendMessage(org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)message));
        }
    }

    public static Player getNearbyPlayer(int i, Location loc) {
        Player plrs = null;
        Iterator iterator = loc.getNearbyPlayers((double)i).iterator();
        while (iterator.hasNext()) {
            Player nearby;
            plrs = nearby = (Player)iterator.next();
        }
        return plrs;
    }

    public static String getFormattedInterval(long ms) {
        long seconds = ms / 1000L % 60L;
        long minutes = ms / 60000L % 60L;
        long hours = ms / 3600000L % 24L;
        long days = ms / 86400000L;
        return String.format("%dd %02dh %02dm %02ds", days, hours, minutes, seconds);
    }

    public static void deleteFortressDat(String worldName) {
        Utils.println(Utils.Prefix() + "&aStarting to delete files that cause memory issues...");
        String nether = worldName.concat("_nether");
        String end = worldName.concat("_the_end");
        File fortress = new File(nether + "/data/Fortress.dat");
        File villagesNether = new File(nether + "/data/villages_nether.dat");
        if (fortress.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + fortress.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + fortress.getPath());
        }
        if (villagesNether.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + villagesNether.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + villagesNether.getPath());
        }
        File endCity = new File(end + "/data/EndCity.dat");
        File villagesEnd = new File(end + "/data/villages_end.dat");
        if (endCity.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + endCity.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + endCity.getPath());
        }
        if (villagesEnd.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + villagesEnd.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + villagesEnd.getPath());
        }
        File village = new File(worldName + "/data/Village.dat");
        File villages = new File(worldName + "/data/villages.dat");
        if (village.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + village.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + village.getPath());
        }
        if (villages.delete()) {
            Utils.println(Utils.Prefix() + "&eDeleted file " + villages.getName());
        } else {
            Utils.println(Utils.Prefix() + "&cCould not find file " + villages.getPath());
        }
        Utils.println(Utils.Prefix() + "&aDeletion process complete!");
    }

    public static void cockRunMcCommand(String cmd) {
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), cmd);
    }

    public static void println(String message) {
        System.out.println(org.bukkit.ChatColor.translateAlternateColorCodes((char)'&', (String)message));
    }

    public static void sendPlayerToServer(Player player, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Connect");
            out.writeUTF(server);
            player.sendPluginMessage((Plugin)plugin, "BungeeCord", b.toByteArray());
            b.close();
            out.close();
        }
        catch (Error | Exception e) {
            player.sendMessage(org.bukkit.ChatColor.RED + "Error when trying to connect to " + server);
        }
    }

    public static int countBlockPerChunk(Chunk chunk, Material lookingFor) {
        int count = 0;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 256; ++y) {
                    if (chunk.getBlock(x, y, z).getType() != lookingFor) continue;
                    ++count;
                }
            }
        }
        return count;
    }

    public static void changeBlockInChunk(Chunk chunk, Material target, Material to) {
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                for (int y = 0; y < 256; ++y) {
                    if (chunk.getBlock(x, y, z).getType() != target) continue;
                    chunk.getBlock(x, y, z).setType(to);
                }
            }
        }
    }

    public static void secondPass(HashMap<Player, Integer> hashMap) {
        for (Map.Entry<Player, Integer> violationEntry : hashMap.entrySet()) {
            if (violationEntry.getValue() <= 0) continue;
            violationEntry.setValue(violationEntry.getValue() - 1);
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer((UUID)Bukkit.getPlayer((String)"").getUniqueId());
    }
}

