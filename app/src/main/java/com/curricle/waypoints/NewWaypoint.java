package com.curricle.waypoints;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;


public class NewWaypoint extends ActionBarActivity implements LocationListener {

    private TextView mLocText;
    private Location mMostAccurateLocation;
    private LocationRequestWrapper mLocationWrapper;
    private ProgressBar locationSearchProgressBar;

    @Override
    protected void onResume() {
        super.onResume();
        mMostAccurateLocation = null;
        mLocationWrapper.startLocationUpdates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_waypoint);
        mLocationWrapper = new LocationRequestWrapper(this, this);
        mLocText = (TextView) findViewById(R.id.position_label);
        locationSearchProgressBar = (ProgressBar) findViewById(R.id.waitingForLocation);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMostAccurateLocation == null) {
            mMostAccurateLocation = location;
            locationSearchProgressBar.setVisibility(View.GONE);
        }

        if (location.getAccuracy() <= mMostAccurateLocation.getAccuracy()) {
            mMostAccurateLocation = location;
        }

        mLocText.setText(mMostAccurateLocation.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationWrapper.stopLocationUpdates();
        mMostAccurateLocation = null;
    }
}