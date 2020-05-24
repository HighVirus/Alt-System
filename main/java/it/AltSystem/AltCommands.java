package it.AltSystem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class AltCommands implements CommandExecutor {

    private AltSystem plugin = AltSystem.getInstance();

    private FileConfiguration config = plugin.getConfig();

    private Set<String> requests = new HashSet<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> altsList = plugin.getDataConfig().getStringList("Alts");

        if (args.length <= 2) {

            if (args.length == 0) {
                if (sender.hasPermission("altsystem.user")) {
                    List<String> help = config.getStringList("Help");
                    for (String msghelp : help) {
                        sender.sendMessage(Colors.color(msghelp));
                    }
                } else {
                    sender.sendMessage(Colors.color(config.getString("NoPerms")));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase(config.getString("ConvertCommand"))) {
                if (sender.hasPermission("altsystem.user")) {
                    if(!(sender instanceof Player)){
                        sender.sendMessage("This command cannot be executed on console!");
                        return true;
                    }
                    Player p = (Player) sender;
                    if (!requests.contains(p.getUniqueId().toString())) {
                        List<String> convertmessage = config.getStringList("ConvertMessage");
                        for (String msgconvert : convertmessage) {
                            p.sendMessage(Colors.color(msgconvert));
                        }
                        requests.add(p.getUniqueId().toString());
                    } else {
                        plugin.getPermission().playerAddGroup(null, p.getPlayer(), config.getString("PermissionsGroup"));
                        requests.remove(p.getUniqueId().toString());
                        p.sendMessage(Colors.color(config.getString("ConversionDone")));
                        altsList.add(p.getUniqueId().toString());
                        plugin.getDataConfig().set("Alts", altsList);
                        plugin.saveData();
                    }
                    return true;
                } else {
                    sender.sendMessage(Colors.color(config.getString("NoPerms")));
                }
                return true;
            }

            if (args[0].equalsIgnoreCase("list")) {
                if (sender.hasPermission("altsystem.staff")) {
                    sender.sendMessage(Colors.color(config.getString("AltsListMessage")));
                    for (String aList : altsList) {
                        sender.sendMessage(Colors.color("&7" + plugin.getServer().getOfflinePlayer(UUID.fromString(aList)).getName()));
                    }
                }
            }

            if (args[0].equalsIgnoreCase(("reload"))) {
                if (sender.hasPermission("altsystem.staff")) {
                    plugin.reloadConfig();
                    sender.sendMessage(Colors.color(plugin.getConfig().getString("Reload")));
                }
            }


        }
        return false;
    }
}