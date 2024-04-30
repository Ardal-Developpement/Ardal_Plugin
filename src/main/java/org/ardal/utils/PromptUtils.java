
package org.ardal.utils;

import org.ardal.Ardal;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PromptUtils {
    public static void sendMsgAndLog( CommandSender sender, String msg) {
        Ardal.writeToLogger(msg);
        if(sender != null){
            sender.sendMessage(msg);
        }
    }

    public static void copyToClipboard(Player player, String message) {
        String tellrawCommand = "{ \"text\": \"[Copy]\", \"clickEvent\": { \"action\": \"copy_to_clipboard\", \"value\": \"" + message + "\" } }";
        player.spigot().sendMessage(net.md_5.bungee.api.ChatMessageType.ACTION_BAR, net.md_5.bungee.api.chat.TextComponent.fromLegacyText(tellrawCommand));
    }
}
