package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.MenuData;
import github.saukiya.sxmenu.menu.action.IExecution;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Open implements IExecution {
    public void execution(Player player, ConfigurationSection config) {
        if (config.contains("open")) {
            MenuData menuData = SXMenu.getInst().getMenuDataManager().getMap().get(config.getString("open"));
            if (menuData != null) {
                menuData.open(player);
            } else {
                SXMenu.getInst().getLogger().warning("No Menu - " + config.getString("open"));
            }

        }
    }
}
