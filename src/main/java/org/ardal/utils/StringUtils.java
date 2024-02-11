package org.ardal.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static String getStringFromConcatStringList(List<String> strList){
        if(strList.isEmpty()) { return ""; }

        StringBuilder stringBuilder = new StringBuilder(strList.get(0));
        for(int i = 1; i < strList.size(); i++){
            stringBuilder.append(" ").append(strList.get(i));
        }

        return stringBuilder.toString().trim();
    }

    public static List<String> getStrListFromStrArray(String[] strArray){
        return new ArrayList<>(Arrays.asList(strArray));
    }

    private static final int SECTION_NORMAL_SIZE = 56;

    public static String getSection(String sectionName, ChatColor sectionColor, ChatColor textColor, boolean returnToLine){
        StringBuilder sB = new StringBuilder();
        sB.append(sectionColor);
        sB.append("--------- ");
        sB.append(textColor);
        sB.append(sectionName);
        sB.append(sectionColor);
        sB.append(' ');

        for(int i = sB.length(); i < SECTION_NORMAL_SIZE; i++){
            sB.append('-');
        }

        if(returnToLine){
            sB.append('\n');
        }

        return sB.toString();
    }

}
