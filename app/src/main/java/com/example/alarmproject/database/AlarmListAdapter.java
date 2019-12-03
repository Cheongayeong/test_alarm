package com.example.alarmproject.database;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.alarmproject.AlarmData;
import com.example.alarmproject.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmListAdapter extends BaseAdapter {
    private List<AlarmData> listItems = new ArrayList<>();
    Context context;
    String[] daysName = {"월", "화", "수", "목","금", "토","일"};



    public AlarmListAdapter(Context context, List<AlarmData> lists){
        this.context = context;
        this.listItems = lists;
    }
    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.alarm_list_item, parent, false);
        }
        TextView tvAmPm = (TextView)convertView.findViewById(R.id.textampm);
        TextView tvAmPm2 = (TextView)convertView.findViewById(R.id.textampm2);
        TextView tvTime = (TextView)convertView.findViewById(R.id.texttime);
        TextView tvTime2 = (TextView)convertView.findViewById(R.id.texttime2);
        TextView tvName = (TextView)convertView.findViewById(R.id.textname);
        Switch lswitch = (Switch)convertView.findViewById(R.id.listswitch);
        TextView tvDays = (TextView)convertView.findViewById(R.id.textdays);

        AlarmData alarmData = listItems.get(position);

        // 기상 알람 시간
        String hour, min;

        String ampm="";
        min = String.valueOf(alarmData.getMinuteA());

        if(alarmData.getHourA() < 12){
            if(alarmData.getHourA() == 0)
                hour = "12";
            else
                hour = String.valueOf(alarmData.getHourA());
            ampm = "오전";
        }
        else{
            if(alarmData.getHourA() == 12)
                hour = "12";
            else
                hour = String.valueOf(alarmData.getHourA()-12);
            ampm = "오후";
        }
        tvAmPm.setText(ampm);

        if(hour.length() < 2)
            hour = "0"+hour;
        if(min.length() < 2)
            min = "0" + min;

        tvTime.setText(hour+":"+min);

        // 일정 알람 시간
        min = String.valueOf(alarmData.getMinuteB());
        if(alarmData.getHourB() < 12){
            if(alarmData.getHourB() == 0)
                hour = "12";
            else
                hour = String.valueOf(alarmData.getHourB());
            ampm = "오전";
        }
        else{
            if(alarmData.getHourB() == 12)
                hour = "12";
            else
                hour = String.valueOf(alarmData.getHourB()-12);
            ampm = "오후";
        }
        tvAmPm2.setText(ampm);


        if(hour.length() < 2)
            hour = "0"+hour;
        if(min.length() < 2)
            min = "0" + min;

        tvTime2.setText(hour+":"+min);

        // 나머지
        tvName.setText(alarmData.getName());
        if (alarmData.isOnoff().equals("1"))
            lswitch.setChecked(true);
        else
            lswitch.setChecked(false);

        ArrayList<String> dayString = new ArrayList<>();
        for(int i=0;i<7;i++){
            if(alarmData.getDays().charAt(i) == '1')
                dayString.add(daysName[i]);
        }
        String textDays = "";
        for(int i=0;i<dayString.size();i++){
            if(i == dayString.size()-1) {
                textDays += dayString.get(i) + " / ";
            }else{
                textDays += dayString.get(i) + ", ";
            }
        }

        Log.d(textDays,"textDays : ");
        tvDays.setText(textDays);



        return convertView;
    }

}
