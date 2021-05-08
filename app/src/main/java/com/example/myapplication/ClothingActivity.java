package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.myapplication.setdata.set_data_cloth;
import com.example.myapplication.setdata.data_short;
public class ClothingActivity extends Activity {

    TextView pmch, winch, tempch, rainch;
    ImageButton btn_return;
    String[] temp = new String[2];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        setTitle("CLOTHING");

        new Thread(new Runnable() {
            @Override
            public void run() {
                temp[0] = "null";
                temp[1] = "null";
                Intent intent = getIntent();
                set_data_cloth sdc = new set_data_cloth();
                pmch = (TextView)findViewById(R.id.daycheckpm10);
                winch = (TextView)findViewById(R.id.daycheckwind);
                tempch = (TextView)findViewById(R.id.daychecktemp);
                rainch = (TextView)findViewById(R.id.daycheckrain);
                double Ta = Double.parseDouble(intent.getStringExtra("Temp"));
                double wsd = Double.parseDouble(intent.getStringExtra("Wsd"));
                String weather = intent.getStringExtra("Weather");
                String rp = intent.getStringExtra("wtype");
                String pm10  = intent.getStringExtra("pm10grade");
                String x_point = intent.getStringExtra("nx");
                String y_point = intent.getStringExtra("ny");
                temp = sdc.set_data_cloth(x_point, y_point);
                double temax = Double.parseDouble(temp[1]);
                double temin = Double.parseDouble(temp[0]);
                double Daily_cross = temax - temin;

                double V = Math.pow(wsd, 0.16); //풍속의 0.16제곱
                double Twc= 13.12+(0.6215*Ta)-(11.37*V)+(0.3965*Ta*V);
                Twc = (Math.round(Twc*100)/100.0); //결과 : x.xx



                ClothingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (rp.equals("0")) {
                            switch (weather) {
                                case "Sunny":
                                    rainch.setText("날씨가 아주 맑아요!");
                                    break;
                                case "Cloud":
                                    rainch.setText("날씨가 조금 흐려요..");
                                    break;
                                case "Blur":
                                    rainch.setText("날씨가 매우 흐려요. 어쩌면 비가 올 수도 있어요");
                                    break;
                            }
                        } else if (rp.equals("3") || rp.equals("7")) {
                            rainch.setText("오늘은 눈이올 예정이에요. 미끄러짐에 주의하세요!");
                        } else {
                            rainch.setText("오늘은 비가 올 예정이에요. 우산을 챙겨가세요!");
                        }
                        switch (pm10) {
                            case "좋음":
                                pmch.setText("미세먼지 걱정은 안하셔도 될 것 같아요!");
                                break;
                            case "보통":
                                pmch.setText("미세먼지가 있는 편이에요");
                                break;
                            case "나쁨":
                                pmch.setText("미세먼지 지수가 높아요. 외출에 유의하셔야 해요!");
                                break;
                            case "매우나쁨":
                                pmch.setText("미세먼지가 너무 많아요! 외출할 때 조심하세요!");
                                break;
                        }
                        if (wsd < 4) {
                            winch.setText("바람이 아주 약해요.");
                        } else if (wsd >= 4 && wsd < 9) {
                            winch.setText("바람이 조금 센 편이에요.");
                        } else if (wsd >= 9 && wsd < 14) {
                            winch.setText("바람이 강해요. 옷을 온도보단 두껍게 입으시는게 좋아요.");
                        } else {
                            winch.setText("바람이 아주 강해요. 외출시 주의하세요!");
                        }
                        if(Daily_cross > 10){
                            tempch.setText("일교차가 큰 날씨에요. 얇은 옷을 여러 겹 입는걸 추천해 드려요!");
                        }else {
                            tempch.setText("오늘은 일교차가 별로 없어요!!");
                        }

        /*
        조건
        1. 비나 눈이나 맑음, 구름많음, 흐림
        rp 0인지 아닌지 확인
        rp가 0이면 weather
        0이 아니면 강수 형태 확인
        2. 미세먼지

        1. 일교차
        10이상 차이 일교차가 큼
        2. 바람의 세기
        약한바람 4m/s미만
        조금강한바람 4m/s이상 9m/s미만
        강한바람 9m/s이상 14m/s미만
        매우강한바람 14m/s이상
         */
                    }
                });
            }
        }).start();
   }
}
