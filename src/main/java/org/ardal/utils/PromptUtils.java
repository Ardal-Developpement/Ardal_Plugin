
package org.ardal.utils;

import org.ardal.Ardal;
import org.bukkit.command.CommandSender;

public class PromptUtils {
    public static void sendMsgAndLog( CommandSender sender, String msg){
        Ardal.writeToLogger(msg);
        if(sender != null){
            sender.sendMessage(msg);
        }
    }
}
