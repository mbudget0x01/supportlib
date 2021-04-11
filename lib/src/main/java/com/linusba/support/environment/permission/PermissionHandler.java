package com.linusba.support.environment.permission;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.List;

/**
 * Provides all functionality to Handle the permissions
 * for this app.
 */

public class PermissionHandler {

    /**
     * Set the initial Base Permissions you need to have granted for your app
     * @param basePermissions Array of Permissions
     * @see Manifest.permission
     */
    public static void setBasePermissions(String[] basePermissions){
        BasePermissionHandler.addBasePermissions(basePermissions);
    }

    /**
     * Adds an android Permission to the handler
     * @param basePermission Permission you wish to add
     */
    public static void addBasePermission(String basePermission){
        BasePermissionHandler.addBasePermission(basePermission);
    }

    /**
     * Removes an android Permission from Handler
     * @param basePermission Permission you want to remove
     * @return True if permission was known to the handler
     */
    public static boolean removeBasePermission(String basePermission){
        return BasePermissionHandler.removeBasePermission(basePermission);
    }

    /**
     * Returns an array of all Base Permissions
     * @return Array of All Base Permissions
     */
    public static String[] getBasePermissions(){
        return BasePermissionHandler.getBasePermissions();
    }

    /* ***************************************************
     *
     * permission Checks
     *
     ****************************************************/

    /**
     * Checks all the Android Permissions specified in BASE_PERMISSIONS
     * @param activity Activity for Context
     * @return List of Missing Permissions
     */
    public static String[] checkBasePermissions(Activity activity){
        return checkBasePermissions(activity.getBaseContext());
    }

    /**
     * Checks all the Android Permissions specified in BASE_PERMISSIONS
     * @param context Context
     * @return List of Missing Permissions
     */
    public static String[] checkBasePermissions(Context context){
        List<String> missingPermissions = BasePermissionHandler.checkBasePermissions(context);
        String [] resp = new String[missingPermissions.size()];
        return missingPermissions.toArray(resp);
    }


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
    public static boolean needDrawOverlayPermission(Activity activity){
        return SpecialPermissionHandler.needDrawOverlayPermission(activity);
    }

    /* ***************************************************
     *
     * permission Requests
     *
     ****************************************************/


    /**
     * Requests Andorid Permissions
     * @param activity Activity for Context
     * @param permissions Android Permissions
     * @see Manifest.permission
     */
    public static void requestBasePermissions(Activity activity, String[] permissions ){
        BasePermissionHandler.requestBasePermissions(permissions, activity);
    }

    /**
     * Requests all Base Permissions needed by this app
     * @param activity Activity for Context
     * @see Manifest.permission, BASE_PERMISSIONS
     */
    public static void requestBasePermissions(Activity activity){
        BasePermissionHandler.requestAllBasePermissions(activity);
    }

    /**
     * Method to request the Change Notification Policy Permission
     * @param activity Activity for Context
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestNotificationPolicyPermission(Activity activity){
        SpecialPermissionHandler.requestNotificationPolicyPermission(activity);
    }

    /**
     * Method to request the Allow Draw Overlay Permission
     * @param activity Activity for Context
     */
    public static void requestDrawOverlayPermission(Activity activity){
        SpecialPermissionHandler.requestDrawOverlayPermission(activity);
    }

}
