package torchdevelopments.erisessentials.Core.Util.RandomOres;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class RandomOres implements Listener {

    @EventHandler
    public void onBreakStone(BlockBreakEvent e)
    {
        if (e.getBlock().getType().equals(Material.STONE))
        {

            Player p = e.getPlayer();

            World world = p.getWorld();

            Random chance = new Random();

            Location blockLocation = e.getBlock().getLocation();

            int oreSpawn = chance.nextInt(10);

            // Check if the world will spawn a random ore

            if (oreSpawn == 7)
            {
                int oreChoice = chance.nextInt(50);

                // Check has passed, a random ore will be dropped based on the number generated

                if (oreChoice == 1)
                {
                    ItemStack item = new ItemStack(Material.DIAMOND, 1);
                    world.dropItemNaturally(blockLocation, item);
                }
                else if (oreChoice == 2)
                {
                    ItemStack item = new ItemStack(Material.EMERALD, 1);
                    world.dropItemNaturally(blockLocation, item);
                }
                else if (oreChoice >= 3 && oreChoice <= 14)
                {
                    ItemStack item = new ItemStack(Material.REDSTONE, 2);
                    world.dropItemNaturally(blockLocation, item);
                }
                else if (oreChoice >= 15 && oreChoice <= 20)
                {
                    ItemStack item = new ItemStack(Material.LAPIS_LAZULI, 2);
                    world.dropItemNaturally(blockLocation, item);
                }
                else if (oreChoice >= 21 && oreChoice <= 25)
                {
                    ItemStack item = new ItemStack(Material.GOLD_ORE, 1);
                    world.dropItemNaturally(blockLocation, item);
                }
                else if (oreChoice >= 26 && oreChoice <= 38)
                {
                    ItemStack item = new ItemStack(Material.IRON_ORE, 1);
                    world.dropItemNaturally(blockLocation, item);
                }
                else
                {
                    ItemStack item = new ItemStack(Material.COAL, 1);
                    world.dropItemNaturally(blockLocation, item);
                }
            }
        }
    }
}
