package org.ardal.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringUtils {
    public static String getStringFromConcatStringList(List<String> strList){
        StringBuilder stringBuilder = new StringBuilder(strList.get(0));
        for(int i = 1; i < strList.size(); i++){
            stringBuilder.append(" ").append(strList.get(i));
        }

        return stringBuilder.toString().trim();
    }

    public static List<String> getStrListFromStrArray(String[] strArray){
        return new ArrayList<>(Arrays.asList(strArray));
    }

}
