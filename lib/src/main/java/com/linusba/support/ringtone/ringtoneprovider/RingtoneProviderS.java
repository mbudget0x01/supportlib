package com.linusba.support.ringtone.ringtoneprovider;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.linusba.support.ringtone.RingtoneUtil;

@RequiresApi(api = Build.VERSION_CODES.S)
public class RingtoneProviderS implements IRingtoneProvider {

    private boolean canDisableDoNotDisturb;
    private boolean shouldAndCanIgnoreDoNotDisturb;
    private int originalRingerMode, originalInterruptionFilter;
    Context context;
    Ringtone currentRingtone;

    /**
     * RingtoneProvider for Android 12 and above
     * @param canDisableDoNotDisturb if the permissions allow to disable dnd
     * @param shouldAndCanIgnoreDoNotDisturb if dnd should be ignored or just silent. Silent counts new as dnd
     * @param context App Context
     */
    public RingtoneProviderS(boolean canDisableDoNotDisturb, boolean shouldAndCanIgnoreDoNotDisturb, Context context){
        this.canDisableDoNotDisturb = canDisableDoNotDisturb;
        this.shouldAndCanIgnoreDoNotDisturb = shouldAndCanIgnoreDoNotDisturb;
        this.context = context;
    }


    @Override
    public void startRingtone(Uri ringtone) {
        saveOriginalValues(context);
        setAlertValues(context);
        currentRingtone = buildRingtone(context,ringtone);
        currentRingtone.play();
    }

    @Override
    public void stopRingtone() {
        currentRingtone.stop();
        setOriginalValues(context);
    }

    @Override
    public boolean isPlaying() {
        if (currentRingtone == null){return false;}
        return currentRingtone.isPlaying();
    }

    private void saveOriginalValues(Context context){
        if(shouldAndCanIgnoreDoNotDisturb){
            originalInterruptionFilter = RingtoneUtil.getCurrentInterruptionFilter(context);
        }
        originalRingerMode = RingtoneUtil.getCurrentRingerMode(context);
    }

    private void setAlertValues(Context context){
        if(originalInterruptionFilter == 0 && !shouldAndCanIgnoreDoNotDisturb){
            // if undefined we treat it as high priority
            return;
        }
        if(originalInterruptionFilter <= NotificationManager.INTERRUPTION_FILTER_PRIORITY && !shouldAndCanIgnoreDoNotDisturb){
            //ignore all Priorities below
            return;
        }
        //order matters see https://stackoverflow.com/questions/58044974/enable-silent-mode-in-android-without-triggering-do-not-disturb
        if(canDisableDoNotDisturb || originalRingerMode != AudioManager.RINGER_MODE_SILENT) {
            RingtoneUtil.changeRingerMode(AudioManager.RINGER_MODE_NORMAL, context);
        }
        if(shouldAndCanIgnoreDoNotDisturb){
            RingtoneUtil.changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL,context);
        }

    }

    private void setOriginalValues(Context context){
        //order matters see https://stackoverflow.com/questions/58044974/enable-silent-mode-in-android-without-triggering-do-not-disturb
        RingtoneUtil.changeRingerMode(originalRingerMode, context);
        if(canDisableDoNotDisturb){
            RingtoneUtil.changeInterruptionFilter(originalInterruptionFilter,context);
        }
    }

    private Ringtone buildRingtone(Context context, Uri customRingtone){
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), customRingtone);
        r.setAudioAttributes(new AudioAttributes.Builder()
                .setFlags(AudioAttributes.FLAG_AUDIBILITY_ENFORCED)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_UNKNOWN)
                .build());
        r.setVolume((float) 1.0);
        r.setLooping(true);
        return r;
    }

}
