package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class SetPrivate {

    public static void setBlockPrivate(Player p, String location, String block, Plugin plugin)
    {
        if((boolean) plugin.getConfig().get(location + ".isPublic"))
        {
            if(p.isOp())
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set " + plugin.getConfig().get(location + ".ownerName").toString()
                        + "'s " + ChatColor.BLUE + block + " to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();

                UUID ownerUUID = UUID.fromString(plugin.getConfig().get(location + ".owner").toString());
                Player owner = Bukkit.getPlayer(ownerUUID);

                if (owner != null)
                {
                    owner.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " set your " + block + " to" + ChatColor.RED + " PRIVATE");
                    plugin.saveConfig();
                }
            }
            else if(p.getUniqueId().toString().equals(plugin.getConfig().get(location + ".owner").toString()))
            {
                plugin.getConfig().set(location + ".isPublic", false);
                p.sendMessage(ChatColor.BLUE + "You set your " + block + " to " + ChatColor.RED + "PRIVATE");
                plugin.saveConfig();
            }
            else
            {
                p.sendMessage(ChatColor.RED + "That is not your " + block);
            }
        }
        else
        {
            p.sendMessage(ChatColor.RED + "That " + block + " is already private");
        }
    }
}
