package com.example.myapplication;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class WeatherAppWidgetProvider extends AppWidgetProvider{
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

        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);


        updateViews.setTextViewText(R.id.realtime,mFormat.format(mCalendar.getTime()));;
        appWidgetManager.updateAppWidget(appWidgetId,updateViews);
    }
}
