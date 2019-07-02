package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class GetTargetBlock {

    public static String targetBlockLocation(Player p)
    {
        Block targetBlock = p.getTargetBlockExact(5);
        Location targetBlockLoc = targetBlock.getLocation();

        int x = targetBlockLoc.getBlockX();
        int y = targetBlockLoc.getBlockY();
        int z = targetBlockLoc.getBlockZ();

        String location = Integer.toString(x);
        location += Integer.toString(y);
        location += Integer.toString(z);

        return location;
    }
}
