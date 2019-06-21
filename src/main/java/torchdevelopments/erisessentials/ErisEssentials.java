package torchdevelopments.erisessentials;

import org.bukkit.plugin.java.JavaPlugin;

public final class ErisEssentials extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // System message at start of startup process
        System.out.println("ErisEssentials is starting up!");

        // Startup process and console outputs to be entered here

        // System message at the end of the startup process
        System.out.println("ErisEssentials has finished loading!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // System message at start of shutdown process
        System.out.println("ErisEssentials has started shutting down!");

        // Shutdown process and console outputs to be entered here

        // System message at end of shutdown process
        System.out.println("ErisEssentials has finished shutting down!");
    }
}
