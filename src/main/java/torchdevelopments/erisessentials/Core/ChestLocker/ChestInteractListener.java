package torchdevelopments.erisessentials.Core.ChestLocker;

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

public class ChestInteractListener implements Listener {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    @EventHandler
    public void onPlayerChestInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            if(e.getClickedBlock().getType().equals(Material.CHEST))
            {
                Player p = e.getPlayer();
                String code = p.getUniqueId().toString();
                Location loc = e.getClickedBlock().getLocation();
                int x = loc.getBlockX();
                int y = loc.getBlockY();
                int z = loc.getBlockZ();
                String location = Integer.toString(x);
                location += Integer.toString(y);
                location += Integer.toString(z);

                String lockedChest;
                lockedChest = (String) plugin.getConfig().get(location);

                p.sendMessage(code);
                p.sendMessage(lockedChest);

                if(!code.contentEquals(lockedChest))
                {
                    e.setCancelled(true);
                    p.sendMessage(ChatColor.RED + "This is not your chest!");
                }
            }
        }
    }
}
