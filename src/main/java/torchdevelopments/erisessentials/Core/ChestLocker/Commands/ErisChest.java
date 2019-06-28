package torchdevelopments.erisessentials.Core.ChestLocker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ErisChest implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                // Check that player is targeting a chest

                if(p.getTargetBlock(5).getType().equals(Material.CHEST))
                {
                    switch(args[0])
                    {
                        case "get":
                            chestGetOwner(p, args);
                            break;
                        case "set":
                            switch(args[1])
                            {
                                case "owner":
                                    chestSetOwner(p, args);
                                    break;
                            }
                            break;

                        case "remove":
                            switch(args[1])
                            {
                                case "owner":
                                    chestRemoveOwner(p,args);
                                    break;
                            }
                            break;
                    }
                } // End if
                else
                {
                    p.sendMessage(ChatColor.RED + "That block is not a Chest!");
                }
            }
            else
            {
                System.out.println("You need to be a player to use this command!");
            }


        }
        catch (NullPointerException e)
        {
            Player p = (Player) sender;
            p.sendMessage(ChatColor.RED + "Invalid Arguments!");
            p.sendMessage(ChatColor.RED + "/chest <get/set/remove> <owner>");
        }

        return false;
    }

    private void chestSetOwner(Player p, String[] args)
    {
        if(p.isOp())
        {
            String location = getTargetChest(p);

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the chest is protected
            {
                changeChestOwner(p, target, location);
            }
            else // Creates a protected chest if the block is unprotected
            {
                changeChestOwner(p, target, location);
            }
        }
        else
        {
            String location = getTargetChest(p);
            Player target = Bukkit.getPlayerExact(args[2]);

            // Checks if the chest is protected and the sender owns the chest
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(location).equals(p.getUniqueId().toString()))
            {
                changeChestOwner(p, target, location);
            }
        }
    } // End chestSetOwner

    private void chestRemoveOwner(Player p, String[] args)
    {

        String location = getTargetChest(p);

        if (p.isOp())
        {
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
    } // End chestRemoveOwner

    private void chestGetOwner(Player p, String[] args)
    {
        String location = getTargetChest(p);

        if(plugin.getConfig().contains(location))
        {
            String owner = plugin.getConfig().get(location).toString();

            UUID uuid = UUID.fromString(owner);

            Player pOwner = Bukkit.getPlayer(uuid);

            if(pOwner != null)
            {
                p.sendMessage(ChatColor.BLUE + "That chest belongs to " + pOwner.getDisplayName());
            }
            else
            {
                p.sendMessage(ChatColor.BLUE + "The owner of that chest is not online at the moment!");
            }
        }
    }

    private String getTargetChest(Player p)
    {
        Block targetChest = p.getTargetBlockExact(5);
        Location targetChestLoc = targetChest.getLocation();

        int x = targetChestLoc.getBlockX();
        int y = targetChestLoc.getBlockY();
        int z = targetChestLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        return location;
    }

    private void changeChestOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location, target.getUniqueId().toString());
        p.sendMessage(ChatColor.BLUE + "You changed the chest owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the chest owner");
        plugin.saveConfig();
    }
}
