package com.linusba.support.preference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;

import androidx.activity.result.ActivityResultCaller;
import androidx.annotation.Nullable;

import com.linusba.support.contact.ui.ContactPicker;
import com.linusba.support.contact.ui.ContactResponse;

/**
 * Extends the PhoneNumberPreference with a Contact Picker
 * Requires READ_CONTACT permission, otherwise no summary will be provided
 */
public class PhoneNumberContactPreference extends PhoneNumberPreference implements ContactPicker.ContactPickerCallback {
    private ContactPicker contactPicker;

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     * @param defStyleRes -
     */
    public PhoneNumberContactPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     */
    public PhoneNumberContactPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     */
    public PhoneNumberContactPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     */
    public PhoneNumberContactPreference(Context context) {
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
        contactPicker = new ContactPicker( (ActivityResultCaller) context,this,context);
    }

    /**
     * Callback for Contact Picker
     * Change for Phone Number gets commited as I am not sure about the lifecycle, I'wont use .apply()
     * @param contactResponse Contact Response from the Picker
     * @param resultCode {@link Activity#RESULT_OK} etc..
     */
    @SuppressLint("ApplySharedPref")
    @Override
    public void onContactPickerResult(@Nullable ContactResponse contactResponse, int resultCode) {
        if(resultCode != Activity.RESULT_OK){
            return;
        }
        if (contactResponse != null) {
            sharedPreferences.edit().putString(getKey() ,contactResponse.getPhoneNumber()).commit();
        }
        super.updateSummary();
    }

    /**
     * launches the Contact Picker
     */
    @Override
    protected void onClick() {
        super.onClick();
        contactPicker.launch();
    }
}
