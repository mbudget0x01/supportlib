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

public abstract class PermissionHandler {

    /**
     * Instantiate Class
     * @param basePermissions the Permissions you wish to handle
     */
    public PermissionHandler(String[] basePermissions){
        setBasePermissions(basePermissions);
    }

    private void setBasePermissions(String[] basePermissions){
        BasePermissionHandler.basePermissions = basePermissions;
    }

    /**
     * Returns an array of all Base Permissions
     * @return Array of All Base Permissions
     */
    public String[] getBasePermissions(){
        return BasePermissionHandler.getBasePermissions();
    }

    /* ***************************************************
     *
     * permission Checks
     *
     ****************************************************/

    /**
     * Checks all the Android Permissions specified @see getBasePermissions
     * @param activity Activity for Context
     * @return List of Missing Permissions
     */
    public String[] checkBasePermissions(Activity activity){
        return checkBasePermissions(activity.getBaseContext());
    }

    /**
     * Checks all the Android Permissions specified @see getBasePermissions
     * @param context Context
     * @return List of Missing Permissions
     */
    public String[] checkBasePermissions(Context context){
        List<String> missingPermissions = BasePermissionHandler.checkBasePermissions(context);
        String [] resp = new String[missingPermissions.size()];
        return missingPermissions.toArray(resp);
    }


    /**
     * Checks if Notification Policy is needed.
     * @param context Context for reference
     * @return true if Permission can be requested and is not granted. False if
     * is not required by api or is granted.
     */
    public boolean needNotificationPolicyPermission(Context context){
        return SpecialPermissionHandler.needNotificationPolicyPermission(context);
    }

    /**
     * Checks if Draw Overlay Permission can be requested and is not granted
     * @param activity Activity for Context
     * @return true if Permission can be requested and is not granted. False if
     * is not required by api or is granted.
     */
    public boolean needDrawOverlayPermission(Activity activity){
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
    public void requestBasePermissions(Activity activity, String[] permissions ){
        BasePermissionHandler.requestBasePermissions(permissions, activity);
    }

    /**
     * Requests all Base Permissions needed by this app
     * @param activity Activity for Context
     * @see Manifest.permission, BASE_PERMISSIONS
     */
    public void requestBasePermissions(Activity activity){
        BasePermissionHandler.requestAllBasePermissions(activity);
    }

    /**
     * Method to request the Change Notification Policy Permission
     * @param activity Activity for Context
     */

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestNotificationPolicyPermission(Activity activity){
        SpecialPermissionHandler.requestNotificationPolicyPermission(activity);
    }

    /**
     * Method to request the Allow Draw Overlay Permission
     * @param activity Activity for Context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestDrawOverlayPermission(Activity activity){
        SpecialPermissionHandler.requestDrawOverlayPermission(activity);
    }

}
