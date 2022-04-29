package com.linusba.support.widget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import com.linusba.support.environment.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible to coordinate Coordinate with all widgets.
 * Be aware the callbacks are saved to the <strong>SharedPreferences File!</strong>
 * If you want to use it you have to enter it in the AndroidManifest.xml as enabled.
 */
public class AppWidgetCoordinator extends BroadcastReceiver {

    public static final String TAG = AppWidgetCoordinator.class.getName();
    private static final String SHARED_PREFERENCES_CALLBACK = AppWidgetCoordinator.class.getSimpleName() + ".callbacks";
    private static final String SETTING_DELIMITER = ",";

    /**
     * Identifier for the String extra containing an additional Identifier about the property which was changed.
     */
    public static final String INTENT_STRING_EXTRA_CHANGED_PROPERTY = AppWidgetCoordinator.class.getSimpleName() + ".property.changed";


    /**
     * Request Update of the Widget via Action
     */
    static final String ACTION_CODE_PROPERTY_CHANGED = "action.property.changed";

    /**
     * Identifier for callbacks only used for callback handling in {@link android.appwidget.AppWidgetProvider} subclasses
     */
    static final String ACTION_CODE_CALLBACK_INTERNAL = "action.property.changed.callback";

    /**
     * Registers a Callback for when a Property has changed.
     * This gets triggered when a Property potentially has changed.
     * @param cls Class to receive Callback
     */
    static void registerOnPropertyChangedCallback(Context context, Class<?> cls){

        List<Class<?>> callbackClasses = getCallbackClasses(context);
        if(callbackClasses.contains(cls)){
            return;
        }
        callbackClasses.add(cls);
        saveCallbackClasses(context, callbackClasses);
    }

    private static String[] getCallbackClassesNames(Context context){
        String settingString = SharedPreferencesUtil.getStringEmptySetting(SHARED_PREFERENCES_CALLBACK, context);
        if(settingString.isEmpty()){
            return new String[0];
        }
        return settingString.split(SETTING_DELIMITER);
    }

    private static List<Class<?>> getCallbackClasses(Context context){
        List<Class<?>> resp = new ArrayList<>();
        String[] classes = getCallbackClassesNames(context);
        for(String callbackClass: classes){
            try{
                resp.add(Class.forName(callbackClass));
            }catch (ClassNotFoundException e){
                Log.w(TAG, "Could not resolve Callback" ,e);
            }
        }
        return resp;
    }

    private static void saveCallbackClasses(Context context,List<Class<?>> callbackClasses){
        StringBuilder stringBuilder = new StringBuilder();
        for(Class<?> cls: callbackClasses){
            stringBuilder.append(cls.getName());
            stringBuilder.append(SETTING_DELIMITER);
        }
        SharedPreferencesUtil.setStringSetting(SHARED_PREFERENCES_CALLBACK,stringBuilder.toString(), context);
    }

    /**
     * Indicates if a Callback is registered
     * @param context App Context
     * @param cls class to check
     * @return True if registered
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PACKAGE_PRIVATE)
    public static boolean classHasCallbackRegistered(Context context ,Class<?> cls){
        return getCallbackClasses(context).contains(cls);
    }


    /**
     * Indicates if a Callback is registered
     * @param context App Context
     * @param className class to check
     * @return True if registered
     */
    static boolean classHasCallbackRegistered(Context context, String className){
        for(String cls: getCallbackClassesNames(context)){
            if(cls.equals(className)){
                return true;
            }
        }
        return false;
    }
    /**
     * Deregister Callbacks
     * @param cls Class to deregister
     */
    static void deregisterOnPropertyChangedCallback(Context context ,Class<?> cls){
        List<Class<?>> callbackClasses = getCallbackClasses(context);
        callbackClasses.remove(cls);
        saveCallbackClasses(context,callbackClasses);
    }


    /**
     * Notifies all registered callbacks of a Property Changed event
     * @param context App Context
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    private void notifyOnPropertyChanged(Context context, Intent intent){
        String changedProperty = null;
        if(intent.hasExtra(INTENT_STRING_EXTRA_CHANGED_PROPERTY)) {
            changedProperty = intent.getStringExtra(INTENT_STRING_EXTRA_CHANGED_PROPERTY);
        }
        for(Class<?> cls: getCallbackClasses(context)){
            try{
                Intent callbackIntent = new Intent(context, cls);
                callbackIntent.setAction(ACTION_CODE_CALLBACK_INTERNAL);
                if(changedProperty != null) {
                    callbackIntent.putExtra(INTENT_STRING_EXTRA_CHANGED_PROPERTY, changedProperty);
                }
                PendingIntent pendingIntent;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    pendingIntent = PendingIntent.getBroadcast(context, 0, callbackIntent, PendingIntent.FLAG_IMMUTABLE);
                } else {
                    // immutable is set above -> ignore lint
                    pendingIntent = PendingIntent.getBroadcast(context, 0, callbackIntent, 0);
                }
                pendingIntent.send();
            }catch (Exception e){
                Log.w(TAG,e);
            }
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case ACTION_CODE_CALLBACK_INTERNAL:
                throw new IllegalArgumentException(ACTION_CODE_CALLBACK_INTERNAL +" is not allowed");

            case ACTION_CODE_PROPERTY_CHANGED:
                notifyOnPropertyChanged(context, intent);
                break;
        }
    }

    //Convenience Functions

    /**
     * Notifies the {@link AppWidgetCoordinator} about a property change
     * @param context App Context
     * @param property changed property
     * @throws PendingIntent.CanceledException can be thrown on send()
     */
    public static void sendOnPropertyChanged(Context context, @Nullable String property) throws PendingIntent.CanceledException {
        getPendingOnPropertyChangedIntent(context, property).send();
    }

    /**
     * Returns a new Broadcasting Pending intent indicating a property has changed.
     * Fire with PendingIntent.send()
     * @param context Context for reference
     * @param property the changed property or null
     * @return Pending Intent
     */
    @SuppressLint("UnspecifiedImmutableFlag")
    static PendingIntent getPendingOnPropertyChangedIntent(Context context, @Nullable String property) {
        Intent intent = new Intent(context, AppWidgetCoordinator.class);
        if(property != null) {
            intent.putExtra(AppWidgetCoordinator.INTENT_STRING_EXTRA_CHANGED_PROPERTY, property);
        }
        intent.setAction(AppWidgetCoordinator.ACTION_CODE_PROPERTY_CHANGED);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            //immutable flag is handled above
            return PendingIntent.getBroadcast(context, 0, intent, 0);
        }}
}
