package github.saukiya.sxmenu.menu.action.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Js implements ICondition {
    private final ScriptEngineManager jsManager = new ScriptEngineManager();

    public boolean condition(Player player, ConfigurationSection config, ConditionType type) {
        if (config.contains("js-condition")) {
            String js = PlaceholderAPI.setPlaceholders(player, config.getString("js-condition"));
            try {
                ScriptEngine engine = this.jsManager.getEngineByName("JavaScript");
                engine.put("player", player);
                return (boolean) engine.eval(js);
            } catch (Exception var6) {
                SXMenu.getInst().getLogger().warning("Js >> " + js + " Error!");
                return false;
            }
        } else {
            return true;
        }
    }
}
