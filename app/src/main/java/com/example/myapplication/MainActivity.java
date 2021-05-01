package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBInit;
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
    String city_data;
    String[] city;
    String x_point;
    String y_point;
    String point_temp;
    String point_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

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
                Short_Data = cd.setdata();
                Data_Air = sa.setdata_air();
                Long_Temp = slt.setdata_longtemp();
                Long_Weather = slw.setdata_longweather();
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
                /*
                String[] set_air = new String[3];
                if(city[0].equals())
                ("서울특별시");        0
                ("부산광역시");        1
                ("대구광역시");        2
                ("인천광역시");        3
                ("광주광역시");        4
                ("대전광역시");        5
                ("울산광역시");        6
                ("경기도");            7
                ("강원도");            8
                ("충청북도");          9
                ("충청남도");          10
                ("전라북도");          11
                ("전라남도");          12
                ("경상북도");          13
                ("경상남도");          14
                ("제주특별자치도");    15
                ("세종특별자치시");    16
                {
                    for(int i =0; i<3; i++)
                    {
                    set_air[i] = Data_Air[0][i] // city[0]이 서울이라면
                    }
                }
                 */

                x_point = Integer.toString(data.getIntExtra("x", 0));
                y_point = Integer.toString(data.getIntExtra("y", 0));
                point_temp = data.getStringExtra("code1");
                point_weather = data.getStringExtra("code2");

            }
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



