package torchdevelopments.erisessentials.Core.Util.Commands.AFK;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class onPlayerMoveAFK implements Listener {

    private static ArrayList<Player> listAFK = Afk.getAfkPlayers();

    int i = 0;

    @EventHandler
    void onPlayerMoveAFKEvent(PlayerMoveEvent e)
    {

        listAFK = Afk.getAfkPlayers();
        Player player = e.getPlayer();
        if(listAFK.contains(player)){
            e.setCancelled(true);
            if (i >= 10)
            {
                player.sendMessage(ChatColor.RED + "You are currently AFK!");
                i = 0;
            }
            else
            {
                i++;
            }
        }
    }
}
