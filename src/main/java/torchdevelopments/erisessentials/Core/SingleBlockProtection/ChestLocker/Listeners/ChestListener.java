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

package torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class ChestListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerPlaceChest(BlockPlaceEvent e)
    {
        if (e.getBlockPlaced().getType().equals(Material.CHEST))
        {

            Player p = e.getPlayer();
            Location loc = e.getBlockPlaced().getLocation();

            String playerUUID = p.getUniqueId().toString();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "chest." + location;

            if(plugin.getConfig().contains(location))
            {

                if(plugin.getConfig().get(location) == null)
                {
                    plugin.getConfig().set(location + ".owner",playerUUID);
                    plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(p.getDisplayName()));
                    plugin.getConfig().set(location + ".isPublic", false);
                    plugin.saveConfig();
                }
                else
                {
                    return;
                }
            }
            else if (checkNearbyBlocks(p,e))
            {
                e.setCancelled(true);
                p.sendMessage(ChatColor.RED + "You're trying to place a chest next to a chest you don't own!");
                return;
            }
            else
            {
                plugin.getConfig().set(location + ".owner",playerUUID);
                plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(p.getDisplayName()));
                plugin.getConfig().set(location + ".isPublic", false);
                plugin.saveConfig();
            }

        }

    }

    private boolean checkNearbyBlocks(Player p, BlockPlaceEvent e)
    {
        // Integer is added to if there is a chest adjacent to the placed block not belonging to the player
        int nearbyChestWrongOwner = 0;

        // Get the blocks that are in adjacent blocks
        Block posX = e.getBlockPlaced().getRelative(1,0,0);
        Block posZ = e.getBlockPlaced().getRelative(0,0,1);
        Block negX = e.getBlockPlaced().getRelative(-1,0,0);
        Block negZ = e.getBlockPlaced().getRelative(0,0,-1);

        // Add blocks to a list
        Block[] nearbyBlocks = {posX, posZ, negX, negZ};

        // Iterate through the list of adjacent blocks to check if one is a chest
        for( Block block : nearbyBlocks)
        {
            // Get the block location
            Location loc = block.getLocation();
            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "chest." + location;

            if(plugin.getConfig().contains(location))
            {
                String ownerUUID = plugin.getConfig().get(location + ".owner").toString();
                String playerUUID = p.getUniqueId().toString();

                if(!playerUUID.contentEquals(ownerUUID))
                {
                    nearbyChestWrongOwner ++;
                }
            }

        }

        if(nearbyChestWrongOwner > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}


