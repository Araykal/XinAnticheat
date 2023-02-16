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
 *  org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer
 *  org.bukkit.event.Listener
 *  org.bukkit.plugin.Plugin
 */
package dev.zenhao.xin.listeners.timer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.FlagUtil;
import dev.zenhao.xin.utils.PacketCount;
import java.util.Timer;
import java.util.TimerTask;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class TimerA
implements Listener {
    public static TimerA INSTANCE;
    public Main main;
    public int val;
    public int val2;

    public TimerA(Main main) {
        this.main = main;
        INSTANCE = this;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
                try {
                    PacketCount.countPosLook.clear();
                    PacketCount.countPos.clear();
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }, 0L, 1000L);
        this.val = main.getConfigInt("AntiCheat.MaxPacket");
        this.val2 = main.getConfigInt("AntiCheat.MaxPacketPosLook");
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION}){

            public void onPacketReceiving(PacketEvent event) {
                TimerA.this.onInavlidMoving(event);
            }
        });
        protocolManager.addPacketListener((PacketListener)new PacketAdapter((Plugin)main, ListenerPriority.HIGHEST, new PacketType[]{PacketType.Play.Client.POSITION_LOOK}){

            public void onPacketReceiving(PacketEvent event) {
                TimerA.this.onInavlidMovingRotation(event);
            }
        });
    }

    public void onInavlidMovingRotation(PacketEvent event) {
        try {
            if (this.main != null && this.main.getConfigBoolean("AntiCheat.AntiFastContracting")) {
                PacketCount.IncreaseCountPosLook(event.getPlayer());
                if (!event.getPlayer().isGliding() && !event.getPlayer().isInsideVehicle() && (float)PacketCount.countPosLook.get(event.getPlayer()).intValue() >= (float)this.val2 + 1.0f) {
                    FlagUtil.flagWithEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation(), event, true);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void onInavlidMoving(PacketEvent event) {
        try {
            if (this.main != null && this.main.getConfigBoolean("AntiCheat.AntiFastContracting")) {
                PacketCount.IncreaseCountPos(event.getPlayer());
                if (event.getPlayer().isGliding() || event.getPlayer().isInsideVehicle()) {
                    if ((double)PacketCount.countPos.get(event.getPlayer()).intValue() >= Math.floor((float)this.val * 2.0f)) {
                        event.getPlayer().setGliding(false);
                        if (event.getPlayer().isInsideVehicle()) {
                            event.getPlayer().getVehicle().eject();
                        }
                        FlagUtil.flagWithEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation(), event, true);
                    }
                } else if (PacketCount.countPos.get(event.getPlayer()) >= this.val) {
                    FlagUtil.flagWithEvent((CraftPlayer)event.getPlayer(), event.getPlayer().getLocation(), event, true);
                }
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }
}

