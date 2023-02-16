/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 */
package dev.zenhao.xin.command.commands;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.command.BaseTabCommands;
import dev.zenhao.xin.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;

public class BaseCommand
extends BaseTabCommands {
    public BaseCommand() {
        super("core", "/core reload | version", "bukkit.command.help", "Base command of the plugin");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        if (sender.isOp()) {
            switch (args[0]) {
                case "reload": {
                    plugin.reloadConfig();
                    Utils.sendMessage(sender, Utils.Prefix() + "&aReloaded configuration file");
                    break;
                }
                case "version": {
                    this.sendMessage(sender, Utils.Prefix() + "&6Version &r&c" + plugin.getDescription().getVersion());
                }
            }
        }
    }

    @Override
    public List<String> onTab(String[] args) {
        if (args.length == 1) {
            ArrayList<String> autoCompletes = new ArrayList<String>();
            autoCompletes.add("reload");
            autoCompletes.add("version");
            return autoCompletes;
        }
        return null;
    }
}

