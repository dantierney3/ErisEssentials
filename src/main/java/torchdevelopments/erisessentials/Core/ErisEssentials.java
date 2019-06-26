package torchdevelopments.erisessentials.Core;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import torchdevelopments.erisessentials.Core.ChestLocker.ChestBreakListener;
import torchdevelopments.erisessentials.Core.ChestLocker.ChestInteractListener;
import torchdevelopments.erisessentials.Core.ChestLocker.ChestListener;
import torchdevelopments.erisessentials.Core.ChestLocker.Commands.ProtectChest;
import torchdevelopments.erisessentials.Core.ChestLocker.Commands.RemoveProtection;
import torchdevelopments.erisessentials.Core.ChestLocker.Commands.SetOwner;
import torchdevelopments.erisessentials.Core.Util.Commands.AFK.Afk;
import torchdevelopments.erisessentials.Core.Util.Commands.Broadcast.Broadcast;
import torchdevelopments.erisessentials.Core.Util.Commands.EnderChest.EnderChestCommand;
import torchdevelopments.erisessentials.Core.Util.Commands.Fly.Fly;
import torchdevelopments.erisessentials.Core.Util.Commands.Invisible.Invisibility;
import torchdevelopments.erisessentials.Core.Util.Commands.PlayerInventory.ViewPlayerInventory;
import torchdevelopments.erisessentials.Core.Util.CustomGreetings.CustomFarewell;
import torchdevelopments.erisessentials.Core.Util.CustomGreetings.CustomGreeting;
import torchdevelopments.erisessentials.Core.Util.MultiPlayerSleep.MultiPlayerSleep;

public final class ErisEssentials extends JavaPlugin{

    Plugin plugin = this;

    @Override
    public void onEnable() {
        // Plugin startup logic

        // System message at start of startup process
        System.out.println("[ErisEssentials] ErisEssentials is starting up!");

        // Startup process and console outputs to be entered here

        // Load Config
        Bukkit.getServer().getPluginManager().getPlugin("ErisEssentials").saveDefaultConfig();

        // Register Multiplayer Sleep events
        System.out.println("[ErisEssentials] Registering Multiplayer Sleep Events");
        getServer().getPluginManager().registerEvents(new MultiPlayerSleep(), this);

        // Register Custom Greeting events
        System.out.println("[ErisEssentials] Registering Custom Greeting Events");
        getServer().getPluginManager().registerEvents(new CustomGreeting(), this);
        getServer().getPluginManager().registerEvents(new CustomFarewell(), this);

        // Register ChestLocker
        System.out.println("[ErisEssentials] Registering ChestProtect");
        getServer().getPluginManager().registerEvents(new ChestListener(), this);
        getServer().getPluginManager().registerEvents(new ChestInteractListener(), this);
        getServer().getPluginManager().registerEvents(new ChestBreakListener(), this);

        getCommand("chestSetOwner").setExecutor(new SetOwner());
        getCommand("chestSetProtected").setExecutor(new ProtectChest());
        getCommand("chestRemoveProtection").setExecutor(new RemoveProtection());

        // Register Custom Commands
        System.out.println("[ErisEssentials] Registering Custom Commands");
        getCommand("fly").setExecutor(new Fly());
        getCommand("afk").setExecutor(new Afk());
        getCommand("ec").setExecutor(new EnderChestCommand());
        getCommand("inv").setExecutor(new ViewPlayerInventory());
        getCommand("broadcast").setExecutor(new Broadcast());
        getCommand("invisible").setExecutor(new Invisibility());

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
