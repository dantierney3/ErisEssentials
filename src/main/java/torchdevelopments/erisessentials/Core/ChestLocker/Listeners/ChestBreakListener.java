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

package torchdevelopments.erisessentials.Core.ChestLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ChestBreakListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onChestBreak (BlockBreakEvent e)
    {
        if(e.getBlock().getType().equals(Material.CHEST))
        {
            Player p = e.getPlayer();
            String playerUUID = p.getUniqueId().toString();
            Location loc = e.getBlock().getLocation();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "chest." + location;
            String lockedChest = (String) plugin.getConfig().get(location + ".owner");

            if(plugin.getConfig().contains(location))
            {

                if(p.isOp() && !playerUUID.equals(lockedChest)) {
                    String ownerName = (String) plugin.getConfig().get(location + ".ownerName");
                    p.sendMessage(ChatColor.RED + "You broke " + ChatColor.GOLD + ownerName + "'s " + ChatColor.RED + "chest");
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                }
                else if (playerUUID.contentEquals(lockedChest))
                {
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                }
                else if(!playerUUID.contentEquals(lockedChest))
                {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "This is not your chest!");
                }

            }

        }

    }
}
