package com.curricle.waypoints.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by conrad on 16/01/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "WayPointDB";

    //Table name
    private static final String TABLE_WAYPOINTS = "waypoints";

    //Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_LAT = "latitude";
    private static final String KEY_LONG = "longitude";
    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_TYPE = "type";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_TRANSMITTED = "transmitted";

    private static final String[] COLUMNS = {KEY_ID, KEY_LAT, KEY_LONG, KEY_TIMESTAMP, KEY_TYPE, KEY_MESSAGE, KEY_TRANSMITTED};


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        SQLiteDatabase db = this.getReadableDatabase();
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_TABLE = "CREATE TABLE " + TABLE_WAYPOINTS + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_LAT + " REAL, " +
                KEY_LONG + " REAL, " +
                KEY_TIMESTAMP + " INTEGER, " +
                KEY_TYPE + " INTEGER, " +
                KEY_MESSAGE + " TEXT, " +
                KEY_TRANSMITTED + " INTEGER )";

        // create books table
        db.execSQL(CREATE_TABLE);

        // DEBUG - DUMMY VALUES
        ContentValues values = createContentValues(new WayPoint(0, 5.12, -112.12, 1421427315, 3, "Conrad war hier!", false));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 15.32, -22.12, 1421427500123L, 2, "Noch ein Text", false));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, -22.4, 92.12, 1422427600555L, 0, "Super hier waren wir campen Super hier waren wir campen Super hier waren wir campen Super hier waren wir campen Super hier waren wir campen", false));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 5.12, -112.12, 1421427315000L, 3, "Conrad war hier!", true));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 15.32, -22.12, 1421327500000L, 2, "Text", true));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 77.4, 171.12, 1421427600555L, 0, "Message", false));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 5.12, -112.12, 1421427415111L, 3, "ASDF!", true));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 15.32, -22.12, 1421427770222L, 2, "Noch ein Text", true));
        db.insert(TABLE_WAYPOINTS, null, values);

        values = createContentValues(new WayPoint(0, 77.4, 171.12, 1421427650123L, 0, "Grenzübergang", true));
        db.insert(TABLE_WAYPOINTS, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do yet
    }

    public void addWayPoint(WayPoint wayPoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = createContentValues(wayPoint);
        db.insert(TABLE_WAYPOINTS, null, values);
        db.close();
    }

    public int updateWayPoint(WayPoint wayPoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = createContentValues(wayPoint);
        int i = db.update(TABLE_WAYPOINTS, values, KEY_ID + " = ?", new String[]{String.valueOf(wayPoint.identifier)});
        db.close();

        return i;
    }

    public void deleteWayPoint(WayPoint wayPoint) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WAYPOINTS, KEY_ID + " = ?", new String[]{String.valueOf(wayPoint.identifier)});
        db.close();
    }

    private ContentValues createContentValues(WayPoint wayPoint) {
        ContentValues values = new ContentValues();
        values.put(KEY_LAT, wayPoint.latitude);
        values.put(KEY_LONG, wayPoint.longitude);
        values.put(KEY_TIMESTAMP, wayPoint.timestamp);
        values.put(KEY_TYPE, wayPoint.type);
        values.put(KEY_MESSAGE, wayPoint.message);
        values.put(KEY_TRANSMITTED, wayPoint.transmitted);
        return values;
    }

    public ArrayList<WayPoint> getAll() {
        ArrayList<WayPoint> result = new ArrayList<WayPoint>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =
                db.query(TABLE_WAYPOINTS, // a. table
                        COLUMNS, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        KEY_TIMESTAMP + " DESC", // g. order by
                        null); // h. limit

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    WayPoint wp = new WayPoint(
                            cursor.getInt(0),
                            cursor.getDouble(1),
                            cursor.getDouble(2),
                            cursor.getLong(3),
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getInt(6) != 0);
                    result.add(wp);
                } while (cursor.moveToNext());
            }
        }
        db.close();

        return result;
    }

}
