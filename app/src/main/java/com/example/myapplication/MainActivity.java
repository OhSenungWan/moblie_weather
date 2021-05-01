package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBInit;
import com.example.myapplication.db.ShowListView;
import com.example.myapplication.setdata.data_short;
import com.example.myapplication.setdata.setdata_short;
import com.example.myapplication.setdata.setdata_air;
import com.example.myapplication.setdata.setdata_long_Temp;
import com.example.myapplication.setdata.setdata_long_weather;


public class MainActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    String DOW;
    String Short_Data[][][] = new String[3][8][14];
    String Long_Temp[][] = new String[8][2];
    String Long_Weather[][] = new String[8][4];
    String Data_Air[][] = new String[17][3];
    String[] set_air = new String[3];
    String city_data;
    String[] city;
    String x_point = "60";
    String y_point = "120";
    String point_temp = "11B10101";
    String point_weather = "11B00000";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        ImageButton Location_img = (ImageButton)findViewById(R.id.location_img);
        Location_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ShowListView.class);
                startActivityForResult(intent,1);
            }
        });
        checkFirstRun();
        setStart();
    }

    public void setStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                setdata_short cd = new setdata_short();
                DOW = cd.DOW;
                setdata_air sa = new setdata_air();
                setdata_long_Temp slt = new setdata_long_Temp();
                setdata_long_weather slw = new setdata_long_weather();
                Short_Data = cd.setdata(x_point,y_point);
                Data_Air = sa.setdata_air();
                Long_Temp = slt.setdata_longtemp(point_temp);
                Long_Weather = slw.setdata_longweather(point_weather);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
                System.out.println(city[0]);
                set_area(city[0]);
                x_point = Integer.toString(data.getIntExtra("x", 0));
                y_point = Integer.toString(data.getIntExtra("y", 0));
                point_temp = data.getStringExtra("code1");
                point_weather = data.getStringExtra("code2");

            }
        }
    }
    public void set_area(String city){

        int k=0;
        if(city.equals("서울특별시")) k = 0;
        else if(city.equals("부산광역시")) k = 1;
        else if(city.equals("대구광역시")) k = 2;
        else if(city.equals("인천광역시")) k = 3;
        else if(city.equals("광주광역시")) k = 4;
        else if(city.equals("대전광역시")) k = 5;
        else if(city.equals("울산광역시")) k = 6;
        else if(city.equals("경기도")) k = 7;
        else if(city.equals("강원도")) k = 8;
        else if(city.equals("충청북도")) k = 9;
        else if(city.equals("충청남도")) k = 10;
        else if(city.equals("전라북도")) k = 11;
        else if(city.equals("전라남도")) k = 12;
        else if(city.equals("경상북도")) k = 13;
        else if(city.equals("경상남도")) k = 14;
        else if(city.equals("제주특별자치도")) k = 15;
        else if(city.equals("세종특별자치시")) k = 16;

            for(int i =0; i<3; i++) {
                set_air[i] = Data_Air[k][i];
        }
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



