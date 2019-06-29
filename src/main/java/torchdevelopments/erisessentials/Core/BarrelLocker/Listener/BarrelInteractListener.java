package torchdevelopments.erisessentials.Core.BarrelLocker.Listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

public class BarrelInteractListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerBarrelInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(e.getClickedBlock().getType().equals(Material.BARREL))
            {
                Player p = e.getPlayer();
                String playerUUID = p.getUniqueId().toString();

                Location loc = e.getClickedBlock().getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();

                String location = Integer.toString(x);
                location += Integer.toString(y);
                location += Integer.toString(z);
                location = "barrel." + location;

                if (plugin.getConfig().contains(location))
                {
                    String lockedBarrel;
                    lockedBarrel = (String) plugin.getConfig().get(location + ".owner");

                    if(p.isOp() && !playerUUID.contentEquals(lockedBarrel))
                    {
                        String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                        p.sendMessage(ChatColor.BLUE + "This is " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "barrel");

                    }
                    else if(!playerUUID.contentEquals(lockedBarrel) && (Boolean) plugin.getConfig().get(location + ".isPublic"))
                    {
                        String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                        p.sendMessage(ChatColor.BLUE + "Accessing " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "public barrel");
                    }
                    else if(!playerUUID.contentEquals(lockedBarrel) && !(Boolean) plugin.getConfig().get(location + ".isPublic"))
                    {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "This is not your barrel!");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "This barrel is unprotected!");
                }
            }
        }
    }
}
