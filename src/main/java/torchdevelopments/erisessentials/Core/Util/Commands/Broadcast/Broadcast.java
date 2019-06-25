package torchdevelopments.erisessentials.Core.Util.Commands.Broadcast;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (sender instanceof Player)
        {
            Player p = (Player) sender;
            if(p.isOp())
            {
                if(args.length > 0)
                {
                    int count = args.length;
                    String message = "[SERVER BROADCAST]";
                    for (int i = 0; i < count; i++)
                    {
                        message = message + " " + args[i];
                    }
                    message = ChatColor.BOLD + message;
                    Bukkit.broadcastMessage(ChatColor.RED+ message);
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "No message detected!");
                    p.sendMessage(ChatColor.RED + "/broadcast <message>");
                }
            }
            else
            {
                p.sendMessage(ChatColor.RED + "You do not have permission to use that command!");
            }
        }

        return false;
    }
}
