package com.linusba.support.environment.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the  normal Android Permissions
 */
class BasePermissionHandler {

    public static String[] basePermissions;

    /**
     * Returns an Array containing all base Permissions
     * @return Array containing the permissions
     */
    public static String[] getBasePermissions() {
        return basePermissions;
    }

    /**
     * Check if a specific permission is granted
     * @param basePermission Permission to check
     * @param context Context for reference
     * @return true if granted
     */
    public static boolean hasPermission(String basePermission, Context context){
        return ActivityCompat.checkSelfPermission(context,basePermission) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * check all Base Permssions
     * @param context Context for Reference
     * @return List of missing Permissions
     */
    public static List<String> checkBasePermissions(Context context){
        List<String> missingPermissions = new ArrayList<>();
        for(String permission: basePermissions){
            if(!hasPermission(permission,context)){
                missingPermissions.add(permission);
            }
        }
        return missingPermissions;
    }

    /**
     * Requests Andorid Permissions
     * @param activity Activity for Context
     * @param basePermissions Android Permissions
     * @see Manifest.permission
     */
    public static void requestBasePermissions(String[] basePermissions, Activity activity){
        ActivityCompat.requestPermissions(activity, basePermissions, 1);
    }


    /**
     * Requests all Base Permissions needed by this app
     * @param activity Activity for Context
     * @see Manifest.permission, BASE_PERMISSIONS
     */
    public static void requestAllBasePermissions(Activity activity){
        requestBasePermissions(getBasePermissions(), activity);
    }
}
