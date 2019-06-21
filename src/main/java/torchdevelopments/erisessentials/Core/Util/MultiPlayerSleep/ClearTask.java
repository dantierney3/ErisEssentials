package torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ClearTask {

    public static void weatherToClear()
    {

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ErisEssentials");

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "weather clear");

            }
        }.runTaskLater(plugin, 100);
    }
}
