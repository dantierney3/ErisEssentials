package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ClaimUnownedBlock {

    public static void claimBlock(Player p,String location, String block, Plugin plugin)
    {
        if(plugin.getConfig().contains(location))
        {
            p.sendMessage(ChatColor.RED + "That " + block + " is already protected");
        }
        else
        {
            plugin.getConfig().set(location + ".owner", p.getUniqueId().toString());
            plugin.getConfig().set(location + ".ownerName", p.getDisplayName());
            plugin.getConfig().set(location + ".isPublic", false);
            p.sendMessage(ChatColor.BLUE + "You claimed the unowned " + block);
            plugin.saveConfig();
        }
    }
}
