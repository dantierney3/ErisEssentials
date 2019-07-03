package torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation.GetFunds.getBalance;

public class GiveFunds {

    public static void sendMoney (Player p, Plugin plugin, String[] args)
    {
        Player target = Bukkit.getPlayerExact(args[2]);

        String playerUUID = p.getUniqueId().toString();
        String targetUUID = target.getUniqueId().toString();

        double senderBal = Double.valueOf(plugin.getConfig().get("economy." + playerUUID).toString());
        double targetBal = Double.valueOf(plugin.getConfig().get("economy." + targetUUID).toString());
        double amount = Double.valueOf(args[3]);

        if(senderBal > amount)
        {
            senderBal -= amount;
            targetBal += amount;

            p.sendMessage(ChatColor.BLUE + "You sent " + ChatColor.GOLD + "£" +
                    amount + ChatColor.BLUE + " to " + ChatColor.GOLD +target.getDisplayName());

            target.sendMessage(ChatColor.BLUE + "You received " + ChatColor.GOLD + "£" +
                    amount + ChatColor.BLUE + " from " + ChatColor.GOLD + p.getDisplayName());

            plugin.getConfig().set("economy." + playerUUID, senderBal);
            plugin.getConfig().set("economy." + targetUUID, targetBal);
            plugin.saveConfig();

            getBalance(p,plugin);
            getBalance(target,plugin);
        }
    }
}
