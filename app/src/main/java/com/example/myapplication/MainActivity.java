package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBInit;
import com.example.myapplication.db.ShowListView;
import com.example.myapplication.setdata.setdata_short;
import com.example.myapplication.setdata.setdata_air;
import com.example.myapplication.setdata.setdata_long_Temp;
import com.example.myapplication.setdata.setdata_long_weather;
import com.example.myapplication.setdata.set_weather;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    String DOW;
    String Short_Data[][][] = new String[3][8][14];
    String Long_Temp[][] = new String[8][2];
    String Long_Weather[][] = new String[8][4];
    String Data_Air[] = new String[3];
    String city_data = "서울특별시 구로구 구로제1동";
    String[] city;
    String x_point = "58";
    String y_point = "125";
    String point_temp = "11B10101";
    String point_weather = "11B00000";
    String weather;
    String temp;
    setdata_air sa;
    set_weather sw;
    TextView Location;
    TextView Comment;
    TextView Temp;
    TextView Days_of_week;

    public void setStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setdata_short cd = new setdata_short();
                System.out.println(DOW);
                sa = new setdata_air();
                sw = new set_weather();
                setdata_long_Temp slt = new setdata_long_Temp();
                setdata_long_weather slw = new setdata_long_weather();
                Short_Data = cd.setdata(x_point,y_point);
                DOW = cd.DOW;
                Data_Air = sa.setdata_air(city[0]);
                Long_Temp = slt.setdata_longtemp(point_temp);
                Long_Weather = slw.setdata_longweather(point_weather);
                weather = sw.set_weather(Short_Data);
                temp = sw.set_temp(Short_Data);
                Location = (TextView)findViewById(R.id.location);
                Comment = (TextView)findViewById(R.id.comment);
                Temp = (TextView)findViewById(R.id.temp);
                Days_of_week = (TextView)findViewById(R.id.days_of_week);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Location.setText(city_data);
                        Comment.setText(weather);
                        Temp.setText(temp+"℃");
                        Days_of_week.setText(DOW);
                    }
                });
            }
        }).start();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        String temp= "";
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                city_data = data.getStringExtra("cityName");
                city = city_data.split(" ");
                x_point = Integer.toString(data.getIntExtra("x", 0));
                y_point = Integer.toString(data.getIntExtra("y", 0));
                point_temp = data.getStringExtra("code1");
                point_weather = data.getStringExtra("code2");
                setStart();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        city = city_data.split(" ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        checkFirstRun();
        ImageButton Location_img = (ImageButton)findViewById(R.id.location_img);
        Location_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ShowListView.class);
                startActivityForResult(intent,1);
            }
        });
        setStart();

    }


    /** 첫 실행시 DB 설계 **/
    public void checkFirstRun() {
        boolean isFirstRun = prefs.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            startActivity(new Intent(MainActivity.this, DBInit.class));
            prefs.edit().putBoolean("isFirstRun", false).apply();
        }

    }

}



