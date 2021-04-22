package com.linusba.support.vibration.vibratorprovider;

import android.Manifest;

import androidx.annotation.RequiresPermission;

public interface IVibratorProvider {

    /**
     * starts the vibration of your device in a Loop
     * @param period the vibration time
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    void startVibrator(long period);

    /**
     * Stops the vibration of your device
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    void stopVibrator();

    /**
     * Indicates if this class is active
     * @return true if this class is active
     */
    @RequiresPermission(Manifest.permission.VIBRATE)
    boolean isVibrating();

}
