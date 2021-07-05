package github.saukiya.sxmenu.command.sub;

import github.saukiya.sxmenu.command.SenderType;
import github.saukiya.sxmenu.command.SubCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TestCommand extends SubCommand {
    public TestCommand() {
        super("test");
        this.setHide();
        this.setType(SenderType.PLAYER);
    }

    public void onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        ItemStack item = new ItemStack(Material.CLOCK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§6菜单");
        meta.setLore(Arrays.asList("", "§7打开菜单"));
        item.setItemMeta(meta);
        player.getInventory().addItem(item);
    }
}
