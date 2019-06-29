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

package torchdevelopments.erisessentials.Core.Util.Commands.Fly;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("fly"))
        {
            if(sender instanceof Player)
            {
                if(sender.isOp())
                {

                    Player player = (Player) sender;

                    if (args.length < 1)
                    {
                        fly(player);
                    }
                    else if(args.length >= 1)
                    {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        fly(target);
                    }

                } // End if Op
                else
                {
                    sender.sendMessage(ChatColor.RED + "You cannot perform this command!");
                } // End else Op
            } // End if Player
            else
            {
                System.out.println("You have to be a player to do that!");
            }
        } // End if Fly

        return false;
    } // End onCommand

    void fly(Player target)
    {
        Player player = target;
        // Sets player allowFlight to true if they aren't already flying
        if(!player.getAllowFlight())
        {
            player.setAllowFlight(true);
            player.sendMessage(ChatColor.AQUA + "You have flight enabled.");
        } // End if !playerFlying
        // Sets player allowFlight to false if the player is already flying
        else if (player.getAllowFlight())
        {
            player.setAllowFlight(false);
            player.sendMessage(ChatColor.AQUA + "You have flight disabled.");
        } // End else if playerFlying
    }
}
