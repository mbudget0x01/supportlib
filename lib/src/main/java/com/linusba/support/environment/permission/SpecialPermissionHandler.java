package com.linusba.support.environment.permission;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

/**
 * This class handles the  normal Android Permissions
 */
class SpecialPermissionHandler {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 5469;

    /**
     * Checks if Notification Policy can be requested and is not granted.
     * @param context Context for reference
     * @return true if Permission can be requested and is not granted. False if
     * is not required by api or is granted.
     */
    public static boolean needNotificationPolicyPermission(Context context){
        //check if Version compatible
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M){
            return false;
        }

        //Check if permission granted
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return !notificationManager.isNotificationPolicyAccessGranted();

        //all failed
    }

    /**
     * Checks if Draw Overlay Permission can be requested and is not granted.
     * @param activity Activity for Context
     * @return true if Permission can be requested and is not granted. False if
     * is not required by api or is granted.
     */
    public static boolean needDrawOverlayPermission(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return !Settings.canDrawOverlays(activity.getApplicationContext());
        }
        return false;
    }

    /**
     * Method to request the Change Notification Policy Permission
     * @param activity Activity for Context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestNotificationPolicyPermission(Activity activity){
        Intent intent = new Intent(
                Settings
                        .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        activity.startActivity(intent);
    }

    /**
     * Method to request the Allow Draw Overlay Permission
     * @param activity Activity for Context
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void requestDrawOverlayPermission(Activity activity){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
    }
}
