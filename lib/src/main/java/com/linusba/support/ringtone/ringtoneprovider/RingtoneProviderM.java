package com.linusba.support.ringtone.ringtoneprovider;

import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.linusba.support.ringtone.RingtoneUtil;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.M)
class RingtoneProviderM implements IRingtoneProvider {

    private boolean canDisableDoNotDisturb;
    private boolean isPlaying = false;
    private int originalRingerMode, originalInterruptionFilter;
    private Timer timer = new Timer();
    Context context;
    Ringtone currentRingtone;

    public RingtoneProviderM(boolean canDisableDoNotDisturb, Context context){
        this.canDisableDoNotDisturb = canDisableDoNotDisturb;
        this.context = context;
    }


    @Override
    public void startRingtone(Uri ringtone) {
        saveOriginalValues(context);
        setAlertValues(context);
        currentRingtone = buildRingtone(context,ringtone);
        startRingtone(currentRingtone);
    }

    @Override
    public void stopRingtone() {
        stopRingtone(currentRingtone);
        setOriginalValues(context);
    }

    @Override
    public boolean isPlaying() {
        return isPlaying;
    }

    private void saveOriginalValues(Context context){
        originalRingerMode = RingtoneUtil.getCurrentRingerMode(context);
        if(canDisableDoNotDisturb){
            originalInterruptionFilter = RingtoneUtil.getCurrentInterruptionFilter(context);
        }
    }

    private void setAlertValues(Context context){
        RingtoneUtil.changeRingerMode(AudioManager.RINGER_MODE_NORMAL, context);
        if(canDisableDoNotDisturb){
            RingtoneUtil.changeInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL,context);
        }
    }

    private void setOriginalValues(Context context){
        RingtoneUtil.changeRingerMode(originalRingerMode, context);
        if(canDisableDoNotDisturb){
            RingtoneUtil.changeInterruptionFilter(originalInterruptionFilter,context);
        }
    }

    private Ringtone buildRingtone(Context context, Uri customRingtone){
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), customRingtone);
        r.setStreamType(AudioManager.STREAM_ALARM);
        return r;
    }

    private void startRingtone(final Ringtone ringtone){
        //make sure to start with playing the ringtone
        //ringtone.play();

        TimerTask timerTask = new TimerTask() {
            public void run() {
                if (!ringtone.isPlaying()) {
                    ringtone.play();
                }
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        isPlaying = true;
    }

    private void stopRingtone(Ringtone ringtone){
        timer.cancel();
        ringtone.stop();
        timer = new Timer();
        isPlaying = false;
    }
}
