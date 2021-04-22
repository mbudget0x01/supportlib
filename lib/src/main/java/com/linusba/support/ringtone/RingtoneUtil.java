package com.linusba.support.ringtone;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Util to interact deal with Ringtone related stuff
 */
public class RingtoneUtil {

    /* ***************************************************
     *
     * Interruption Filter
     *
     ****************************************************/

    /**
     * Returns the current interruption filter
     * @param context Context for reference
     * @return int representation of the Interruption Filter
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getCurrentInterruptionFilter(Context context){
       return getNotificationManager(context).getCurrentInterruptionFilter();
    }

    /**
     * Sets the Interruptionfilter.
     * Be aware if you set ringermode to silent it sets this filter to dnd! Consider
     * setting the ringermode befor the interruptionfilter
     * @param interruptionFilter interuption filter according to android docs
     * @param context context for reference
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void changeInterruptionFilter(Integer interruptionFilter, Context context){
        getNotificationManager(context).setInterruptionFilter(interruptionFilter);
    }

    private static NotificationManager getNotificationManager(Context context){
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /* ***************************************************
     *
     * Audio Manager
     *
     ****************************************************/

    /**
     * Returns the current ringer mode
     * @param context Context for reference
     * @return int representation of the Ringer Mode
     */
    public static int getCurrentRingerMode(Context context){
        return getAudioManager(context).getRingerMode();
    }

    /**
     * Sets the ringermode.
     * Be aware if you set ringermode to silent it sets this filter to dnd! Consider
     * setting the ringermode befor the interruptionfilter
     * @param ringerMode ringermode filter according to android docs
     * @param context context for reference
     */
    public static void changeRingerMode(Integer ringerMode, Context context){
        getAudioManager(context).setRingerMode(ringerMode);
    }

    private static AudioManager getAudioManager(Context context){
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    /**
     * If specified returns the custom ringtone, else returns the default Alarm Ringtone.
     * @param context Context for reference
     * @param customRingtone path to the custom Ringtone
     * @param ringtoneType RingtoneManager Type e.g. Alarm
     * @see  RingtoneManager
     * @return Uri to the ringtone.
     */
    public static Uri getRingtone(Context context, String customRingtone, int ringtoneType){
        if (customRingtone.isEmpty()) {
            return RingtoneManager.getDefaultUri(ringtoneType);
        } else {
            return Uri.parse(customRingtone);
        }
    }
}
