package com.linusba.support.ringtone.ui;

import static com.linusba.support.ringtone.ui.RingtonePickerUtil.getIntent;

import android.app.Activity;
import android.content.Intent;
import android.media.RingtoneManager;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

/**
 * Class to capsule all the Handling needed for the Ringtone Picker.
 * This replaces the Use of {@link RingtonePickerUtil}
 * Make sure to instantiate this class on Activity.create()
 */
public class RingtonePicker {
    private ActivityResultLauncher<Intent> launcher;

    /**
     * Creates a new Instance of the Ringtone Picker Class
     * @param caller parent Activity or Fragment or what else
     * @param callback The callback to use for this
     */
    public RingtonePicker(ActivityResultCaller caller, RingtonePickerCallback callback){
        prepareLauncher(caller, callback);
    }

    /**
     * Prepares the launcher for Activity Result
     * @param caller parent Activity or Fragment or what else
     * @param callback The callback to use for this
     */
    private void prepareLauncher(ActivityResultCaller caller, RingtonePickerCallback callback){
        this.launcher = caller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    String uri = parseUri(result.getData());
                    callback.onRingtonePickerResult(uri, result.getResultCode());
                });
    }

    /**
     * Launches The Ringtone Picker
     */
    public void launch(){
        launcher.launch(getIntent());
    }


    /**
     * Callback Interface for this class
     */
    public interface RingtonePickerCallback {
        /**
         * Callback Function with the selected ringtone
         *
         * @param ringtoneUri the uri as String or null if none chosen
         * @param resultCode the result code from the activity
         * @see Activity
         */
        void onRingtonePickerResult(@Nullable String ringtoneUri, int resultCode );
    }

    /**
     * Parses the RingtonePicker response
     *
     * @param data Intent from Activity Result
     * @return URI of the file as String, null if data not available
     */
    private static String parseUri(@Nullable Intent data) {
        if (data == null) {
            return null;
        }
        return String.valueOf(data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI));
    }

}
