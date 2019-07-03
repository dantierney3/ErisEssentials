package torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation.GetFunds.getBalance;

public class AddFunds {

    public static void addFunds(Player p, Plugin plugin, String[] args)
    {
        String playerUUID = p.getUniqueId().toString();

        if(p.isOp())
        {
            Player target = Bukkit.getPlayerExact(args[2]);

            String targetUUID = target.getUniqueId().toString();

            if(plugin.getConfig().contains("economy." + targetUUID))
            {
                double total = Double.valueOf(plugin.getConfig().get("economy." + targetUUID).toString());
                total += Double.valueOf(args[3]);
                plugin.getConfig().set("economy." + targetUUID, total);
                plugin.saveConfig();

                String targetName = target.getDisplayName();

                p.sendMessage(ChatColor.BLUE + "The funds have been added to " + ChatColor.GOLD + targetName + ChatColor.GOLD + "'s" + ChatColor.BLUE + " balance");
                target.sendMessage(ChatColor.BLUE + "Funds have been added to your account");
                getBalance(target,plugin);
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command");
        }
    }
}
