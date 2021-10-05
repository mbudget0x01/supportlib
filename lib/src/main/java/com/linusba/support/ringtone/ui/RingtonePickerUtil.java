package com.linusba.support.ringtone.ui;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Utility to easy access a Ringtone Picker from the UI
 */
public class RingtonePickerUtil {

    static Intent getIntent() {
        return new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
    }



    /*---------------------------------------------------------------------------------------------

        Legacy stuff
        TODO: delete in next major version

     */


    /**
     * Displays a ringtone Picker for Activity Result
     *
     * @param fragment    fragment to handle the result
     * @param requestCode requestCode for the result
     * @since since androidx uses activity result api
     * @deprecated use {@link RingtonePicker} instead
     */
    @Deprecated
    @SuppressWarnings("deprecation")
    public static void displayRingtonePicker(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(getIntent(), requestCode);
    }

    /**
     * Displays a ringtone Picker for Activity Result
     *
     * @param activity    activity to handle the result
     * @param requestCode requestCode for the result
     * @since since androidx uses activity result api
     * @deprecated use {@link RingtonePicker} instead
     */
    @Deprecated
    public static void displayRingtonePicker(Activity activity, int requestCode) {
        activity.startActivityForResult(getIntent(), requestCode);
    }


    /**
     * Handles a RingtonePicker response
     *
     * @param data Intent from Activity Result
     * @return URI of the file as String, null if data not available
     * @deprecated use {@link RingtonePicker} instead
     * @since since androidx uses activity result api
     */
    @Nullable
    @Deprecated
    public static String handleRingtonePickerResponse(@Nullable Intent data) {
        if (data == null) {
            return null;
        }
        return String.valueOf(data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));
    }


}
