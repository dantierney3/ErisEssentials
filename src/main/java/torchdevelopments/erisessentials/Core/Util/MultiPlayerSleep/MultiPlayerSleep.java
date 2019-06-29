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

package torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep;

import com.sun.jmx.snmp.EnumRowStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import torchdevelopments.erisessentials.Core.ErisEssentials;

public class MultiPlayerSleep implements Listener
{



    @EventHandler
    void onBedEnter(PlayerBedEnterEvent e) {

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ErisEssentials");


        // Gets the player data
        Player player = e.getPlayer();
        String pName = player.getDisplayName();

        if(!player.getWorld().isDayTime())
        {
            Bukkit.broadcastMessage(ChatColor.GOLD + pName + ChatColor.WHITE + " is now sleeping!");

            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    if(player.isSleeping())
                    {
                        int time = Integer.valueOf(Long.toString(player.getWorld().getTime()));
                        time = 23500 - time;
                        String timeToAdd = Integer.toString(time);
                        // time add not time set to ensure that the game passes to the next day
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time add " + timeToAdd);
                        RainTask.weatherToRain();
                        ClearTask.weatherToClear();
                    }
                }

            }.runTaskLater(plugin, 200);

        }


    } // End onBedEnter


} // End MultiPlayerSleep



