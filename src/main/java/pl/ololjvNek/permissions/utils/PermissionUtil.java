package pl.ololjvNek.permissions.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import pl.ololjvNek.permissions.Main;
import pl.ololjvNek.permissions.data.User;
import pl.ololjvNek.permissions.managers.UserManager;

import java.util.UUID;

public class PermissionUtil {

    public static void permissionsSetter(Player p) {
        User u = UserManager.getUser(p.getUniqueId());
        if(u.getAttachment() == null){
            u.setAttachment(p.addAttachment(Main.getPlugin()));
        }
        PermissionAttachment attachment = u.getAttachment();
        for (String groups : Main.getPlugin().getConfig().getConfigurationSection("permissions.groups").getKeys(false)) {
            if(u.getGroup().equalsIgnoreCase(groups)){
                for (String permissions : Main.getPlugin().getConfig().getStringList("permissions.groups." + groups + ".permissions")) {
                    System.out.print(permissions);
                    attachment.setPermission(permissions, true);
                }
            }
        }
    }

    public static boolean isGroupExists(String name){
        for(String group : Main.getPlugin().getConfig().getConfigurationSection("permissions.groups").getKeys(false)){
            if(group.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public static boolean isInGroup(String name, String group){
        User u = UserManager.getUser(name);
        return u.getGroup().equalsIgnoreCase(group);
    }

    public static void setGroup(String name, String group, long time){
        User u = UserManager.getUser(name);
        if(u == null){
            Bukkit.getLogger().warning("User doesn't exists in data base");
            return;
        }
        if(time == 0L){
            u.setGroup(group);
            u.setGroupTime(0L);
            Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".group", group);
            Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".groupTime", 0L);
            Main.getPlugin().saveConfig();
            Bukkit.getLogger().warning("Group of player " + name + " changed by another plugin side");
        }else{
            u.setGroup(group);
            u.setGroupTime(time);
            Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".group", group);
            Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".groupTime", time);
            Main.getPlugin().saveConfig();
            Bukkit.getLogger().warning("Group of player " + name + " changed by another plugin side with time " + time);
        }
    }

    public static String getActuallyGroup(Player p){
        User u = UserManager.getUser(p);
        return u.getGroup();
    }

    public static long getGroupTime(Player p){
        User u = UserManager.getUser(p);
        return u.getGroupTime();
    }



}
