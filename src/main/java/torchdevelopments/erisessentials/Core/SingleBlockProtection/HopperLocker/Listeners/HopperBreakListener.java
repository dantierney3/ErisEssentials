package torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class HopperBreakListener implements Listener {


    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onHopperBreak (BlockBreakEvent e)
    {
        if(e.getBlock().getType().equals(Material.HOPPER))
        {
            Player p = e.getPlayer();
            String playerUUID = p.getUniqueId().toString();
            Location loc = e.getBlock().getLocation();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "hopper." + location;
            String lockedHopper = (String) plugin.getConfig().get(location + ".owner");

            if(plugin.getConfig().contains(location))
            {

                if(p.isOp() && !playerUUID.equals(lockedHopper)) {
                    String ownerName = (String) plugin.getConfig().get(location + ".ownerName");
                    p.sendMessage(ChatColor.RED + "You broke " + ChatColor.GOLD + ownerName + "'s " + ChatColor.RED + "hopper");
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                }
                else if (playerUUID.contentEquals(lockedHopper))
                {
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                }
                else if(!playerUUID.contentEquals(lockedHopper))
                {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "This is not your hopper!");
                }

            }

        }

    }
}
