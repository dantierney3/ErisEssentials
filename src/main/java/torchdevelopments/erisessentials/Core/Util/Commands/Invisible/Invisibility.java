package torchdevelopments.erisessentials.Core.Util.Commands.Invisible;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

public class Invisibility implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("invisible") && args.length == 1)
        {
            if(sender instanceof Player)
            {
                Player p = (Player) sender;

                if(p.isOp())
                {
                    if(args[0].contentEquals("true"))
                    {
                        for (Player players : Bukkit.getOnlinePlayers())
                        {
                            if(players != p)
                            {
                                players.hidePlayer(plugin, p);
                            }
                        }
                        p.sendMessage(ChatColor.BLUE + "You are now invisible!");
                    }
                    else if (args[0].contentEquals("false"))
                    {
                        for(Player players : Bukkit.getOnlinePlayers())
                        {
                            if (players != p)
                            {
                                players.showPlayer(plugin, p);
                            }
                        }

                        p.sendMessage(ChatColor.BLUE + "You are no longer invisible");
                    }
                }
            }
        }

        return false;
    }
}
