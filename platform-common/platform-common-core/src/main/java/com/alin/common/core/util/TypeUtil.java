package com.alin.common.core.util;

public class TypeUtil {
    private TypeUtil() {
    }

    public static Long[] stringArrToLongArr(String[] arrs) {
        Long[] longs = new Long[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            longs[i] = Long.parseLong(arrs[i]);
        }
        return longs;
    }
}
