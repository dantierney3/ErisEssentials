package torchdevelopments.erisessentials.Core.FurnaceLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

public class FurnaceBreakListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onFurnaceBreak(BlockBreakEvent e) {
        if (e.getBlock().getType().equals(Material.FURNACE)) {
            Player p = e.getPlayer();
            String playerUUID = p.getUniqueId().toString();
            Location loc = e.getBlock().getLocation();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);
            location = "furnace." + location;
            String lockedfurnace = (String) plugin.getConfig().get(location + ".owner");

            if (plugin.getConfig().contains(location)) {

                if (p.isOp() && !playerUUID.equals(lockedfurnace)) {
                    String ownerName = (String) plugin.getConfig().get(location + ".ownerName");
                    p.sendMessage(ChatColor.RED + "You broke " + ChatColor.GOLD + ownerName + "'s " + ChatColor.RED + "furnace");
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                } else if (playerUUID.contentEquals(lockedfurnace)) {
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                } else if (!playerUUID.contentEquals(lockedfurnace)) {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "This is not your furnace!");
                }

            }

        }
    }
}
