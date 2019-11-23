package pl.ololjvNek.permissions.managers;

import org.bukkit.entity.Player;
import pl.ololjvNek.permissions.Main;
import pl.ololjvNek.permissions.data.User;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {

    public static ConcurrentHashMap<UUID, User> permissions = new ConcurrentHashMap<UUID, User>();

    public static User createUser(Player p){
        User u = new User(p);
        permissions.put(u.getUuid(), u);
        return u;
    }

    public static User getUser(Player p){
        return permissions.get(p.getUniqueId());
    }

    public static User getUser(UUID uuid){
        return permissions.get(uuid);
    }

    public static User getUser(String name){
        for(User u : UserManager.permissions.values()){
            if(u.getLastName().equalsIgnoreCase(name)){
                return u;
            }
        }
        return null;
    }

    public static void setupPermissions(){
        for(String name : Main.getPlugin().getConfig().getConfigurationSection("users").getKeys(false)){
            User u = new User(name);
            permissions.put(u.getUuid(), u);
        }
    }
}
