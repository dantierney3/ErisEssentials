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

public class SetOwner implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("chestSetOwner"))
        {
            if(sender instanceof Player)
            {
                Player p = (Player) sender;

                if(p.isOp() && args.length == 1)
                {

                    Block targetChest = p.getTargetBlockExact(5);
                    Location targetChestLoc = targetChest.getLocation();

                    int x = targetChestLoc.getBlockX();
                    int y = targetChestLoc.getBlockY();
                    int z = targetChestLoc.getBlockZ();

                    String location = Integer.toString(x);
                    location += Integer.toString(y);
                    location += Integer.toString(z);

                    Player target = Bukkit.getPlayerExact(args[0]);

                    if(plugin.getConfig().contains(location))
                    {
                        plugin.getConfig().set(location, target.getUniqueId().toString());
                        p.sendMessage(ChatColor.BLUE + "You changed the chest owner to " + ChatColor.GOLD + target.getDisplayName());
                        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the chest owner");
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "That chest is not protected!");
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
