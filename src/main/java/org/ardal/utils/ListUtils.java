package org.ardal.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListUtils {
    public static <T> void removeFirstIfPossible(List<T> list){
        if(!list.isEmpty()){
            list.remove(0);
        }
    }

    public static Map<String, Integer> getFirstOccFromStrList(List<String> list, String elem) {
        for(int i = 0; i < list.size(); i++){
            int index = list.get(i).indexOf(elem);
            if(index != -1){
                HashMap<String, Integer> hashMap = new HashMap<>();

                hashMap.put("listIndex", i);
                hashMap.put("subListIndex", index);
                return hashMap;
            }
        }

        return null;
    }
}
