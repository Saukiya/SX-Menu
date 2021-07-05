package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import github.saukiya.sxmenu.menu.action.IExecution;
import github.saukiya.sxmenu.util.Message;
import github.saukiya.sxmenu.util.MoneyUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Money implements ICondition, IExecution {
    public boolean condition(Player player, ConfigurationSection config, ConditionType type) {
        if (!SXMenu.isVault() && config.contains("money")) return false;
        if (MoneyUtil.has(player, config.getInt("money"))) {
            return true;
        } else {
            player.sendMessage(Message.getMsg(Message.PLAYER__NO_MONEY, config.getInt("money")));
            return false;
        }
    }

    public void execution(Player player, ConfigurationSection config) {
        if (SXMenu.isVault() && config.contains("money")) {
            MoneyUtil.take(player, config.getInt("money"));
            player.sendMessage(Message.getMsg(Message.PLAYER__TAKE_MONEY, config.getInt("money")));
        }

    }
}
