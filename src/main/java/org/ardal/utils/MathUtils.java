package org.ardal.utils;

import java.util.Random;

public class MathUtils {
    public static int clamp(int x, int min, int max){
        return Math.max(min, Math.min(max, x));
    }

    public static int getRandom(int low, int high){
        return new Random().nextInt(high-low) + low;
    }
}
