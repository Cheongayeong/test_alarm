package com.example.alarmproject.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alarmproject.AlarmData;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "AlarmDatas_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create AlarmDatas table
        db.execSQL(AlarmData.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + AlarmData.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertAlarmData(AlarmData data) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` will be inserted automatically.
        // no need to add it
        values.put(AlarmData.COLUMN_NAME, data.getName());
        values.put(AlarmData.COLUMN_HOUR_A, data.getHourA());
        values.put(AlarmData.COLUMN_HOUR_B, data.getHourB());
        values.put(AlarmData.COLUMN_MINUTE_A, data.getMinuteA());
        values.put(AlarmData.COLUMN_MINUTE_B, data.getMinuteB());
        values.put(AlarmData.COLUMN_DAYS, data.getDays());
        values.put(AlarmData.COLUMN_LOCATION, data.getLocation());
        values.put(AlarmData.COLUMN_MESSAGE, data.isMessage());
        values.put(AlarmData.COLUMN_HASPASSWORD, data.isHasPassword());
        values.put(AlarmData.COLUMN_PASSWORD, data.getPassword());
        values.put(AlarmData.COLUMN_ONOFF, data.isOnoff());



        // insert row
        //long id = db.insert(AlarmData.TABLE_NAME, null, values);
        long id = 0;
        String sql = "insert into alarms(name, hour_a, hour_b, minute_a, minute_b"
        + ", days, location, message, haspassword, password, onoff) values(?, ?, ?, ?,?,?,?,?,?,?,?)";
        Object[] params = {data.getName(), data.getHourA(), data.getHourB(), data.getMinuteA(), data.getMinuteB(), data.getDays(), data.getLocation(), data.isMessage(), data.isHasPassword(), data.getPassword(), data.isOnoff()};
        db.execSQL(sql, params);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public AlarmData getAlarmData(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(AlarmData.TABLE_NAME,
                new String[]{AlarmData.COLUMN_ID, AlarmData.COLUMN_NAME, AlarmData.COLUMN_HOUR_A,
                        AlarmData.COLUMN_HOUR_B, AlarmData.COLUMN_MINUTE_A, AlarmData.COLUMN_MINUTE_B,
                        AlarmData.COLUMN_DAYS, AlarmData.COLUMN_LOCATION,AlarmData.COLUMN_MESSAGE,
                        AlarmData.COLUMN_HASPASSWORD,AlarmData.COLUMN_PASSWORD, AlarmData.COLUMN_ONOFF,},
                AlarmData.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare AlarmData object
        AlarmData alarm = new AlarmData(
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_NAME)),
                cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_HOUR_A)),
                cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_HOUR_B)),
                cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_MINUTE_A)),
                cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_MINUTE_B)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_DAYS)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_LOCATION)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_MESSAGE)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_HASPASSWORD)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_PASSWORD)),
                cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_ONOFF))
                );

        // close the db connection
        cursor.close();

        return alarm;
    }

    public List<AlarmData> getAllAlarmDatas() {
        List<AlarmData> alarms = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + AlarmData.TABLE_NAME + " ORDER BY " +
                AlarmData.COLUMN_HOUR_A + ", "+ AlarmData.COLUMN_MINUTE_A  + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AlarmData alarm = new AlarmData();
                alarm.setId(cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_ID)));
                alarm.setName(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_NAME)));
                alarm.setHourA(cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_HOUR_A)));
                alarm.setHourB(cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_HOUR_B)));
                alarm.setMinuteA(cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_MINUTE_A)));
                alarm.setMinuteB(cursor.getInt(cursor.getColumnIndex(AlarmData.COLUMN_MINUTE_B)));
                alarm.setDays(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_DAYS)));
                alarm.setLocation(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_LOCATION)));
                alarm.setMessage(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_MESSAGE)));
                alarm.setHasPassword(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_HASPASSWORD)));
                alarm.setPassword(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_PASSWORD)));
                alarm.setOnoff(cursor.getString(cursor.getColumnIndex(AlarmData.COLUMN_ONOFF)));

                alarms.add(alarm);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return AlarmDatas list
        return alarms;
    }

    public int getAlarmDatasCount() {
        String countQuery = "SELECT  * FROM " + AlarmData.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }


    // 지금은 이름만 수정 가능
    public int updateAlarmData(AlarmData alarm) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlarmData.COLUMN_NAME, alarm.getName());

        // updating row
        return db.update(AlarmData.TABLE_NAME, values, AlarmData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(alarm.getId())});
    }

    public void deleteAlarmData(AlarmData alarm) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(AlarmData.TABLE_NAME, AlarmData.COLUMN_ID + " = ?",
                new String[]{String.valueOf(alarm.getId())});
        db.close();
    }
}
