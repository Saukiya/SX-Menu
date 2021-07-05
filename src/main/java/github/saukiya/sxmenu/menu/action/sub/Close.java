package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.menu.action.IExecution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Close implements IExecution {
    public void execution(Player player, ConfigurationSection config) {
        if (config.getBoolean("yes-close") || config.getBoolean("close")) {
            player.closeInventory();
        }

    }

    public void otherwise(Player player, ConfigurationSection config) {
        if (config.getBoolean("no-close") || config.getBoolean("close")) {
            player.closeInventory();
        }
    }
}
