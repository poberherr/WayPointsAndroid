package com.curricle.waypoints;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.google.android.gms.location.LocationListener;


public class NewWaypoint extends ActionBarActivity implements LocationListener {

    private TextView mLocText;
    private Location mMostAccurateLocation;
    private LocationWrapper mLocationWrapper;

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
        mLocationWrapper = new LocationWrapper(this, this);
        mLocText = (TextView) findViewById(R.id.position_label);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (mMostAccurateLocation == null) {
            mMostAccurateLocation = location;
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