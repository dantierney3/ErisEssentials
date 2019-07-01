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

package torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class ShulkerInteractListener implements Listener {


    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");
    final String DELIMITER = ",";

    @EventHandler
    public void onPlayerShulkerInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            Player p = e.getPlayer();

            // Checks if the block is a variant of shulker box

            if(p.getTargetBlock(5).getType().equals(Material.SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.WHITE_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.ORANGE_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.MAGENTA_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.LIGHT_BLUE_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.YELLOW_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.LIME_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.PINK_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.GRAY_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.LIGHT_GRAY_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.CYAN_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.PURPLE_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.BLUE_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.BROWN_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.GREEN_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.RED_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if
            else if(p.getTargetBlock(5).getType().equals(Material.BLACK_SHULKER_BOX))
            {
                shulkerInteract(e);
            } // End if

        }


    }
    
    private void shulkerInteract(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        String playerUUID = p.getUniqueId().toString();

        Location loc = e.getClickedBlock().getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);
        location = "shulker." + location;

        String friends = null;
        String[] friendsArray = null;

        if (plugin.getConfig().contains(location + ".friends"))
        {
            friends = plugin.getConfig().get(location + ".friends").toString();

            friendsArray = friends.split(DELIMITER);
        }

        if (plugin.getConfig().contains(location))
        {
            String lockedShulker;
            lockedShulker = (String) plugin.getConfig().get(location + ".owner");

            if(p.isOp() && !playerUUID.contentEquals(lockedShulker))
            {
                String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                p.sendMessage(ChatColor.BLUE + "This is " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "shulker box");

            }
            else if(!playerUUID.contentEquals(lockedShulker) && (Boolean) plugin.getConfig().get(location + ".isPublic"))
            {
                String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                p.sendMessage(ChatColor.BLUE + "Accessing " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "public shulker box");
            }
            else if(!playerUUID.contentEquals(lockedShulker) && friendsArray != null)
            {
                for(String uuid : friendsArray)
                {
                    if(playerUUID.contentEquals(uuid))
                    {
                        String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                        p.sendMessage(ChatColor.BLUE + "Accessing " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "shulker box");

                        break;
                    }
                }
                // Break out of the if-else-if statements otherwise the last statement will also run true
                return;
            }
            else if(!playerUUID.contentEquals(lockedShulker) && !(Boolean) plugin.getConfig().get(location + ".isPublic"))
            {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "This is not your shulker box!");
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "This shulker box is unprotected!");
        }
    }
}
