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

package torchdevelopments.erisessentials.Core.Util.Commands.PlayerInventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class ViewPlayerInventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player)
        {
            Player p = (Player) sender;
            try
            {
                if (p.isOp() && args.length > 0)
                {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    Inventory inv = target.getInventory();
                    p.sendMessage(ChatColor.BLUE + "Opening " + ChatColor.GOLD + target.getDisplayName() + "'s " + ChatColor.BLUE + "inventory...");
                    System.out.println(p.getDisplayName() + " opened " + target.getDisplayName() + "'s inventory");
                    p.openInventory(inv);
                }
                else if (p.isOp() && args.length == 0)
                {
                    p.sendMessage(ChatColor.RED + "You must enter a target!");
                    p.sendMessage(ChatColor.RED + "/inv <Player>");
                }
                else if (!p.isOp())
                {
                    p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
                }
            }
            catch(NullPointerException ex)
            {
                p.sendMessage(ChatColor.RED + "That player is offline...");
                System.out.println(p.getDisplayName() + " tried to access an offline player's inventory");
            }

        }

        return false;
    }
}
