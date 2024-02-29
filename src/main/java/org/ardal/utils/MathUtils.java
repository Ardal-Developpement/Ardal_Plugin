package org.ardal.utils;

public class MathUtils {
    public static int clamp(int x, int min, int max){
        return Math.max(min, Math.min(max, x));
    }
}
