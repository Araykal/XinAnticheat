/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.comphenix.protocol.utility.MinecraftVersion
 *  org.bukkit.Location
 *  org.bukkit.Material
 *  org.bukkit.attribute.Attribute
 *  org.bukkit.attribute.AttributeInstance
 *  org.bukkit.block.Block
 *  org.bukkit.block.BlockFace
 *  org.bukkit.enchantments.Enchantment
 *  org.bukkit.entity.Boat
 *  org.bukkit.entity.Entity
 *  org.bukkit.entity.Player
 *  org.bukkit.inventory.ItemStack
 *  org.bukkit.util.NumberConversions
 */
package dev.zenhao.xin.utils;

import com.comphenix.protocol.utility.MinecraftVersion;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

public final class Utilities {
    private static final List<Material> INSTANT_BREAK = new ArrayList<Material>();
    private static final List<Material> FOOD = new ArrayList<Material>();
    private static final List<Material> CLIMBABLE = new ArrayList<Material>();
    private static final Map<Material, Material> COMBO = new HashMap<Material, Material>();
    public static final double JUMP_MOTION_Y = (double)0.42f;

    public static boolean cantStandAtSingle(Block block) {
        Block otherBlock = block.getLocation().add(0.0, -0.5, 0.0).getBlock();
        boolean center = otherBlock.getType() == Material.AIR;
        return center;
    }

    public static boolean cantStandAt(Block block) {
        return !Utilities.canStand(block) && Utilities.cantStandClose(block) && Utilities.cantStandFar(block);
    }

    public static boolean cantStandAtExp(Location location) {
        return Utilities.cantStandAt(new Location(location.getWorld(), location.getX(), location.getY() - 0.01, location.getZ()).getBlock());
    }

    public static boolean cantStandClose(Block block) {
        return !Utilities.canStand(block.getRelative(BlockFace.NORTH)) && !Utilities.canStand(block.getRelative(BlockFace.EAST)) && !Utilities.canStand(block.getRelative(BlockFace.SOUTH)) && !Utilities.canStand(block.getRelative(BlockFace.WEST));
    }

    public static boolean cantStandFar(Block block) {
        return !Utilities.canStand(block.getRelative(BlockFace.NORTH_WEST)) && !Utilities.canStand(block.getRelative(BlockFace.NORTH_EAST)) && !Utilities.canStand(block.getRelative(BlockFace.SOUTH_WEST)) && !Utilities.canStand(block.getRelative(BlockFace.SOUTH_EAST));
    }

    public static boolean canStand(Block block) {
        return !block.isLiquid() && block.getType() != Material.AIR;
    }

    public static boolean couldBeOnBoat(Player player) {
        return Utilities.couldBeOnBoat(player, 0.35, false);
    }

    public static boolean couldBeOnBoat(Player player, double range, boolean checkY) {
        for (Entity entity : player.getNearbyEntities(range, range, range)) {
            if (!(entity instanceof Boat)) continue;
            if (entity.getLocation().getY() < player.getLocation().getY() + 0.35) {
                return true;
            }
            if (checkY) continue;
            return true;
        }
        return false;
    }

    public static boolean couldBeOnIce(Location location) {
        return Utilities.isNearIce(new Location(location.getWorld(), location.getX(), location.getY() - 0.01, location.getZ())) || Utilities.isNearIce(new Location(location.getWorld(), location.getX(), location.getY() - 0.26, location.getZ())) || Utilities.isNearIce(new Location(location.getWorld(), location.getX(), location.getY() - 0.51, location.getZ()));
    }

    public static boolean isNearIce(Location location) {
        return Utilities.isCollisionPoint(location, material -> material.name().toUpperCase().endsWith("ICE"));
    }

    public static boolean isNearShulkerBox(Location location) {
        if (MinecraftVersion.getCurrentVersion().isAtLeast(MinecraftVersion.COLOR_UPDATE)) {
            return false;
        }
        return Utilities.isCollisionPoint(location, material -> material.name().toUpperCase().endsWith("SHULKER_BOX"));
    }

    public static boolean isFullyInWater(Location player) {
        double touchedX = Utilities.fixXAxis(player.getX());
        if (!new Location(player.getWorld(), touchedX, player.getY(), (double)player.getBlockZ()).getBlock().isLiquid() && !new Location(player.getWorld(), touchedX, (double)Math.round(player.getY()), (double)player.getBlockZ()).getBlock().isLiquid()) {
            return true;
        }
        return new Location(player.getWorld(), touchedX, player.getY(), (double)player.getBlockZ()).getBlock().isLiquid() && new Location(player.getWorld(), touchedX, (double)Math.round(player.getY()), (double)player.getBlockZ()).getBlock().isLiquid();
    }

    public static double fixXAxis(double x) {
        double touchedX = x;
        double rem = touchedX - (double)Math.round(touchedX) + 0.01;
        if (rem < 0.3) {
            touchedX = NumberConversions.floor((double)x) - 1;
        }
        return touchedX;
    }

    public static boolean isHoveringOverWater(Location player, int blocks) {
        for (int i = player.getBlockY(); i > player.getBlockY() - blocks; --i) {
            Block newloc = new Location(player.getWorld(), (double)player.getBlockX(), (double)i, (double)player.getBlockZ()).getBlock();
            if (newloc.getType() == Material.AIR) continue;
            return newloc.isLiquid();
        }
        return false;
    }

    public static boolean isHoveringOverWater(Location player) {
        return Utilities.isHoveringOverWater(player, 25);
    }

    public static boolean isInstantBreak(Material m) {
        return INSTANT_BREAK.contains(m);
    }

    public static boolean isFood(Material m) {
        return FOOD.contains(m);
    }

    public static boolean isSlab(Block block) {
        Material type = block.getType();
        return type.name().toUpperCase().endsWith("SLAB") || type.name().toUpperCase().endsWith("STEP");
    }

    public static boolean isNearBed(Location location) {
        return Utilities.isCollisionPoint(location, material -> material.name().toUpperCase().endsWith("BED"));
    }

    public static boolean isStair(Block block) {
        Material type = block.getType();
        return type.name().endsWith("STAIRS");
    }

    public static boolean isWall(Block block) {
        Material type = block.getType();
        return type.name().endsWith("WALL") || type.name().endsWith("FENCE") || type.name().endsWith("FENCE_GATE");
    }

    public static boolean isNearWall(Location location) {
        return Utilities.isWall(location.getBlock()) || Utilities.isWall(location.getBlock().getRelative(BlockFace.NORTH)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.SOUTH)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.EAST)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.WEST)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.NORTH_EAST)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.NORTH_WEST)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.SOUTH_EAST)) || Utilities.isWall(location.getBlock().getRelative(BlockFace.SOUTH_WEST));
    }

    public static boolean isOnCarpet(Player player) {
        Block block = player.getLocation().getBlock();
        return block.getType().name().endsWith("CARPET") || block.getRelative(BlockFace.NORTH).getType().name().endsWith("CARPET") || block.getRelative(BlockFace.SOUTH).getType().name().endsWith("CARPET") || block.getRelative(BlockFace.EAST).getType().name().endsWith("CARPET") || block.getRelative(BlockFace.WEST).getType().name().endsWith("CARPET");
    }

    public static boolean isSubmersed(Player player) {
        return player.getLocation().getBlock().isLiquid() && player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid();
    }

    public static boolean isNearWater(Player player) {
        return player.getLocation().getBlock().isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.UP).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.DOWN).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.NORTH).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.SOUTH).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.EAST).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.WEST).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.NORTH_EAST).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.NORTH_WEST).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.SOUTH_EAST).isLiquid() || player.getLocation().getBlock().getRelative(BlockFace.SOUTH_WEST).isLiquid();
    }

    public static boolean isNearWater(Location location) {
        return location.getBlock().isLiquid() || location.getBlock().getRelative(BlockFace.UP).isLiquid() || location.getBlock().getRelative(BlockFace.DOWN).isLiquid() || location.getBlock().getRelative(BlockFace.NORTH).isLiquid() || location.getBlock().getRelative(BlockFace.SOUTH).isLiquid() || location.getBlock().getRelative(BlockFace.EAST).isLiquid() || location.getBlock().getRelative(BlockFace.WEST).isLiquid() || location.getBlock().getRelative(BlockFace.NORTH_EAST).isLiquid() || location.getBlock().getRelative(BlockFace.NORTH_WEST).isLiquid() || location.getBlock().getRelative(BlockFace.SOUTH_EAST).isLiquid() || location.getBlock().getRelative(BlockFace.SOUTH_WEST).isLiquid();
    }

    public static boolean isSurroundedByWater(Player player) {
        Location location = player.getLocation().clone().subtract(0.0, 0.1, 0.0);
        return location.getBlock().isLiquid() && location.getBlock().getRelative(BlockFace.NORTH).isLiquid() && location.getBlock().getRelative(BlockFace.SOUTH).isLiquid() && location.getBlock().getRelative(BlockFace.EAST).isLiquid() && location.getBlock().getRelative(BlockFace.WEST).isLiquid() && location.getBlock().getRelative(BlockFace.NORTH_EAST).isLiquid() && location.getBlock().getRelative(BlockFace.NORTH_WEST).isLiquid() && location.getBlock().getRelative(BlockFace.SOUTH_EAST).isLiquid() && location.getBlock().getRelative(BlockFace.SOUTH_WEST).isLiquid();
    }

    public static boolean isNearClimbable(Player player) {
        return Utilities.isCollisionPoint(player.getLocation(), material -> CLIMBABLE.contains(material));
    }

    public static boolean isNearClimbable(Location location) {
        return Utilities.isCollisionPoint(location, material -> CLIMBABLE.contains(material));
    }

    public static boolean isNearClimbable(Location location, double expand) {
        return Utilities.isCollisionPoint(location, expand, material -> CLIMBABLE.contains(material));
    }

    public static boolean isClimbableBlock(Block block) {
        return CLIMBABLE.contains(block.getType());
    }

    public static boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public static boolean blockIsnt(Block block, Material[] materials) {
        Material type = block.getType();
        for (Material m : materials) {
            if (m != type) continue;
            return false;
        }
        return true;
    }

    public static boolean blockIsnt(Block block, String[] endTypes) {
        Material type = block.getType();
        for (String s : endTypes) {
            if (!type.name().endsWith(s)) continue;
            return false;
        }
        return true;
    }

    public static String[] getCommands(String command) {
        return command.replaceAll("COMMAND\\[", "").replaceAll("]", "").split(";");
    }

    public static String removeWhitespace(String string) {
        return string.replaceAll(" ", "");
    }

    public static boolean hasArmorEnchantment(Player player, Enchantment e) {
        for (ItemStack is : player.getInventory().getArmorContents()) {
            if (is == null || !is.containsEnchantment(e)) continue;
            return true;
        }
        return false;
    }

    public static ArrayList<String> stringToList(final String string) {
        return new ArrayList<String>(){
            private static final long serialVersionUID = 364115444874638230L;
            {
                this.add(string);
            }
        };
    }

    public static String listToCommaString(List<String> list) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < list.size(); ++i) {
            b.append(list.get(i));
            if (i >= list.size() - 1) continue;
            b.append(",");
        }
        return b.toString();
    }

    public static long lifeToSeconds(String string) {
        if (string.equals("0") || string.equals("")) {
            return 0L;
        }
        String[] lifeMatch = new String[]{"d", "h", "m", "s"};
        int[] lifeInterval = new int[]{86400, 3600, 60, 1};
        long seconds = 0L;
        for (int i = 0; i < lifeMatch.length; ++i) {
            Matcher matcher = Pattern.compile("([0-9]*)" + lifeMatch[i]).matcher(string);
            while (matcher.find()) {
                seconds += (long)(Integer.parseInt(matcher.group(1)) * lifeInterval[i]);
            }
        }
        return seconds;
    }

    public static float roundFloat(float value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).floatValue();
    }

    public static double roundDouble(double value, int scale) {
        return new BigDecimal(value).setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static int floor(double value) {
        int rounded = (int)value;
        return value < (double)rounded ? rounded - 1 : rounded;
    }

    public static float computeAngleDifference(float a, float b) {
        float diff = Math.abs(a - b);
        float altDiff = b + 360.0f - a;
        float altAltDiff = a + 360.0f - b;
        if (altDiff < diff) {
            diff = altDiff;
        }
        if (altAltDiff < diff) {
            diff = altAltDiff;
        }
        return diff;
    }

    public static long getGcd(long current, long previous) {
        return previous <= 16384L ? current : Utilities.getGcd(previous, current % previous);
    }

    public static boolean isCollisionPoint(Location location, Predicate<Material> predicate) {
        return Utilities.isCollisionPoint(location, 0.3, predicate);
    }

    public static boolean isCollisionPoint(Location location, double expand, Predicate<Material> predicate) {
        ArrayList<Material> materials = new ArrayList<Material>();
        for (double x = -expand; x <= expand; x += expand) {
            for (double y = -expand; y <= expand; y += expand) {
                for (double z = -expand; z <= expand; z += expand) {
                    Material material = location.clone().add(x, y, z).getBlock().getType();
                    if (material == null) continue;
                    materials.add(material);
                }
            }
        }
        return materials.stream().anyMatch(predicate);
    }

    public static double getVariance(Collection<? extends Number> data) {
        int count = 0;
        double sum = 0.0;
        double variance = 0.0;
        for (Number number : data) {
            sum += number.doubleValue();
            ++count;
        }
        double average = sum / (double)count;
        for (Number number : data) {
            variance += Math.pow(number.doubleValue() - average, 2.0);
        }
        return variance;
    }

    public static double getMovementSpeed(Player player) {
        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        return attribute.getValue();
    }
}

