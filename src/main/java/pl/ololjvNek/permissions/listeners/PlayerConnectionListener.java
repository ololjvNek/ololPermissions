package pl.ololjvNek.permissions.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.ololjvNek.permissions.data.User;
import pl.ololjvNek.permissions.managers.UserManager;
import pl.ololjvNek.permissions.utils.PermissionUtil;

public class PlayerConnectionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(UserManager.getUser(p) == null){
            UserManager.createUser(p);
        }
        PermissionUtil.permissionsSetter(p);
        if(p.hasPermission("ololPermissions.*")){
            p.sendMessage("You have all permissions of plugin ololPermissions");
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(UserManager.getUser(p) != null){
            p.removeAttachment(UserManager.getUser(p).getAttachment());
            UserManager.getUser(p).setAttachment(null);
        }
    }

}
