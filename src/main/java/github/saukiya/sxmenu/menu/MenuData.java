package github.saukiya.sxmenu.menu;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.node.INode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Getter
public class MenuData {
    private final MenuHolder holder;
    private final String key;
    private final String title;
    private final InventoryType type;
    private final char[] layout;
    private final List<String> commands;
    private final String openLore;
    private final Map<Integer, INode> indexMap = new HashMap();

    public MenuData(String key, YamlConfiguration yml) {
        Map<Character, INode> nodeMap = new HashMap();
        this.key = key;
        this.holder = () -> this;
        this.title = yml.getString("Title").replace('&', '§');
        this.type = yml.contains("Type") ? InventoryType.valueOf(yml.getString("Type")) : null;
        this.layout = String.join("", yml.getStringList("Layout")).toCharArray();
        this.commands = yml.getStringList("Command");
        this.openLore = yml.getString("OpenLore");
        if (yml.contains("Node")) {
            Iterator<String> var4 = yml.getConfigurationSection("Node").getKeys(false).iterator();

            to1:
            while (true) {
                to2:
                while (true) {
                    if (!var4.hasNext()) {
                        break to1;
                    }

                    String node = var4.next();
                    String type = yml.getString("Node." + node + ".type", "default");

                    for (INode iNode : INode.nodes) {
                        if (iNode.getType().equals(type)) {
                            nodeMap.put(node.charAt(0), iNode.newNote(this, node.charAt(0), yml.getConfigurationSection("Node." + node)));
                            continue to2;
                        }
                    }

                    SXMenu.getInst().getLogger().warning("not Node Type: " + key + File.separator + node + " - " + type + " !");
                }
            }
        }
        // 检查不存在的layout排版中的node
        for (int i = 0; i < this.layout.length; ++i) {
            char c = this.layout[i];
            if (c != ' ') {
                INode node = nodeMap.get(c);
                if (node != null) {
                    this.indexMap.put(i, node);
                } else {
                    SXMenu.getInst().getLogger().warning("Not Node: " + key + " - " + c + " !");
                }
            }
        }
        SXMenu.getInst().getLogger().info("Menu " + key + " Load " + this.indexMap.size() + " Node");

    }

    public INode getNode(int i) {
        return this.indexMap.get(i);
    }

    public void open(Player player) {
        Inventory inv = this.type == null ? Bukkit.createInventory(this.holder, this.layout.length, this.title) : SXMenu.getInst().getMenuDataManager().getConverter().createInventory(this.holder, this.type, this.title);
//        Map<Character, ItemStack> nodeTemp = new HashMap();
        //nodeTemp防止重复使用? 我暂时感觉跟66行行为有重叠
        for (Entry<Integer, INode> entry : this.indexMap.entrySet()) {
//            ItemStack item = nodeTemp.get(entry.getValue().getKey());
//            if (item == null) {
//                item = entry.getValue().getItemStack(player);
//                nodeTemp.put(entry.getValue().getKey(), item);
//            }
//            inv.setItem(entry.getKey(), item);
            inv.setItem(entry.getKey(), entry.getValue().getItemStack(player));
        }

        player.openInventory(inv);
        SXMenu.getInst().getMenuDataManager().getAddInv().add(inv);
    }

    public void update(Player player, Inventory inv) {

        for (Entry<Integer, INode> entry : this.indexMap.entrySet()) {
            if (entry.getValue().getConfig().getBoolean("update")) {
                inv.setItem(entry.getKey(), entry.getValue().getItemStack(player));
            }
        }

    }
}
