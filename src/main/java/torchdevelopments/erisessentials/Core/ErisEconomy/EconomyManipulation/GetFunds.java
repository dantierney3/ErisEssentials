package torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetFunds {

    public static void getBalance(Player p, Plugin plugin)
    {
        String playerUUID = p.getUniqueId().toString();

        if(plugin.getConfig().contains("economy." + playerUUID))
        {
            double playerBal =  Double.valueOf(plugin.getConfig().get("economy." + playerUUID).toString());

            p.sendMessage(ChatColor.BLUE + "Your balance is :" + ChatColor.GOLD + " £" + playerBal);
        }
    }
}
