/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.util.Vector
 */
package dev.zenhao.xin.utils;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.util.Vector;

public class VelocityTracker {
    private final long velocitizedTime;
    private final List<VelocityWrapper> velocities = new ArrayList<VelocityWrapper>();

    public void registerVelocity(Vector vector) {
        this.velocities.add(new VelocityWrapper(vector.getX(), vector.getY(), vector.getZ(), Math.hypot(vector.getX(), vector.getZ()), Math.abs(vector.getY()), System.currentTimeMillis()));
    }

    public void tick() {
        this.velocities.removeIf(velocity -> velocity.getTimestamp() + this.velocitizedTime < System.currentTimeMillis());
    }

    public double getHorizontal() {
        return Math.sqrt(this.velocities.parallelStream().mapToDouble(VelocityWrapper::getHorizontal).max().orElse(0.0));
    }

    public double getVertical() {
        return Math.sqrt(this.velocities.parallelStream().mapToDouble(VelocityWrapper::getVertical).max().orElse(0.0));
    }

    public boolean isVelocitized() {
        return this.velocities.size() != 0;
    }

    public VelocityTracker(long velocitizedTime) {
        this.velocitizedTime = velocitizedTime;
    }

    private class VelocityWrapper {
        private final double motionX;
        private final double motionY;
        private final double motionZ;
        private final double horizontal;
        private final double vertical;
        private final long timestamp;

        public VelocityWrapper(double motionX, double motionY, double motionZ, double horizontal, double vertical, long timestamp) {
            this.motionX = motionX;
            this.motionY = motionY;
            this.motionZ = motionZ;
            this.horizontal = horizontal;
            this.vertical = vertical;
            this.timestamp = timestamp;
        }

        public double getMotionX() {
            return this.motionX;
        }

        public double getMotionY() {
            return this.motionY;
        }

        public double getMotionZ() {
            return this.motionZ;
        }

        public double getHorizontal() {
            return this.horizontal;
        }

        public double getVertical() {
            return this.vertical;
        }

        public long getTimestamp() {
            return this.timestamp;
        }
    }
}

