package com.curricle.waypoints;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.curricle.waypoints.data.DBHelper;
import com.curricle.waypoints.data.WayPoint;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class Home extends ActionBarActivity {

    DBHelper dbHelper;
    private FloatingActionButton fab;

    public void actionButton1(View button) {
        List<WayPoint> res = dbHelper.getAll();
        for (WayPoint wp : res) {
            Log.d("WPR", wp.toString());
        }
    }

    public void actionButton2(View button) {
        dbHelper.addWayPoint(new WayPoint(0, 5.12, -112.12, 1421427315, 3, "Conrad war hier!", false));
    }

    public void actionButton3(View button) {
        WayPoint wp = dbHelper.getAll().getFirst();
        wp.message += "UPDATE!!";
        wp.transmitted = true;
        dbHelper.updateWayPoint(wp);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        dbHelper = new DBHelper(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show new activity
                Intent intent = new Intent(Home.this, NewWaypoint.class);
                startActivity(intent);

               /*
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", "12125551212");
                smsIntent.putExtra("sms_body","Body of Message");
                startActivity(smsIntent);
                */
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
