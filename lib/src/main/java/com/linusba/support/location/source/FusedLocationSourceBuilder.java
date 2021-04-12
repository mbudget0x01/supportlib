package com.linusba.support.location.source;

import android.content.Context;
import androidx.annotation.RequiresPermission;
import com.google.android.gms.maps.LocationSource;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Provides chainable functions to build an Location Source, adds execution Time.
 * If Values are not specified standard Values from {@link FusedLocationSource} are used.
 */
public class FusedLocationSourceBuilder {

    //Intervall in milliseconds
    private Integer standardInterval = null;
    //max Wait Time in milliseconds
    private Integer maxWaitTime = null;
    private Integer locationRequestPriority = null;
    private final List<LocationSource.OnLocationChangedListener> onLocationChangedListeners = new ArrayList<>();

    /**
     * Desired update Intervall for Loction Updates
     * @param milliseconds time in milliseconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setStandardInterval(int milliseconds){
        this.standardInterval = milliseconds;
        return this;
    }

    /**
     * Desired update Intervall for Loction Updates
     * @param seconds time in seconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setStandardIntervalSeconds(int seconds){
        return setStandardInterval(seconds * 1000);
    }

    /**
     * Desired max Update Intervall for Loction Updates
     * @param milliseconds time in milliseconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setMaxWaitTime(int milliseconds){
        this.maxWaitTime = milliseconds;
        return this;
    }


    /**
     * Desired max Update Intervall for Loction Updates
     * @param seconds time in milliseconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setMaxWaitTimeSeconds(int seconds){
        return setMaxWaitTime(seconds * 1000);
    }

    /**
     * Set the Priority of the Location Requests
     * @param priority @see {@link com.google.android.gms.location.LocationRequest}
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setLocationRequestPriority(int priority){
        this.locationRequestPriority = priority;
        return this;
    }

    /**
     * Add Listeners you want to serve
     * @param onLocationChangedListener Listener to server
     * @return this for chaining
     */
    public FusedLocationSourceBuilder addOnLocationChangedListener(LocationSource.OnLocationChangedListener onLocationChangedListener){
        this.onLocationChangedListeners.add(onLocationChangedListener);
        return this;
    }

    /**
     * Returns a new Instance of Fused Location Source
     * @param context Context for Reference
     * @return FusedLocationSource
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource build(Context context){
        validate();
        FusedLocationSource fusedLocationSource = new FusedLocationSource(context,
                standardInterval,
                maxWaitTime,
                locationRequestPriority);
        for (LocationSource.OnLocationChangedListener listener:onLocationChangedListeners) {
            fusedLocationSource.activate(listener);
        }
        return fusedLocationSource;
    }

    /**
     * Validate the values we want to set, internal use only
     */
    private void validate(){
        if(standardInterval == null){
            standardInterval = FusedLocationSource.STANDARD_INTERVAL;
        }
        if(maxWaitTime == null){
            maxWaitTime = FusedLocationSource.STANDARD_MAX_WAIT_TIME;
        }
        if(locationRequestPriority == null){
            locationRequestPriority = FusedLocationSource.LOCATION_REQUEST_PRIORITY;
        }
    }
}
