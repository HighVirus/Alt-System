package it.AltSystem;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Deny implements Listener {
    private AltSystem plugin = AltSystem.getInstance();
    List<String> AllowedCommands = plugin.getConfig().getStringList("Allowed");
    String AltError = plugin.getConfig().getString("AltError");

    private boolean verify(String message) {
        for (String command : AllowedCommands) {
            if (message.toLowerCase().startsWith(command))
                return false;
        }
        return true;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        if (plugin.alt(e.getPlayer()) && verify(e.getMessage())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnCommand")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
        if (plugin.alt((Player) e.getPlayer())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnOpenInventory")) {
                Player p = (Player) e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        if (plugin.alt(e.getPlayer()) && verify(e.getMessage())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnChat")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (plugin.alt(e.getPlayer())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnPlaceOrBreak")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (plugin.alt(e.getPlayer())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnPlaceOrBreak")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onCollect(PlayerPickupItemEvent e) {
        if (plugin.alt(e.getPlayer())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnCollect")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        if (plugin.alt(e.getPlayer())) {
            e.setCancelled(true);
            if (plugin.getConfig().getBoolean("SendOnDrop")) {
                Player p = e.getPlayer();
                p.sendMessage(Colors.color(AltError));
            }
        }
    }


}