/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.block.Block
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package dev.zenhao.xin.utils;

import dev.zenhao.xin.manager.CheckManager;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class RotationUtil {
    public static CheckManager onStrictRotationYaw(Block pos, Player player, float offset) {
        double playerYaw;
        Location eyeLocation = player.getEyeLocation();
        double yawDifference = RotationUtil.calculateYawDifference(eyeLocation, pos.getLocation());
        double angleDifference = Math.abs(180.0 - Math.abs(Math.abs(yawDifference - (playerYaw = (double)player.getEyeLocation().getYaw())) - 180.0));
        if ((float)Math.round(angleDifference) > offset) {
            return new CheckManager(CheckManager.Result.FAILED, "Rotation", player.getName() + " failed aiming (Yaw) + val: " + angleDifference);
        }
        return new CheckManager(CheckManager.Result.PASSED);
    }

    public static CheckManager onStrictRotationPitch(Block pos, Player player, float offset) {
        double playerPitch;
        Location eyeLocation = player.getEyeLocation();
        double pitchDifference = RotationUtil.calculatePitchDifference(eyeLocation, pos.getLocation());
        double angleDifference = Math.abs(90.0 - Math.abs(Math.abs(pitchDifference - (playerPitch = (double)player.getEyeLocation().getPitch())) - 90.0));
        if ((float)Math.round(angleDifference) > offset) {
            return new CheckManager(CheckManager.Result.FAILED, "Rotation", player.getName() + " failed aiming (Pitch) + val: " + angleDifference);
        }
        return new CheckManager(CheckManager.Result.PASSED);
    }

    public static CheckManager onStrictRotationYaw(Entity entity, Player player, float offset) {
        double playerYaw;
        Location eyeLocation = player.getEyeLocation();
        double yawDifference = RotationUtil.calculateYawDifference(eyeLocation, entity.getLocation());
        double angleDifference = Math.abs(180.0 - Math.abs(Math.abs(yawDifference - (playerYaw = (double)player.getEyeLocation().getYaw())) - 180.0));
        if ((float)Math.round(angleDifference) > offset) {
            return new CheckManager(CheckManager.Result.FAILED, "Rotation", player.getName() + " failed aiming (Yaw) + val: " + angleDifference);
        }
        return new CheckManager(CheckManager.Result.PASSED);
    }

    public static CheckManager onStrictRotationPitch(Entity entity, Player player, float offset) {
        double playerPitch;
        Location eyeLocation = player.getEyeLocation();
        double pitchDifference = RotationUtil.calculatePitchDifference(eyeLocation, entity.getLocation());
        double angleDifference = Math.abs(90.0 - Math.abs(Math.abs(pitchDifference - (playerPitch = (double)player.getEyeLocation().getPitch())) - 90.0));
        if ((float)Math.round(angleDifference) > offset) {
            return new CheckManager(CheckManager.Result.FAILED, "Rotation", player.getName() + " failed aiming (Pitch) + val: " + angleDifference);
        }
        return new CheckManager(CheckManager.Result.PASSED);
    }

    public static float calculateYawDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));
        return clonedFrom.getYaw();
    }

    public static float calculatePitchDifference(Location from, Location to) {
        Location clonedFrom = from.clone();
        Vector startVector = clonedFrom.toVector();
        Vector targetVector = to.toVector();
        clonedFrom.setDirection(targetVector.subtract(startVector));
        return clonedFrom.getPitch();
    }

    public static float[] getLegitRotations(Location vec, Player player) {
        Location eyesPos = RotationUtil.getEyesPos(player);
        double diffX = vec.getX() - eyesPos.getX();
        double diffY = vec.getY() - eyesPos.getY();
        double diffZ = vec.getZ() - eyesPos.getZ();
        double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan2(diffY, diffXZ)));
        return new float[]{player.getLocation().getYaw() + RotationUtil.wrapDegrees(yaw - player.getLocation().getYaw()), player.getLocation().getPitch() + RotationUtil.wrapDegrees(pitch - player.getLocation().getPitch())};
    }

    public static float wrapDegrees(float angle) {
        if ((angle %= 360.0f) >= 180.0f) {
            angle -= 360.0f;
        }
        if (angle < -180.0f) {
            angle += 360.0f;
        }
        return angle;
    }

    public static Location getEyesPos(Player player) {
        return new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY() + player.getEyeHeight(), player.getLocation().getZ());
    }
}

