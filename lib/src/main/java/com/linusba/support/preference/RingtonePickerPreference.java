package com.linusba.support.preference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.Nullable;

import com.linusba.support.ringtone.ui.RingtonePicker;

/**
 * Preference to store ringtone Uris.
 * Displays the ringtones title if available & opens a Ringtone Picker on click
 */
public class RingtonePickerPreference extends RingtonePreference implements RingtonePicker.RingtonePickerCallback {

    private RingtonePicker ringtonePicker;

    /**
     * Needed for Android Activity don't change
     *
     * @param context      -
     * @param attrs        -
     * @param defStyleAttr -
     * @param defStyleRes  -
     */
    public RingtonePickerPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     *
     * @param context      -
     * @param attrs        -
     * @param defStyleAttr -
     */
    public RingtonePickerPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     *
     * @param context -
     * @param attrs   -
     */
    public RingtonePickerPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     *
     * @param context -
     */
    public RingtonePickerPreference(Context context) {
        super(context);
        init(context);
    }


    /**
     * Initializes the Preference
     * Be aware it's always assumed the context is a Activity
     * @param context Assumed Activity
     */
    private void init(Context context){
        //do this here => lifeCycle
        ringtonePicker = new RingtonePicker( (ActivityResultCaller) context,this);
    }

    /**
     * Callback Function with the selected ringtone
     *
     * @param ringtoneUri the uri as String or null if none chosen
     * @param resultCode  the result code from the activity
     * @see Activity
     */
    @SuppressLint("ApplySharedPref")
    @Override
    public void onRingtonePickerResult(@Nullable String ringtoneUri, int resultCode) {
        if(resultCode != Activity.RESULT_OK || ringtoneUri == null){
            return;
        }
        sharedPreferences.edit().putString(getKey(),ringtoneUri).commit();
    }

    @Override
    protected void onClick() {
        super.onClick();
        ringtonePicker.launch();
    }
}
