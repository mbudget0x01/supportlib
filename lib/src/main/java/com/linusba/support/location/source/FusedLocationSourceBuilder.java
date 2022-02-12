package com.linusba.support.location.source;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Context;
import android.os.HandlerThread;
import android.os.Looper;

import androidx.annotation.RequiresPermission;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.LocationSource;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides chainable functions to build an Location Source, adds execution Time.
 * If Values are not specified standard Values from {@link FusedLocationSource} are used.
 */
public class FusedLocationSourceBuilder {

    /**
     * Default = 4000 milliseconds
     */
    public static final Integer STANDARD_INTERVAL = 4000;

    /**
     * Default = 6000 milliseconds
     */
    public static final Integer STANDARD_MAX_WAIT_TIME = 6000;

    /**
     * Default = PRIORITY_HIGH_ACCURACY
     */
    public static final Integer LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    //Interval in milliseconds
    private Integer standardInterval = null;
    //max Wait Time in milliseconds
    private Integer maxWaitTime = null;
    private Integer locationRequestPriority = null;
    private Looper looper = null;
    private final List<LocationSource.OnLocationChangedListener> onLocationChangedListeners = new ArrayList<>();

    /**
     * Desired update Interval for Location Updates
     * @param milliseconds time in milliseconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setStandardInterval(int milliseconds){
        this.standardInterval = milliseconds;
        return this;
    }

    /**
     * Desired update Interval for Location Updates
     * @param seconds time in seconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setStandardIntervalSeconds(int seconds){
        return setStandardInterval(seconds * 1000);
    }

    /**
     * Desired max Update Interval for Location Updates
     * @param milliseconds time in milliseconds
     * @return this for chaining
     */
    public FusedLocationSourceBuilder setMaxWaitTime(int milliseconds){
        this.maxWaitTime = milliseconds;
        return this;
    }


    /**
     * Desired max Update Interval for Location Updates
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
     * Defines if the Location Updates run on the Main Thread or a new thread.
     * @param runSeparate spawns a new thread if true
     * @return this for chaining
     */
    public FusedLocationSourceBuilder runInSeparateThread(boolean runSeparate){
        if(runSeparate && looper == null){
            this.looper = getNewLooper();
        } else {
            looper = null;
        }
        return this;
    }


    /**
     * Instantiates a new Looper Thread to pass to the FusedLocationSource
     * @return a new Looper
     */
    private Looper getNewLooper(){
        UUID threadUUID = UUID.randomUUID();
        String threadName = "LocationSource-" + threadUUID.toString();
        HandlerThread handlerThread = new HandlerThread(threadName);
        handlerThread.start();
        return handlerThread.getLooper();
    }

    /**
     * Returns a new Instance of Fused Location Source.
     * If a parameter is not set a Standard value will be set.
     * @param context Context for Reference
     * @return FusedLocationSource
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource build(Context context){
        // validate parameters
        validate();
        //prepare Data
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(locationRequestPriority)
                .setMaxWaitTime(maxWaitTime)
                .setInterval(standardInterval);

        //Instantiate Class
        FusedLocationSource fusedLocationSource = new FusedLocationSource(context, locationRequest,looper);

        //set Callbacks

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
            standardInterval = STANDARD_INTERVAL;
        }
        if(maxWaitTime == null){
            maxWaitTime = STANDARD_MAX_WAIT_TIME;
        }
        if(locationRequestPriority == null){
            locationRequestPriority = LOCATION_REQUEST_PRIORITY;
        }
    }
}
