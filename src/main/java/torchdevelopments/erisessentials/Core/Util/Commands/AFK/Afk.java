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

            afkPlayers.remove(player);

            if(afkPlayers.isEmpty())
            {
                HandlerList.unregisterAll(new onPlayerMoveAFK());
            }

            player.sendMessage(ChatColor.GRAY + "You are no longer AFK!");
        }
        else if (!afkPlayers.contains(player))
        {
            player.setInvulnerable(true);
            if (afkPlayers.isEmpty())
            {
                Bukkit.getServer().getPluginManager().registerEvents(new onPlayerMoveAFK(), plugin);
            }

            afkPlayers.add(player);

            player.sendMessage(ChatColor.GRAY + "You are now AFK!");
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