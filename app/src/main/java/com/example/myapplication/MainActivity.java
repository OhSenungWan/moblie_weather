package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBInit;
import com.example.myapplication.setdata.data;
import com.example.myapplication.setdata.setdata;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    String DOW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);

        checkFirstRun();

        setStart();
    }

    public void setStart() {
        data wd = new data();
        new Thread(new Runnable() {
            @Override
            public void run() {
                setdata cd = new setdata();
                cd.setdata();
                wd.savedata = cd.setdata();
                DOW = cd.DOW;
                System.out.println(DOW);
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
                /* city = 도시이름
                String cityName = data.getStringExtra("city");
                int X = data.getIntExtra("x", 0);
                int Y = data.getIntExtra("x", 0);*/
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



