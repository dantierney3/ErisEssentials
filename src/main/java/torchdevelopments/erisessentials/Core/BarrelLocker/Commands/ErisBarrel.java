package torchdevelopments.erisessentials.Core.BarrelLocker.Commands;

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

public class ErisBarrel implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                // Check that player is targeting a barrel

                if(p.getTargetBlock(5).getType().equals(Material.BARREL))
                {
                    switch(args[0])
                    {
                        case "get":
                            barrelGetOwner(p, args);
                            break;
                        case "set":
                            switch(args[1])
                            {
                                case "owner":
                                    barrelSetOwner(p, args);
                                    break;
                                case "public":
                                    barrelSetPublic(p, args);
                                    break;
                                case "private":
                                    barrelSetPrivate(p,args);
                                    break;
                            }
                            break;


                    }
                } // End if
                else
                {
                    p.sendMessage(ChatColor.RED + "That block is not a barrel!");
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
            p.sendMessage(ChatColor.RED + "/barrel <get/set/remove> <owner>");
        }

        return false;
    }

    private void barrelSetOwner(Player p, String[] args)
    {

        String location = getTargetbarrel(p);

        if(p.isOp())
        {

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the barrel is protected
            {
                changebarrelOwner(p, target, location);
            }
            else // Creates a protected barrel if the block is unprotected
            {
                changebarrelOwner(p, target, location);
            }
        }
        else
        {
            Player target = Bukkit.getPlayerExact(args[2]);
            String barrelOwner = location + ".owner";

            // Checks if the barrel is protected and the sender owns the barrel
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(barrelOwner).equals(p.getUniqueId().toString()))
            {
                changebarrelOwner(p, target, location);
            }
        }
    } // End barrelSetOwner

    private void barrelSetPublic(Player p, String[] args)
    {
        String location = getTargetbarrel(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"barrel to " + ChatColor.RED + "PUBLIC");

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your barrel to" + ChatColor.RED + " PUBLIC");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set your barrel to " + ChatColor.RED + "PUBLIC");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your barrel");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That barrel is already public");
        }
    }

    private void barrelSetPrivate(Player p, String[] args)
    {
        String location = getTargetbarrel(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"barrel to " + ChatColor.RED + "PRIVATE");

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your barrel to" + ChatColor.RED + " PRIVATE");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your barrel to " + ChatColor.RED + "PRIVATE");
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your barrel");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That barrel is already private");
        }
    }

    private void barrelGetOwner(Player p, String[] args)
    {
        String location = getTargetbarrel(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That barrel belongs to " + ChatColor.GOLD + ownerName);

        }
    }

    private String getTargetbarrel(Player p)
    {
        Block targetbarrel = p.getTargetBlockExact(5);
        Location targetbarrelLoc = targetbarrel.getLocation();

        int x = targetbarrelLoc.getBlockX();
        int y = targetbarrelLoc.getBlockY();
        int z = targetbarrelLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        location = "barrel." + location;

        return location;
    }

    private void changebarrelOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        p.sendMessage(ChatColor.BLUE + "You changed the barrel owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the barrel owner");
        plugin.saveConfig();
    }
}
