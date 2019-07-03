/*
 * Copyright © Daniel Tierney, 2019. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright.txt notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright.txt notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */

package torchdevelopments.erisessentials.Core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import torchdevelopments.erisessentials.Core.ErisEconomy.Commands.ErisEconomy;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.BarrelLocker.Commands.ErisBarrel;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.BarrelLocker.Listener.BarrelBreakListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.BarrelLocker.Listener.BarrelInteractListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.BarrelLocker.Listener.BarrelPlaceListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Listeners.ChestBreakListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Listeners.ChestInteractListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Listeners.ChestListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ChestLocker.Commands.*;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.FurnaceLocker.Commands.ErisFurnace;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.FurnaceLocker.Listeners.FurnaceBreakListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.FurnaceLocker.Listeners.FurnaceInteractListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.FurnaceLocker.Listeners.FurnacePlaceListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Commands.ErisHopper;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Listeners.HopperBreakListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Listeners.HopperInteractListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.HopperLocker.Listeners.HopperPlaceListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Commands.ErisShulker;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Listeners.ShulkerBreakListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Listeners.ShulkerInteractListener;
import torchdevelopments.erisessentials.Core.SingleBlockProtection.ShulkerLocker.Listeners.ShulkerPlaceListener;
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

        try
        {
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
            System.out.println("[ErisEssentials] Registering ChestLocker");
            getServer().getPluginManager().registerEvents(new ChestListener(), this);
            getServer().getPluginManager().registerEvents(new ChestInteractListener(), this);
            getServer().getPluginManager().registerEvents(new ChestBreakListener(), this);
            getCommand("chest").setExecutor(new ErisChest());

            // Register BarrelLocker
            System.out.println("[ErisEssentials] Registering BarrelLocker");

            getServer().getPluginManager().registerEvents(new BarrelPlaceListener(), this);
            getServer().getPluginManager().registerEvents(new BarrelInteractListener(), this);
            getServer().getPluginManager().registerEvents(new BarrelBreakListener(), this);
            getCommand("barrel").setExecutor(new ErisBarrel());

            // Register FurnaceLocker
            System.out.println("[ErisEssentials] Registering FurnaceLocker");
            getServer().getPluginManager().registerEvents(new FurnacePlaceListener(), this);
            getServer().getPluginManager().registerEvents(new FurnaceInteractListener(), this);
            getServer().getPluginManager().registerEvents(new FurnaceBreakListener(), this);
            getCommand("furnace").setExecutor(new ErisFurnace());

            // Register FurnaceLocker
            System.out.println("[ErisEssentials] Registering HopperLocker");
            getServer().getPluginManager().registerEvents(new HopperPlaceListener(), this);
            getServer().getPluginManager().registerEvents(new HopperInteractListener(), this);
            getServer().getPluginManager().registerEvents(new HopperBreakListener(), this);
            getCommand("hopper").setExecutor(new ErisHopper());

            // Register ShulkerLocker
            System.out.println("[ErisEssentials] Registering ShulkerLocker");
            getServer().getPluginManager().registerEvents(new ShulkerInteractListener(), this);
            getServer().getPluginManager().registerEvents(new ShulkerBreakListener(), this);
            getServer().getPluginManager().registerEvents(new ShulkerPlaceListener(), this);
            getCommand("shulker").setExecutor(new ErisShulker());

            // Register ErisEconomy
            System.out.println("[ErisEssentials] Registering ErisEconomy");
            getCommand("economy").setExecutor(new ErisEconomy());

            // Register Custom Commands
            System.out.println("[ErisEssentials] Registering Custom Commands");
            getCommand("fly").setExecutor(new Fly());
            getCommand("afk").setExecutor(new Afk());
            getCommand("ec").setExecutor(new EnderChestCommand());
            getCommand("inventory").setExecutor(new ViewPlayerInventory());
            getCommand("broadcast").setExecutor(new Broadcast());
            getCommand("invisible").setExecutor(new Invisibility());

            // System message at the end of the startup process
            System.out.println("[ErisEssentials] ErisEssentials has finished loading!");
        }
        catch (NullPointerException e)
        {
            System.out.println(ChatColor.RED + "[ErisEssentials] Critical Error, please send the developer the following:");
            System.out.println(ChatColor.RED + "--------------------------------------");
            System.out.println(ChatColor.RED + "[ErisEssentials] START OF STACK TRACE");
            e.printStackTrace();
            System.out.println(ChatColor.RED + "[ErisEssentials] END OF STACK TRACE");
            System.out.println(ChatColor.RED + "--------------------------------------");
        }

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
