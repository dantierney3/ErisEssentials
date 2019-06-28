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
                                case "public":
                                    chestSetPublic(p, args);
                                    break;
                                case "private":
                                    chestSetPrivate(p,args);
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

        String location = getTargetChest(p);

        if(p.isOp())
        {

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
            Player target = Bukkit.getPlayerExact(args[2]);
            String chestOwner = location + ".owner";

            // Checks if the chest is protected and the sender owns the chest
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(chestOwner).equals(p.getUniqueId().toString()))
            {
                changeChestOwner(p, target, location);
            }
        }
    } // End chestSetOwner

    private void chestSetPublic(Player p, String[] args)
    {
        String location = getTargetChest(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"to " + ChatColor.RED + "PUBLIC");
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set your chest to " + ChatColor.RED + "PUBLIC");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your chest");
            }
        }
    }

    private void chestSetPrivate(Player p, String[] args)
    {
        String location = getTargetChest(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"to " + ChatColor.RED + "PRIVATE");
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your chest to " + ChatColor.RED + "PRIVATE");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your chest");
            }
        }
    }

    private void chestGetOwner(Player p, String[] args)
    {
        String location = getTargetChest(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That chest belongs to " + ChatColor.GOLD + ownerName);

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

        location = "chest." + location;

        return location;
    }

    private void changeChestOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        p.sendMessage(ChatColor.BLUE + "You changed the chest owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the chest owner");
        plugin.saveConfig();
    }
}
