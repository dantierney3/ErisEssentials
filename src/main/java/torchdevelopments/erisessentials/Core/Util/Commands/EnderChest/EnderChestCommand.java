package torchdevelopments.erisessentials.Core.Util.Commands.EnderChest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnderChestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;

            // try-catch loop in the if statement as the exception being caught is caused
            // by the user trying to access an offline player's Ender chest
            try
            {
                // Check to see if the player has permission to look at another Player's Ender Chest
                if (p.isOp())
                {
                    // Argument Check, if returns true, then another Player's Ender Chest will be accessed
                    if(args.length > 0)
                    {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        // Gets the target's Ender Chest
                        Inventory inv = target.getEnderChest();
                        p.sendMessage(ChatColor.BLUE + "Openining " + ChatColor.GOLD + target.getDisplayName() + "'s " + ChatColor.BLUE + "Ender Chest...");
                        System.out.println(p.getDisplayName() + " opened " + target.getDisplayName() + "'s Ender Chest");
                        p.openInventory(inv);

                    }
                    else // If the player doesnt provide a target, it will open their ender chest
                    {
                        Inventory inv = p.getEnderChest();
                        p.sendMessage(ChatColor.BLUE + "Opening your Ender Chest...");
                        p.openInventory(inv);
                    }
                }
                else
                {
                    if(args.length > 0)
                    {
                        p.sendMessage(ChatColor.RED + "You do not have permission to see this person's Ender Chest!");
                    }
                    else
                    {
                        Inventory inv = p.getEnderChest();
                        p.sendMessage(ChatColor.BLUE + "Opening your Ender Chest...");
                        p.openInventory(inv);
                    }

                }
            }
            catch (NullPointerException ex)
            {

                // Exception caused by player not being online, so message is sent to player
                p.sendMessage(ChatColor.RED + "That player is not online at the moment...");
                System.out.println(p.getDisplayName() + " tried accessing an offline player's Ender Chest");
            }

        }
        return false;
    }
}
