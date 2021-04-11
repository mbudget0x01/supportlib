package com.linusba.support.environment;

import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;


/**
 * Provides functionality to Handle Lockscreens
 */
public class LockscreenHandler {

    /**
     * Unlocks the Screen for the Calling Activity according to the
     * different APIs
     * @param activity Activity you want to unlock the Screen for
     */
    public static void unlockScreen(Activity activity){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            unlockScreen29(activity);
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            unlockScreen27(activity);
            return;
        }


        unlockScreenLegacy(activity.getWindow());
    }

    //use when API < 27
    private static void unlockScreenLegacy(Window window){
        window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //use when API >= 27
    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    private static void unlockScreen27(Activity activity){
        activity.setShowWhenLocked(true);
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //use when API >= 30
    //delayed because of crappy activity life cycle in lower versions
    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    private static void unlockScreen29(Activity activity){
        activity.setShowWhenLocked(true);
        activity.setTurnScreenOn(true);
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

}
