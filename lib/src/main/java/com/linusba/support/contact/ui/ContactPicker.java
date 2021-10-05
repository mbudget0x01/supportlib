package com.linusba.support.contact.ui;

import static com.linusba.support.contact.ui.ContactPickerUtil.getIntent;
import static com.linusba.support.contact.ui.ContactPickerUtil.parseResponse;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.activity.result.ActivityResultCaller;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

/**
 * Class to capsule all the Handling needed for the Ringtone Picker.
 * This replaces the Use of {@link ContactPickerUtil}
 * Make sure to instantiate this class on Activity.create()
 */
public class ContactPicker {


    private ActivityResultLauncher<Intent> launcher;

    private Context context;

    /**
     * Creates a new Instance of the Ringtone Picker Class
     * @param caller parent Activity or Fragment or what else
     * @param callback The callback to use for this
     * @param context Context the application is running in
     */
    public ContactPicker(ActivityResultCaller caller, ContactPickerCallback callback ,Context context) {
        this.context = context;
        prepareLauncher(caller,callback);
    }

    /**
     * Prepares the launcher for Activity Result
     * @param caller parent Activity or Fragment or what else
     * @param callback The callback to use for this
     */
    private void prepareLauncher(ActivityResultCaller caller, ContactPickerCallback callback){
        this.launcher = caller.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    ContactResponse contactResponse = parseResponse(result.getData(),context);
                    callback.onContactPickerResult(contactResponse, result.getResultCode());
                });
    }

    /**
     * Launches The Contact Picker
     */
    public void launch(){
        launcher.launch(getIntent());
    }



    /**
     * Callback Interface for this class
     */
    public interface ContactPickerCallback{
        /**
         * Callback Function with the selected ringtone
         *
         * @param contactResponse the {@link ContactResponse} or null if none chosen
         * @param resultCode the result code from the activity
         * @see Activity
         */
        void onContactPickerResult(@Nullable ContactResponse contactResponse, int resultCode);
    }


}
