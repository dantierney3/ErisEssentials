package torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask {

    public static void saveAll()
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ErisEssentials");

        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "save-all");
            }
        }.runTaskLater(plugin, 100);
    }
}
