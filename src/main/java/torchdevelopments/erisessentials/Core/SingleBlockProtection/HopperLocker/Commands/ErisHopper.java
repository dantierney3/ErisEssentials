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

package torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.AddFriend.addFriendToBlock;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.ClaimUnownedBlock.claimBlock;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.GetTargetBlock.targetBlockLocation;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.GetOwner.*;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.RemoveFriend.removeFriendFromBlock;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.SetOwner.setBlockOwner;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.SetPrivate.setBlockPrivate;
import static torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil.SetPublic.setBlockPublic;

public class ErisHopper implements CommandExecutor {


    private Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        try
        {
            if (sender instanceof Player)
            {
                Player p = (Player) sender;

                // Check that player is targeting a hopper

                if(p.getTargetBlock(5).getType().equals(Material.HOPPER))
                {
                    String block = "hopper";
                    String location = "hopper." + targetBlockLocation(p);

                    switch(args[0])
                    {
                        case "get":
                            getBlockOwner(p, location,block,plugin);
                            break;
                        case "set":
                            switch(args[1])
                            {
                                case "owner":
                                    setBlockOwner(p,location,block,plugin,args);
                                    break;
                                case "public":
                                    setBlockPublic(p,location,block,plugin);
                                    break;
                                case "private":
                                    setBlockPrivate(p,location,block,plugin);
                                    break;
                            }
                            break;
                        case "add":
                            if(args[1].contentEquals("friend"))
                            {
                                addFriendToBlock(p,location,block,plugin,args);
                                break;
                            }
                            break;
                        case "remove" :

                            if(args[1].contentEquals("friend"))
                            {
                                removeFriendFromBlock(p,location,block,plugin,args);
                                break;
                            }
                            break;
                        case "claim":
                            claimBlock(p,location,block,plugin);
                            break;

                    }
                } // End if
                else
                {
                    p.sendMessage(ChatColor.RED + "That block is not a Hopper!");
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
            p.sendMessage(ChatColor.RED + "/hopper <get/set/add/remove> <owner/friend/public/private>");
            p.sendMessage(Integer.toString(length));
            e.printStackTrace();
        }

        return false;
    }

}
