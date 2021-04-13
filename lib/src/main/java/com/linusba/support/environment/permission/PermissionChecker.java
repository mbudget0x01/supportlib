package com.linusba.support.environment.permission;

import android.app.Activity;
import android.content.Context;

/**
 * Implementation of the various Permission Check Methods
 * Only Used to check for specific permissions
 */
public class PermissionChecker {

    /**
     * Checks if Notification Policy is needed.
     * @param context Context for reference
     * @return true if Permission needs to be requested
     */
    public static boolean needNotificationPolicyPermission(Context context){
        return SpecialPermissionHandler.needNotificationPolicyPermission(context);
    }

    /**
     * Checks if Draw Overlay Permission can be requested and is not granted
     * @param activity Activity for Context
     * @return true if Permission needs to be requested
     */
    public boolean needDrawOverlayPermission(Activity activity){
        return SpecialPermissionHandler.needDrawOverlayPermission(activity);
    }

    /**
     * Check if a specific Android permission is granted
     * @param basePermission Permission to check
     * @param context Context for reference
     * @return true if granted
     */
    public static boolean hasPermission(String basePermission, Context context){
        return BasePermissionHandler.hasPermission(basePermission,context);
    }
}
