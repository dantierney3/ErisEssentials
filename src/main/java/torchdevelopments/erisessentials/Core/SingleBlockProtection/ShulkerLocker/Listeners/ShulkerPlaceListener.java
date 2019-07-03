package torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.plugin.Plugin;

public class ShulkerPlaceListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerPlaceShulker(BlockPlaceEvent e)
    {
            Player p = e.getPlayer();

            // Checks if the block is a variant of shulker box

            if (p.getTargetBlock(5).getType().equals(Material.SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.WHITE_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.ORANGE_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.MAGENTA_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.LIGHT_BLUE_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.YELLOW_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.LIME_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.PINK_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.GRAY_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.LIGHT_GRAY_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.CYAN_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.PURPLE_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.BLUE_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.BROWN_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.GREEN_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.RED_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if
            else if (p.getTargetBlock(5).getType().equals(Material.BLACK_SHULKER_BOX)) {
                shulkerPlace(e);
            } // End if

    }
    
    private void shulkerPlace(BlockPlaceEvent e)
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
        location = "shulker." + location;

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
