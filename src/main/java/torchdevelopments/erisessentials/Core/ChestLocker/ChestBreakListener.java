package torchdevelopments.erisessentials.Core.ChestLocker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class ChestBreakListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onChestBreak (BlockBreakEvent e)
    {
        if(e.getBlock().getType().equals(Material.CHEST))
        {
            Player p = e.getPlayer();
            String code = p.getUniqueId().toString();
            Location loc = e.getBlock().getLocation();

            int x = loc.getBlockX();
            int y = loc.getBlockY();
            int z = loc.getBlockZ();

            String location = Integer.toString(x);
            location += Integer.toString(y);
            location += Integer.toString(z);

            if(plugin.getConfig().contains(location))
            {
                String lockedChest = (String) plugin.getConfig().get(location);
                if(p.isOp() && !code.contentEquals(lockedChest))
                {
                    UUID uuid = UUID.fromString(lockedChest);
                    Player target = Bukkit.getServer().getPlayer(uuid);
                    if(target != null)
                    {
                        String name = target.getDisplayName();
                        p.sendMessage(ChatColor.RED + "You broke " + ChatColor.GOLD + name +  "'s " + ChatColor.RED + "chest");
                    }
                    else
                    {
                        p.sendMessage(ChatColor.RED + "You broke " + ChatColor.GOLD + "Someone" +  "'s " + ChatColor.RED + "chest");
                    }

                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();

                }
                else if (code.contentEquals(lockedChest))
                {
                    plugin.getConfig().set(location, null);
                    plugin.saveConfig();
                }
                else if(!code.contentEquals(lockedChest))
                {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "This is not your chest!");
                }
            }

        }
    }
}
