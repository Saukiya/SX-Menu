package github.saukiya.sxmenu.listener;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.menu.MenuData;
import github.saukiya.sxmenu.menu.MenuHolder;
import github.saukiya.sxmenu.menu.action.ConditionType;
import github.saukiya.sxmenu.menu.action.ICondition;
import github.saukiya.sxmenu.menu.action.IExecution;
import github.saukiya.sxmenu.menu.node.INode;
import github.saukiya.sxmenu.util.Config;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;

public class OnListener implements Listener {
    @EventHandler(
            ignoreCancelled = true
    )
    void on(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        InventoryHolder holder = inv.getHolder();
        if (holder != null) {
            Player player = (Player) event.getView().getPlayer();
            if (event.getRawSlot() == -999 && holder.equals(player) && event.getClick().equals(ClickType.RIGHT)) {
                MenuData menuData = SXMenu.getInst().getMenuDataManager().getMap().get(Config.getConfig().getString("DefaultMenu"));
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                if (menuData != null) {
                    menuData.open(player);
                }

            } else {
                if (holder instanceof MenuHolder menuHolder) {
                    MenuData menu =  menuHolder.getMenu();
//                    for (MenuData menu : SXMenu.getInst().getMenuDataManager().getMap().values()) {
//                        if (menu.getHolder().equals(holder)) {
                    event.setCancelled(true);
                    ItemStack currentItem = event.getCurrentItem();
                    if (currentItem != null && !currentItem.getType().equals(Material.AIR)) {
                        if (event.getRawSlot() >= 0 && event.getRawSlot() < menu.getLayout().length) {
                            INode iNode = menu.getNode(event.getRawSlot());
                            ConfigurationSection action = iNode.getActionMap().get(event.getClick());
                            if (action != null) {
                                if (ICondition.conditionAll(player, action, ConditionType.INTERACTION)) {
                                    IExecution.executionAll(player, action);
                                } else {
                                    IExecution.otherwiseAll(player, action);
                                }
                            }
                        }
//                        return;
//                    }
//                            return;
//                        }
                    }
                }

            }
        }
    }

    @EventHandler(
            ignoreCancelled = true
    )
    void on(PlayerCommandPreprocessEvent event) {
        String cmd = event.getMessage().substring(1).split(" ")[0].toLowerCase();
        Iterator var3 = SXMenu.getInst().getMenuDataManager().getMap().values().iterator();

        MenuData menu;
        do {
            if (!var3.hasNext()) {
                return;
            }

            menu = (MenuData) var3.next();
        } while (!menu.getCommands().contains(cmd));

        menu.open(event.getPlayer());
        event.setCancelled(true);
    }

    @EventHandler
    void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();
        if (item != null && !item.getType().equals(Material.AIR)) {
            if (event.getHand().equals(EquipmentSlot.HAND)) {
                ItemMeta meta = item.getItemMeta();
                if (meta.hasLore()) {
                    Iterator var5 = SXMenu.getInst().getMenuDataManager().getMap().values().iterator();

                    while (true) {
                        MenuData menu;
                        do {
                            if (!var5.hasNext()) {
                                return;
                            }

                            menu = (MenuData) var5.next();
                        } while (menu.getOpenLore() == null);

                        Iterator var7 = meta.getLore().iterator();

                        while (var7.hasNext()) {
                            String lore = (String) var7.next();
                            if (lore.contains(menu.getOpenLore())) {
                                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
                                menu.open(player);
                                event.setCancelled(true);
                                return;
                            }
                        }
                    }
                }
            }

        }
    }
}
