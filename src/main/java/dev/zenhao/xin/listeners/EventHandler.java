/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 */
package dev.zenhao.xin.listeners;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.listeners.EventProcessor;
import dev.zenhao.xin.listeners.combat.AntiWeakness;
import dev.zenhao.xin.listeners.combat.AttackFrequency;
import dev.zenhao.xin.listeners.combat.CrystalStrict;
import dev.zenhao.xin.listeners.exploit.LagInventory;
import dev.zenhao.xin.listeners.exploit.Nuker;
import dev.zenhao.xin.listeners.inventory.InventoryClickCount;
import dev.zenhao.xin.listeners.inventory.InventoryMove;
import dev.zenhao.xin.listeners.misc.ChunkLoadLimit;
import dev.zenhao.xin.listeners.misc.SkylightUpdate;
import dev.zenhao.xin.listeners.movement.BoatSpeed;
import dev.zenhao.xin.listeners.movement.FastWeb;
import dev.zenhao.xin.listeners.movement.PacketElytraFly;
import dev.zenhao.xin.listeners.movement.PhaseCheck;
import dev.zenhao.xin.listeners.movement.VoidDamage;
import dev.zenhao.xin.listeners.packet.PacketFlyCheck;
import dev.zenhao.xin.listeners.player.OffhandCrash;
import dev.zenhao.xin.listeners.timer.TimerA;
import dev.zenhao.xin.listeners.timer.TimerB;
import dev.zenhao.xin.listeners.timer.TimerC;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class EventHandler {
    Main main;

    public EventHandler(Main main) {
        this.main = main;
        PluginManager pluginManager = main.pluginManager;
        pluginManager.registerEvents((Listener)new EventProcessor(), (Plugin)main);
        pluginManager.registerEvents((Listener)new InventoryClickCount(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new PacketElytraFly(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new AttackFrequency(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new ChunkLoadLimit(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new SkylightUpdate(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new PacketFlyCheck(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new CrystalStrict(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new InventoryMove(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new OffhandCrash(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new AntiWeakness(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new LagInventory(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new PhaseCheck(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new VoidDamage(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new BoatSpeed(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new FastWeb(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new TimerA(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new TimerB(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new TimerC(main), (Plugin)main);
        pluginManager.registerEvents((Listener)new Nuker(main), (Plugin)main);
    }
}

