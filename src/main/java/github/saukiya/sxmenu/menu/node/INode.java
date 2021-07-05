package github.saukiya.sxmenu.menu.node;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.MenuData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface INode {
    List<INode> nodes = new ArrayList();

    static void regNode(INode iNode) {
        if (iNode.getType() != null && nodes.stream().noneMatch((node) -> node.getType().equals(iNode.getType()))) {
            nodes.add(iNode);
            SXMenu.getInst().getLogger().info("INode >> Register [" + iNode.getClass().getSimpleName() + "] To Type " + iNode.getType() + " !");
        } else {
            SXMenu.getInst().getLogger().warning("INode >>  [" + iNode.getClass().getSimpleName() + "] Type Error!");
        }
    }

    MenuData getMenu();

    char getKey();

    ConfigurationSection getConfig();

    String getType();

    INode newNote(MenuData var1, char var2, ConfigurationSection var3);

    ItemStack getItemStack(Player var1);

    Map<ClickType, ConfigurationSection> getActionMap();

    default void loadAction() {
        if (this.getConfig().contains("action")) {
            for (String acKey : this.getConfig().getConfigurationSection("action").getKeys(false)) {
                ClickType action = ClickType.valueOf(acKey);
                this.getActionMap().put(action, this.getConfig().getConfigurationSection("action." + acKey));
            }
        }

    }
}
