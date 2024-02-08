package org.ardal.utils;


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

    public static List<String> getTabCompleteFromStrList(List<String> keySet, String questName){
        List<String> tabComplete = new ArrayList<>();

        for(String name : keySet){
            if(name.startsWith(questName)){
                tabComplete.add(name);
            }
        }

        return tabComplete;
    }

    public static List<String> getTabCompleteFromStrList(List<String> keySet, List<String> questNameList){
        String questName = StringUtils.getStringFromConcatStringList(questNameList);
        return getTabCompleteFromStrList(keySet, questName);
    }
}