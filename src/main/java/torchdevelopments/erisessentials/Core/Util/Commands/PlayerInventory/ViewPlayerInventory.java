package torchdevelopments.erisessentials.Core.Util.Commands.PlayerInventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ViewPlayerInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;
            try
            {
                if (p.isOp() && args.length > 0)
                {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    Inventory inv = target.getInventory();
                    p.sendMessage(ChatColor.BLUE + "Opening " + ChatColor.GOLD + target.getDisplayName() + "'s " + ChatColor.BLUE + "inventory...");
                    System.out.println(p.getDisplayName() + " opened " + target.getDisplayName() + "'s inventory");
                    p.openInventory(inv);
                }
                else if (p.isOp() && args.length == 0)
                {
                    p.sendMessage(ChatColor.RED + "You must enter a target!");
                    p.sendMessage(ChatColor.RED + "/inv <Player>");
                }
                else if (!p.isOp())
                {
                    p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                }
            }
            catch(NullPointerException ex)
            {
                p.sendMessage(ChatColor.RED + "That player is offline...");
                System.out.println(p.getDisplayName() + " tried to access an offline player's inventory");
            }

        }

        return false;
    }
}
