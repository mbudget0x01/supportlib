package com.linusba.support.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;

/**
 * Preference used to Store Ringtone Uris. Displays the Ringtone's title if available.
 * You probably want to use the {@link RingtonePickerPreference}
 * @see RingtonePickerPreference
 */
public class RingtonePreference extends Preference implements SharedPreferences.OnSharedPreferenceChangeListener {

    SharedPreferences sharedPreferences;

    /**
     * Denotes the query key which encodes the title in content:// uris
     */
    private static final String URI_TITLE_KEY = "title";

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     * @param defStyleRes -
     */
    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     * @param defStyleAttr -
     */
    public RingtonePreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     * @param attrs -
     */
    public RingtonePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Needed for Android Activity don't change
     * @param context -
     */
    public RingtonePreference(Context context) {
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
     * Parse Title from supplied uri String
     * @param uri uri as String. This string can not be null or an exception is thrown
     * @return the Title or null if not in uri
     */
    @Nullable
    private String parseTitle(@NonNull String uri){
        return parseTitle(Uri.parse(uri));
    }

    /**
     * Parse Title from supplied uri
     * @param uri Uri as Uri Object
     * @return the title or null if not found
     */
    @Nullable
    private String parseTitle(Uri uri){
        return uri.getQueryParameter(URI_TITLE_KEY);
    }

    /**
     * Updates the Summary Line with the Ringtone
     */
    void updateSummary(){
        String val = sharedPreferences.getString(getKey(),null);
        if (val == null){
            return;
        }
        String newSummary = parseTitle(val);
        if(newSummary == null){
            return;
        }
        super.setSummary(newSummary);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getKey())) {
            updateSummary();
        }
    }
}
