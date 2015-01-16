package com.curricle.waypoints;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by conrad on 15/01/15.
 */
public class LocationWrapper {

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private LocationListener mLocationListener;
    private boolean mIsUpdatingLocation;

    public LocationWrapper(LocationListener locationListener, Context context) {
        mLocationListener = locationListener;

        // Create API Client (Ugly anonymous inner classes, but hides interface implementation)
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        startLocationUpdates();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

        // Create Location Request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(2000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void startLocationUpdates() {
        if (mGoogleApiClient.isConnected() && !mIsUpdatingLocation) {
            mIsUpdatingLocation = true;
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, mLocationListener);
        }
    }

    public void stopLocationUpdates() {
        mIsUpdatingLocation = false;
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, mLocationListener);
    }

}

