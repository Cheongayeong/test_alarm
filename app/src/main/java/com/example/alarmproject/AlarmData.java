package com.example.alarmproject;

import java.io.Serializable;

public class AlarmData implements Serializable {

    public static final String TABLE_NAME = "alarms";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_MINUTE_A = "minute_a";
    public static final String COLUMN_MINUTE_B = "minute_b";
    public static final String COLUMN_HOUR_A = "hour_a";
    public static final String COLUMN_HOUR_B = "hour_b";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_HASPASSWORD = "haspassword";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_ONOFF = "onoff";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_NAME + " TEXT,"
                    + COLUMN_HOUR_A + " TEXT NOT NULL,"
                    + COLUMN_HOUR_B + " TEXT,"
                    + COLUMN_MINUTE_A + " TEXT NOT NULL,"
                    + COLUMN_MINUTE_B + " TEXT,"
                    + COLUMN_DAYS + " TEXT,"
                    + COLUMN_LOCATION + " TEXT,"
                    + COLUMN_MESSAGE + " TEXT,"
                    + COLUMN_HASPASSWORD + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_ONOFF + " TEXT"
                    + ")";


    private int id;
    private int hourA;
    private int hourB;
    private int minuteA;
    private int minuteB;
    private String days;
    private String name;
    private String location;
    private String message;
    private String hasPassword;
    private String password;
    private String onoff;

    public AlarmData(String name, int hour,  int hour2, int minute, int minute2, String days, String location, String message, String hasPassword, String password, String onoff) {
        this.hourA = hour;
        this.minuteA = minute;
        this.hourB = hour2;
        this.minuteB = minute2;
        this.days = days;
        this.name = name;
        this.location = location;
        this.message = message;
        this.hasPassword = hasPassword;
        this.password = password;
        this.onoff = onoff;
    }
    public AlarmData(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHourA() {
        return hourA;
    }

    public void setHourA(int hourA) {
        this.hourA = hourA;
    }

    public int getMinuteA() {
        return minuteA;
    }

    public void setMinuteA(int minute) {
        this.minuteA = minute;
    }

    public int getHourB() {
        return hourB;
    }

    public void setHourB(int hour) {
        this.hourB = hour;
    }

    public int getMinuteB() {
        return minuteB;
    }

    public void setMinuteB(int minute) {
        this.minuteB = minute;
    }


    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String isMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String isHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(String hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String isOnoff() {
        return onoff;
    }

    public void setOnoff(String onoff) {
        this.onoff = onoff;
    }
}

