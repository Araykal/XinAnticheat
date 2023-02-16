/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.ProtocolLibrary
 *  com.comphenix.protocol.ProtocolManager
 *  org.bukkit.Bukkit
 *  org.bukkit.ChatColor
 *  org.bukkit.plugin.Plugin
 *  org.bukkit.plugin.PluginManager
 *  org.bukkit.plugin.java.JavaPlugin
 */
package dev.zenhao.xin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import dev.zenhao.xin.listeners.EventHandler;
import dev.zenhao.xin.manager.UserManager;
import dev.zenhao.xin.manager.ViolationManager;
import dev.zenhao.xin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main
extends JavaPlugin {
    public static Main INSTANCE;
    public PluginManager pluginManager = this.getServer().getPluginManager();
    public UserManager Usermanager;
    ProtocolManager protocolManager;
    private CopyOnWriteArrayList<ViolationManager> violationManagers;

    public static void sendToMainThread(Runnable runnable) {
        Bukkit.getScheduler().runTask((Plugin)Main.getPlugin(Main.class), runnable);
    }

    public void registerViolationManager(ViolationManager manager) {
        if (this.violationManagers.contains(manager)) {
            return;
        }
        this.violationManagers.add(manager);
    }

    public UserManager getUserManager() {
        return this.Usermanager;
    }

    public void onLoad() {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    public void onEnable() {
        try {
            INSTANCE = this;
            System.out.println(ChatColor.YELLOW + "\u6210\u529f\u542f\u7528" + ChatColor.RED + "XinAnticheat");
            this.Usermanager = new UserManager(this);
            new Utils(this);
            this.violationManagers = new CopyOnWriteArrayList();
            this.saveDefaultConfig();
            new EventHandler(this);
            ScheduledExecutorService service = Executors.newScheduledThreadPool(4);
            service.scheduleAtFixedRate(() -> this.violationManagers.forEach(ViolationManager::decrementAll), 0L, 1L, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean getConfigBoolean(String path) {
        return this.getConfig().getBoolean(path);
    }

    public String getConfigString(String path) {
        return this.getConfig().getString(path);
    }

    public int getConfigInt(String path) {
        return this.getConfig().getInt(path);
    }
}

