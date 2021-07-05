package github.saukiya.sxmenu.menu.node.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.MenuData;
import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import github.saukiya.sxmenu.menu.node.INode;
import github.saukiya.sxmenu.util.Config;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class NodeData implements INode {
    MenuData menu;
    char key;
    ConfigurationSection config;
    ItemStack itemStack;
    Map<ClickType, ConfigurationSection> actionMap = new HashMap();

    public NodeData(MenuData menu, char key, ConfigurationSection config) {
        this.key = key;
        this.config = config;
        Material material = Material.getMaterial(config.getString("id").replace(' ', '_').toUpperCase());
        this.itemStack = new ItemStack(material == null ? Material.BARRIER : material, config.getInt("amount", 1));
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName((Config.getConfig().get("Default-Color.Name") + config.getString("name")).replace('&', '§'));
        meta.setLore(config.getStringList("lore").stream().map((lore) -> (Config.getConfig().get("Default-Color.Lore") + lore).replace('&', '§')).collect(Collectors.toList()));
        if (config.getBoolean("enchant")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(meta);
        this.loadAction();
    }

    public String getType() {
        return "default";
    }

    public INode newNote(MenuData menu, char key, ConfigurationSection config) {
        return new NodeData(menu, key, config);
    }

    public ItemStack getItemStack(Player player) {
        if (ICondition.conditionAll(player, this.config, ConditionType.DISPLAY)) {
            ItemStack item = this.itemStack.clone();
            ItemMeta meta = item.getItemMeta();
            if (this.config.contains("skullName") && meta instanceof SkullMeta) {
                String skullName = PlaceholderAPI.setPlaceholders(player, config.getString("skullName"));

                try {
                    UUID skullUUID = UUID.fromString(skullName);
                    OfflinePlayer skullPlayer = Bukkit.getOfflinePlayer(skullUUID);
                    ((SkullMeta) meta).setOwningPlayer(skullPlayer);
                } catch (Exception var7) {
                    SXMenu.getInst().getLogger().warning("SkullData Error");
                }
            }

            meta.setDisplayName(PlaceholderAPI.setPlaceholders(player, meta.getDisplayName()));
            if (meta.getLore() != null) 
            meta.setLore(PlaceholderAPI.setPlaceholders(player, meta.getLore()));
            item.setItemMeta(meta);
            return item;
        }
        return null;
    }
}
