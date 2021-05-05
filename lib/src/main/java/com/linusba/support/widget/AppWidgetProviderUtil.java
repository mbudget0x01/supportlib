package com.linusba.support.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
    public static PendingIntent getPendingActionIntent(Context context, String action, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
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
