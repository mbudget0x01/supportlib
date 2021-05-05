package com.linusba.support.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * This class provides some Utility Functions for App Handling
 */
public class AppPackageUtil {

    /**
     * Get the Application Main class
     * @param context Context for reference
     * @return MainClass (Launcher)
     */
    public static Class<?> getMainClass(Context context) throws ClassNotFoundException {
        String packageName = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        return Class.forName(className);
    }

    /**
     * Returns a Pending Intent to Launch the main App
     * @param context Context for Reference
     * @return Pending Intent to Launch the Main App
     */
     public static PendingIntent getLaunchAppPendingIntent(Context context) throws ClassNotFoundException {
        Intent intent = new Intent(context, getMainClass(context));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return PendingIntent.getActivity(context,0, intent, 0);
    }
}
