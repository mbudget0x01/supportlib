package com.linusba.support.vibration.vibratorprovider;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresPermission;

public class VibratorProviderFactory {

    private static final String TAG = VibratorProviderFactory.class.getSimpleName();

    @RequiresPermission(Manifest.permission.VIBRATE)
    public static IVibratorProvider getInstance(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loggingLoadedInstance(VibratorProviderO.class.getSimpleName());
            return new VibratorProviderO(context);
        }

        loggingLoadedInstance(VibratorProviderLegacy.class.getSimpleName());
        return new VibratorProviderLegacy(context);

    }

    private static void loggingLoadedInstance(String loadedClass){
        Log.d(TAG,"loaded Class: " + loadedClass);
    }
}
