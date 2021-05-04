package com.linusba.support.widget;

import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.linusba.support.widget.AppWidgetCoordinator.ACTION_CODE_CALLBACK_INTERNAL;
import static com.linusba.support.widget.AppWidgetCoordinator.INTENT_STRING_EXTRA_CHANGED_PROPERTY;


/**
 * This class extends {@link AppWidgetProvider} it provides callback functionality.
 * This can be used to broadcast on Property Changed to all subscribed {@link AppWidgetProvider}
 */
public abstract class CallbackAppWidgetProvider extends AppWidgetProvider {

    private static final String TAG = CallbackAppWidgetProvider.class.getName();


    /**
     * Notifies the {@link AppWidgetCoordinator} instance that a property has changed.
     * And if no Callback is registered for the calling class it executes the onPropertyChanged void.
     * Execute this function for actions witch should be broadcasted.
     * @param context App Context
     */
    public void notifyOnPropertyChanged(Context context, Intent intent) {
        String propertyName = intent.getStringExtra(INTENT_STRING_EXTRA_CHANGED_PROPERTY);
        notifyOnPropertyChanged(context,intent,propertyName);
    }

    /**
     * Notifies the {@link AppWidgetCoordinator} instance that a property has changed.
     * And if no Callback is registered for the calling class it executes the onPropertyChanged void.
     * Execute this function for actions witch should be broadcasted.
     * @param context App Context
     */
    public void notifyOnPropertyChanged(Context context, Intent intent, String changedProperty) {
        try {
            AppWidgetCoordinator.sendOnPropertyChanged(context, changedProperty);
        } catch (Exception e) {
            Log.w(TAG, "Could not notify AppWidgetCoordinator", e);
        }
        String className = intent.getComponent().getClassName();
        if (!AppWidgetCoordinator.classHasCallbackRegistered(context, className)) {
            onPropertyChanged(context, changedProperty);
        }
    }

    /**
     * Handles the Property Changed Event. If a callback is registered this will be triggered too.
     * {@link AppWidgetCoordinator}.
     *
     * @param context Receiving Context
     * @param property String representing the property that was changed. null if not supplied by the
     *                 sender.
     */
    public abstract void onPropertyChanged(Context context, @Nullable String property);

    /**
     * Wrapper function to get the relevant String extra from Intent
     * @param context App Contex
     * @param intent calling Intent
     */
    private void onPropertyChanged(Context context, Intent intent) {
        String property =  intent.getStringExtra(AppWidgetCoordinator.INTENT_STRING_EXTRA_CHANGED_PROPERTY);
        onPropertyChanged(context, property);
    }

    /**
     * Implements BroadcastReceiver.onReceive to dispatch calls to the various other methods on AppWidgetProvider.
     * You need to execute super onReceive to be able to receive broadcasts.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //handle internal Callback
        if (ACTION_CODE_CALLBACK_INTERNAL.equals(intent.getAction())) {
            onPropertyChanged(context, intent);
        }
    }

    //convenience Functions

    /**
     * Subscribe for Callback
     * @param context {@link AppWidgetProvider}s Context
     * @param cls Your subclass
     */
    public static void subscribeOnPropertyChangedCallback(Context context, Class<?> cls){
        AppWidgetCoordinator.registerOnPropertyChangedCallback(context,cls);
    }

    /**
     * Unsubscribe for Callback
     * @param context {@link AppWidgetProvider}s Context
     * @param cls Your subclass
     */
    public static void unsubscribeOnPropertyChangedCallback(Context context, Class<?> cls){
        AppWidgetCoordinator.deregisterOnPropertyChangedCallback(context,cls);
    }
}
