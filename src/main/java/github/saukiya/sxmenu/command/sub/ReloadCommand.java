package github.saukiya.sxmenu.command.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.command.SubCommand;
import github.saukiya.sxmenu.util.Config;
import github.saukiya.sxmenu.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;

public class ReloadCommand extends SubCommand {
    public ReloadCommand() {
        super("reload");
    }

    public void onCommand(CommandSender sender, String[] args) {
        Config.loadConfig();
        Message.loadMessage();
        Bukkit.getOnlinePlayers().forEach(HumanEntity::closeInventory);
        SXMenu.getInst().getMenuDataManager().loadData();
        sender.sendMessage(Message.getMsg(Message.ADMIN__PLUGIN_RELOAD));
    }
}
