package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ClothingActivity extends Activity {

    ImageButton btn_return;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        setTitle("CLOTHING");
        Intent intent = getIntent();
        double Ta = Double.parseDouble(intent.getStringExtra("Temp"));
        double wsd = Double.parseDouble(intent.getStringExtra("Wsd"));
        String wtype = intent.getStringExtra("Wtype");
        String weather = intent.getStringExtra("Weather");
        double V = Math.pow(wsd, 0.16); //풍속의 0.16제곱
        double Twc= 13.12+(0.6215*Ta)-(11.37*V)+(0.3965*Ta*V);
        Twc = (Math.round(Twc*100)/100.0); //결과 : x.xx
        System.out.println(Twc);
    }
}
