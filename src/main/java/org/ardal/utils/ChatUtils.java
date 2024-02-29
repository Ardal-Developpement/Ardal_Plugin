package org.ardal.utils;

public class ChatUtils {
    public static String getFormattedMsg(String pseudo, String msg){
        return String.format("<%s> %s", pseudo, msg);
    }
}
