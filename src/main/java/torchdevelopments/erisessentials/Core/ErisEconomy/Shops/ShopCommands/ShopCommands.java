package torchdevelopments.erisessentials.Core.ErisEconomy.Shops.ShopCommands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ShopCommands implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player p = (Player) sender;

        Location chestLocation = p.getTargetBlock(5).getLocation();

        String playerUUID = p.getUniqueId().toString();

        int x = chestLocation.getBlockX();
        int y = chestLocation.getBlockY();
        int z = chestLocation.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        String senderUUID = p.getUniqueId().toString();

        if (senderUUID == plugin.getConfig().get("chest." + location + ".owner"))
        {
            
        }

        return false;
    }
}
