
package org.ardal.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
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
}
