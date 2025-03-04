package com.scs.apigateway.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ArrayUtils {
    public static String[] mergeArrays(String[] array1, String[] array2) {
        if (array1 == null) return array2;
        if (array2 == null) return array1;

        String[] merged = new String[array1.length + array2.length];
        System.arraycopy(array1, 0, merged, 0, array1.length);
        System.arraycopy(array2, 0, merged, array1.length, array2.length);
        return merged;
    }
}