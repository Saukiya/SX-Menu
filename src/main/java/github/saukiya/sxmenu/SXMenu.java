package github.saukiya.sxmenu;

import github.saukiya.sxmenu.command.MainCommand;
import github.saukiya.sxmenu.listener.OnListener;
import github.saukiya.sxmenu.menu.MenuDataManager;
import github.saukiya.sxmenu.menu.action.sub.*;
import github.saukiya.sxmenu.menu.node.INode;
import github.saukiya.sxmenu.menu.node.sub.NodeData;
import github.saukiya.sxmenu.util.Config;
import github.saukiya.sxmenu.util.Message;
import github.saukiya.sxmenu.util.MoneyUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class SXMenu extends JavaPlugin {
    private static SXMenu inst = null;
    private static boolean vault = false;
    private MainCommand mainCommand;
    private MenuDataManager menuDataManager;

    public static SXMenu getInst() {
        return inst;
    }

    public static boolean isVault() {
        return vault;
    }

    public void onLoad() {
        inst = this;
        Config.loadConfig();
        Message.loadMessage();
        this.mainCommand = new MainCommand();
        INode.regNode(new NodeData());
        new Open().register();
        new Cooldown().register();
        new Close().register();
        new Money().register();
        new Permission().register();
        new Js().register();
        new Commands().register();
        new Sound().register();
        new Update().register();
    }

    public void onEnable() {
        long oldTimes = System.currentTimeMillis();
        this.menuDataManager = new MenuDataManager();
        if (Bukkit.getPluginManager().isPluginEnabled("Vault")) {
            try {
                MoneyUtil.setup();
                vault = true;
                this.getLogger().info("Find Vault");
            } catch (NullPointerException var4) {
                this.getLogger().warning("No Find Vault-Economy!");
            }
        } else {
            this.getLogger().warning("No Find Vault!");
        }

        Bukkit.getPluginManager().registerEvents(new OnListener(), this);
        this.mainCommand.setup("sxmenu");
        this.getLogger().info("Loading Time: " + (System.currentTimeMillis() - oldTimes) + " ms");
        this.getLogger().info("Author: Saukiya QQ: 1940208750");
    }

    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
    }
}
