/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandSender
 *  org.bukkit.entity.Player
 */
package dev.zenhao.xin.command;

import dev.zenhao.xin.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class BaseCommands {
    public final String CONSOLE_ONLY = "This command is console only";
    public final String PLAYER_ONLY = "This command is player only";
    public Command cmd;
    public String name;
    public String usage;
    public String permission;
    private String description;
    private String[] subCommands;

    public BaseCommands(String name, String usage, String permission) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
    }

    public BaseCommands(String name, Command cmd, String usage, String permission) {
        this.name = name;
        this.cmd = cmd;
        this.usage = usage;
        this.permission = permission;
    }

    public BaseCommands(String name, String usage, String permission, String description) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.description = description;
    }

    public BaseCommands(String name, String usage, String permission, String description, String[] subCommands) {
        this.name = name;
        this.usage = usage;
        this.permission = permission;
        this.description = description;
        this.subCommands = subCommands;
    }

    public String getName() {
        return this.name;
    }

    public Command getCommand() {
        return this.cmd;
    }

    public String getUsage() {
        return this.usage;
    }

    public String getPermission() {
        return this.permission;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getSubCommands() {
        return this.subCommands;
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)message));
    }

    public void sendNoPermission(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes((char)'&', (String)("&4Error:&r&c You are lacking the permission " + this.getPermission())));
    }

    public void sendErrorMessage(CommandSender sender, String message) {
        String finalMessage = "&4Error:&r&c " + message;
        finalMessage = ChatColor.translateAlternateColorCodes((char)'&', (String)finalMessage);
        sender.sendMessage(finalMessage);
    }

    public Player getSenderAsPlayer(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player)sender;
        }
        return null;
    }

    public abstract void execute(CommandSender var1, String[] var2, Main var3);
}

