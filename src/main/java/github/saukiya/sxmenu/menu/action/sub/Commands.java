package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.action.IExecution;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

public class Commands implements IExecution {
    public static void onPlayCommand(Player player, List<String> commandList) {
        int delay = 0;
        commandList = PlaceholderAPI.setPlaceholders(player, commandList);
        Iterator var3 = commandList.iterator();

        while (var3.hasNext()) {
            String cmd = (String) var3.next();
            String command = cmd.replace("%player%", player.getName());
            if (command.startsWith("[DELAY] ")) {
                delay += Integer.parseInt(command.substring(8));
            } else {
                Bukkit.getScheduler().runTaskLater(SXMenu.getInst(), () -> {
                    onPlayerCommand(player, command.replace('&', '§'));
                }, delay);
            }
        }

    }

    public static void onPlayerCommand(Player player, String command) {
        if (command.startsWith("[CONSOLE] ")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command.substring(10));
        } else if (command.startsWith("[MESSAGE] ")) {
            player.sendMessage(command.substring(10));
        } else if (command.startsWith("[CHAT] ")) {
            player.chat(command.substring(6));
        } else if (command.startsWith("[BC] ")) {
            Bukkit.broadcastMessage(command.substring(5));
        } else {
            String[] split;
            if (command.startsWith("[SOUND] ")) {
                split = command.substring(8).split(":");
                player.playSound(player.getLocation(), org.bukkit.Sound.valueOf(split[0]), Float.parseFloat(split[1]), Float.parseFloat(split[2]));
            } else if (command.startsWith("[TITLE] ")) {
                split = command.substring(8).split(":");
                player.sendTitle(split[0], split.length > 1 ? split[1] : null, split.length > 2 ? Integer.parseInt(split[2]) : 5, split.length > 3 ? Integer.parseInt(split[3]) : 30, split.length > 4 ? Integer.parseInt(split[4]) : 5);
            } else if (command.startsWith("[ACTIONBAR] ")) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(command.substring(12)));
            } else {
                Bukkit.dispatchCommand(player, command);
            }
        }

    }

    public void execution(Player player, ConfigurationSection config) {
        if (config.contains("yes-commands")) {
            onPlayCommand(player, config.getStringList("yes-commands"));
        }
    }

    public void otherwise(Player player, ConfigurationSection config) {
        if (config.contains("no-commands")) {
            onPlayCommand(player, config.getStringList("no-commands"));
        }
    }
}
