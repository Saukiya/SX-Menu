package github.saukiya.sxmenu.menu;

import github.saukiya.sxmenu.SXMenu;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.inventory.util.CraftCustomInventoryConverter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.util.*;

@Getter
public class MenuDataManager implements Runnable {
    private final List<Inventory> updateInvs = new ArrayList();
    private final List<Inventory> addInv = new ArrayList();
    private final CraftCustomInventoryConverter converter = new CraftCustomInventoryConverter();
    private final Map<String, MenuData> map = new HashMap();
    private final File file = new File(SXMenu.getInst().getDataFolder(), "Data");

    public MenuDataManager() {
        this.loadData();
        Bukkit.getScheduler().runTaskTimer(SXMenu.getInst(), this, 20L, 21L);
    }

    public void loadData() {
        this.map.clear();
        if (!this.file.exists() || this.file.listFiles().length == 0) {
            SXMenu.getInst().saveResource("Data/Default.yml", true);
            SXMenu.getInst().saveResource("Data/DefaultType1.yml", true);
            SXMenu.getInst().saveResource("Data/DefaultType2.yml", true);
        }

        this.loadFile(this.file);
        SXMenu.getInst().getLogger().info("Load " + this.map.size() + " Data");
    }

    private void loadFile(File files) {
        File[] fileArray = files.listFiles();
        for (File file : fileArray) {
            if (file.isDirectory()) {
                this.loadFile(file);
            } else if (file.getName().endsWith(".yml")) {
                String key = file.getName().substring(0, file.getName().length() - 4);
                if (this.map.containsKey(key)) {
                    SXMenu.getInst().getLogger().warning("MenuData: " + key + " Repeat");
                } else {
                    MenuData menuData = new MenuData(key, YamlConfiguration.loadConfiguration(file));
                    this.map.put(key, menuData);
                }
            }
        }

    }

    public void update(Player player) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        if (inv.getHolder() != null) {
            for (MenuData menu : SXMenu.getInst().getMenuDataManager().getMap().values()) {
                if (menu.getHolder().equals(inv.getHolder())) {
                    menu.update(player, inv);
                    return;
                }
            }
        }

    }

    public void run() {
        this.updateInvs.addAll(this.addInv);
        this.addInv.clear();
        Iterator<Inventory> invIter = this.updateInvs.iterator();

        while (invIter.hasNext()) {
            Inventory inv = invIter.next();
            if (inv.getViewers().size() == 0) {
                inv.clear();
                invIter.remove();
            } else {
                Player player = (Player) inv.getViewers().get(0);
                MenuData menu = ((MenuHolder) inv.getHolder()).getMenu();
                menu.update(player, inv);
            }
        }

    }

}
