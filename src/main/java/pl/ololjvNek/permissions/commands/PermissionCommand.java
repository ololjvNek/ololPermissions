package pl.ololjvNek.permissions.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import pl.ololjvNek.permissions.Main;
import pl.ololjvNek.permissions.data.User;
import pl.ololjvNek.permissions.managers.UserManager;
import pl.ololjvNek.permissions.utils.DataUtil;
import pl.ololjvNek.permissions.utils.PermissionUtil;
import pl.ololjvNek.permissions.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class PermissionCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!sender.hasPermission("ololPermissions.*")){
            return Util.sendMessage(sender, "&aololPermissions &8>> &cSorry, but you don't have permission to do that!");
        }
        if(args.length < 2){
            return Util.sendMessage(sender, "&a&m--------------&9 [ &aololPermissions &9] &a&m--------------\n&a/perm user <user> group set <group>\n&a/perm user <user> group time <group> <time>\n&a/perm options defaultGroup <group>\n&a/perm group <group> add <permission>");
        }
        if(args[0].equalsIgnoreCase("group")){
            if(PermissionUtil.isGroupExists(args[1])){
                if(args[2].equalsIgnoreCase("add")){
                    List<String> permissions = Main.getPlugin().getConfig().getStringList("permissions.groups." + args[1] + ".permissions");
                    permissions.add(args[3]);
                    Main.getPlugin().getConfig().set("permissions.groups." + args[1] + ".permissions", permissions);
                    Main.getPlugin().saveConfig();
                    return Util.sendMessage(sender, "&aololPermissions &8>> &7Permission '&c" + args[3] + "&7' added to group &9" + args[1]);
                }
            }else{
                if(args[2].equalsIgnoreCase("add")){
                    List<String> permissions = new ArrayList<>();
                    permissions.add(args[3]);
                    Main.getPlugin().getConfig().set("permissions.groups." + args[1] + ".permissions", permissions);
                    Main.getPlugin().saveConfig();
                    return Util.sendMessage(sender, "&aololPermissions &8>> &7Permission '&c" + args[3] + "&7' added to group &9(CREATED NOW) " + args[1]);
                }
            }
        }
        if(args[0].equalsIgnoreCase("user")){
            if(UserManager.getUser(args[1]) != null){
                if(args[2].equalsIgnoreCase("group")){
                    if(args[3].equalsIgnoreCase("set")){
                        User u = UserManager.getUser(args[1]);
                        u.setGroup(args[4]);
                        return Util.sendMessage(sender, "&aololPermissions &8>> &7Group of player &9" + u.getLastName() + " &7has been changed to &9" + args[4] + " &7for &aalways");
                    }
                    if(args[3].equalsIgnoreCase("time")){
                        User u = UserManager.getUser(args[1]);
                        long time = DataUtil.parseDateDiff(args[5], true);
                        if(u.getGroup().equalsIgnoreCase(args[4])){
                            if(u.getGroupTime() != 0L){
                                long toAdd = time-System.currentTimeMillis();
                                u.setGroupTime(u.getGroupTime()+toAdd);
                                Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".groupTime", u.getGroupTime());
                                Main.getPlugin().saveConfig();
                                return Util.sendMessage(sender, "&aololPermissions &8>> &7Group of player &9" + u.getLastName() + " &7has been extended &9" + args[4] + " &7time &a" + DataUtil.secondsToString(u.getGroupTime()));
                            }else{
                                u.setGroupTime(DataUtil.parseDateDiff(args[5], true));
                                Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".groupTime", u.getGroupTime());
                                Main.getPlugin().saveConfig();
                                return Util.sendMessage(sender, "&aololPermissions &8>> &7Group of player &9" + u.getLastName() + " &7has been set for time &9" + args[4] + " &7time: &a" + DataUtil.secondsToString(u.getGroupTime()));
                            }
                        }
                        u.setGroup(args[4]);
                        u.setGroupTime(DataUtil.parseDateDiff(args[5], true));
                        Main.getPlugin().getConfig().set("users." + u.getUuid().toString() + ".groupTime", u.getGroupTime());
                        Main.getPlugin().saveConfig();
                        return Util.sendMessage(sender, "&aololPermissions &8>> &7Group of player &9" + u.getLastName() + " &7has been changed to &9" + args[4] + " &7for time &a" + DataUtil.secondsToString(u.getGroupTime()));
                    }
                }
            }else{
                return Util.sendMessage(sender, "&aololPermissions &8>> &cSorry, but this user doesn't exists in data base!");
            }
        }
        if(args[0].equalsIgnoreCase("options")){
            if(args[1].equalsIgnoreCase("defaultGroup")){
                Main.getPlugin().getConfig().set("options.defaultGroupName", args[2]);
                Main.getPlugin().saveConfig();
                return Util.sendMessage(sender, "&aololPermissions &8>> &7Default group has been changed to &9" + args[2]);
            }
        }
        return false;
    }
}
