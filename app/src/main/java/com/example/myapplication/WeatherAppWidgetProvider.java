package com.example.myapplication;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.myapplication.savedata.PreferenceManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.example.myapplication.AppWidgetConfig.KEY_BUTTON_TEXT;
import static com.example.myapplication.AppWidgetConfig.SHARED_PRES;

public class WeatherAppWidgetProvider extends AppWidgetProvider{
    public static final String ACTION_TOAST = "actionRefresh";
    public static final String EXTRA_ITEM_POSITION = "extraItemPosition";
    private Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent){
        //날씨정보 받아오는 기능 실행 됐을 때
        if(ACTION_TOAST.equals(intent.getAction())){
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            AppWidgetManager appWidgetManager= AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.update);
        }
        String action = intent.getAction();
        if(AppWidgetManager.ACTION_APPWIDGET_UPDATE.equals(action)){
            Bundle extras = intent.getExtras();
            if(extras != null){
                int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
                if(appWidgetIds != null && appWidgetIds.length > 0){
                    this.onUpdate(context, AppWidgetManager.getInstance(context),appWidgetIds);
                }
            }
        }

        super.onReceive(context,intent);

    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){

        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.activity_widget);


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
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PRES, Context.MODE_PRIVATE);
        String buttonText = prefs.getString(KEY_BUTTON_TEXT+appWidgetId,"Press me");
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.activity_widget);
        Intent intent = new Intent(context, MainActivity.class).setAction("ButtonClick");
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        updateViews.setOnClickPendingIntent(R.id.Weather,pendingIntent);
        RemoteViews update = new RemoteViews(context.getPackageName(), R.layout.activity_widget);
        update.setOnClickPendingIntent(R.id.update,pendingIntent);
        update.setCharSequence(R.id.update,"setText", buttonText);

        Intent clickIntent = new Intent(context, WeatherAppWidgetProvider.class);
        clickIntent.setAction(ACTION_TOAST);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(context,
                0,clickIntent, 0);

        Intent serviceIntent = new Intent(context, widgetservice.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

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
                updateViews.setImageViewResource(R.id.Weather, R.drawable.list_rain);
                break;
            case "Snow":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.list_snow);
                break;
            case "Sunny":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.list_sun);
                break;
            case "Cloud":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.list_cloud1);
                break;
            case "Blur":
                updateViews.setImageViewResource(R.id.Weather, R.drawable.list_cloud2);
                break;
        }
        Calendar mCalendar = Calendar.getInstance();
        SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd HH:mm", Locale.KOREA);

        updateViews.setTextViewText(R.id.location, city_data);
        updateViews.setTextViewText(R.id.realtime,"Update : " + mFormat.format(mCalendar.getTime()));
        updateViews.setTextViewText(R.id.Temp, idata[0]);
        updateViews.setTextViewText(R.id.pm10grade, idata[1]);
        updateViews.setTextViewText(R.id.pm25grade, idata[3]);
        updateViews.setTextViewText(R.id.pm10, idata[4]);
        updateViews.setTextViewText(R.id.pm25, idata[5]);
        updateViews.setTextViewText(R.id.pop, idata[6]);
        updateViews.setTextViewText(R.id.weatherText, weather);
        Intent intent1 = new Intent(context, WeatherAppWidgetProvider.class).setAction("Button2");
        PendingIntent pendingIntent1 = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
        updateViews.setOnClickPendingIntent(R.id.update,pendingIntent1);
        updateViews.setPendingIntentTemplate(R.id.update, clickPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId,updateViews);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.update);
    }
}
