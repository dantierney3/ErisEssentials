package torchdevelopments.erisessentials.Core.ChestLocker;

import org.bukkit.Bukkit;
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

            String code = p.getUniqueId().toString();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);

            System.out.println(location);
            System.out.println(code);

            if(plugin.getConfig().contains(location))
            {
                return;
            }

            plugin.getConfig().set(location,code);
            plugin.saveConfig();
        }

    }

}


