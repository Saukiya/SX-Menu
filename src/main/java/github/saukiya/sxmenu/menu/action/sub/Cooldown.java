package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import github.saukiya.sxmenu.menu.action.IExecution;
import github.saukiya.sxmenu.util.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Cooldown implements ICondition, IExecution {
    private static final Map<UUID, Long> cooldownMap = new HashMap();

    public static void add(Player player, int times) {
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis() + (long) times);
    }

    private static double get(Player player) {
        Long time = cooldownMap.get(player.getUniqueId());
        return time != null ? (double) (time - System.currentTimeMillis()) / 1000.0D : -1.0D;
    }

    public static boolean is(Player player) {
        if (get(player) > 0) {
            return false;
        } else {
            cooldownMap.remove(player.getUniqueId());
            return true;
        }
    }

    public boolean condition(Player player, ConfigurationSection config, ConditionType type) {
        return !type.equals(ConditionType.INTERACTION) || is(player);
    }

    public void execution(Player player, ConfigurationSection config) {
        add(player, config.getInt("cooldown", Config.getConfig().getInt("DefaultCooldown")));
    }
}
