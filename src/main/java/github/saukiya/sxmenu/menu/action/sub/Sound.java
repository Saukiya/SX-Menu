package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.menu.action.IExecution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Sound implements IExecution {
    public void execution(Player player, ConfigurationSection config) {
        if (config.contains("yes-sound")) {
            player.playSound(player.getLocation(), org.bukkit.Sound.valueOf(config.getString("yes-sound")), 1.0F, 1.0F);
        } else {
            player.playSound(player.getLocation(), org.bukkit.Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
        }

    }

    public void otherwise(Player player, ConfigurationSection config) {
        if (config.contains("no-sound")) {
            player.playSound(player.getLocation(), org.bukkit.Sound.valueOf(config.getString("no-sound")), 1.0F, 1.0F);
        } else {
            player.playSound(player.getLocation(), org.bukkit.Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
        }

    }
}
