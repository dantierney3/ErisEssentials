package torchdevelopments.erisessentials.Core.FurnaceLocker.Listeners;

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

public class FurnaceInteractListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerFurnaceInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(e.getClickedBlock().getType().equals(Material.FURNACE))
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
                location = "furnace." + location;

                if (plugin.getConfig().contains(location))
                {
                    String lockedfurnace;
                    lockedfurnace = (String) plugin.getConfig().get(location + ".owner");

                    if(p.isOp() && !playerUUID.contentEquals(lockedfurnace))
                    {
                        String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                        p.sendMessage(ChatColor.BLUE + "This is " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "furnace");

                    }
                    else if(!playerUUID.contentEquals(lockedfurnace) && (Boolean) plugin.getConfig().get(location + ".isPublic"))
                    {
                        String ownerName = (String) plugin.getConfig().get(location + ".ownerName");

                        p.sendMessage(ChatColor.BLUE + "Accessing " + ChatColor.GOLD + ownerName +  "'s " + ChatColor.BLUE + "public furnace");
                    }
                    else if(!playerUUID.contentEquals(lockedfurnace) && !(Boolean) plugin.getConfig().get(location + ".isPublic"))
                    {
                        e.setCancelled(true);
                        p.sendMessage(ChatColor.RED + "This is not your furnace!");
                    }
                }
                else
                {
                    p.sendMessage(ChatColor.RED + "This furnace is unprotected!");
                }
            }
        }
    }
}
