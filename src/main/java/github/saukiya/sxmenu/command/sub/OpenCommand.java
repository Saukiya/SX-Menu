package github.saukiya.sxmenu.command.sub;

import github.saukiya.sxmenu.SXMenu;
import github.saukiya.sxmenu.command.SubCommand;
import github.saukiya.sxmenu.menu.MenuData;
import github.saukiya.sxmenu.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class OpenCommand extends SubCommand {
    public OpenCommand() {
        super("open");
        this.setArg(" [name]");
    }

    public void onCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(Message.getMsg(Message.ADMIN__NO_FORMAT));
        } else {
            MenuData menu = SXMenu.getInst().getMenuDataManager().getMap().get(args[1]);
            if (menu == null) {
                sender.sendMessage(Message.getMsg(Message.PLAYER__NO_MENU));
            } else {
                Player player = null;
                if (args.length > 2) {
                    if ((player = Bukkit.getPlayerExact(args[2])) == null) {
                        sender.sendMessage(Message.getMsg(Message.ADMIN__NO_ONLINE));
                        return;
                    }
                } else if (sender instanceof Player) {
                    player = (Player) sender;
                }

                if (player == null) {
                    sender.sendMessage(Message.getMsg(Message.ADMIN__NO_CONSOLE));
                } else {
                    menu.open(player);
                }
            }
        }
    }

    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return args.length == 2 ? new ArrayList(SXMenu.getInst().getMenuDataManager().getMap().keySet()) : null;
    }
}
