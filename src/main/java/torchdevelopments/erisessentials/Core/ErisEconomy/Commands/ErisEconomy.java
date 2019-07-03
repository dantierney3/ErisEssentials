package torchdevelopments.erisessentials.Core.ErisEconomy.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation.AddFunds.addFunds;
import static torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation.GetFunds.getBalance;
import static torchdevelopments.erisessentials.Core.ErisEconomy.EconomyManipulation.GiveFunds.sendMoney;

public class ErisEconomy implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");
        try
        {
            if(sender instanceof Player)
            {
                Player p = (Player) sender;

                switch(args[0])
                {
                    case "get":
                        if(args[1].contentEquals("balance"))
                        {
                            getBalance(p, plugin);
                            break;
                        }
                    case "add":
                        if(args[1].contentEquals("money"))
                        {
                            addFunds(p, plugin, args);
                        }
                        break;
                    case "send":
                        if(args[1].contentEquals("money"))
                        {
                            sendMoney(p, plugin, args);
                        }
                }
            }
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
