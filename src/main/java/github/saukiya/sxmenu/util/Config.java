package github.saukiya.sxmenu.util;

import github.saukiya.sxmenu.SXMenu;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Config {
    @Getter
    private static YamlConfiguration config;

    public static void loadConfig() {
        File file = new File(SXMenu.getInst().getDataFolder(), "Config.yml");
        if (!file.exists()) {
            SXMenu.getInst().getLogger().info("Create Config.yml");
            SXMenu.getInst().saveResource("Config.yml", true);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

}
