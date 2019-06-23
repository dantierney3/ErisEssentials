package torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep;

import com.sun.jmx.snmp.EnumRowStatus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import torchdevelopments.erisessentials.Core.ErisEssentials;

public class MultiPlayerSleep implements Listener
{



    @EventHandler
    void onBedEnter(PlayerBedEnterEvent e) {

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ErisEssentials");


        // Gets the player data
        Player player = e.getPlayer();
        String pName = player.getDisplayName();

        if(!player.getWorld().isDayTime())
        {
            Bukkit.broadcastMessage(ChatColor.GOLD + pName + ChatColor.WHITE + " is now sleeping!");

            new BukkitRunnable()
            {

                @Override
                public void run()
                {
                    if(player.isSleeping())
                    {
                        // What you want to schedule goes here
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "time set 0");
                        RainTask.weatherToRain();
                        ClearTask.weatherToClear();
                    }
                }

            }.runTaskLater(plugin, 200);

        }


    } // End onBedEnter


} // End MultiPlayerSleep



