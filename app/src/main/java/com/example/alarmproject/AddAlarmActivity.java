package com.example.alarmproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarmproject.database.DatabaseHelper;

import java.io.Serializable;
import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {

    TimePicker mtimePicker;
    TimePicker mtimePicker2;
    Button btnAdd;
    ImageButton btnLocation;
    EditText editTextName;
    TextView textViewLocation;
    CheckBox[] checkBoxes;
    Spinner spinner;
    Switch switchMessage;
    Switch switchPassword;

    String passwd;
    boolean isRepeat;
    AlarmManager mAlarmManager;
    AlarmData mAlarmData;
    int hour, min;
    int hour2, min2;
    private DatabaseHelper db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        db = new DatabaseHelper(this);

        mtimePicker = (TimePicker)findViewById(R.id.timepicker);
        mtimePicker2 = (TimePicker)findViewById(R.id.timepicker2);
        btnAdd = (Button)findViewById(R.id.btnadd);
        btnLocation = (ImageButton)findViewById(R.id.btnloc);
        editTextName = (EditText)findViewById(R.id.editname);
        textViewLocation = (TextView)findViewById(R.id.tvlocation);
        checkBoxes = new CheckBox[7];
        for(int i=0;i<7;i++){
            int k = getResources().getIdentifier("check"+(i+1),"id",getPackageName());
            checkBoxes[i] = (CheckBox)findViewById(k);
        }
        spinner = (Spinner)findViewById(R.id.spinner);
        switchMessage = (Switch)findViewById(R.id.switchmessage);
        switchPassword = (Switch)findViewById(R.id.switchpassword);

        String[] options = {"알람음"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        passwd = "0";
        mAlarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

    }


    public void addAlarm(View view) {

        AlarmData newAlarm = new AlarmData();

        newAlarm.setName(editTextName.getText().toString());

        // 첫 번째 시간
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            hour = mtimePicker.getHour();
            min = mtimePicker.getMinute();
        }
        else{
            hour = mtimePicker.getCurrentHour();
            min = mtimePicker.getCurrentMinute();
        }

        // 두 번째 시간
        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            hour2 = mtimePicker2.getHour();
            min2 = mtimePicker2.getMinute();
        }
        else{
            hour2 = mtimePicker2.getCurrentHour();
            min2 = mtimePicker2.getCurrentMinute();
        }

        newAlarm.setHourA(hour);
        newAlarm.setHourB(hour2);
        newAlarm.setMinuteA(min);
        newAlarm.setMinuteB(min2);

        // 요일 - 선택된 요일은 1 아니면 0, 월화수목금토일 순서
        String days = "";
        for(int i=0;i<7;i++) {
            if (checkBoxes[i].isChecked())
                days += "1";
            else
                days += "0";
        }
        newAlarm.setDays(days);

        newAlarm.setLocation(textViewLocation.getText().toString());
        if(switchMessage.isChecked())
            newAlarm.setMessage("1");
        else
            newAlarm.setMessage("0");

        if(switchPassword.isChecked())
            newAlarm.setPassword("1");
        else
            newAlarm.setPassword("0");
        newAlarm.setPassword(passwd);

        newAlarm.setOnoff("1");


        registAlarm(); // 알람 등록
        Toast.makeText(this,"저장하였습니다.",Toast.LENGTH_LONG).show();

        // db에 추가
        db.insertAlarmData(newAlarm);

        finish();
    }
    private void registAlarm(){

        cancelAlarm();

        boolean[] week = new boolean[7];
        for(int i=0;i<7;i++){
            if(checkBoxes[i].isChecked()){
                week[i] = true;
                isRepeat = true;
            }
            else{
                week[i] = false;
            }
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        long triggerTime = 0;
        long intervalTime = 24*60*60*1000; // 24시간

        if(isRepeat){
            intent.putExtra("one_time", false);
            intent.putExtra("repeat_day", week);
            PendingIntent pending = getPendingIntent(intent);
            triggerTime = setTriggerTime();
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, triggerTime,intervalTime,pending);
        }
        else{
            intent.putExtra("one_time", true);
            PendingIntent pending = getPendingIntent(intent);
            triggerTime = setTriggerTime();
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pending);
        }
        //Toast.makeText(getApplicationContext(),"alarm!",Toast.LENGTH_LONG).show();

    }

    private long setTriggerTime() {
        // current Time
        long atime = System.currentTimeMillis();
        // timepicker
        Calendar curTime = Calendar.getInstance();

        // 알람 시간 설정
        curTime.set(Calendar.HOUR_OF_DAY, hour);
        curTime.set(Calendar.MINUTE, min);
        curTime.set(Calendar.SECOND, 0);
        curTime.set(Calendar.MILLISECOND, 0);
        long btime = curTime.getTimeInMillis();
        long triggerTime = btime;
        if (atime > btime)
            triggerTime += 1000 * 60 * 60 * 24;

        return triggerTime;
    }

    private PendingIntent getPendingIntent(Intent intent) {
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pIntent;
    }
    private void cancelAlarm()
    {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pending = getPendingIntent(intent);
        this.mAlarmManager.cancel(pending);
    }

}
