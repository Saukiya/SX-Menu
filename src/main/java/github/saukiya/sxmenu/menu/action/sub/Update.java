package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.action.IExecution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Update implements IExecution {
    public void execution(Player player, ConfigurationSection config) {
        if (config.getBoolean("update")) {
            SXMenu.getInst().getMenuDataManager().update(player);
            player.updateInventory();
        }

    }
}
