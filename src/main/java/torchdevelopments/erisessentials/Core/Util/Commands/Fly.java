package torchdevelopments.erisessentials.Core.Util.Commands;

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
