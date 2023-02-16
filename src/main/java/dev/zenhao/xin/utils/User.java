/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Bukkit
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 */
package dev.zenhao.xin.utils;

import dev.zenhao.xin.manager.MovementManager;
import dev.zenhao.xin.utils.VelocityTracker;
import java.sql.Timestamp;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class User {
    private final UUID uuid;
    private final String name;
    private final int id;
    private final VelocityTracker velocityTracker;
    private final MovementManager movementManager;
    private final int lastPing = -1;
    private final int ping = -1;
    private int level = 0;
    private static Location goodLocation;
    private int toX;
    private int toY;
    private int toZ;
    private String[] messages = new String[2];
    private Long[] messageTimes = new Long[2];
    private String[] commands = new String[2];
    private Long[] commandTimes = new Long[2];
    private boolean isWaitingOnLevelSync;
    private Timestamp levelSyncTimestamp;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.name = this.getPlayer() != null && this.getPlayer().isOnline() ? this.getPlayer().getName() : "";
        this.id = this.getPlayer() != null && this.getPlayer().isOnline() ? this.getPlayer().getEntityId() : -1;
        this.movementManager = new MovementManager();
        this.velocityTracker = new VelocityTracker(1500L);
        this.setIsWaitingOnLevelSync(true);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer((UUID)this.uuid);
    }

    public int getLevel() {
        return this.level;
    }

    public void decreaseLevel() {
        this.level = this.level != 0 ? this.level - 1 : 0;
    }

    public static Location getGoodLocation(Location location) {
        if (goodLocation == null || !location.getWorld().equals(goodLocation.getWorld()) || location.distance(goodLocation) > 4.0) {
            return location;
        }
        Location moveTo = goodLocation;
        moveTo.setPitch(location.getPitch());
        moveTo.setYaw(location.getYaw());
        return moveTo;
    }

    public boolean setGoodLocation(Location location) {
        goodLocation = location;
        return true;
    }

    public void setTo(double x, double y, double z) {
        this.toX = (int)x;
        this.toY = (int)y;
        this.toZ = (int)z;
    }

    public boolean checkTo(double x, double y, double z) {
        return (int)x == this.toX && (int)y == this.toY && (int)z == this.toZ;
    }

    public void addMessage(String message) {
        this.addToSpamLog(message, this.messages, this.messageTimes);
    }

    public void addCommand(String command) {
        this.addToSpamLog(command, this.commands, this.commandTimes);
    }

    private void addToSpamLog(String string, String[] messages, Long[] times) {
        messages[1] = messages[0];
        messages[0] = string;
        times[1] = times[0];
        times[0] = System.currentTimeMillis();
    }

    public String getMessage(int index) {
        return this.messages[index];
    }

    public String getCommand(int index) {
        return this.commands[index];
    }

    public Long getMessageTime(int index) {
        return this.messageTimes[index];
    }

    public Long getCommandTime(int index) {
        return this.commandTimes[index];
    }

    public void clearMessages() {
        this.messages = new String[2];
        this.messageTimes = new Long[2];
    }

    public void clearCommands() {
        this.commands = new String[2];
        this.commandTimes = new Long[2];
    }

    public Long getLastMessageTime() {
        return this.getMessageTime(0) == null ? -1L : this.getMessageTime(0);
    }

    public Long getLastCommandTime() {
        return this.getCommandTime(0) == null ? -1L : this.getCommandTime(0);
    }

    public void setIsWaitingOnLevelSync(boolean b) {
        this.isWaitingOnLevelSync = b;
    }

    public boolean isWaitingOnLevelSync() {
        return this.isWaitingOnLevelSync;
    }

    public Timestamp getLevelSyncTimestamp() {
        return this.levelSyncTimestamp;
    }

    public void setLevelSyncTimestamp(Timestamp timestamp) {
        this.levelSyncTimestamp = timestamp;
    }

    public String toString() {
        return "User {name = " + this.getName() + ", level = " + this.level + "}";
    }

    public VelocityTracker getVelocityTracker() {
        return this.velocityTracker;
    }

    public MovementManager getMovementManager() {
        return this.movementManager;
    }
}

