package org.ardal.utils;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteUtils {
    public static List<String> getTabCompleteForBool(String arg){
        List<String> tabComplete = new ArrayList<>();

        if("true".startsWith(arg.toLowerCase())){
            tabComplete.add("true");
        }
        if("false".startsWith(arg.toLowerCase())){
            tabComplete.add("false");
        }

        return tabComplete;
    }

    public static List<String> getTabCompleteFromStrList(@Nullable List<String> keySet, String targetName){
        List<String> tabComplete = new ArrayList<>();

        if(keySet == null) { return tabComplete; }

        for(String name : keySet){
            if(name.startsWith(targetName)){
                tabComplete.add(name);
            }
        }

        return tabComplete;
    }

    public static List<String> getTabCompleteFromStrList(List<String> keySet, List<String> targetNameList){
        String questName = StringUtils.getStringFromConcatStringList(targetNameList);
        return getTabCompleteFromStrList(keySet, questName);
    }
}