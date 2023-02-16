/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.entity.Player
 *  org.bukkit.potion.PotionEffectType
 */
package dev.zenhao.xin.manager;

import dev.zenhao.xin.Main;
import dev.zenhao.xin.utils.Distance;
import dev.zenhao.xin.utils.User;
import dev.zenhao.xin.utils.Utilities;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class MovementManager {
    public int airTicks = 0;
    public int groundTicks = 0;
    public int iceTicks = 0;
    public int slimeTicks = 0;
    public int airTicksBeforeGrounded = 0;
    public int iceInfluenceTicks = 0;
    public int slimeInfluenceTicks = 0;
    public int soilInfluenceTicks = 0;
    public double motionY;
    public double lastMotionY;
    public double distanceXZ;
    public double distanceX;
    public double distanceZ;
    public double lastDistanceXZ;
    public double lastDistanceX;
    public double lastDistanceZ;
    public float deltaPitch;
    public float deltaYaw;
    public float lastDeltaPitch;
    public float lastDeltaYaw;
    public boolean touchedGroundThisTick = false;
    public Distance lastDistance = new Distance();
    public double acceleration;
    public double lastAcceleration;
    public boolean topSolid;
    public boolean bottomSolid;
    public boolean halfMovement;
    public boolean onGround;
    public boolean wasOnGround;
    public int halfMovementHistoryCounter = 0;
    public long lastTeleport;
    public int elytraEffectTicks;
    public double velocityExpectedMotionY;
    public double velocityExpectedMotionXZ;
    public int sneakingTicks;
    public int nearLiquidTicks;
    public int blockingTicks;
    public boolean hasSpeedEffect = false;
    public boolean hadSpeedEffect = false;
    public int riptideTicks;
    public long lastUpdate;
    public Location lastLocation;
    public float friction;
    public double adjusted;

    public void handle(Player player, Location from, Location to, Distance distance) {
        this.wasOnGround = this.onGround;
        this.onGround = player.isOnGround();
        this.lastLocation = from;
        double x = distance.getXDifference();
        double z = distance.getZDifference();
        this.lastDistanceXZ = this.distanceXZ;
        this.lastDistanceX = this.distanceX;
        this.lastDistanceZ = this.distanceZ;
        this.distanceXZ = Math.sqrt(x * x + z * z);
        this.distanceX = x;
        this.distanceZ = z;
        this.lastDeltaPitch = this.deltaPitch;
        this.lastDeltaYaw = this.deltaYaw;
        this.deltaPitch = Math.abs(to.getPitch() - from.getPitch());
        this.deltaYaw = Math.abs(Utilities.computeAngleDifference(to.getYaw(), from.getYaw()));
        if (Utilities.couldBeOnBoat(player, 0.25, true) && !Utilities.isSubmersed(player)) {
            this.onGround = true;
        }
        this.touchedGroundThisTick = false;
        this.halfMovement = false;
        if (!this.onGround) {
            this.groundTicks = 0;
            ++this.airTicks;
        } else {
            if (this.airTicks > 0) {
                this.touchedGroundThisTick = true;
            }
            this.airTicksBeforeGrounded = this.airTicks;
            this.airTicks = 0;
            ++this.groundTicks;
        }
        if (Utilities.couldBeOnIce(to)) {
            ++this.iceTicks;
            this.iceInfluenceTicks = 60;
        } else {
            this.iceTicks = 0;
            if (this.iceInfluenceTicks > 0) {
                --this.iceInfluenceTicks;
            }
        }
        if (player.isGliding()) {
            this.elytraEffectTicks = 30;
        } else if (this.elytraEffectTicks > 0) {
            --this.elytraEffectTicks;
        }
        this.sneakingTicks = player.isSneaking() ? ++this.sneakingTicks : 0;
        this.blockingTicks = player.isBlocking() ? ++this.blockingTicks : 0;
        this.nearLiquidTicks = Utilities.isNearWater(player) ? 8 : (this.nearLiquidTicks > 0 ? --this.nearLiquidTicks : 0);
        this.hadSpeedEffect = this.hasSpeedEffect;
        this.hasSpeedEffect = player.hasPotionEffect(PotionEffectType.SPEED);
        double lastDistanceSq = Math.sqrt(this.lastDistance.getXDifference() * this.lastDistance.getXDifference() + this.lastDistance.getZDifference() * this.lastDistance.getZDifference());
        double currentDistanceSq = Math.sqrt(distance.getXDifference() * distance.getXDifference() + distance.getZDifference() * distance.getZDifference());
        this.lastAcceleration = this.acceleration;
        this.acceleration = currentDistanceSq - lastDistanceSq;
        this.lastMotionY = this.motionY;
        this.motionY = to.getY() - from.getY();
        Location top = to.clone().add(0.0, 2.0, 0.0);
        this.topSolid = top.getBlock().getType().isSolid();
        Location bottom = to.clone().add(0.0, -1.0, 0.0);
        this.bottomSolid = bottom.getBlock().getType().isSolid();
        if (this.motionY >= (double)0.42f && this.motionY <= 0.5625 && Utilities.isNearBed(to)) {
            this.halfMovement = true;
            this.halfMovementHistoryCounter = 30;
        } else if (this.halfMovementHistoryCounter > 0) {
            --this.halfMovementHistoryCounter;
        }
        this.lastUpdate = System.currentTimeMillis();
        User user = Main.INSTANCE.getUserManager().getUser(player.getUniqueId());
        user.getVelocityTracker().tick();
        user.setGoodLocation(from);
    }
}

