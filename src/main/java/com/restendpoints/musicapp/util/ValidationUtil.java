package com.restendpoints.musicapp.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ValidationUtil {

    /* Is updatable if update attribute exists and change is detected */
    public static boolean isUpdatable(String existingString, String updateString){
        boolean isUpdateStringNotEmpty = StringUtils.isNotEmpty(updateString);
        boolean isChangeDetected = isChangeDetected(existingString, updateString);
        return isUpdateStringNotEmpty && isChangeDetected;
    }

    public static boolean isUpdatableAscendingOnly(Long existingNumber, Long updateNumber){
        boolean isChangeDetected = isChangeDetected(existingNumber, updateNumber);

        if(isChangeDetected){
            boolean isAscendingUpdateValue = updateNumber > existingNumber;
            return isAscendingUpdateValue;
        }else{
            return false;
        }
    }

    public static boolean isChangeDetected(Long existingNumber, Long updateNumber){
        //No value update attempted
        if(existingNumber == null || updateNumber == null){
            return false;
        //Check if value is changed
        } else {
            return !existingNumber.equals(updateNumber);
        }
    }
    private static boolean isChangeDetected(String existingString, String updateString){
        //No change detected if both values are null
        if (existingString==null && updateString==null){
            return false;
        //Change detected if existing value is going to be replaced with null value (value -> null)
        } else if (StringUtils.isNotEmpty((existingString)) && updateString==null) {
            return true;
        //Change detected if existing null value is going to be updated (null -> value)
        } else if (existingString==null && StringUtils.isNotEmpty(updateString)) {
            return true;
        //Change detected if values are different (value -> value123)
        }else{
          return !existingString.trim().equals(updateString.trim());
        }
    }
}
