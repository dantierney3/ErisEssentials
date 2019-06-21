package torchdevelopments.erisessentials.Core.Util.CustomGreetings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CustomFarewell implements Listener {

    @EventHandler
    void onPlayerLeave(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        e.setQuitMessage("Thanks for stopping by " + ChatColor.GOLD + player.getDisplayName());
    }
}
