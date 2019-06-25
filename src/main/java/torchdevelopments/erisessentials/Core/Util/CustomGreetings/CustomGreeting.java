package torchdevelopments.erisessentials.Core.Util.CustomGreetings;

import org.bukkit.Bukkit;
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

        if(player.getUniqueId() == Bukkit.getPlayerUniqueId("Erisdar"))
        {
            player.setDisplayName(ChatColor.GOLD + player.getDisplayName()+ ChatColor.WHITE);
            player.setPlayerListName(ChatColor.GOLD + player.getPlayerListName() + ChatColor.WHITE);
        }
    }
}
