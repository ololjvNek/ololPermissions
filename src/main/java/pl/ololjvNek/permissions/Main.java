package pl.ololjvNek.permissions;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.ololjvNek.permissions.commands.PermissionCommand;
import pl.ololjvNek.permissions.data.User;
import pl.ololjvNek.permissions.listeners.PlayerConnectionListener;
import pl.ololjvNek.permissions.managers.UserManager;
import pl.ololjvNek.permissions.utils.PermissionUtil;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends JavaPlugin {

    @Getter private static String defaultGroup;
    @Getter private static Main plugin;

    public void onEnable(){
        saveDefaultConfig();
        reloadConfig();
        plugin = this;
        defaultGroup = getConfig().getString("options.defaultGroupName");
        new BukkitRunnable(){
            public void run(){
                Bukkit.getOnlinePlayers().forEach(p -> {
                    User u = UserManager.getUser(p);
                    if(u.getGroupTime() != 0L && System.currentTimeMillis() > u.getGroupTime()){
                        u.setGroupTime(0L);
                        u.setGroup(defaultGroup);
                        PermissionUtil.permissionsSetter(p);
                    }
                });
            }
        }.runTaskTimer(this, 120L, 120L);
        getCommand("perm").setExecutor(new PermissionCommand());
        Bukkit.getPluginManager().registerEvents(new PlayerConnectionListener(), this);
        UserManager.setupPermissions();
    }



}
