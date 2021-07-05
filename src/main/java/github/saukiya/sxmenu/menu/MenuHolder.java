package github.saukiya.sxmenu.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public interface MenuHolder extends InventoryHolder {
    MenuData getMenu();

    default Inventory getInventory() {
        return null;
    }
}
