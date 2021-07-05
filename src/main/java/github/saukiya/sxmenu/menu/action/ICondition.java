package github.saukiya.sxmenu.menu.action;

import github.saukiya.sxmenu.SXMenu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface ICondition extends IAction {
    List<ICondition> conditions = new ArrayList();

    static void regExecution(ICondition condition) {
        if (conditions.stream().anyMatch((sub) -> sub.getClass().getSimpleName().equals(condition.getClass().getSimpleName()))) {
            SXMenu.getInst().getLogger().warning("ICondition >> Repeat - " + condition.getClass().getSimpleName() + "!");
        } else {
            conditions.add(condition);
            SXMenu.getInst().getLogger().info("ICondition >> Register  - " + condition.getClass().getSimpleName() + "!");
        }
    }

    static boolean conditionAll(Player player, ConfigurationSection config, ConditionType type) {
        for (ICondition condition : conditions) {
            if (!condition.condition(player, config, type)) {
                System.out.println(condition.getClass().getSimpleName() + " FALSE");
                return false;
            }
        }
        return true;
    }

    //true
    boolean condition(Player var1, ConfigurationSection var2, ConditionType var3);
}
