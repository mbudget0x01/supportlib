package com.linusba.support.ringtone.ringtoneprovider;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.linusba.support.environment.permission.PermissionChecker;

/**
 * Provides the Ringtone Provider according to the API
 */
public class RingtoneProviderFactory {

    private static final String TAG = RingtoneProviderFactory.class.getSimpleName();

    /**
     * Provides the Ringtone Provider according to the API
     * Ringtone Type is alert
     * @param context Context for reference
     * @param ignoreDoNotDisturb set True if do Not Disturb should be ignored
     * @return IRingtoneProvider Instance according to your API
     */
    public static IRingtoneProvider getInstance(Context context, boolean ignoreDoNotDisturb){

        //Version M supports Interruption Filter, possible depreciated Methods
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            loggingLoadedInstance(RingtoneProviderM.class.getSimpleName());
            return new RingtoneProviderM(canDisableDoNotDisturb(context) && ignoreDoNotDisturb ,context);
        }

        //standard Ringtone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
            loggingLoadedInstance(RingtoneProviderP.class.getSimpleName());
            return new RingtoneProviderP(canDisableDoNotDisturb(context) && ignoreDoNotDisturb,context);
        }

        // Android had behavioral changes in DND
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
            loggingLoadedInstance(RingtoneProviderS.class.getSimpleName());
            boolean canDisableDND = canDisableDoNotDisturb(context);
            return new RingtoneProviderS(canDisableDND, canDisableDND && ignoreDoNotDisturb, context);
        }

        //legacy support, no interruption filter, possible depreciated Methods
        loggingLoadedInstance(RingtoneProviderLegacy.class.getSimpleName());
        return new RingtoneProviderLegacy(context);
    }

    private static void loggingLoadedInstance(String loadedClass){
        Log.d(TAG,"loaded Class: " + loadedClass);
    }

    /**
     * Returns true if DoNotDisturb can be accessed
     * @param context Context for reference
     * @return true if its save
     */
    private static boolean canDisableDoNotDisturb(Context context){
        //To avoid Security Exceptions we check here
        return !PermissionChecker.needNotificationPolicyPermission(context);
    }

}
