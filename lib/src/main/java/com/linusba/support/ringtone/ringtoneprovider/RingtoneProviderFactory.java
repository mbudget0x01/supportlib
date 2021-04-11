package com.linusba.support.ringtone.ringtoneprovider;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.linusba.support.environment.permission.PermissionHandler;

public class RingtoneProviderFactory {

    private static final String TAG = RingtoneProviderFactory.class.getSimpleName();

    public static IRingtoneProvider getIntsance(Context context){

        //Version M supports Interruption Filter, possible depreciated Methods
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
            loggingLoadedInstance(RingtoneProviderM.class.getSimpleName());
            return new RingtoneProviderM(disableDoNotDisturb(context),context);
        }

        //newest and standard Ringtone
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            loggingLoadedInstance(RingtoneProviderP.class.getSimpleName());
            return new RingtoneProviderP(disableDoNotDisturb(context),context);
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
    private static boolean disableDoNotDisturb(Context context){
        //To avoid Security Exceptions we check here
        //TODO: check if sufficient like this
        return !PermissionHandler.needNotificationPolicyPermission(context);
    }

}
