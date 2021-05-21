package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.myapplication.savedata.PreferenceManager;
import com.example.myapplication.setdata.set_data_cloth;
import com.example.myapplication.setdata.data_short;
import com.example.myapplication.setdata.set_weather;

import java.util.Calendar;

public class ClothingActivity extends Activity {
    private Context mContext;
    int tempset[];      // 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
    String mtlist[];
    String mblist[];
    String wtlist[];
    String wblist[];
    String topcloth[];  //split mantopclothlist[해당 온도의 정수번호]
    String botcloth[];
    /*
        1. 온도에 따른 추천 옷 개수 를 저장해서 정수형 변수에 집어넣는다
        2. 온도에 따른 추천 옷의 종류들을 스트링형에 집어 넣는다.
        3. 해당 온도를 출력할 때 ...cloth변수에 리스트를 스플릿 형태로 바꿔서 저장한다.
        4. 해당 추천온도의 옷 종류들이 저장된 ...cloth변수
        5. 기존의 방식과 같이 이용한다.
     */
    LinearLayout BackGround;
    int background;
    int Time;
    LinearLayout man;
    LinearLayout woman;
    TextView pmch, winch, tempch, rainch, tman, twoman;
    String[] temp = new String[2];

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothing);
        setTitle("CLOTHING");
        mContext = this;
        String text = PreferenceManager.getString(mContext,"CLOTHSET");
        if(text.equals("")) {
            text = "7 7 7 7 7 5 4 4 4 2 3 4 3 3 3 3 3 3 7 7 7 7 6 5 5 4 4 3 5 6 4 5 5 5 4 5";
            PreferenceManager.setString(mContext, "CLOTHSET", text);
        }
        String data[] = text.split(" ");
        tempset = new int[data.length];
        for(int i = 0; i<data.length; i++){
            tempset[i] = Integer.parseInt(data[i]);
        }
        String tlist1 = PreferenceManager.getString(mContext,"MTLIST");
        if(tlist1.equals("")) {
            tlist1 = "히트텍 기모후드 롱패딩 기모코트 기모맨투맨 오리털패딩 스웨터" +
                    "  히트텍 기모후드 모직코트 기모맨투맨 숏패딩 두꺼운니트 가죽자켓" +
                    "  점퍼 후드 맨투맨 니트 가죽자켓 패딩조끼 트렌치코트" +
                    "  점퍼 후드 맨투맨 패딩조끼 청자켓 가죽자켓 가디건" +
                    "  점퍼 후드 얇은맨투맨 블레이저 얇은가디건 두꺼운티셔츠 셔츠" +
                    "  얇은맨투맨 블레이저 티셔츠 칠부티셔츠 셔츠" +
                    "  얇은맨투맨 티셔츠 칠부티셔츠 셔츠" +
                    "  반팔셔츠 티셔츠 칠부티셔츠 셔츠" +
                    "  칠부티셔츠 반팔티 반팔셔츠 나시";
            PreferenceManager.setString(mContext, "MTLIST", tlist1);
        }
        mtlist = tlist1.split("  ");
        String tlist2 = PreferenceManager.getString(mContext,"MBLIST");
        if(tlist2.equals("")) {
            tlist2 = "기모조거팬츠 기모청바지" +
                    "  기모조거팬츠 기모청바지 기모슬렉스" +
                    "  조거팬츠 청바지 슬렉스 면바지(남)" +
                    "  청바지 슬렉스 면바지(남)" +
                    "  청바지 슬렉스 면바지(남)" +
                    "  얇은청바지 슬렉스 면바지(남)" +
                    "  얇은청바지 슬렉스 면바지(남)" +
                    "  얇은청바지 슬렉스 면바지(남)" +
                    "  얇은청바지 슬렉스 칠부청바지";
            PreferenceManager.setString(mContext, "MBLIST", tlist2);
        }
        mblist = tlist2.split("  ");
        String tlist3 = PreferenceManager.getString(mContext,"WTLIST");
        if(tlist3.equals("")) {
            tlist3 = "히트텍 기모후드 기모코트 롱패딩 기모맨투맨 오리털패딩 스웨터" +
                    "  히트텍 기모후드 모직코트 기모맨투맨 숏패딩 두꺼운니트 가죽자켓" +
                    "  점퍼 후드 맨투맨 니트 가죽자켓 패딩조끼 트렌치코트" +
                    "  점퍼 후드 맨투맨 패딩조끼 청자켓 가죽자켓 가디건" +
                    "  점퍼 후드 얇은맨투맨 얇은가디건 두꺼운티셔츠 셔츠" +
                    "  얇은맨투맨 티셔츠 셔츠 칠부티셔츠 긴원피스" +
                    "  얇은맨투맨 티셔츠 셔츠 칠부티셔츠 긴원피스" +
                    "  티셔츠 셔츠 칠부티셔츠 긴원피스" +
                    "  칠부티셔츠 원피스 반팔티 반팔셔츠";
            PreferenceManager.setString(mContext, "WTLIST", tlist3);
        }
        wtlist = tlist3.split("  ");
        String tlist4 = PreferenceManager.getString(mContext,"WBLIST");
        if(tlist4.equals("")) {
            tlist4 = "기모조거팬츠 기모청바지 기모스타킹" +
                    "  기모조거팬츠 기모청바지 두꺼운스타킹 레깅스 기모슬렉스" +
                    "  조거팬츠 청바지 스타킹 레깅스 슬렉스 면바지(여)" +
                    "  청바지 스타킹 슬렉스 면바지(여)" +
                    "  청바지 스타킹 슬렉스 면바지(여) 긴치마" +
                    "  얇은청바지 스타킹 슬렉스 면바지(여) 긴치마" +
                    "  얇은청바지 슬렉스 면바지(여) 긴치마 테니스치마" +
                    "  얇은청바지 슬렉스 면바지(여) 테니스치마" +
                    "  얇은청바지 슬렉스 테니스치마 칠부청바지 짧은청바지";
            PreferenceManager.setString(mContext, "WBLIST", tlist4);
        }
        wblist = tlist4.split("  ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                temp[0] = "null";
                temp[1] = "null";
                Intent intent = getIntent();
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
                set_data_cloth sdc = new set_data_cloth();
                BackGround = (LinearLayout)findViewById(R.id.background);
                pmch = (TextView)findViewById(R.id.daycheckpm10);
                winch = (TextView)findViewById(R.id.daycheckwind);
                tempch = (TextView)findViewById(R.id.daychecktemp);
                rainch = (TextView)findViewById(R.id.daycheckrain);
                man = (LinearLayout)findViewById(R.id.man);
                woman = (LinearLayout)findViewById(R.id.woman);
                tman = (TextView)findViewById(R.id.tman);
                twoman = (TextView)findViewById(R.id.twoman);
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
                final double finalT = (Math.round(Twc*100)/100.0); //결과 : x.xx

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

                double finalTwc = Twc;
                ClothingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (background) {
                            case 1:
                                BackGround.setBackgroundResource(R.drawable.bg_1dawn);
                                break;
                            case 2:
                                BackGround.setBackgroundResource(R.drawable.bg_2afternoon);
                                break;
                            case 3:
                                BackGround.setBackgroundResource(R.drawable.bg_3sunset);
                                break;
                            case 4:
                                BackGround.setBackgroundResource(R.drawable.bg_4night);
                                break;
                        }
                        if (rp.equals("0")) {
                            switch (weather) {
                                case "Sunny":
                                    rainch.setText("햇빛이 많이 드는 날이에요. 선크림 바르시는 걸 추천할게요.");
                                    break;
                                case "Cloud":
                                    rainch.setText("날씨가 조금 흐리군요. 습도에 유의하셔야 할 것 같아요.");
                                    break;
                                case "Blur":
                                    rainch.setText("날씨가 많이 흐리군요. 습도에 주의하셔야 할 것 같아요.");
                                    break;
                            }
                        } else if (rp.equals("3") || rp.equals("7")) {
                            rainch.setText("오늘은 눈이올 예정이에요. 미끄러짐에 주의하세요");
                        } else {
                            rainch.setText("오늘은 비가 올 예정이에요. 바짓단이 긴 옷은 피해주세요.");
                        }
                        switch (pm10) {
                            case "좋음":
                                pmch.setText("미세먼지가 며칠 간 좋을 것 같아요.");
                                break;
                            case "보통":
                                pmch.setText("미세먼지가 며칠 간 좋지 않을 것 같아요.");
                                break;
                            case "나쁨":
                                pmch.setText("많은 미세먼지가 지속적으로 있을 것 같아요.");
                                break;
                            case "매우나쁨":
                                pmch.setText("매우 많은 미세먼지가 며칠 간동안 지속적으로 있을 것 같아요.");
                                break;
                        }
                        if (finalT <= 4) {
                            winch.setText("한파가 지속돼요. 기모제품과 내복입으시는 걸 추천드려요.");
                        } else if (finalT >= 4 && finalT < 8) {
                            winch.setText("차가운 날씨에요. 두꺼운 아우터안에 여러 옷을 껴 입으시는 걸 추천드려요.");
                        } else if (finalT >= 8 && finalT < 12) {
                            winch.setText("아직은 추운 날씨군요. 두꺼운 아우터에 옷을 한 두겹 껴 입으시는 걸 추천드려요.");
                        } else if (finalT >= 12 && finalT < 16) {
                            winch.setText("쌀쌀한 날씨에요. 일반 아우터안에  여러겹 껴 입으시는 걸 추천드려요.");
                        }else if (finalT >= 16 && finalT < 19) {
                            winch.setText("서늘한 날씨군요. 일반 아우터안에  옷을 한 두겹 껴 입으시는 걸 추천드려요.");
                        }else if (finalT >= 19 && finalT < 21) {
                            winch.setText("선선한 날씨군요. 얇은 아우터를 걸치시는 걸 추천드려요.");

                        }else if (finalT >= 21 && finalT < 23) {
                            winch.setText("따듯한 날씨지만, 가벼운 소재의 아우터나 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else if (finalT >= 23 && finalT < 25) {
                            winch.setText("약간 더울 수 있는 날씨에요. 가벼운 소재와 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else if (finalT >= 25 && finalT < 29) {
                            winch.setText("더울 수 있는 날씨에요. 겉 옷은 입지 않고 얇은 상의&하의로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else {
                            winch.setText("매우 더울 수 있는 날씨에요. 반팔 의상 및 칠부 의상으로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        if(Daily_cross > 10){
                            tempch.setText("일교차가 큰 날씨에요. 옷을 유동적으로 입으셔야 할 것 같아요.");
                        }else {
                            tempch.setText("오늘은 일교차가 별로 없는 날이에요.");
                        }
                        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
                        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);
                        linearLayoutTop.removeAllViews();
                        linearLayoutBottom.removeAllViews();
                        if(finalT <= 4){// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            topcloth = mtlist[0].split(" ");
                            for(int i =0; i<tempset[0]; i++)
                            {
                                setCloth_image_top(tempset[0], i, topcloth[i]);
                            }
                            botcloth = mblist[0].split(" ");
                            for(int i =0; i<tempset[9]; i++)
                            {
                                setCloth_image_bot(tempset[9], i, botcloth[i]);
                            }
                        }else if(finalT<=8 && finalT > 4){
                            topcloth = mtlist[1].split(" ");
                            for(int i =0; i<tempset[1]; i++)
                            {
                                setCloth_image_top(tempset[1], i, topcloth[i]);
                            }
                            botcloth = mblist[1].split(" ");
                            for(int i =0; i<tempset[10]; i++)
                            {
                                setCloth_image_bot(tempset[10], i, botcloth[i]);
                            }
                        }else if(finalT<=12 && finalT > 8){
                            topcloth = mtlist[2].split(" ");
                            for(int i =0; i<tempset[2]; i++)
                            {
                                setCloth_image_top(tempset[2], i, topcloth[i]);
                            }
                            botcloth = mblist[2].split(" ");
                            for(int i =0; i<tempset[11]; i++)
                            {
                                setCloth_image_bot(tempset[11], i, botcloth[i]);
                            }
                        }else if(finalT<=16 && finalT > 12){
                            topcloth = mtlist[3].split(" ");
                            for(int i =0; i<tempset[3]; i++)
                            {
                                setCloth_image_top(tempset[3], i, topcloth[i]);
                            }
                            botcloth = mblist[3].split(" ");
                            for(int i =0; i<tempset[12]; i++)
                            {
                                setCloth_image_bot(tempset[12], i, botcloth[i]);
                            }
                        }else if(finalT<=19 && finalT > 16){
                            topcloth = mtlist[4].split(" ");
                            for(int i =0; i<tempset[4]; i++)
                            {
                                setCloth_image_top(tempset[4], i, topcloth[i]);
                            }
                            botcloth = mblist[4].split(" ");
                            for(int i =0; i<tempset[13]; i++)
                            {
                                setCloth_image_bot(tempset[13], i, botcloth[i]);
                            }
                        }else if(finalT<=22 && finalT > 19){
                            topcloth = mtlist[5].split(" ");
                            for(int i =0; i<tempset[5]; i++)
                            {
                                setCloth_image_top(tempset[5], i, topcloth[i]);
                            }
                            botcloth = mblist[5].split(" ");
                            for(int i =0; i<tempset[14]; i++)
                            {
                                setCloth_image_bot(tempset[14], i, botcloth[i]);
                            }
                        }else if(finalT<=24 && finalT > 22){
                            topcloth = mtlist[6].split(" ");
                            for(int i =0; i<tempset[6]; i++)
                            {
                                setCloth_image_top(tempset[6], i, topcloth[i]);
                            }
                            botcloth = mblist[6].split(" ");
                            for(int i =0; i<tempset[15]; i++)
                            {
                                setCloth_image_bot(tempset[15], i, botcloth[i]);
                            }
                        }else if(finalT<=29 && finalT > 24){
                            topcloth = mtlist[7].split(" ");
                            for(int i =0; i<tempset[7]; i++)
                            {
                                setCloth_image_top(tempset[7], i, topcloth[i]);
                            }
                            botcloth = mblist[7].split(" ");
                            for(int i =0; i<tempset[16]; i++)
                            {
                                setCloth_image_bot(tempset[16], i, botcloth[i]);
                            }
                        }else{
                            topcloth = mtlist[8].split(" ");
                            for(int i =0; i<tempset[8]; i++)
                            {
                                setCloth_image_top(tempset[8], i, topcloth[i]);
                            }
                            botcloth = mblist[8].split(" ");
                            for(int i =0; i<tempset[17]; i++)
                            {
                                setCloth_image_bot(tempset[17], i, botcloth[i]);
                            }
                        }
                        man.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
                                LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);
                                linearLayoutTop.removeAllViews();
                                linearLayoutBottom.removeAllViews();
                                man.setBackgroundResource(R.drawable.change_background);
                                woman.setBackgroundResource(0);
                                tman.setTextColor(Color.parseColor("#777777"));
                                twoman.setTextColor(Color.parseColor("#FFFFFF"));
                                if(finalT <= 4){// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                                    topcloth = mtlist[0].split(" ");
                                    for(int i =0; i<tempset[0]; i++)
                                    {
                                        setCloth_image_top(tempset[0], i, topcloth[i]);
                                    }
                                    botcloth = mblist[0].split(" ");
                                    for(int i =0; i<tempset[9]; i++)
                                    {
                                        setCloth_image_bot(tempset[9], i, botcloth[i]);
                                    }
                                }else if(finalT<=8 && finalT > 4){
                                    topcloth = mtlist[1].split(" ");
                                    for(int i =0; i<tempset[1]; i++)
                                    {
                                        setCloth_image_top(tempset[1], i, topcloth[i]);
                                    }
                                    botcloth = mblist[1].split(" ");
                                    for(int i =0; i<tempset[10]; i++)
                                    {
                                        setCloth_image_bot(tempset[10], i, botcloth[i]);
                                    }
                                }else if(finalT<=12 && finalT > 8){
                                    topcloth = mtlist[2].split(" ");
                                    for(int i =0; i<tempset[2]; i++)
                                    {
                                        setCloth_image_top(tempset[2], i, topcloth[i]);
                                    }
                                    botcloth = mblist[2].split(" ");
                                    for(int i =0; i<tempset[11]; i++)
                                    {
                                        setCloth_image_bot(tempset[11], i, botcloth[i]);
                                    }
                                }else if(finalT<=16 && finalT > 12){
                                    topcloth = mtlist[3].split(" ");
                                    for(int i =0; i<tempset[3]; i++)
                                    {
                                        setCloth_image_top(tempset[3], i, topcloth[i]);
                                    }
                                    botcloth = mblist[3].split(" ");
                                    for(int i =0; i<tempset[12]; i++)
                                    {
                                        setCloth_image_bot(tempset[12], i, botcloth[i]);
                                    }
                                }else if(finalT<=19 && finalT > 16){
                                    topcloth = mtlist[4].split(" ");
                                    for(int i =0; i<tempset[4]; i++)
                                    {
                                        setCloth_image_top(tempset[4], i, topcloth[i]);
                                    }
                                    botcloth = mblist[4].split(" ");
                                    for(int i =0; i<tempset[13]; i++)
                                    {
                                        setCloth_image_bot(tempset[13], i, botcloth[i]);
                                    }
                                }else if(finalT<=22 && finalT > 19){
                                    topcloth = mtlist[5].split(" ");
                                    for(int i =0; i<tempset[5]; i++)
                                    {
                                        setCloth_image_top(tempset[5], i, topcloth[i]);
                                    }
                                    botcloth = mblist[5].split(" ");
                                    for(int i =0; i<tempset[14]; i++)
                                    {
                                        setCloth_image_bot(tempset[14], i, botcloth[i]);
                                    }
                                }else if(finalT<=24 && finalT > 22){
                                    topcloth = mtlist[6].split(" ");
                                    for(int i =0; i<tempset[6]; i++)
                                    {
                                        setCloth_image_top(tempset[6], i, topcloth[i]);
                                    }
                                    botcloth = mblist[6].split(" ");
                                    for(int i =0; i<tempset[15]; i++)
                                    {
                                        setCloth_image_bot(tempset[15], i, botcloth[i]);
                                    }
                                }else if(finalT<=29 && finalT > 24){
                                    topcloth = mtlist[7].split(" ");
                                    for(int i =0; i<tempset[7]; i++)
                                    {
                                        setCloth_image_top(tempset[7], i, topcloth[i]);
                                    }
                                    botcloth = mblist[7].split(" ");
                                    for(int i =0; i<tempset[16]; i++)
                                    {
                                        setCloth_image_bot(tempset[16], i, botcloth[i]);
                                    }
                                }else{
                                    topcloth = mtlist[8].split(" ");
                                    for(int i =0; i<tempset[8]; i++)
                                    {
                                        setCloth_image_top(tempset[8], i, topcloth[i]);
                                    }
                                    botcloth = mblist[8].split(" ");
                                    for(int i =0; i<tempset[17]; i++)
                                    {
                                        setCloth_image_bot(tempset[17], i, botcloth[i]);
                                    }
                                }
                            }
                        });
                        woman.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
                                LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);
                                linearLayoutTop.removeAllViews();
                                linearLayoutBottom.removeAllViews();
                                man.setBackgroundResource(0);
                                woman.setBackgroundResource(R.drawable.change_background);
                                tman.setTextColor(Color.parseColor("#FFFFFF"));
                                twoman.setTextColor(Color.parseColor("#777777"));
                                if(finalT <= 4){
                                    topcloth = wtlist[0].split(" ");
                                    for(int i =0; i<tempset[18]; i++)
                                    {
                                        setCloth_image_top(tempset[18], i, topcloth[i]);
                                    }
                                    botcloth = wblist[0].split(" ");
                                    for(int i =0; i<tempset[27]; i++)
                                    {
                                        setCloth_image_bot(tempset[27], i, botcloth[i]);
                                    }
                                }else if(finalT<=8 && finalT > 4){
                                    topcloth = wtlist[1].split(" ");
                                    for(int i =0; i<tempset[19]; i++)
                                    {
                                        setCloth_image_top(tempset[19], i, topcloth[i]);
                                    }
                                    botcloth = wblist[1].split(" ");
                                    for(int i =0; i<tempset[28]; i++)
                                    {
                                        setCloth_image_bot(tempset[28], i, botcloth[i]);
                                    }
                                }else if(finalT<=11 && finalT > 8){
                                    topcloth = wtlist[2].split(" ");
                                    for(int i =0; i<tempset[20]; i++)
                                    {
                                        setCloth_image_top(tempset[20], i, topcloth[i]);
                                    }
                                    botcloth = wblist[2].split(" ");
                                    for(int i =0; i<tempset[29]; i++)
                                    {
                                        setCloth_image_bot(tempset[29], i, botcloth[i]);
                                    }
                                }else if(finalT<=16 && finalT > 11){
                                    topcloth = wtlist[3].split(" ");
                                    for(int i =0; i<tempset[21]; i++)
                                    {
                                        setCloth_image_top(tempset[21], i, topcloth[i]);
                                    }
                                    botcloth = wblist[3].split(" ");
                                    for(int i =0; i<tempset[30]; i++)
                                    {
                                        setCloth_image_bot(tempset[30], i, botcloth[i]);
                                    }
                                }else if(finalT<=19 && finalT > 16){
                                    topcloth = wtlist[4].split(" ");
                                    for(int i =0; i<tempset[22]; i++)
                                    {
                                        setCloth_image_top(tempset[22], i, topcloth[i]);
                                    }
                                    botcloth = wblist[4].split(" ");
                                    for(int i =0; i<tempset[31]; i++)
                                    {
                                        setCloth_image_bot(tempset[31], i, botcloth[i]);
                                    }
                                }else if(finalT<=22 && finalT > 19){
                                    topcloth = wtlist[5].split(" ");
                                    for(int i =0; i<tempset[23]; i++)
                                    {
                                        setCloth_image_top(tempset[23], i, topcloth[i]);
                                    }
                                    botcloth = wblist[5].split(" ");
                                    for(int i =0; i<tempset[32]; i++)
                                    {
                                        setCloth_image_bot(tempset[32], i, botcloth[i]);
                                    }
                                }else if(finalT<=24 && finalT > 22){
                                    topcloth = wtlist[6].split(" ");
                                    for(int i =0; i<tempset[24]; i++)
                                    {
                                        setCloth_image_top(tempset[24], i, topcloth[i]);
                                    }
                                    botcloth = wblist[6].split(" ");
                                    for(int i =0; i<tempset[33]; i++)
                                    {
                                        setCloth_image_bot(tempset[33], i, botcloth[i]);
                                    }
                                }else if(finalT<=29 && finalT > 24){
                                    topcloth = wtlist[7].split(" ");
                                    for(int i =0; i<tempset[25]; i++)
                                    {
                                        setCloth_image_top(tempset[25], i, topcloth[i]);
                                    }
                                    botcloth = wblist[7].split(" ");
                                    for(int i =0; i<tempset[34]; i++)
                                    {
                                        setCloth_image_bot(tempset[34], i, botcloth[i]);
                                    }
                                }else{
                                    topcloth = wtlist[8].split(" ");
                                    for(int i =0; i<tempset[26]; i++)
                                    {
                                        setCloth_image_top(tempset[26], i, topcloth[i]);
                                    }
                                    botcloth = wblist[8].split(" ");
                                    for(int i =0; i<tempset[35]; i++)
                                    {
                                        setCloth_image_bot(tempset[35], i, botcloth[i]);
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
   }
    public void setCloth_image_top(int max, int i, String cloth){
        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
        LinearLayout[] linearLayoutTopV = new LinearLayout[max];
        TextView[] topTextView = new TextView[max];
        ImageView[] topImageView = new ImageView[max];

        linearLayoutTopV[i] = new LinearLayout(this);
        topTextView[i] = new TextView(this);
        topImageView[i] = new ImageView(this);

        linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);
        switch (cloth) {
            case "옷":
                topImageView[i].setImageResource(R.drawable.cloth);
                break;
            case "가디건":
                topImageView[i].setImageResource(R.drawable.cloth_cardigun);
                break;
            case "얇은가디건":
                topImageView[i].setImageResource(R.drawable.cloth_cardigun);
                break;
            case "히트텍":
                topImageView[i].setImageResource(R.drawable.cloth_hitec);
                break;
            case "후드":
                topImageView[i].setImageResource(R.drawable.cloth_hood);
                break;
            case "기모후드":
                topImageView[i].setImageResource(R.drawable.cloth_hood);
                break;
            case "청자켓":
                topImageView[i].setImageResource(R.drawable.cloth_jeanjacket);
                break;
            case "기모맨투맨":
                topImageView[i].setImageResource(R.drawable.cloth_kimomtm1);
                break;
            case "가죽자켓":
                topImageView[i].setImageResource(R.drawable.cloth_leatherjacket);
                break;
            case "롱패딩":
                topImageView[i].setImageResource(R.drawable.cloth_longpadding);
                break;
            case "셔츠":
                topImageView[i].setImageResource(R.drawable.cloth_longshirts);
                break;
            case "점퍼":
                topImageView[i].setImageResource(R.drawable.cloth_ma1);
                break;
            case "블레이저":
                topImageView[i].setImageResource(R.drawable.cloth_manblazer);
                break;
            case "롱코트(남)":
                topImageView[i].setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "칠부티셔츠":
                topImageView[i].setImageResource(R.drawable.cloth_midlesleeve);
                break;
            case "맨투맨":
                topImageView[i].setImageResource(R.drawable.cloth_mtm3);
                break;
            case "얇은맨투맨":
                topImageView[i].setImageResource(R.drawable.cloth_mtm4);
                break;
            case "나시":
                topImageView[i].setImageResource(R.drawable.cloth_nasi);
                break;
            case "니트":
                topImageView[i].setImageResource(R.drawable.cloth_neat);
                break;
            case "두꺼운니트":
                topImageView[i].setImageResource(R.drawable.cloth_neat);
                break;
            case "원피스":
                topImageView[i].setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "긴원피스":
                topImageView[i].setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "숏패딩":
                topImageView[i].setImageResource(R.drawable.cloth_padding1);
                break;
            case "오리털패딩":
                topImageView[i].setImageResource(R.drawable.cloth_padding2);
                break;
            case "패딩조끼":
                topImageView[i].setImageResource(R.drawable.cloth_paddingvest);
                break;
            case "셔츠(중)":
                topImageView[i].setImageResource(R.drawable.cloth_shirts);
                break;
            case "반팔셔츠":
                topImageView[i].setImageResource(R.drawable.cloth_shortshirts);
                break;
            case "반팔티":
                topImageView[i].setImageResource(R.drawable.cloth_shortsleeve);
                break;
            case "스웨터":
                topImageView[i].setImageResource(R.drawable.cloth_sweater);
                break;
            case "트렌치코트":
                topImageView[i].setImageResource(R.drawable.cloth_trenchcoat);
                break;
            case "티셔츠":
                topImageView[i].setImageResource(R.drawable.cloth_tshirts);
                break;
            case "두꺼운티셔츠":
                topImageView[i].setImageResource(R.drawable.cloth_tshirts);
                break;
            case "기모코트":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat1);
                break;
            case "모직코트":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat2);
                break;
            default:
                break;

        }

        topImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        topTextView[i].setTextSize(10);
        topTextView[i].setTextColor(Color.WHITE);
        topTextView[i].setText(cloth);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.bottomMargin = 10;
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        topTextView[i].setLayoutParams(lp1);
        linearLayoutTopV[i].addView(topImageView[i],180,180);
        linearLayoutTopV[i].addView(topTextView[i]);
        linearLayoutTopV[i].setMinimumWidth(100);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.rightMargin = 70;
        linearLayoutTopV[i].setLayoutParams(lp2);
        linearLayoutTop.addView(linearLayoutTopV[i]);
    }
    public void setCloth_image_bot(int max, int i, String cloth){
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);

        LinearLayout[] linearLayoutBotV = new LinearLayout[max];
        TextView[] botTextView = new TextView[max];
        ImageView[] botImageView = new ImageView[max];

        linearLayoutBotV[i] = new LinearLayout(this);
        botTextView[i] = new TextView(this);
        botImageView[i] = new ImageView(this);

        linearLayoutBotV[i].setOrientation(LinearLayout.VERTICAL);
        switch (cloth) {
            case "옷":
                botImageView[i].setImageResource(R.drawable.cloth);
                break;
            case "청바지":
                botImageView[i].setImageResource(R.drawable.cloth_jean);
                break;
            case "조거팬츠":
                botImageView[i].setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "기모조거팬츠":
                botImageView[i].setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "기모청바지":
                botImageView[i].setImageResource(R.drawable.cloth_kimojean);
                break;
            case "두꺼운스타킹":
                botImageView[i].setImageResource(R.drawable.cloth_kimostocking);
                break;
            case "레깅스":
                botImageView[i].setImageResource(R.drawable.cloth_leggings);
                break;
            case "긴치마":
                botImageView[i].setImageResource(R.drawable.cloth_longskirt);
                break;
            case "면바지(남)":
                botImageView[i].setImageResource(R.drawable.cloth_mancotton);
                break;
            case "칠부청바지":
                botImageView[i].setImageResource(R.drawable.cloth_middlejean);
                break;
            case "짧은청바지":
                botImageView[i].setImageResource(R.drawable.cloth_shortjean);
                break;
            case "테니스치마":
                botImageView[i].setImageResource(R.drawable.cloth_shortskirt);
                break;
            case "슬렉스":
                botImageView[i].setImageResource(R.drawable.cloth_slacks);
                break;
            case "기모슬렉스":
                botImageView[i].setImageResource(R.drawable.cloth_slacks);
                break;
            case "스타킹":
                botImageView[i].setImageResource(R.drawable.cloth_stocking);
                break;
            case "얇은청바지":
                botImageView[i].setImageResource(R.drawable.cloth_thinjean);
                break;
            case "면바지(여)":
                botImageView[i].setImageResource(R.drawable.cloth_womancotton);
                break;
            default:
                break;

        }

        botImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        botTextView[i].setTextSize(10);
        botTextView[i].setTextColor(Color.WHITE);
        botTextView[i].setText(cloth);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.bottomMargin = 10;
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        botTextView[i].setLayoutParams(lp1);
        linearLayoutBotV[i].addView(botImageView[i],180,180);
        linearLayoutBotV[i].addView(botTextView[i]);
        linearLayoutBotV[i].setMinimumWidth(100);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.rightMargin = 70;
        linearLayoutBotV[i].setLayoutParams(lp2);
        linearLayoutBottom.addView(linearLayoutBotV[i]);
    }
}
