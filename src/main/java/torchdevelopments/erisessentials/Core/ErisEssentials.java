package torchdevelopments.erisessentials.Core;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep.MultiPlayerSleep;

public final class ErisEssentials extends JavaPlugin{

    Plugin plugin = this;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // System message at start of startup process
        System.out.println("[ErisEssentials] ErisEssentials is starting up!");

        // Startup process and console outputs to be entered here

        // Register Multiplayer Sleep events
        System.out.println("[ErisEssentials] Registering Multiplayer Sleep Events");
        getServer().getPluginManager().registerEvents(new MultiPlayerSleep(), this);

        // System message at the end of the startup process
        System.out.println("[ErisEssentials] ErisEssentials has finished loading!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // System message at start of shutdown process
        System.out.println("[ErisEssentials] ErisEssentials has started shutting down!");

        // Shutdown process and console outputs to be entered here

        // System message at end of shutdown process
        System.out.println("[ErisEssentials] ErisEssentials has finished shutting down!");
    }
}
