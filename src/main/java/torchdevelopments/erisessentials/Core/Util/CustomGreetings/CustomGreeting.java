package torchdevelopments.erisessentials.Core.Util.CustomGreetings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CustomGreeting implements Listener {

    @EventHandler
    void onPlayerJoin(PlayerJoinEvent e)
    {
        Player player = e.getPlayer();
        e.setJoinMessage("Welcome back to the Server " + ChatColor.GOLD + player.getDisplayName());
    }
}
