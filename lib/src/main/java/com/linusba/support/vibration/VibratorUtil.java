package com.linusba.support.vibration;

import android.content.Context;
import android.os.Vibrator;

/**
 * Utility class to interact with the Vibrator
 */
public class VibratorUtil {

    /* ***************************************************
     *
     * System Service functions
     *
     ****************************************************/

    /**
     * Returns the Vibrator class
     * @param context Context for refernece
     * @return Vibrator Class
     */
    public static Vibrator getVibrator(Context context){
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
}
