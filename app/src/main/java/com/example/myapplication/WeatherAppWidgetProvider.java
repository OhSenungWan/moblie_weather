package com.example.myapplication;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.myapplication.savedata.PreferenceManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeatherAppWidgetProvider extends AppWidgetProvider{
    private Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent){
        //날씨정보 받아오는 기능 실행 됐을 때
        super.onReceive(context,intent);

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for(int i = 0; i<appWidgetIds.length; i++){
            updateAppWidget(context,appWidgetManager, appWidgetIds[i]);
        }
    }
    @Override
    public void onEnabled(Context context){
        super.onEnabled(context);
    }
    @Override
    public void onDisabled(Context context){
        super.onDisabled(context);
    }
    @Override
    public void onDeleted(Context context, int[] appWidgetIds){
        super.onDeleted(context, appWidgetIds);
    }
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId){
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.activity_widget);
        Intent intent = new Intent(context, MainActivity.class).setAction("ButtonClick");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        updateViews.setOnClickPendingIntent(R.id.Weather,pendingIntent);

        String text = PreferenceManager.getString(context,"rebuild");
        if(text.equals("")) {
            text = "58 125 11B10101 11B00000 서울특별시 구로구 구로제1동";
            PreferenceManager.setString(context, "rebuild", text);
        }
        String intentdata = PreferenceManager.getString(context,"data");
        String[] idata = intentdata.split(" ");
        String[] data = text.split(" ");
        String point_temp = data[2];
        String point_weather = data[3];
        String city_data = data[4] + " " + data[5] + " " +data[6];
        String weather = idata[2];
        switch (weather){
            case "Rain":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.rain);
                break;
            case "Snow":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.snow);
                break;
            case "Sunny":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.sunn);
                break;
            case "Cloud":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.cloud1);
                break;
            case "Blur":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.cloud2);
                break;
        }

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);

        updateViews.setTextViewText(R.id.location, city_data);
        updateViews.setTextViewText(R.id.realtime,mFormat.format(mCalendar.getTime()));
        updateViews.setTextViewText(R.id.Temp, idata[0]);
        updateViews.setTextViewText(R.id.pm10, "미세먼지 : " + idata[1] + " | ");

        appWidgetManager.updateAppWidget(appWidgetId,updateViews);
    }
}
