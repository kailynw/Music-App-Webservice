package com.restendpoints.musicapp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ValidationUtil {
    public static boolean isUpdatable(String existingString, String updateString){
        boolean isUpdateStringNotEmpty = StringUtils.isNotEmpty(updateString);
        boolean isChangeDetected = isChangeDetected(existingString, updateString);
        return isUpdateStringNotEmpty && isChangeDetected;
    }

    private static boolean isChangeDetected(String existingString, String updateString){
        return !existingString.trim().equals(updateString.trim());
    }
}
