package com.curricle.waypoints;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.curricle.waypoints.data.DBHelper;
import com.curricle.waypoints.data.WayPoint;
import com.curricle.waypoints.data.WayPointAdapter;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

public class Home extends ActionBarActivity {

    DBHelper dbHelper;
    private FloatingActionButton fab;
    private ListView mListView;

    public void actionButton1(View button) {
        List<WayPoint> res = dbHelper.getAll();
        for (WayPoint wp : res) {
            Log.d("WPR", wp.toString());
        }
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
            }
        });

        mListView = (ListView) findViewById(R.id.listView);
        fab.attachToListView(mListView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mListView.setAdapter(new WayPointAdapter(this, dbHelper.getAll()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // Example SMS intent
            Intent smsIntent = new Intent(Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "12125551212");
            smsIntent.putExtra("sms_body", "Body of Message");
            startActivity(smsIntent);
        }

        return super.onOptionsItemSelected(item);
    }
}
