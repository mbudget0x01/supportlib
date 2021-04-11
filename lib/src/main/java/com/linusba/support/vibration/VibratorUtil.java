package com.linusba.support.vibration;

import android.content.Context;
import android.os.Vibrator;

public class VibratorUtil {

    /****************************************************
     *
     * System Service functions
     *
     ****************************************************/

    public static Vibrator getVibrator(Context context){
        return (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }
}
