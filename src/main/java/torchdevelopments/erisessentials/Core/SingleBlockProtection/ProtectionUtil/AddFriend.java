package torchdevelopments.erisessentials.Core.SingleBlockProtection.ProtectionUtil;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class AddFriend {

    public static void addFriendToBlock (Player p, String location, String block, Plugin plugin, String[] args)
    {
        if(plugin.getConfig().contains(location))
        {
            String ownerUUID = plugin.getConfig().get(location + ".owner").toString();
            String playerUUID = p.getUniqueId().toString();
            Player target = Bukkit.getPlayerExact(args[2]);
            if(target != null)
            {
                String targetUUID = target.getUniqueId().toString();
                if(playerUUID.contentEquals(ownerUUID))
                {
                    if(plugin.getConfig().contains(location + ".friends"))
                    {
                        String friends = plugin.getConfig().get(location + ".friends").toString();
                        List<String> friendsList = null;
                        String[] friendsArray = friends.split(",");
                        for(String friend : friendsArray)
                        {
                            friendsList.add(friend);
                        }
                        friendsList.add(targetUUID);

                        String updatedFriends = null;
                        for(String friend : friendsList)
                        {
                            updatedFriends = updatedFriends + friend + ",";
                        }

                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that " + block);
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that " + block);

                        plugin.getConfig().set(location + ".friends", updatedFriends);
                        plugin.saveConfig();
                    }
                    else
                    {
                        p.sendMessage(ChatColor.BLUE + "You gave " + ChatColor.GOLD +  target.getDisplayName()
                                + ChatColor.BLUE + " access to that " + block);
                        target.sendMessage(ChatColor.GOLD +  p.getDisplayName()
                                + ChatColor.BLUE + " gave you access to that " + block);

                        plugin.getConfig().set(location + ".friends", targetUUID + ",");
                        plugin.saveConfig();
                    }

                }
            }
        }
    }
}
