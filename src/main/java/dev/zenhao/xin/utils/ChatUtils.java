/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.bukkit.ChatColor
 */
package dev.zenhao.xin.utils;

import org.bukkit.ChatColor;

public class ChatUtils {
    public static String TranslateToColorChat(String text) {
        return ChatColor.translateAlternateColorCodes((char)'&', (String)text);
    }
}

