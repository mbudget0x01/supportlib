package com.linusba.support.location.source;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.LocationSource;
import com.linusba.support.environment.permission.PermissionHandler;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Implementation of FusedLocationProviderClient with use case Interface ready to use
 */
public class FusedLocationSource extends LocationCallback implements LocationSource, LocationListener {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private List<OnLocationChangedListener> locationChangedListeners = new ArrayList<>();
    private Location lastLocation = null;
    static final Integer STANDARD_INTERVAL = 4000;
    static final Integer STANDARD_MAX_WAIT_TIME = 6000;
    static final Integer LOCATION_REQUEST_PRIORITY = LocationRequest.PRIORITY_HIGH_ACCURACY;

    /**
     * Implementation of FusedLocationProviderClient with use case Interface ready to use
     * Uses standard Values
     * @param context Context
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource(Context context) {
        init(context,STANDARD_INTERVAL,STANDARD_MAX_WAIT_TIME, LOCATION_REQUEST_PRIORITY);
    }

    /**
     *
     * @param context Context
     * @param updateInterval The desired Update-Interval in milliseconds
     * @param maxWaitTime The max interval Time in milliseconds
     * @param locationRequestPriority The Priority for Location requests
     * @see LocationRequest
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource(Context context, Integer updateInterval, Integer maxWaitTime, Integer locationRequestPriority) {
        init(context, updateInterval, maxWaitTime, locationRequestPriority);
    }

    //Actual function of the constructor
    //As we don't have unit tests now && we check permissions at other places
    @SuppressLint("VisibleForTests")
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    private void init(Context context, Integer updateInterval, Integer maxWaitTime, Integer locationRequestPriority){
        checkPermissions(context);
        fusedLocationProviderClient = new FusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(updateInterval);
        locationRequest.setMaxWaitTime(maxWaitTime);
        locationRequest.setPriority(locationRequestPriority);
        fusedLocationProviderClient.requestLocationUpdates(locationRequest,this,Looper.getMainLooper());
    }

    @Override
    public void onLocationResult(@NonNull LocationResult locationResult) {
        super.onLocationResult(locationResult);
        lastLocation = locationResult.getLastLocation();
        onLocationChanged(locationResult.getLastLocation());
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListeners.add(onLocationChangedListener);
    }


    @Override
    public void deactivate() {
        locationChangedListeners.clear();
        locationChangedListeners = new ArrayList<>();
    }

    public void deactivate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListeners.remove(onLocationChangedListener);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        for (OnLocationChangedListener l : locationChangedListeners) {
            l.onLocationChanged(location);
        }
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    private void checkPermissions(Context context){
        String[] missingPermissions = PermissionHandler.checkBasePermissions(context);
        for (String permission : missingPermissions) {
            if (permission.equals(ACCESS_FINE_LOCATION)) {
                throw new SecurityException("Missing Fine Location Permission");
            }
            if (permission.equals(ACCESS_COARSE_LOCATION)) {
                throw new SecurityException("Missing Coarse Location Permission");
            }
        }
    }

}
