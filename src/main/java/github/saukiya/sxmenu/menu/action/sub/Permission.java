package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import github.saukiya.sxmenu.util.Message;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Permission implements ICondition {
    public boolean condition(Player player, ConfigurationSection config, ConditionType type) {
        if (config.contains("permission")) {
            if (!player.hasPermission(config.getString("permission"))) {
                if (type.equals(ConditionType.INTERACTION)) {
                    player.sendMessage(Message.getMsg(Message.PLAYER__NO_PERMISSION, config.getString("permission")));
                }
                return false;
            }
        }
        return true;
    }
}
