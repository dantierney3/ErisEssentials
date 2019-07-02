package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class GetOwner {

    public static void getBlockOwner(Player p, String location, String block, Plugin plugin)
    {
        if(plugin.getConfig().contains(location))
        {
            String ownerName = plugin.getConfig().get(location + ".ownerName").toString();


            p.sendMessage(ChatColor.BLUE + "That " + block + " to " + ChatColor.GOLD + ownerName);

        }
    }
}
