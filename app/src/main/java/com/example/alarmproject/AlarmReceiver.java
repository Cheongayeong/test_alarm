package com.example.alarmproject;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;

import android.content.BroadcastReceiver;

import android.content.Context;

import android.content.Intent;

import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;


import android.media.RingtoneManager;

import android.net.Uri;

import android.os.PowerManager;

import android.support.v4.app.NotificationCompat;

import android.util.Log;

import android.view.Gravity;

import android.widget.Toast;



import java.io.File;
import java.util.Calendar;
import static android.content.Context.MODE_PRIVATE;

public class AlarmReceiver extends BroadcastReceiver
{
    Context context;

    @Override
    public void onReceive(final Context context, Intent intent)
    {
        this.context = context;
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();

        Log.d("alarm","gogo");

        PendingIntent pendingIntent;
        Toast toast = Toast.makeText(context, "알람이 울립니다.", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP,0,200);
        toast.show();

        wakeLock.release();

        try {
            intent = new Intent(context, RemoveAlarmActivity.class);
            pendingIntent = PendingIntent.getActivity(context, 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.d("ServicePending++ : ",""+pendingIntent.toString());
            pendingIntent.send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
        notification_start();
    }

    void notification_start() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");


        //OREO API 26 이상에서는 채널 필요
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName = "알람 채널";
            String description = "알람채널입니다";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        } else
            builder.setSmallIcon(R.mipmap.ic_launcher); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남


        builder.setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())

                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("알람이 울립니다.")
                .setContentText("!!!")
                .setContentInfo("INFO")
                .setContentIntent(pendingI);

        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());

        }
    }

}

