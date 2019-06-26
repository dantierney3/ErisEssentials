package torchdevelopments.erisessentials.Core.ChestLocker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class RemoveProtection implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equals("chestRemoveProtection"))
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                if (p.isOp())
                {

                    Block targetChest = p.getTargetBlockExact(5);
                    Location targetChestLoc = targetChest.getLocation();

                    int x = targetChestLoc.getBlockX();
                    int y = targetChestLoc.getBlockY();
                    int z = targetChestLoc.getBlockZ();

                    String location = Integer.toString(x);
                    location += Integer.toString(y);
                    location += Integer.toString(z);

                    if (plugin.getConfig().contains(location))
                    {
                        plugin.getConfig().set(location, null);
                        p.sendMessage(ChatColor.BLUE + "You have removed protection from this chest");
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "That chest is already unprotected!");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
                }
            }
            else
            {
                System.out.println("You have to be a player to use this command!");
            }
        }
        return false;
    }
}
