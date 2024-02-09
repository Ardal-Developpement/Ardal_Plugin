package org.ardal.utils;

import java.util.List;

public class ListUtils {
    public static <T> void removeFirstIfPossible(List<T> list){
        if(list.size() > 1){
            list.remove(0);
        }
    }
}
