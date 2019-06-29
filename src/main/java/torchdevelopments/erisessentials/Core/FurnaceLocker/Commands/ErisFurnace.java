package torchdevelopments.erisessentials.Core.FurnaceLocker.Commands;


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

public class ErisFurnace implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                // Check that player is targeting a furnace

                if(p.getTargetBlock(5).getType().equals(Material.FURNACE))
                {
                    switch(args[0])
                    {
                        case "get":
                            furnaceGetOwner(p, args);
                            break;
                        case "set":
                            switch(args[1])
                            {
                                case "owner":
                                    furnaceSetOwner(p, args);
                                    break;
                                case "public":
                                    furnaceSetPublic(p, args);
                                    break;
                                case "private":
                                    furnaceSetPrivate(p,args);
                                    break;
                            }
                            break;


                    }
                } // End if
                else
                {
                    p.sendMessage(ChatColor.RED + "That block is not a furnace!");
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
            p.sendMessage(ChatColor.RED + "/furnace <get/set/remove> <owner>");
        }

        return false;
    }

    private void furnaceSetOwner(Player p, String[] args)
    {

        String location = getTargetfurnace(p);

        if(p.isOp())
        {

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the furnace is protected
            {
                changefurnaceOwner(p, target, location);
            }
            else // Creates a protected furnace if the block is unprotected
            {
                changefurnaceOwner(p, target, location);
            }
        }
        else
        {
            Player target = Bukkit.getPlayerExact(args[2]);
            String furnaceOwner = location + ".owner";

            // Checks if the furnace is protected and the sender owns the furnace
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(furnaceOwner).equals(p.getUniqueId().toString()))
            {
                changefurnaceOwner(p, target, location);
            }
        }
    } // End furnaceSetOwner

    private void furnaceSetPublic(Player p, String[] args)
    {
        String location = getTargetfurnace(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"furnace to " + ChatColor.RED + "PUBLIC");

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your furnace to" + ChatColor.RED + " PUBLIC");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set your furnace to " + ChatColor.RED + "PUBLIC");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your furnace");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That furnace is already public");
        }
    }

    private void furnaceSetPrivate(Player p, String[] args)
    {
        String location = getTargetfurnace(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"furnace to " + ChatColor.RED + "PRIVATE");

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your furnace to" + ChatColor.RED + " PRIVATE");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your furnace to " + ChatColor.RED + "PRIVATE");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your furnace");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That furnace is already private");
        }
    }

    private void furnaceGetOwner(Player p, String[] args)
    {
        String location = getTargetfurnace(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That furnace belongs to " + ChatColor.GOLD + ownerName);

        }
    }

    private String getTargetfurnace(Player p)
    {
        Block targetfurnace = p.getTargetBlockExact(5);
        Location targetfurnaceLoc = targetfurnace.getLocation();

        int x = targetfurnaceLoc.getBlockX();
        int y = targetfurnaceLoc.getBlockY();
        int z = targetfurnaceLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        location = "furnace." + location;

        return location;
    }

    private void changefurnaceOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        plugin.getConfig().set(location + ".isPublic", false);
        p.sendMessage(ChatColor.BLUE + "You changed the furnace owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the furnace owner");
        plugin.saveConfig();
    }
}
