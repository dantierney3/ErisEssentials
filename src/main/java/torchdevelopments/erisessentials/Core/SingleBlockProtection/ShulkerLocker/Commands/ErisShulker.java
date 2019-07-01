/*
 * Copyright © Daniel Tierney, 2019. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
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

package torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Commands;

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

public class ErisShulker implements CommandExecutor {


    private Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                // Check that player is targeting a shulker

                if(p.getTargetBlock(5).getType().equals(Material.SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.WHITE_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.ORANGE_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.MAGENTA_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.LIGHT_BLUE_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.YELLOW_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.LIME_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.PINK_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.GRAY_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.LIGHT_GRAY_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.CYAN_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.PURPLE_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.BLUE_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.BROWN_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.GREEN_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.RED_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else if(p.getTargetBlock(5).getType().equals(Material.BLACK_SHULKER_BOX))
                {
                    shulkerCommandTree(p, args);
                } // End if
                else
                {
                    p.sendMessage(ChatColor.RED + "That block is not a Shulker box!");
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
            p.sendMessage(ChatColor.RED + "/shulker <get/set/add/remove> <owner/friend/public/private>");
            p.sendMessage(Integer.toString(length));
            e.printStackTrace();
        }

        return false;
    }

    private void shulkerCommandTree(Player p, String[] args)
    {
        switch(args[0])
        {
            case "get":
                shulkerGetOwner(p);
                break;
            case "set":
                switch(args[1])
                {
                    case "owner":
                        shulkerSetOwner(p, args);
                        break;
                    case "public":
                        shulkerSetPublic(p);
                        break;
                    case "private":
                        shulkerSetPrivate(p);
                        break;
                }
                break;
            case "add":
                if(args[1].contentEquals("friend"))
                {
                    shulkerAddFriend(p, args);
                    break;
                }
                break;
            case "remove" :

                if(args[1].contentEquals("friend"))
                {
                    shulkerRemoveFriend(p, args);
                    break;
                }
                break;


        }
    }

    private void shulkerSetOwner(Player p, String[] args)
    {

        String location = getTargetShulker(p);

        if(p.isOp())
        {

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the shulker is protected
            {
                changeShulkerOwner(p, target, location);
            }
            else // Creates a protected shulker if the block is unprotected
            {
                changeShulkerOwner(p, target, location);
            }
        }
        else
        {
            Player target = Bukkit.getPlayerExact(args[2]);
            String shulkerOwner = location + ".owner";

            // Checks if the shulker is protected and the sender owns the shulker
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(shulkerOwner).equals(p.getUniqueId().toString()))
            {
                changeShulkerOwner(p, target, location);
            }
        }
    } // End shulkerSetOwner

    private void shulkerSetPublic(Player p)
    {
        String location = getTargetShulker(p);

        if(!(boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"shulker box to " + ChatColor.RED + "PUBLIC");
                plugin.saveConfig();

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your shulker box to" + ChatColor.RED + " PUBLIC");
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", true);
                p.sendMessage(ChatColor.BLUE + "You set your shulker box to " + ChatColor.RED + "PUBLIC");
                plugin.saveConfig();
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your shulker box");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That shulker box is already public");
        }
    }

    private void shulkerSetPrivate(Player p)
    {
        String location = getTargetShulker(p);

        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE +"shulker box to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your shulker box to" + ChatColor.RED + " PRIVATE");
                    plugin.saveConfig();
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your shulker box to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your shulker box");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That shulker box is already private");
        }
    }

    private void shulkerGetOwner(Player p)
    {
        String location = getTargetShulker(p);

        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That shulker box belongs to " + ChatColor.GOLD + ownerName);

        }
    }

    private void shulkerAddFriend(Player p, String[] args)
    {
        String location = getTargetShulker(p);
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
                                + ChatColor.BLUE + " access to that shulker box");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that shulker box");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that shulker box");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that shulker box");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }

    private void shulkerRemoveFriend(Player p, String[] args)
    {
        String location = getTargetShulker(p);
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
                                + ChatColor.BLUE + "access to that shulker box");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that shulker box");

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You revoked " + ChatColor.GOLD +  target.getDisplayName() + "'s "
                                + ChatColor.BLUE + "access to that shulker box");
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " revoked your access to that shulker box");

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }

    private String getTargetShulker(Player p)
    {
        Block targetShulker = p.getTargetBlockExact(5);
        Location targetShulkerLoc = targetShulker.getLocation();

        int x = targetShulkerLoc.getBlockX();
        int y = targetShulkerLoc.getBlockY();
        int z = targetShulkerLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        location = "shulker." + location;

        return location;
    }

    private void changeShulkerOwner (Player p, Player target, String location)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        plugin.getConfig().set(location + ".isPublic", false);
        p.sendMessage(ChatColor.BLUE + "You changed the shulker box owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the shulker box owner");
        plugin.saveConfig();
    }
}
