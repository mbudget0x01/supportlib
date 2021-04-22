package com.linusba.support.environment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This provides some functionality to work with shared references
 */
public class SharedPreferencesUtil {

    /**
     * Returns the value of a specific SharedPreference
      * @param name name of the preference
     * @param context Context for reference
     * @return boolean value of the setting, True if it doesn't exist
     */
    public static boolean getBooleanTrueSetting(String name, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(name, true);
    }


    /**
     * Returns the value of a specific SharedPreference
     * @param name name of the preference
     * @param context Context for reference
     * @return boolean value of the setting, False if it doesn't exist
     */
    public static boolean getBooleanFalseSetting(String name, Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean(name, false);
    }

    /**
     * Sets the value of a specific SharedPreference
     * @param name name of the preference
     * @param value value of the preference
     * @param context Context for reference
     */
    public static void setBooleanSetting(String name, Boolean value, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putBoolean(name, value).apply();
    }


    /**
     * Returns the value of a specific SharedPreference
     * @param name name of the preference
     * @param defaultValue the default value of the preference
     * @param context Context for reference
     * @return String value of the setting, defaultValue if it doesn't exist
     */
    public static String getStringCustomSetting(String name, String defaultValue, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(name, defaultValue);
    }
    /**
     * Returns the value of a specific SharedPreference
     * @param name name of the preference
     * @param context Context for reference
     * @return String value of the setting, empty String if it doesn't exist
     */
    public static String getStringEmptySetting(String name, Context context){
        return getStringCustomSetting(name, "", context);
    }


    /**
     * Sets the value of a specific SharedPreference
     * @param name name of the preference
     * @param value value of the preference
     * @param context Context for reference
     */
    public static void setStringSetting(String name, String value, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(name ,value).apply();
    }

    /**
     * Returns the value of a specific SharedPreference
     * @param name name of the preference
     * @param defaultValue the default value of the preference
     * @param context Context for reference
     * @return int value of the setting, defaultValue if it doesn't exist
     */
    public static int getIntCustomSetting(String name, int defaultValue, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getInt(name, defaultValue);
    }

    /**
     * Returns the value of a specific SharedPreference
     * @param name name of the preference
     * @param context Context for reference
     * @return int value of the setting, 0 if it doesn't exist
     */
    public static int getIntZeroSetting(String name, Context context){
        return getIntCustomSetting(name, 0, context);
    }

    /**
     * Sets the value of a specific SharedPreference
     * @param name name of the preference
     * @param value value of the preference
     * @param context Context for reference
     */
    public static void setIntSetting(String name, int value, Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putInt(name ,value).apply();
    }
}
