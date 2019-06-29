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

package torchdevelopments.erisessentials.Core.Util.Commands.AFK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Afk implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    private static ArrayList<Player> afkPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(afkPlayers.contains(player))
        {
            player.setInvulnerable(false);
            player.setCollidable(true);

            afkPlayers.remove(player);

            if(afkPlayers.isEmpty())
            {
                HandlerList.unregisterAll(new onPlayerMoveAFK());
            }

            player.setDisplayName(ChatColor.stripColor(player.getDisplayName()));
            player.setPlayerListName(ChatColor.stripColor(player.getPlayerListName()));

            if(player.getUniqueId() == Bukkit.getPlayerUniqueId("Erisdar"))
            {
                player.setDisplayName(ChatColor.GOLD + player.getDisplayName()+ ChatColor.WHITE);
                player.setPlayerListName(ChatColor.GOLD + player.getPlayerListName() + ChatColor.WHITE);
            }

            player.sendMessage(ChatColor.GRAY + "You are no longer AFK!");
            for (Player players : Bukkit.getOnlinePlayers())
            {
                if(players != player)
                {
                    players.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.BLUE + " is no longer AFK!");
                }
            }
            System.out.println(player.getDisplayName() + " is no longer AFK");
        }
        else if (!afkPlayers.contains(player))
        {
            player.setInvulnerable(true);
            player.setCollidable(false);
            if (afkPlayers.isEmpty())
            {
                Bukkit.getServer().getPluginManager().registerEvents(new onPlayerMoveAFK(), plugin);
            }

            afkPlayers.add(player);

            for (Player players : Bukkit.getOnlinePlayers())
            {
                if(players != player)
                {
                    players.sendMessage(ChatColor.GOLD + player.getDisplayName() + ChatColor.BLUE + " is now AFK!");
                }
            }
            player.setDisplayName(ChatColor.GRAY + player.getDisplayName());
            player.setPlayerListName(ChatColor.GRAY + player.getPlayerListName());

            player.sendMessage(ChatColor.GRAY + "You are now AFK!");

            System.out.println(player.getDisplayName() + " went AFK");
        }

        return false;
    } // End onCommand

    public static ArrayList<Player> getAfkPlayers() {
        return afkPlayers;
    }

    public static void setAfkPlayers(ArrayList<Player> afkPlayers) {
        Afk.afkPlayers = afkPlayers;
    }
}
