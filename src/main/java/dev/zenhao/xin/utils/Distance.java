/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 */
package dev.zenhao.xin.utils;

import org.bukkit.Location;

public class Distance {
    private final double l1Y;
    private final double l2Y;
    private final double XDiff;
    private final double YDiff;
    private final double ZDiff;
    private final Location from;
    private final Location to;

    public Distance(Location from, Location to) {
        this.l1Y = to.getY();
        this.l2Y = from.getY();
        this.XDiff = Math.abs(to.getX() - from.getX());
        this.ZDiff = Math.abs(to.getZ() - from.getZ());
        this.YDiff = Math.abs(this.l1Y - this.l2Y);
        this.from = from;
        this.to = to;
    }

    public Distance() {
        this.l1Y = 0.0;
        this.l2Y = 0.0;
        this.XDiff = 0.0;
        this.ZDiff = 0.0;
        this.YDiff = 0.0;
        this.from = null;
        this.to = null;
    }

    public double fromY() {
        return this.l2Y;
    }

    public double toY() {
        return this.l1Y;
    }

    public double getXDifference() {
        return this.XDiff;
    }

    public double getZDifference() {
        return this.ZDiff;
    }

    public double getYDifference() {
        return this.YDiff;
    }

    public Location getFrom() {
        return this.from;
    }

    public Location getTo() {
        return this.to;
    }
}

