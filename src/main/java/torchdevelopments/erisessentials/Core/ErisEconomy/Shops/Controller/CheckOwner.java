package torchdevelopments.erisessentials.Core.ErisEconomy.Shops.Controller;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CheckOwner {

    public static boolean ownerCheck(String playerUUID, String location, Plugin plugin)
    {

        if(plugin.getConfig().contains("chest." + location))
        {

            String ownerUUID = plugin.getConfig().get("chest." + location + ".owner").toString();

            if(ownerUUID.contentEquals(playerUUID))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }

    }
}
