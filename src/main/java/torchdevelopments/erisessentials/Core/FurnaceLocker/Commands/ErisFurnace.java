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

import java.util.List;
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
                        case "add":
                            if(args[1].contentEquals("friend"))
                            {
                                furnaceAddFriend(p,args);
                                break;
                            }
                            break;
                        case "remove":
                            if(args[1].contentEquals("friend"))
                            {
                                furnaceRemoveFriend(p, args);
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

        String location = getTargetFurnace(p);

        if(p.isOp())
        {

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the furnace is protected
            {
                changeFurnaceOwner(p, target, location);
            }
            else // Creates a protected furnace if the block is unprotected
            {
                changeFurnaceOwner(p, target, location);
            }
        }
        else
        {
            Player target = Bukkit.getPlayerExact(args[2]);
            String furnaceOwner = location + ".owner";

            // Checks if the furnace is protected and the sender owns the furnace
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(furnaceOwner).equals(p.getUniqueId().toString()))
            {
                changeFurnaceOwner(p, target, location);
            }
        }
    } // End furnaceSetOwner

    private void furnaceSetPublic(Player p, String[] args)
    {
        String location = getTargetFurnace(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                plugin.saveConfig();
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
                plugin.saveConfig();
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
        String location = getTargetFurnace(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                plugin.saveConfig();
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
                plugin.saveConfig();
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
        String location = getTargetFurnace(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That furnace belongs to " + ChatColor.GOLD + ownerName);

        }
    }

    private void furnaceAddFriend(Player p, String[] args)
    {
        String location = getTargetFurnace(p);
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
                                + ChatColor.BLUE + " access to that furnace");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that furnace");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that furnace");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that furnace");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }

    private void furnaceRemoveFriend(Player p, String[] args)
    {
        String location = getTargetFurnace(p);
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
                                + ChatColor.BLUE + "access to that furnace");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that furnace");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You revoked " + ChatColor.GOLD +  target.getDisplayName() + "'s "
                                + ChatColor.BLUE + "access to that furnace");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that furnace");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }

    private String getTargetFurnace(Player p)
    {
        Block targetFurnace = p.getTargetBlockExact(5);
        Location targetFurnaceLoc = targetFurnace.getLocation();

        int x = targetFurnaceLoc.getBlockX();
        int y = targetFurnaceLoc.getBlockY();
        int z = targetFurnaceLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        location = "furnace." + location;

        return location;
    }

    private void changeFurnaceOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        plugin.getConfig().set(location + ".isPublic", false);
        p.sendMessage(ChatColor.BLUE + "You changed the furnace owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the furnace owner");
        plugin.saveConfig();
    }
}
