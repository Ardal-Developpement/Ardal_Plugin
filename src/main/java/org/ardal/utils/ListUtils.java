package org.ardal.utils;

import java.util.List;

public class ListUtils {
    public static <T> void removeFirstIfPossible(List<T> list){
        if(!list.isEmpty()){
            list.remove(0);
        }
    }
}
