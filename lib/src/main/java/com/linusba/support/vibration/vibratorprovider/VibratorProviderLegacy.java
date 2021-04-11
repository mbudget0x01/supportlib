package com.linusba.support.vibration.vibratorprovider;

import android.content.Context;
import android.os.Vibrator;

import com.linusba.support.vibration.VibratorUtil;

class VibratorProviderLegacy implements IVibratorProvider {

    private Vibrator vibrator;
    private boolean _isVibrating = false;

    public VibratorProviderLegacy(Context context){
        this.vibrator = VibratorUtil.getVibrator(context);
    }

    @Override
    public void startVibrator(long period) {
        vibrator.vibrate(period);
        _isVibrating = true;
    }

    @Override
    public void stopVibrator() {
        vibrator.cancel();
        _isVibrating = true;
    }

    @Override
    public boolean isVibrating() {
        return _isVibrating;
    }
}
