package com.linusba.support.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

/**
 * This class provides functionality to Handle AppWidgets using the AppWidgetProvider
 */
public class AppWidgetProviderUtil {

    /**
     * Returns a new Broadcasting Pending intent Pointing to the cls
     * @param context Context for reference
     * @param action the Action for the Intent Action
     * @param cls class its pointing to, typically your AppWidgetProvider
     * @return Pending Intent
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    public static PendingIntent getPendingActionIntent(Context context, String action, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            //lint -> immutable flag is handled above
            return PendingIntent.getBroadcast(context, 0, intent, 0);
        }
    }

    /**
     * Request an Update at the AppWidgetManager.
     * @param context Context for Reference
     * @param remoteViews Updated Remote Views matching the AppWidgetProvider
     * @param cls Class of the AppWidgetProvider
     */
    public static void requestUpdate(Context context, RemoteViews remoteViews, Class<?> cls) {
        ComponentName componentName = new ComponentName(context, cls);
        AppWidgetManager.getInstance(context).updateAppWidget(componentName, remoteViews);
    }
}
