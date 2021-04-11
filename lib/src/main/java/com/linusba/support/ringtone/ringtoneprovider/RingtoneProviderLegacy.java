package com.linusba.support.ringtone.ringtoneprovider;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.linusba.support.ringtone.RingtoneUtil;

import java.util.Timer;
import java.util.TimerTask;

class RingtoneProviderLegacy implements IRingtoneProvider {

    private int originalRingerMode;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private Context context;
    private Ringtone currentRingtone;
    private boolean isPlaying = false;

    public RingtoneProviderLegacy(Context context){
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
    }

    private void setAlertValues(Context context){
        RingtoneUtil.changeRingerMode(AudioManager.RINGER_MODE_NORMAL, context);
    }

    private void setOriginalValues(Context context){
        RingtoneUtil.changeRingerMode(originalRingerMode, context);
    }

    private Ringtone buildRingtone(Context context, Uri customRingtone){
        Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), customRingtone);
        r.setStreamType(AudioManager.STREAM_ALARM);
        return r;
    }

    private void startRingtone(final Ringtone ringtone){
        //make sure to start with playing the ringtone
        if(timerTask == null){
            timerTask = new TimerTask() {
                public void run() {
                    if (!ringtone.isPlaying()) {
                        ringtone.play();
                    }
                }
            };
            timer.scheduleAtFixedRate(timerTask, 1000, 1000);
        }
        timerTask.run();
        ringtone.play();

        isPlaying = true;
    }

    private void stopRingtone(Ringtone ringtone){
        timerTask.cancel();
        ringtone.stop();
        isPlaying = false;
    }
}
