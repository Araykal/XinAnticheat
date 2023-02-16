/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.command.Command
 *  org.bukkit.command.CommandExecutor
 *  org.bukkit.command.CommandSender
 *  org.bukkit.command.TabExecutor
 *  org.bukkit.entity.Player
 *  org.jetbrains.annotations.NotNull
 */
package dev.zenhao.xin.command;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.command.BaseCommands;
import dev.zenhao.xin.command.BaseTabCommands;
import dev.zenhao.xin.command.commands.BaseCommand;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandHandler
implements TabExecutor {
    private final ArrayList<BaseCommands> commands = new ArrayList();
    private final Main plugin;

    public CommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        try {
            this.addCommand(new BaseCommand());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCommand(BaseCommands command) {
        this.commands.add(command);
        Objects.requireNonNull(this.plugin.getCommand(command.getName())).setExecutor((CommandExecutor)this);
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        for (BaseCommands command : new ArrayList<BaseCommands>(this.commands)) {
            if (!command.getName().equalsIgnoreCase(cmd.getName())) continue;
            if (!sender.hasPermission(command.getPermission())) break;
            command.execute(sender, args, this.plugin);
            break;
        }
        return true;
    }

    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, String[] args) {
        for (BaseCommands command : new ArrayList<BaseCommands>(this.commands)) {
            try {
                if (!command.getName().equalsIgnoreCase(cmd.getName())) continue;
                if (command instanceof BaseTabCommands) {
                    BaseTabCommands tabCommand = (BaseTabCommands)command;
                    return tabCommand.onTab(args);
                }
                ArrayList<String> players = new ArrayList<String>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.add(player.getName());
                }
                return players;
            }
            catch (Exception exception) {
            }
        }
        return Collections.singletonList("Not a tab command");
    }

    public ArrayList<BaseCommands> getCommands() {
        return this.commands;
    }
}

