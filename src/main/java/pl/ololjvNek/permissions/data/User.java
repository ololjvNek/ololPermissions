package pl.ololjvNek.permissions.data;

import lombok.Data;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import pl.ololjvNek.permissions.Main;

import java.util.UUID;

@Data
public class User {

    private UUID uuid;
    private String lastName, group;
    private PermissionAttachment attachment;
    private long groupTime;

    public User(Player player){
        uuid = player.getUniqueId();
        lastName = player.getName();
        group = Main.getDefaultGroup();
        attachment = player.addAttachment(Main.getPlugin());
        groupTime = 0L;
        Main.getPlugin().getConfig().set("users." + uuid.toString() + ".lastName", lastName);
        Main.getPlugin().getConfig().set("users." + uuid.toString() + ".group", group);
        Main.getPlugin().getConfig().set("users." + uuid.toString() + ".groupTime", groupTime);
        Main.getPlugin().saveConfig();
    }

    public User(String uuid){
        this.uuid = UUID.fromString(uuid);
        lastName = Main.getPlugin().getConfig().getString("users." + uuid + ".lastName");
        groupTime = Main.getPlugin().getConfig().getLong("users." + uuid + ".groupTime");
        group = Main.getPlugin().getConfig().getString("users." + uuid + ".group");
        attachment = null;
    }
}
