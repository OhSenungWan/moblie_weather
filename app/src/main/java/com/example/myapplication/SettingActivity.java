package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.savedata.PreferenceManager;

import org.w3c.dom.Text;

import java.util.Calendar;


public class SettingActivity extends Activity {

    private Context mContext;

    LinearLayout Background;
    LinearLayout cmin, c8, c12, c16, c19, c22, c24, c29, cmax;

    int background;
    int Time;
    double ShareT;
    TextView winch;
    TextView tmin, t8, t12, t16, t19, t22, t24, t29, tmax;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("CLOTHING");
        mContext = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 시간에따라 배경이 바뀌게 한다.
                        setBG();
                        // 끝

                        // 현재 기온에 맞도록 자동적으로 디폴트 조정

                        settemp();

                        set_temp_btn_match();

                        set_temp_btn();

                        // 끝

                        // 온도를 선택시 아래 내용들이 변경

                        //끝

                        //남, 여 선택시 아래 내용이 변경

                        // 끝

                        //의상 추가

                        //끝

                        //의상 이미지 선택시 체감온도 위로, 아래로 설정

                        //끝

                        //의상 길게 눌렀을 때 삭제

                        //끝
                    }
                });
            }
        }).start();
    }

    public void setBG(){

        Background = (LinearLayout) findViewById(R.id.set_bg);
        Calendar cal = Calendar.getInstance();
        Time = cal.get(cal.HOUR);
        int AMPM = cal.get(Calendar.AM_PM);
        if (AMPM == 1) {
            Time += 12;
        }

        if(Time>=5 && Time<7)
        {
            background = 1;
        }
        else if(Time>=7 && Time<17)
        {
            background = 2;
        }
        else if(Time>=17 && Time<19)
        {
            background = 3;
        }
        else
        {
            background = 4;
        }

        switch (background){
            case 1:
                Background.setBackgroundResource(R.drawable.bg_1dawn);
                break;
            case 2:
                Background.setBackgroundResource(R.drawable.bg_2afternoon);
                break;
            case 3:
                Background.setBackgroundResource(R.drawable.bg_3sunset);
                break;
            case 4:
                Background.setBackgroundResource(R.drawable.bg_4night);
                break;
        }


    }  // 1.백그라운드 변경

    public void settemp(){
        mContext = this;
        String text = PreferenceManager.getString(mContext,"twdata");
        String data[] = text.split(" ");
        double temp = Double.parseDouble(data[0]);
        double wsd = Double.parseDouble(data[1]);
        double V = Math.pow(wsd, 0.16); //풍속의 0.16제곱
        double Twc= 13.12+(0.6215*temp)-(11.37*V)+(0.3965*temp*V);
        ShareT = (Math.round(Twc*100)/100.0); //결과 : x.xx

        winch = (TextView) findViewById(R.id.set_text);
        if (ShareT <= 4) {
            winch.setText("한파가 지속돼요. 기모제품과 내복입으시는 걸 추천드려요.");

        } else if (ShareT >= 4 && ShareT < 8) {
            winch.setText("차가운 날씨에요. 두꺼운 아우터안에 여러 옷을 껴 입으시는 걸 추천드려요.");
        } else if (ShareT >= 8 && ShareT < 12) {
            winch.setText("아직은 추운 날씨군요. 두꺼운 아우터에 옷을 한 두겹 껴 입으시는 걸 추천드려요.");
        } else if (ShareT >= 12 && ShareT < 16) {
            winch.setText("쌀쌀한 날씨에요. 일반 아우터안에  여러겹 껴 입으시는 걸 추천드려요.");
        }else if (ShareT >= 16 && ShareT < 19) {
            winch.setText("서늘한 날씨군요. 일반 아우터안에  옷을 한 두겹 껴 입으시는 걸 추천드려요.");
        }else if (ShareT >= 19 && ShareT < 21) {
            winch.setText("선선한 날씨군요. 얇은 아우터를 걸치시는 걸 추천드려요.");

        }else if (ShareT >= 21 && ShareT < 23) {
            winch.setText("따듯한 날씨지만, 가벼운 소재의 아우터나 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
        }
        else if (ShareT >= 23 && ShareT < 25) {
            winch.setText("약간 더울 수 있는 날씨에요. 가벼운 소재와 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
        }
        else if (ShareT >= 25 && ShareT < 29) {
            winch.setText("더울 수 있는 날씨에요. 겉 옷은 입지 않고 얇은 상의&하의로 스타일을 구성하시는 것이 좋겠어요.");
        }
        else {
            winch.setText("매우 더울 수 있는 날씨에요. 반팔 의상 및 칠부 의상으로 스타일을 구성하시는 것이 좋겠어요.");
        }


    }  //2-1. 온도설정

    public void set_temp_btn_match(){
        cmin = (LinearLayout) findViewById(R.id.cmin);
        c8 = (LinearLayout) findViewById(R.id.c8);
        c12 = (LinearLayout) findViewById(R.id.c12);
        c16 = (LinearLayout) findViewById(R.id.c16);
        c19 = (LinearLayout) findViewById(R.id.c19);
        c22 = (LinearLayout) findViewById(R.id.c22);
        c24 = (LinearLayout) findViewById(R.id.c24);
        c29 = (LinearLayout) findViewById(R.id.c29);
        cmax = (LinearLayout) findViewById(R.id.cmax);

        tmin = (TextView) findViewById(R.id.tmin);
        t8 = (TextView) findViewById(R.id.t8);
        t12 = (TextView) findViewById(R.id.t12);
        t16 = (TextView) findViewById(R.id.t16);
        t19 = (TextView) findViewById(R.id.t19);
        t22 = (TextView) findViewById(R.id.t22);
        t24 = (TextView) findViewById(R.id.t24);
        t29 = (TextView) findViewById(R.id.t29);
        tmax = (TextView) findViewById(R.id.tmax);
    } //2-2. 버튼 매칭

    public void set_temp_btn(){
        cmin.setBackgroundResource(R.drawable.darkcircle);
        c8.setBackgroundResource(R.drawable.darkcircle);
        c12.setBackgroundResource(R.drawable.darkcircle);
        c16.setBackgroundResource(R.drawable.darkcircle);
        c19.setBackgroundResource(R.drawable.darkcircle);
        c22.setBackgroundResource(R.drawable.darkcircle);
        c24.setBackgroundResource(R.drawable.darkcircle);
        c29.setBackgroundResource(R.drawable.darkcircle);
        cmax.setBackgroundResource(R.drawable.darkcircle);

        tmin.setTextColor(Color.parseColor("#FFFFFF"));
        t8.setTextColor(Color.parseColor("#FFFFFF"));
        t12.setTextColor(Color.parseColor("#FFFFFF"));
        t16.setTextColor(Color.parseColor("#FFFFFF"));
        t19.setTextColor(Color.parseColor("#FFFFFF"));
        t22.setTextColor(Color.parseColor("#FFFFFF"));
        t24.setTextColor(Color.parseColor("#FFFFFF"));
        t29.setTextColor(Color.parseColor("#FFFFFF"));
        tmax.setTextColor(Color.parseColor("#FFFFFF"));

        if (ShareT <= 4) {
            cmin.setBackgroundResource(R.drawable.whitecircle);
            tmin.setTextColor(Color.parseColor("#353535"));
        } else if (ShareT >= 4 && ShareT < 8) {
            c8.setBackgroundResource(R.drawable.whitecircle);
            t8.setTextColor(Color.parseColor("#353535"));
        } else if (ShareT >= 8 && ShareT < 12) {
            c12.setBackgroundResource(R.drawable.whitecircle);
            t12.setTextColor(Color.parseColor("#353535"));
        } else if (ShareT >= 12 && ShareT < 16) {
            c16.setBackgroundResource(R.drawable.whitecircle);
            t16.setTextColor(Color.parseColor("#353535"));
        }else if (ShareT >= 16 && ShareT < 19) {
            c19.setBackgroundResource(R.drawable.whitecircle);
            t19.setTextColor(Color.parseColor("#353535"));
        }else if (ShareT >= 19 && ShareT < 21) {
            c22.setBackgroundResource(R.drawable.whitecircle);
            t22.setTextColor(Color.parseColor("#353535"));
        }else if (ShareT >= 21 && ShareT < 23) {
            c24.setBackgroundResource(R.drawable.whitecircle);
            t24.setTextColor(Color.parseColor("#353535"));
        }
        else if (ShareT >= 23 && ShareT < 25) {
            c29.setBackgroundResource(R.drawable.whitecircle);
            t29.setTextColor(Color.parseColor("#353535"));
        }
        else if (ShareT >= 25 && ShareT < 29) {
            c29.setBackgroundResource(R.drawable.whitecircle);
            t29.setTextColor(Color.parseColor("#353535"));
        }
        else {
            cmax.setBackgroundResource(R.drawable.whitecircle);
            tmax.setTextColor(Color.parseColor("#353535"));
        }

    }

}