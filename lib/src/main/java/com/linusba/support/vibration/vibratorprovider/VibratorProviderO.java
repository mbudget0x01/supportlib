package com.linusba.support.vibration.vibratorprovider;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

import com.linusba.support.vibration.VibratorUtil;

import java.util.Timer;
import java.util.TimerTask;

@RequiresApi(api = Build.VERSION_CODES.O)
class VibratorProviderO implements IVibratorProvider {

    private Vibrator vibrator;
    private boolean _isVibrating = false;
    private Timer timer = new Timer();
    private boolean vibratorState = false;
    private TimerTask timerTask;

    public VibratorProviderO(Context context){
        this.vibrator = VibratorUtil.getVibrator(context);
    }

    @Override
    public void startVibrator(final long period) {

        if(timerTask == null) {
            timerTask = new TimerTask() {
                public void run() {
                    if (!vibratorState) {
                        vibrator.vibrate(VibrationEffect.createOneShot(period, VibrationEffect.DEFAULT_AMPLITUDE));
                        vibratorState = true;
                    } else {
                        vibratorState = false;
                    }
                }
            };

            timer.scheduleAtFixedRate(timerTask, 1000, period + 100);
        }
        timerTask.run();
        _isVibrating = true;
    }

    @Override
    public void stopVibrator() {
        timerTask.cancel();
        vibrator.cancel();
        _isVibrating = false;
    }

    @Override
    public boolean isVibrating() {
        return _isVibrating;
    }
}
