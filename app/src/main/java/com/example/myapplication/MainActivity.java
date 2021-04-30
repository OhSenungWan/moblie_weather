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
                System.out.println(DOW);
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
                /* temp = data.getStringExtra("cityName")+ " ";
                temp += Integer.toString(data.getIntExtra("x", 0)) + " ";
                temp += Integer.toString(data.getIntExtra("y", 0)) + "\n";
                temp += data.getStringExtra("code1")+ " ";
                temp += data.getStringExtra("code2");*/
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



