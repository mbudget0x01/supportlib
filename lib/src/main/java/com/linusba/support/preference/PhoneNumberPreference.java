package com.linusba.support.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.preference.Preference;

import com.linusba.support.contact.ContactUtil;

/**
 * Preference to Store Phone Numbers
 * Requires READ_CONTACT permission, otherwise no summary will be provided
 */
public class PhoneNumberPreference extends Preference implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPreferences;
    private static final String TAG = PhoneNumberPreference.class.getSimpleName();

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     * @param defStyleRes -
     */
    public PhoneNumberPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     */
    public PhoneNumberPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     */
    public PhoneNumberPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     */
    public PhoneNumberPreference(Context context) {
        super(context);
        init(context);
    }

    /**
     * Initializes the fragment Specific functionality
     * @param context for shared References access
     */
    private void init(Context context){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        updateSummary();
    }


    /**
     * Gets the phone number associated with this reference
     * @return Phone Number as String or null if not available or empty
     */
    @SuppressLint("MissingPermission")
    @Nullable
    String getNameByPhoneNumberPreference() {
        // fragments can obviously support null as context, why? wtf?

        String phoneNumber = sharedPreferences.getString(getKey(),null);
        if(phoneNumber == null){
            return null;
        }
        if (phoneNumber.isEmpty()) {
            return null;
        }
        try {
            return ContactUtil.getContactNameByNumber(phoneNumber, getContext());
        } catch (SecurityException ignored){
            //catches if READ Contacts is not Displayed => no summary line
            return null;
        }
    }

    /**
     * Updates the Summary Line with the Contact
     */
    void updateSummary(){
        String newSummary = getNameByPhoneNumberPreference();
        if (newSummary != null){
            super.setSummary(newSummary);
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        updateSummary();
    }

}
