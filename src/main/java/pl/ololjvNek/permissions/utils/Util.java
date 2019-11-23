package pl.ololjvNek.permissions.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Util {

    static String[] find;
    static String[] replace;
    private static String serverName;
    private static int playerCount;

    static {
        Util.find = new String[] { ">>", "<<", "{o}" };
        Util.replace = new String[] { "»", "«", "\u2022" };
    }

    public static void sendTitle(final Player player, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
    }

    public static void sendSubTitle(final Player player, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
    }

    public static void sendTitle(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
        }
    }

    public static void sendSubTitle(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(5, 60, 5);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
        }
    }

    public static void sendTitleX(final Player player, final String text, final int show, final int duration, final int end) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(20 * show, 20 * duration, 20 * end);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
    }

    public static void sendSubTitleX(final Player player, final String text, final int show, final int duration, final int end) {
        final IChatBaseComponent chatTitle = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\",color:" + ChatColor.GOLD.name().toLowerCase() + "}");
        final PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, chatTitle);
        final PacketPlayOutTitle length = new PacketPlayOutTitle(20 * show, 20 * duration, 20 * end);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(title);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(length);
    }

    public static void sendActionBar(final Player player, final String text) {
        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\"}");
        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
    }

    public static void sendActionBar(final Collection<? extends Player> players, final String text) {
        final IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + Util.fixColors(text) + "\"}");
        final PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        for (final Player player : players) {
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(bar);
        }
    }

    public static double round(double value, int decimals)
    {
        double p = Math.pow(10.0D, decimals);
        return Math.round(value * p) / p;
    }

    public static String setHEX(final String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String fixColors(final String text) {
        return ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(text, Util.find, Util.replace));
    }

    public static List<String> fixColors(final List<String> text) {
        final List<String> ret = new ArrayList<String>();
        for (final String s : text) {
            ret.add(ChatColor.translateAlternateColorCodes('&', StringUtils.replaceEach(s, Util.find, Util.replace)));
        }
        return ret;
    }

    public static boolean sendMessage(final CommandSender player, final String text) {
        player.sendMessage(setHEX(text).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022"));
        return true;
    }

    public static boolean sendMessage(final CommandSender player, final List<String> text) {
        for (final String s : text) {
            player.sendMessage(setHEX(s).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022").replace("{NICK}", player.getName()));
        }
        return true;
    }

    public static boolean sendMessage(final Collection<? extends CommandSender> players, final String text) {
        for (final CommandSender cs : players) {
            cs.sendMessage(setHEX(text).replace(">>", "»").replace("<<", "«").replace("{o}", "\u2022"));
        }
        return true;
    }
}
