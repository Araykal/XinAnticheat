/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.entity.Player
 *  org.bukkit.util.Vector
 */
package dev.zenhao.xin.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class CSpeed {
    public static final Map<Player, Location> LastRegularBoatLocation = new HashMap<Player, Location>();
    public static CSpeed awa = new CSpeed();
    public String worldName;
    public double x;
    public double y;
    public double z;
    public float pitch;
    public float yaw;
    public int time;
    public long msTime;
    public boolean isValid = false;

    public static double offset2d(Location a, Location b) {
        return CSpeed.offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(Vector a, Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static double offset(Location a, Location b) {
        return CSpeed.offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static double sqDistanceToX(Location to) {
        return to.getX();
    }

    public static double sqDistanceToY(Location to) {
        return to.getY();
    }

    public static double sqDistanceToZ(Location to) {
        return to.getZ();
    }

    public static boolean onBlock(Location loc) {
        double xMod = loc.getX() % 1.0;
        if (loc.getX() < 0.0) {
            xMod += 1.0;
        }
        double zMod = loc.getZ() % 1.0;
        if (loc.getZ() < 0.0) {
            zMod += 1.0;
        }
        int xMin = 0;
        int xMax = 0;
        int zMin = 0;
        int zMax = 0;
        if (xMod < 0.3) {
            xMin = -1;
        }
        if (xMod > 0.7) {
            xMax = 1;
        }
        if (zMod < 0.3) {
            zMin = -1;
        }
        if (zMod > 0.7) {
            zMax = 1;
        }
        for (int x = xMin; x <= xMax; ++x) {
            for (int z = zMin; z <= zMax; ++z) {
                if (loc.add((double)x, 0.0, (double)z).getBlock().getType() == Material.LEGACY_WATER_LILY) {
                    return true;
                }
                if (loc.add((double)x, -0.5, (double)z).getBlock().getType() != Material.AIR && !loc.add((double)x, -0.5, (double)z).getBlock().isLiquid()) {
                    return true;
                }
                Material beneath = loc.add((double)x, -1.5, (double)z).getBlock().getType();
                if (loc.getY() % 0.5 != 0.0 || !beneath.toString().toLowerCase().contains("fence") && !beneath.toString().toLowerCase().contains("rod") && !beneath.toString().toLowerCase().contains("bamboo") && !beneath.toString().toLowerCase().contains("wall")) continue;
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Block> getSurroundingIgnoreAir(Block block, boolean diagonals) {
        ArrayList<Block> blocks = new ArrayList<Block>();
        if (diagonals) {
            for (int x = -1; x <= 1; ++x) {
                for (int y = -1; y <= 1; ++y) {
                    for (int z = -1; z <= 1; ++z) {
                        if (x == 0 && y == 0 && z == 0 || block.getRelative(x, y, z).getType() == Material.AIR) continue;
                        blocks.add(block.getRelative(x, y, z));
                    }
                }
            }
        } else {
            if (block.getRelative(BlockFace.UP).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.UP));
            }
            if (block.getRelative(BlockFace.DOWN).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.DOWN));
            }
            if (block.getRelative(BlockFace.NORTH).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.NORTH));
            }
            if (block.getRelative(BlockFace.SOUTH).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.SOUTH));
            }
            if (block.getRelative(BlockFace.EAST).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.EAST));
            }
            if (block.getRelative(BlockFace.WEST).getType() != Material.AIR) {
                blocks.add(block.getRelative(BlockFace.WEST));
            }
        }
        return blocks;
    }

    public static Location getLastRegularBoatLocation(Player p) {
        if (!LastRegularBoatLocation.containsKey(p)) {
            return null;
        }
        return LastRegularBoatLocation.get(p);
    }

    public void set(String worldName, double x, double y, double z, float yaw, float pitch, int time, long msTime) {
        if (worldName == null) {
            throw new NullPointerException("World name must not be null.");
        }
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.time = time;
        this.msTime = msTime;
        this.isValid = true;
    }

    public void set(Location loc, int time, long msTime) {
        this.set(loc.getWorld().getName(), loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch(), time, msTime);
    }
}

