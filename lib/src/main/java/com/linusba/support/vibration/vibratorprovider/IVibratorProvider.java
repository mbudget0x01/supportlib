package com.linusba.support.vibration.vibratorprovider;

import android.Manifest;

import androidx.annotation.RequiresPermission;

public interface IVibratorProvider {

    @RequiresPermission(Manifest.permission.VIBRATE)
    void startVibrator(long period);
    @RequiresPermission(Manifest.permission.VIBRATE)
    void stopVibrator();
    @RequiresPermission(Manifest.permission.VIBRATE)
    boolean isVibrating();

}
