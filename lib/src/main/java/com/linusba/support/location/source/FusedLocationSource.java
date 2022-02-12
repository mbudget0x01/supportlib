package com.linusba.support.location.source;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.LocationSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of FusedLocationProviderClient with use case Interface ready to use
 */
public class FusedLocationSource extends LocationCallback implements LocationSource, LocationListener {

    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private List<OnLocationChangedListener> locationChangedListeners = new ArrayList<>();
    private Location lastLocation = null;
    private Looper looper;
    private boolean locationUpdatedEnabled = false;


    /**
     * Implementation of FusedLocationProviderClient with use case Interface ready to use
     * @param context Context
     * @param locationRequest The Location Request with the needed parameters
     * @see LocationRequest
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource(Context context, LocationRequest locationRequest){
        init(context,locationRequest,null);
    }

    /**
     * Implementation of FusedLocationProviderClient with use case Interface ready to use
     * @param context Context
     * @param locationRequest The Location Request with the needed parameters
     * @param looper The looper to use for Location updates. Essentially the thread to use.
     *               If looper is null the default looper will be used.
     * @see LocationRequest
     */
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public FusedLocationSource(Context context, LocationRequest locationRequest,@Nullable Looper looper){
        init(context, locationRequest, looper);
    }


    /**
     * Initialization to support multiple constructors
     * @param context Context
     * @param locationRequest The Location Request with the needed parameters
     * @param looper The looper to use for Location updates. Essentially the thread to use.
     *               If looper is null the default looper will be used.
     * @see LocationRequest
     */
    @SuppressLint("VisibleForTests")
    @RequiresPermission(allOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    private void init(Context context, LocationRequest locationRequest,@Nullable Looper looper){
        this.looper = looper;
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
    public void activate(@NonNull OnLocationChangedListener onLocationChangedListener) {
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
        Looper locationLooper;
        if(this.looper != null){
            locationLooper = looper;
        } else {
            locationLooper = Looper.getMainLooper();
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, this, locationLooper);
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
