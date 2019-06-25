package torchdevelopments.erisessentials.Core.Util.Commands.AFK;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class Afk implements CommandExecutor {

    Plugin plugin = Bukkit.getPluginManager().getPlugin("ErisEssentials");

    private static ArrayList<Player> afkPlayers = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        if(afkPlayers.contains(player))
        {
            player.setInvulnerable(false);
            player.setCollidable(true);

            afkPlayers.remove(player);

            if(afkPlayers.isEmpty())
            {
                HandlerList.unregisterAll(new onPlayerMoveAFK());
            }

            player.setDisplayName(ChatColor.stripColor(player.getDisplayName()));
            player.setPlayerListName(ChatColor.stripColor(player.getPlayerListName()));

            if(player.getUniqueId() == Bukkit.getPlayerUniqueId("Erisdar"))
            {
                player.setDisplayName(ChatColor.GOLD + player.getDisplayName()+ ChatColor.WHITE);
                player.setPlayerListName(ChatColor.GOLD + player.getPlayerListName() + ChatColor.WHITE);
            }
            
            player.sendMessage(ChatColor.GRAY + "You are no longer AFK!");
            System.out.println(player.getDisplayName() + " is no longer AFK");
        }
        else if (!afkPlayers.contains(player))
        {
            player.setInvulnerable(true);
            player.setCollidable(false);
            if (afkPlayers.isEmpty())
            {
                Bukkit.getServer().getPluginManager().registerEvents(new onPlayerMoveAFK(), plugin);
            }

            afkPlayers.add(player);
            player.setDisplayName(ChatColor.GRAY + player.getDisplayName());
            player.setPlayerListName(ChatColor.GRAY + player.getPlayerListName());

            player.sendMessage(ChatColor.GRAY + "You are now AFK!");
            System.out.println(player.getDisplayName() + " went AFK");
        }

        return false;
    } // End onCommand

    public static ArrayList<Player> getAfkPlayers() {
        return afkPlayers;
    }

    public static void setAfkPlayers(ArrayList<Player> afkPlayers) {
        Afk.afkPlayers = afkPlayers;
    }
}
