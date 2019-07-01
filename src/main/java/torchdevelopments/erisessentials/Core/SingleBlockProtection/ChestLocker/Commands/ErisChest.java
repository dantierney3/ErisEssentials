/*
 * Copyright © Daniel Tierney, 2019. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright.txt notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright.txt notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Commands;

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

import java.util.List;
import java.util.UUID;

public class ErisChest implements CommandExecutor {

    private Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

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
                            chestGetOwner(p);
                            break;
                        case "set":
                            switch(args[1])
                            {
                                case "owner":
                                    chestSetOwner(p, args);
                                    break;
                                case "public":
                                    chestSetPublic(p);
                                    break;
                                case "private":
                                    chestSetPrivate(p);
                                    break;
                            }
                            break;
                        case "add":
                            if(args[1].contentEquals("friend"))
                            {
                                chestAddFriend(p, args);
                                break;
                            }
                            break;
                        case "remove" :

                            if(args[1].contentEquals("friend"))
                            {
                                chestRemoveFriend(p, args);
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
            int length = args.length;
            p.sendMessage(ChatColor.RED + "Invalid Arguments!");
            p.sendMessage(ChatColor.RED + "/chest <get/set/add/remove> <owner/friend/public/private>");
            p.sendMessage(Integer.toString(length));
            e.printStackTrace();
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

    private void chestSetPublic(Player p)
    {
        String location = getTargetChest(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"chest to " + ChatColor.RED + "PUBLIC");
                plugin.saveConfig();

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your chest to" + ChatColor.RED + " PUBLIC");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set your chest to " + ChatColor.RED + "PUBLIC");
                plugin.saveConfig();
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your chest");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That chest is already public");
        }
    }

    private void chestSetPrivate(Player p)
    {
        String location = getTargetChest(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"chest to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your chest to" + ChatColor.RED + " PRIVATE");
                    plugin.saveConfig();
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your chest to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your chest");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That chest is already private");
        }
    }

    private void chestGetOwner(Player p)
    {
        String location = getTargetChest(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That chest belongs to " + ChatColor.GOLD + ownerName);

        }
    }

    private void chestAddFriend(Player p, String[] args)
    {
        String location = getTargetChest(p);
        if(plugin.getConfig().contains(location))
        {
            String ownerUUID = plugin.getConfig().get(location + ".owner").toString();
            String playerUUID = p.getUniqueId().toString();
            Player target = Bukkit.getPlayerExact(args[2]);
            if(target != null)
            {
                String targetUUID = target.getUniqueId().toString();
                if(playerUUID.contentEquals(ownerUUID))
                {
                    if(plugin.getConfig().contains(location + ".friends"))
                    {
                        String friends = plugin.getConfig().get(location + ".friends").toString();
                        List<String> friendsList = null;
                        String[] friendsArray = friends.split(",");
                        for(String friend : friendsArray)
                        {
                            friendsList.add(friend);
                        }
                        friendsList.add(targetUUID);

                        String updatedFriends = null;
                        for(String friend : friendsList)
                        {
                            updatedFriends = updatedFriends + friend + ",";
                        }

                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that chest");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that chest");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that chest");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that chest");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }

    private void chestRemoveFriend(Player p, String[] args)
    {
        String location = getTargetChest(p);
        if(plugin.getConfig().contains(location))
        {
            String ownerUUID = plugin.getConfig().get(location + ".owner").toString();
            String playerUUID = p.getUniqueId().toString();
            Player target = Bukkit.getPlayerExact(args[2]);
            if(target != null)
            {
                String targetUUID = target.getUniqueId().toString();
                if(playerUUID.contentEquals(ownerUUID))
                {
                    if(plugin.getConfig().contains(location + ".friends"))
                    {
                        String friends = plugin.getConfig().get(location + ".friends").toString();
                        List<String> friendsList = null;
                        String[] friendsArray = friends.split(",");
                        for(String friend : friendsArray)
                        {
                            friendsList.add(friend);
                        }
                        if(friendsList.contains(targetUUID))
                        {
                            friendsList.remove(targetUUID);
                        }

                        String updatedFriends = null;
                        for(String friend : friendsList)
                        {
                            updatedFriends = updatedFriends + friend + ",";
                        }

                        p.sendMessage(ChatColor.BLUE + "You revoked " + ChatColor.GOLD +  target.getDisplayName() + "'s "
                                + ChatColor.BLUE + "access to that chest");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that chest");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You revoked " + ChatColor.GOLD +  target.getDisplayName() + "'s "
                                + ChatColor.BLUE + "access to that chest");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that chest");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
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

        location = "chest." + location;

        return location;
    }

    private void changeChestOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        plugin.getConfig().set(location + ".isPublic", false);
        p.sendMessage(ChatColor.BLUE + "You changed the chest owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the chest owner");
        plugin.saveConfig();
    }
}
