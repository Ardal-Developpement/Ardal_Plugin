package org.ardal.utils;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.entity.Player;

public class ChatUtils {
    public static String getFormattedMsg(String pseudo, String msg){
        return String.format("<%s> %s", pseudo, msg);
    }

    public static void copyToClipboard(Player player, TextComponent message, String dataToCopy) {
        message.setClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, dataToCopy));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("Copy in clipboard")));
        player.spigot().sendMessage(message);
    }

    public static void sendActionBar(Player player, TextComponent message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, message);
    }
}
