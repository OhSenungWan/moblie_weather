package com.example.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    String DOW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}



