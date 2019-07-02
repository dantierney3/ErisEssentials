package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class SetOwner {
    
    public static void setBlockOwner(Player p, String location, String block, Plugin plugin, String[] args)
    {
        if(p.isOp())
        {

            Player target = Bukkit.getPlayerExact(args[2]);

            if(plugin.getConfig().contains(location)) // Changes the owner if the hopper is protected
            {
                changeBlockOwner(p, target, location, block, plugin);
            }
            else // Creates a protected hopper if the block is unprotected
            {
                changeBlockOwner(p, target, location, block, plugin);
            }
        }
        else
        {
            Player target = Bukkit.getPlayerExact(args[2]);
            String hopperOwner = location + ".owner";

            // Checks if the hopper is protected and the sender owns the hopper
            if(plugin.getConfig().contains(location) && plugin.getConfig().get(hopperOwner).equals(p.getUniqueId().toString()))
            {
                changeBlockOwner(p, target, location, block, plugin);
            }
        }
    }

    private static void changeBlockOwner (Player p, Player target, String location, String block, Plugin plugin)
    {
        plugin.getConfig().set(location + ".owner", target.getUniqueId().toString());
        plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(target.getDisplayName()));
        plugin.getConfig().set(location + ".isPublic", false);
        p.sendMessage(ChatColor.BLUE + "You changed the " + block + " owner to " + ChatColor.GOLD + target.getDisplayName());
        target.sendMessage(ChatColor.GOLD + p.getDisplayName() + ChatColor.BLUE + " made you the " + block + " owner");
        plugin.saveConfig();
    }
}
