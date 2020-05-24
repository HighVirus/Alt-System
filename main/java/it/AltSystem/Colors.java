package it.AltSystem;

import org.bukkit.ChatColor;

public class Colors {
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
