package com.curricle.waypoints.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

/**
 * Created by conrad on 16/01/15.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "WayPointDB";

    // Books table name
    private static final String TABLE_WAYPOINTS = "waypoints";

    // Books Table Columns names
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nothing to do yet
    }

    public void addWayPoint(WayPoint wayPoint) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = createContentValues(wayPoint);
        // 3. insert
        db.insert(TABLE_WAYPOINTS, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close
        db.close();
    }

    public int updateWayPoint(WayPoint wayPoint) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = createContentValues(wayPoint);

        // 3. updating row
        int i = db.update(TABLE_WAYPOINTS, //table
                values, // column/value
                KEY_ID + " = ?", // selections
                new String[]{String.valueOf(wayPoint.identifier)}); //selection args

        // 4. close
        db.close();

        return i;
    }

    public void deleteWayPoint(WayPoint wayPoint) {
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.delete(TABLE_WAYPOINTS, //table name
                KEY_ID + " = ?",  // selections
                new String[]{String.valueOf(wayPoint.identifier)}); //selections args

        // 3. close
        db.close();

        //log
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

    public LinkedList<WayPoint> getAll() {

        LinkedList<WayPoint> result = new LinkedList<WayPoint>();

        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();

        // 2. build query
        Cursor cursor =
                db.query(TABLE_WAYPOINTS, // a. table
                        COLUMNS, // b. column names
                        null, // c. selections
                        null, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                WayPoint wp = new WayPoint(
                        cursor.getInt(0),
                        cursor.getDouble(1),
                        cursor.getDouble(2),
                        cursor.getLong(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getInt(6) != 0);
                cursor.moveToNext();
                result.add(wp);
            }
        }

        db.close();
        return result;

    }

}
