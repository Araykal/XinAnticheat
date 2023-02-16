/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.command.CommandSender
 *  org.bukkit.event.Listener
 */
package dev.zenhao.xin.command.commands;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.command.BaseTabCommands;
import dev.zenhao.xin.listeners.combat.CrystalStrict;
import dev.zenhao.xin.listeners.movement.FastWeb;
import dev.zenhao.xin.listeners.packet.PacketFlyCheck;
import dev.zenhao.xin.listeners.timer.TimerA;
import dev.zenhao.xin.listeners.timer.TimerB;
import dev.zenhao.xin.listeners.timer.TimerC;
import dev.zenhao.xin.utils.ChatUtils;
import dev.zenhao.xin.utils.Utils;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class AntiCheatModify
extends BaseTabCommands
implements Listener {
    public static AntiCheatModify INSTANCE = new AntiCheatModify();

    public AntiCheatModify() {
        super("anticheat", "/anticheat", "bukkit.command.help");
    }

    @Override
    public void execute(CommandSender sender, String[] args, Main plugin) {
        if (sender.isOp()) {
            try {
                switch (args[0]) {
                    case "TimerAPos": {
                        TimerA.INSTANCE.val = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lTimerAPos\u73b0\u5728\u7684\u503c\u4e3a" + TimerA.INSTANCE.val));
                        break;
                    }
                    case "TimerAPosLook": {
                        TimerA.INSTANCE.val2 = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lTimerAPosLook\u73b0\u5728\u7684\u503c\u4e3a" + TimerA.INSTANCE.val2));
                        break;
                    }
                    case "TimerB": {
                        TimerB.INSTANCE.cfg = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lTimerB\u73b0\u5728\u7684\u503c\u4e3a" + TimerB.INSTANCE.cfg));
                        break;
                    }
                    case "TimerCVal": {
                        TimerC.val = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lTimerCVal\u73b0\u5728\u7684\u503c\u4e3a" + TimerC.val));
                        break;
                    }
                    case "Teleport": {
                        PacketFlyCheck.INSTANCE.tp = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lPacketFly\u73b0\u5728\u7684\u503c\u4e3a" + PacketFlyCheck.INSTANCE.tp));
                        break;
                    }
                    case "FastWebClick": {
                        FastWeb.click = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lFastWeb Click\u73b0\u5728\u7684\u503c\u4e3a" + FastWeb.click));
                        break;
                    }
                    case "FastWebEat": {
                        FastWeb.eat = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lFastWeb Eat\u73b0\u5728\u7684\u503c\u4e3a" + FastWeb.eat));
                        break;
                    }
                    case "CrystalStrict": {
                        CrystalStrict.strictDelay = Integer.parseInt(args[1]);
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&l\u4fee\u6539\u6210\u529f! &d&lCrystalStrict\u73b0\u5728\u7684\u503c\u4e3a" + CrystalStrict.strictDelay));
                        break;
                    }
                    default: {
                        Utils.sendMessage(sender, ChatUtils.TranslateToColorChat("&c&lError Value!"));
                        break;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }

    @Override
    public List<String> onTab(String[] args) {
        CopyOnWriteArrayList<String> commands = new CopyOnWriteArrayList<String>();
        if (args.length == 1) {
            commands.add("inv");
            commands.add("TimerAPos");
            commands.add("TimerAPosLook");
            commands.add("TimerB");
            commands.add("TimerCVal");
            commands.add("Teleport");
            commands.add("FastWebClick");
            commands.add("FastWebEat");
            commands.add("CrystalStrict");
            commands.add("c");
            return commands;
        }
        return null;
    }
}

