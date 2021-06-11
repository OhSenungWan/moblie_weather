/*package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RemoteViews;
public class AppWidgetConfig extends AppCompatActivity {
    public static final String SHARED_PRES = "prefs";
    public static final String KEY_BUTTON_TEXT = "keyButtonText";

    private int appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private EditText editTextButton;


    @Override

    //시도하다 파기
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this,MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        Intent serviceIntent = new Intent(this, widgetservice.class);
        serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
        serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));


        Intent clickIntent = new Intent(this, WeatherAppWidgetProvider.class);
        clickIntent.setAction(WeatherAppWidgetProvider.ACTION_TOAST);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(this,
                0,clickIntent,0);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(this, WeatherAppWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.update);

    }
    public void confirmConfiguration(View v){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        finish();
    }
}


 */