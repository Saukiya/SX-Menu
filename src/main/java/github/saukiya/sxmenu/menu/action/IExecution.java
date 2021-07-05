package github.saukiya.sxmenu.menu.action;

import github.saukiya.sxmenu.SXMenu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface IExecution extends IAction {
    List<IExecution> executions = new ArrayList();

    static void regExecution(IExecution execution) {
        if (executions.stream().anyMatch((sub) -> sub.getClass().getSimpleName().equals(execution.getClass().getSimpleName()))) {
            SXMenu.getInst().getLogger().warning("IExecution >> Repeat - " + execution.getClass().getSimpleName() + "!");
        } else {
            executions.add(execution);
            SXMenu.getInst().getLogger().info("IExecution >> Register  - " + execution.getClass().getSimpleName() + "!");
        }
    }

    static void executionAll(Player player, ConfigurationSection config) {
        for (IExecution execution : executions) {
            execution.execution(player, config);
        }

    }

    static void otherwiseAll(Player player, ConfigurationSection config) {
        for (IExecution execution : executions) {
            execution.otherwise(player, config);
        }

    }

    void execution(Player var1, ConfigurationSection var2);

    default void otherwise(Player player, ConfigurationSection config) {
    }
}
