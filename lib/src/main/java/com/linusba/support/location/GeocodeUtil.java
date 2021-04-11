package com.linusba.support.location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;

/**
 * This provides some basic Geocoding functionality.
 */
public class GeocodeUtil {

    private static final String TAG = GeocodeUtil.class.getSimpleName();
    private final static String GEOCODER_ERROR_EMPTY_STRING = "Invalid String provided";
    private final static String GEOCODER_ERROR_NO_LAT_LNG = "No Lat or Lng provided";


    /**
     * Geocodes a given Address as String to a {@link Address} Object
     * @param addressString String, representing the address
     * @param context Context of the caller
     * @return Address Object
     * @throws IOException If network is unavailable or IO Error occurs
     * @throws IllegalArgumentException If addressString is null
     */
    public static Address geocodeAddressString(String addressString, Context context) throws IOException, IllegalArgumentException {
        Geocoder geocoder = new Geocoder(context);
        return geocoder.getFromLocationName(addressString, 1).get(0);
    }

    /**
     * Geocodes a given Address as String to a {@link Address} Object and checks
     * its validity.
     * @param addressString String, representing the address
     * @param context Context of the caller
     * @return Address Object
     * @throws NullPointerException If Latitude or Longitude is not present
     * @throws IllegalArgumentException If addressString is null
     * @throws IOException If network is unavailable or IO Error occurs
     */
    public static Address geocodeAddressStringWithCheck(String addressString, Context context) throws NullPointerException, IllegalArgumentException, IOException {
        //we check here as in case it throws exception it's a better performance
        if(addressString == null || addressString.isEmpty()){
            Log.e(TAG, GEOCODER_ERROR_EMPTY_STRING);
            throw new IllegalArgumentException(GEOCODER_ERROR_EMPTY_STRING);
        }
        //we use application Context cause of lifecycle
        Address internal = geocodeAddressString(addressString, context.getApplicationContext());
        if (internal == null){
            Log.e(TAG, GEOCODER_ERROR_EMPTY_STRING);
            throw new IllegalArgumentException(GEOCODER_ERROR_EMPTY_STRING);
        }
        if (!internal.hasLatitude() || !internal.hasLongitude()){
            Log.e(TAG, GEOCODER_ERROR_NO_LAT_LNG);
            throw new NullPointerException(GEOCODER_ERROR_NO_LAT_LNG);
        }
        return internal;
    }
}
