package torchdevelopments.erisessentials.Core.ChestLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class ChestListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerPlaceChest(BlockPlaceEvent e)
    {
        if (e.getBlockPlaced().getType().equals(Material.CHEST))
        {

            Player p = e.getPlayer();
            Location loc = e.getBlockPlaced().getLocation();

            String uuid = p.getUniqueId().toString();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "chest." + location;

            if(plugin.getConfig().contains(location))
            {
                if(plugin.getConfig().get(location) == null)
                {
                    plugin.getConfig().set(location + ".owner",uuid);
                    plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(p.getDisplayName()));
                    plugin.getConfig().set(location + ".isPublic", false);
                    plugin.saveConfig();
                }
                else
                {
                    return;
                }
            }
            else
            {
                plugin.getConfig().set(location + ".owner",uuid);
                plugin.getConfig().set(location + ".ownerName", ChatColor.stripColor(p.getDisplayName()));
                plugin.getConfig().set(location + ".isPublic", false);
                plugin.saveConfig();
            }

        }

    }

}


