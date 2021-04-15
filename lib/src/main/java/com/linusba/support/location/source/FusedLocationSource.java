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

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * Implementation of FusedLocationProviderClient with use case Interface ready to use
 */
public class FusedLocationSource extends LocationCallback implements LocationSource, LocationListener {

    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final LocationRequest locationRequest;
    private List<OnLocationChangedListener> locationChangedListeners = new ArrayList<>();
    private Location lastLocation = null;
    private boolean locationUpdatedEnabled = false;


    /**
     * Implementation of FusedLocationProviderClient with use case Interface ready to use
     * @param context Context
     * @param locationRequest The Location Request with the needed paramters
     * @see LocationRequest
     */
    @SuppressLint("VisibleForTests")
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource(Context context, LocationRequest locationRequest){
        this.locationRequest = locationRequest;
        //We can ignore visible for tests as we capsulated this and need the context
        this.fusedLocationProviderClient = new FusedLocationProviderClient(context);
        enableLocationUpdates();

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

    /**
     * Disables Location Updates as whole.
     */
    public void disableLocationUpdates(){
      fusedLocationProviderClient.removeLocationUpdates(this);
      locationUpdatedEnabled = false;
    }

    /**
     * Enables Location updates if not already Enabled. Is enabled after instantiation
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void enableLocationUpdates(){
        if(locationUpdatedEnabled){
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, this, Looper.getMainLooper());
        locationUpdatedEnabled = true;
    }

    /**
     * Indicates if Loaction Updates are enabled, default is true.
     * @return True if is enabled
     */
    public boolean isLocationUpdatedEnabled() {
        return locationUpdatedEnabled;
    }
}
