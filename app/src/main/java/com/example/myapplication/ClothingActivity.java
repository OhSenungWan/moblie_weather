package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.savedata.PreferenceManager;
import com.example.myapplication.setdata.set_data_cloth;
import com.example.myapplication.setdata.data_short;
import com.example.myapplication.setdata.set_weather;

import org.w3c.dom.Text;

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
    int sets = 1;
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
    TextView pmch, winch, tempch, rainch, tman, twoman, set_text , bset_text;
    String[] temp = new String[2];
    String[] tempdata;
    double ShareT;


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
        tempdata = data;
        tempset = new int[data.length];
        for(int i = 0; i<data.length; i++){
            tempset[i] = Integer.parseInt(data[i]);
        }
        String tlist1 = PreferenceManager.getString(mContext,"MTLIST");
        if(tlist1.equals("")) {
            tlist1 = "히트텍 기모후드 롱패딩 기모코트(남) 기모맨투맨 오리털패딩 스웨터" +
                    "  히트텍 기모후드 모직코트(남) 기모맨투맨 숏패딩 두꺼운니트 가죽자켓" +
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
            tlist3 = "히트텍 기모후드 기모코트(여) 롱패딩 기모맨투맨 오리털패딩 스웨터" +
                    "  히트텍 기모후드 모직코트(여) 기모맨투맨 숏패딩 두꺼운니트 가죽자켓" +
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
                set_text = (TextView)findViewById(R.id.top_set_text);
                bset_text = (TextView)findViewById(R.id.bot_set_text);
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
                ShareT = finalT;




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
                                    rainch.setText("햇빛이 많이 드는 날이에요. " +
                                            "선크림 바르시는 걸 추천할게요.");
                                    break;
                                case "Cloud":
                                    rainch.setText("날씨가 조금 흐리군요. " +
                                            "습도에 유의하셔야 할 것 같아요.");
                                    break;
                                case "Blur":
                                    rainch.setText("날씨가 많이 흐리군요. " +
                                            "습도에 주의하셔야 할 것 같아요.");
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
                                pmch.setText("매우 많은 미세먼지가 " +
                                        "며칠 간동안 지속적으로 있을 것 같아요.");
                                break;
                        }
                        if (finalT <= 4) {
                            winch.setText("한파가 지속돼요. 기모제품과 내복입으시는 걸 추천드려요.");
                        } else if (finalT >= 4 && finalT < 8) {
                            winch.setText("차가운 날씨에요. 두꺼운 아우터안에 " +
                                    "여러 옷을 껴 입으시는 걸 추천드려요.");
                        } else if (finalT >= 8 && finalT < 12) {
                            winch.setText("아직은 추운 날씨군요. " +
                                    "두꺼운 아우터에 옷을 한 두겹 껴 입으시는 걸 추천드려요.");
                        } else if (finalT >= 12 && finalT < 16) {
                            winch.setText("쌀쌀한 날씨에요. " +
                                    "일반 아우터안에  여러겹 껴 입으시는 걸 추천드려요.");
                        }else if (finalT >= 16 && finalT < 19) {
                            winch.setText("서늘한 날씨군요. 일반 아우터안에  " +
                                    "옷을 한 두겹 껴 입으시는 걸 추천드려요.");
                        }else if (finalT >= 19 && finalT < 21) {
                            winch.setText("선선한 날씨군요. 얇은 아우터를 걸치시는 걸 추천드려요.");

                        }else if (finalT >= 21 && finalT < 23) {
                            winch.setText("따듯한 날씨지만, 가벼운 소재의 " +
                                    "아우터나 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else if (finalT >= 23 && finalT < 25) {
                            winch.setText("약간 더울 수 있는 날씨에요. 가벼운 " +
                                    "소재와 얇은 옷들로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else if (finalT >= 25 && finalT < 29) {
                            winch.setText("더울 수 있는 날씨에요. 겉 옷은 입지 " +
                                    "않고 얇은 상의&하의로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        else {
                            winch.setText("매우 더울 수 있는 날씨에요. " +
                                    "반팔 의상 및 칠부 의상으로 스타일을 구성하시는 것이 좋겠어요.");
                        }
                        if(Daily_cross > 10){
                            tempch.setText("일교차가 큰 날씨에요. " +
                                    "옷을 유동적으로 입으셔야 할 것 같아요.");
                        }else {
                            tempch.setText("오늘은 일교차가 별로 없는 날이에요.");
                        }
                        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
                        LinearLayout linearLayoutBottom = findViewById
                                (R.id.layout_timeWeatherBottom);
                        linearLayoutTop.removeAllViews();
                        linearLayoutBottom.removeAllViews();
                        set_text.setText("현재 체감온도는 " + ShareT +"℃ 입니다. ");
                        bset_text.setText("현재 체감온도는 " + ShareT +"℃ 입니다. ");
                        if(finalT <= 4){// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " "+ topcloth[1] + " "+topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
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
                                sets = 1;
                                LinearLayout linearLayoutTop = findViewById
                                        (R.id.layout_timeWeatherTop);
                                LinearLayout linearLayoutBottom = findViewById
                                        (R.id.layout_timeWeatherBottom);
                                linearLayoutTop.removeAllViews();
                                linearLayoutBottom.removeAllViews();
                                man.setBackgroundResource(R.drawable.change_background);
                                woman.setBackgroundResource(0);
                                tman.setTextColor(Color.parseColor("#777777"));
                                twoman.setTextColor(Color.parseColor("#FFFFFF"));
                                if(finalT <= 4){// 0~8 mantop9~17manbot 18~26womantop27~35 womanbot
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
                                sets = 0;
                                LinearLayout linearLayoutTop = findViewById
                                        (R.id.layout_timeWeatherTop);
                                LinearLayout linearLayoutBottom = findViewById
                                        (R.id.layout_timeWeatherBottom);
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
    } // 클로싱 액티비티 처음 호출시 호출

    public void addtop(View v){

        View dialogView = getLayoutInflater().inflate(R.layout.activity_clothadd, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        EditText edtAdd = dialogView.findViewById(R.id.edtadd);
        ImageView cancel = dialogView.findViewById(R.id.addcancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        TextView ok = dialogView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtAdd.getText().toString().length() <= 0){//빈값이 넘어올때의 처리

                    Toast.makeText(getApplicationContext(), "값을 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                        //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                        // 옷 더하기 mtlist[0] += " 추가할옷";
                        if (sets == 1) {
                            tempset[0] += 1;
                            mtlist[0] = mtlist[0] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[18] += 1;
                            wtlist[0] = wtlist[0] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 8 && ShareT > 4) {
                        if (sets == 1) {
                            tempset[1] += 1;
                            mtlist[1] = mtlist[1] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[19] += 1;
                            wtlist[1] = wtlist[1] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 12 && ShareT > 8) {
                        if (sets == 1) {
                            tempset[2] += 1;
                            mtlist[2] = mtlist[2] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[20] += 1;
                            wtlist[2] = wtlist[2] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 16 && ShareT > 12) {
                        if (sets == 1) {
                            tempset[3] += 1;
                            mtlist[3] = mtlist[3] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[21] += 1;
                            wtlist[3] = wtlist[3] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 19 && ShareT > 16) {
                        if (sets == 1) {
                            tempset[4] += 1;
                            mtlist[4] = mtlist[4] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[22] += 1;
                            wtlist[4] = wtlist[4] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 22 && ShareT > 19) {
                        if (sets == 1) {
                            tempset[5] += 1;
                            mtlist[5] = mtlist[5] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[23] += 1;
                            wtlist[5] = wtlist[5] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 24 && ShareT > 22) {
                        if (sets == 1) {
                            tempset[6] += 1;
                            mtlist[6] = mtlist[6] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[24] += 1;
                            wtlist[6] = wtlist[6] + " " + edtAdd.getText().toString();
                        }
                    } else if (ShareT <= 29 && ShareT > 24) {
                        if (sets == 1) {
                            tempset[7] += 1;
                            mtlist[7] = mtlist[7] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[25] += 1;
                            wtlist[7] = wtlist[7] + " " + edtAdd.getText().toString();
                        }
                    } else {
                        if (sets == 1) {
                            tempset[8] += 1;
                            mtlist[8] = mtlist[8] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[26] += 1;
                            wtlist[8] = wtlist[8] + " " + edtAdd.getText().toString();
                        }
                    }
                    for(int i = 0; i<tempdata.length; i++){
                        tempdata[i] = Integer.toString(tempset[i]);
                    }
                    String text = "";
                    for(int i = 0; i<tempdata.length; i++){
                        if(i == tempdata.length-1){
                            text += tempdata[i];
                        }
                        else{
                            text += tempdata[i] + " ";
                        }

                    }
                    PreferenceManager.setString(mContext, "CLOTHSET", text);
                    String tlist1 = "";
                    for(int i =0; i<mtlist.length;i++){
                        if(i == mtlist.length-1){
                            tlist1 += mtlist[i];
                        }
                        else{
                            tlist1 += mtlist[i] + "  ";
                        }
                    }
                    PreferenceManager.setString(mContext, "MTLIST", tlist1);

                    String tlist2 = "";
                    for(int i =0; i<wtlist.length;i++){
                        if(i == wtlist.length-1){
                            tlist2 += wtlist[i];
                        }
                        else{
                            tlist2 += wtlist[i] + "  ";
                        }
                    }
                    PreferenceManager.setString(mContext, "WTLIST", tlist2);

                    Toast.makeText(getApplicationContext(), "저장되었습니다.",
                            Toast.LENGTH_LONG).show();

                    drawCloth();
                    alertDialog.dismiss();

                }

            }
        });

        /*Intent intent = new Intent(this, PopupActivity.class);
        startActivityForResult(intent, 1);*/



        /*//데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopActivity.class);
        startActivityForResult(intent, 1);*/
    } // 위쪽 의상 추가시 호출

    public void addbottom(View v){

        View dialogView = getLayoutInflater().inflate(R.layout.activity_clothadd, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        EditText edtAdd = dialogView.findViewById(R.id.edtadd);
        ImageView cancel = dialogView.findViewById(R.id.addcancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        TextView ok = dialogView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtAdd.getText().toString().length() <= 0){//빈값이 넘어올때의 처리

                    Toast.makeText(getApplicationContext(), "값을 입력하세요.",
                            Toast.LENGTH_SHORT).show();
                }

                else{
                    if(ShareT <= 4){// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                        //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                        // 옷 더하기 mtlist[0] += " 추가할옷";
                        if(sets == 1) {
                            tempset[9] += 1;
                            mblist[0] = mblist[0] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[27] += 1;
                            wblist[0] = wblist[0] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=8 && ShareT > 4){
                        if(sets == 1) {
                            tempset[10] += 1;
                            mblist[1] = mblist[1] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[28] += 1;
                            wblist[1] = wblist[1] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=12 && ShareT > 8){
                        if(sets == 1) {
                            tempset[11] += 1;
                            mblist[2] = mblist[2] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[29] += 1;
                            wblist[2] = wblist[2] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=16 && ShareT > 12){
                        if(sets == 1) {
                            tempset[12] += 1;
                            mblist[3] = mblist[3] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[30] += 1;
                            wblist[3] = wblist[3] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=19 && ShareT > 16){
                        if(sets == 1) {
                            tempset[13] += 1;
                            mblist[4] = mblist[4] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[31] += 1;
                            wblist[4] = wblist[4] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=22 && ShareT > 19){
                        if(sets == 1) {
                            tempset[14] += 1;
                            mblist[5] = mblist[5] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[32] += 1;
                            wblist[5] = wblist[5] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=24 && ShareT > 22){
                        if(sets == 1) {
                            tempset[15] += 1;
                            mblist[6] = mblist[6] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[33] += 1;
                            wblist[6] = wblist[6] + " " + edtAdd.getText().toString();
                        }
                    }else if(ShareT<=29 && ShareT > 24){
                        if(sets == 1) {
                            tempset[16] += 1;
                            mblist[7] = mblist[7] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[34] += 1;
                            wblist[7] = wblist[7] + " " + edtAdd.getText().toString();
                        }
                    }else{
                        if(sets == 1) {
                            tempset[17] += 1;
                            mblist[8] = mblist[8] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[35] += 1;
                            wblist[8] = wblist[8] + " " + edtAdd.getText().toString();
                        }
                    }

                    for(int i = 0; i<tempdata.length; i++){
                        tempdata[i] = Integer.toString(tempset[i]);
                    }
                    String text = "";
                    for(int i = 0; i<tempdata.length; i++){
                        if(i == tempdata.length-1){
                            text += tempdata[i];
                        }
                        else{
                            text += tempdata[i] + " ";
                        }

                    }
                    PreferenceManager.setString(mContext, "CLOTHSET", text);

                    String tlist3 = "";
                    for(int i =0; i<mblist.length;i++){
                        if(i == mblist.length-1){
                            tlist3 += mblist[i];
                        }
                        else{
                            tlist3 += mblist[i] + "  ";
                        }
                    }
                    PreferenceManager.setString(mContext, "MBLIST", tlist3);

                    String tlist4 = "";
                    for(int i =0; i<wblist.length;i++){
                        if(i == wblist.length-1){
                            tlist4 += wblist[i];
                        }
                        else{
                            tlist4 += wblist[i] + "  ";
                        }
                    }
                    PreferenceManager.setString(mContext, "WBLIST", tlist4);

                    Toast.makeText(getApplicationContext(), "저장되었습니다.",
                            Toast.LENGTH_LONG).show();

                    drawCloth();
                    alertDialog.dismiss();

                }
            }
        });

        /*Intent intent = new Intent(this, PopupActivity.class);
        startActivityForResult(intent, 1);*/



        /*//데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopActivity.class);
        startActivityForResult(intent, 1);*/
    } // 아래쪽 의상 추가시 호출

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
            case "기모코트(남)":
                topImageView[i].setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "기모코트(여)":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat1);
                break;
            case "모직코트(남)":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat2);
                break;
            case "모직코트(여)":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat2);
                break;
            default:
                topImageView[i].setImageResource(R.drawable.custom2);
                break;

        }

        topImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        topImageView[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothcontrol, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                ImageView cancel = dialogView.findViewById(R.id.cancle);
                ImageView Weather = dialogView.findViewById(R.id.Weather);
                TextView ctext = dialogView.findViewById(R.id.setctext);
                ctext.setText(cloth);
                switch (cloth) {
                    case "옷":
                        Weather.setImageResource(R.drawable.cloth);
                        break;
                    case "가디건":
                        Weather.setImageResource(R.drawable.cloth_cardigun);
                        break;
                    case "얇은가디건":
                        Weather.setImageResource(R.drawable.cloth_cardigun);
                        break;
                    case "히트텍":
                        Weather.setImageResource(R.drawable.cloth_hitec);
                        break;
                    case "후드":
                        Weather.setImageResource(R.drawable.cloth_hood);
                        break;
                    case "기모후드":
                        Weather.setImageResource(R.drawable.cloth_hood);
                        break;
                    case "청자켓":
                        Weather.setImageResource(R.drawable.cloth_jeanjacket);
                        break;
                    case "기모맨투맨":
                        Weather.setImageResource(R.drawable.cloth_kimomtm1);
                        break;
                    case "가죽자켓":
                        Weather.setImageResource(R.drawable.cloth_leatherjacket);
                        break;
                    case "롱패딩":
                        Weather.setImageResource(R.drawable.cloth_longpadding);
                        break;
                    case "셔츠":
                        Weather.setImageResource(R.drawable.cloth_longshirts);
                        break;
                    case "점퍼":
                        Weather.setImageResource(R.drawable.cloth_ma1);
                        break;
                    case "블레이저":
                        Weather.setImageResource(R.drawable.cloth_manblazer);
                        break;
                    case "롱코트(남)":
                        Weather.setImageResource(R.drawable.cloth_manlongcoat1);
                        break;
                    case "칠부티셔츠":
                        Weather.setImageResource(R.drawable.cloth_midlesleeve);
                        break;
                    case "맨투맨":
                        Weather.setImageResource(R.drawable.cloth_mtm3);
                        break;
                    case "얇은맨투맨":
                        Weather.setImageResource(R.drawable.cloth_mtm4);
                        break;
                    case "나시":
                        Weather.setImageResource(R.drawable.cloth_nasi);
                        break;
                    case "니트":
                        Weather.setImageResource(R.drawable.cloth_neat);
                        break;
                    case "두꺼운니트":
                        Weather.setImageResource(R.drawable.cloth_neat);
                        break;
                    case "원피스":
                        Weather.setImageResource(R.drawable.cloth_onepeicedress);
                        break;
                    case "긴원피스":
                        Weather.setImageResource(R.drawable.cloth_onepeicedress);
                        break;
                    case "숏패딩":
                        Weather.setImageResource(R.drawable.cloth_padding1);
                        break;
                    case "오리털패딩":
                        Weather.setImageResource(R.drawable.cloth_padding2);
                        break;
                    case "패딩조끼":
                        Weather.setImageResource(R.drawable.cloth_paddingvest);
                        break;
                    case "셔츠(중)":
                        Weather.setImageResource(R.drawable.cloth_shirts);
                        break;
                    case "반팔셔츠":
                        Weather.setImageResource(R.drawable.cloth_shortshirts);
                        break;
                    case "반팔티":
                        Weather.setImageResource(R.drawable.cloth_shortsleeve);
                        break;
                    case "스웨터":
                        Weather.setImageResource(R.drawable.cloth_sweater);
                        break;
                    case "트렌치코트":
                        Weather.setImageResource(R.drawable.cloth_trenchcoat);
                        break;
                    case "티셔츠":
                        Weather.setImageResource(R.drawable.cloth_tshirts);
                        break;
                    case "두꺼운티셔츠":
                        Weather.setImageResource(R.drawable.cloth_tshirts);
                        break;
                    case "기모코트(남)":
                        topImageView[i].setImageResource(R.drawable.cloth_manlongcoat1);
                        break;
                    case "기모코트(여)":
                        topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat1);
                        break;
                    case "모직코트(남)":
                        Weather.setImageResource(R.drawable.cloth_womanlongcoat2);
                        break;
                    case "모직코트(여)":
                        Weather.setImageResource(R.drawable.cloth_womanlongcoat2);
                        break;
                    default:
                        Weather.setImageResource(R.drawable.custom2);
                        break;

                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    alertDialog.dismiss();
                    }
                });
                TextView up = dialogView.findViewById(R.id.up);
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] +" "+topcloth[3];옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                tempset[1] += 1;
                                tempset[0] -= 1;
                                mtlist[1] = mtlist[1] + " " + cloth;
                                mtlist[0] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[0] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[0] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[0] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[0] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[19] += 1;
                                tempset[18] -= 1;
                                wtlist[1] = wtlist[1] + " " + cloth;
                                wtlist[0] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[0] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[0] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[0] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[0] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                tempset[2] += 1;
                                tempset[1] -= 1;
                                mtlist[2] = mtlist[2] + " " + cloth;
                                mtlist[1] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[1] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[1] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[1] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[1] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[20] += 1;
                                tempset[19] -= 1;
                                wtlist[2] = wtlist[2] + " " + cloth;
                                wtlist[1] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[1] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[1] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[1] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[1] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                tempset[3] += 1;
                                tempset[2] -= 1;
                                mtlist[3] = mtlist[3] + " " + cloth;
                                mtlist[2] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[2] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[2] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[2] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[2] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[21] += 1;
                                tempset[20] -= 1;
                                wtlist[3] = wtlist[3] + " " + cloth;
                                wtlist[2] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[2] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[2] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[2] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[2] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                tempset[4] += 1;
                                tempset[3] -= 1;
                                mtlist[4] = mtlist[4] + " " + cloth;
                                mtlist[3] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length -1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[3] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[3] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[3] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[3] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[22] += 1;
                                tempset[21] -= 1;
                                wtlist[4] = wtlist[4] + " " + cloth;
                                wtlist[3] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[3] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[3] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[3] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[3] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                tempset[5] += 1;
                                tempset[4] -= 1;
                                mtlist[5] = mtlist[5] + " " + cloth;
                                mtlist[4] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[4] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[4] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[4] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[4] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[23] += 1;
                                tempset[22] -= 1;
                                wtlist[5] = wtlist[5] + " " + cloth;
                                wtlist[4] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[4] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[4] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[4] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[4] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                tempset[6] += 1;
                                tempset[5] -= 1;
                                mtlist[6] = mtlist[6] + " " + cloth;
                                mtlist[5] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[5] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[5] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[5] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[5] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[24] += 1;
                                tempset[23] -= 1;
                                wtlist[6] = wtlist[6] + " " + cloth;
                                wtlist[5] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[5] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[5] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[5] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[5] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                tempset[7] += 1;
                                tempset[6] -= 1;
                                mtlist[7] = mtlist[7] + " " + cloth;
                                mtlist[6] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[6] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[6] += topcloth[j] + " ";
                                        }
                                    }
                                    else{
                                        if (j < i-1) {
                                            mtlist[6] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[6] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[25] += 1;
                                tempset[24] -= 1;
                                wtlist[7] = wtlist[7] + " " + cloth;
                                wtlist[6] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[6] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[6] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[6] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[6] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                tempset[8] += 1;
                                tempset[7] -= 1;
                                mtlist[8] = mtlist[8] + " " + cloth;
                                mtlist[7] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[7] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[7] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[7] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[7] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[26] += 1;
                                tempset[25] -= 1;
                                wtlist[8] = wtlist[8] + " " + cloth;
                                wtlist[7] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[7] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[7] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[7] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[7] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else {
                            if (sets == 1) {
                                tempset[8] -= 1;
                                mtlist[8] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[8] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[8] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[8] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[8] += topcloth[j];
                                        }
                                    }

                                }
                            } else {
                                tempset[26] -= 1;
                                wtlist[8] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[8] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[8] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[8] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[8] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);
                        String tlist1 = "";
                        for(int i =0; i<mtlist.length;i++){
                            if(i == mtlist.length-1){
                                tlist1 += mtlist[i];
                            }
                            else{
                                tlist1 += mtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MTLIST", tlist1);

                        String tlist2 = "";
                        for(int i =0; i<wtlist.length;i++){
                            if(i == wtlist.length-1){
                                tlist2 += wtlist[i];
                            }
                            else{
                                tlist2 += wtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WTLIST", tlist2);


                        Toast.makeText(getApplicationContext(), "해당 의상의 " +
                                        "체감온도를 올렸습니다.",
                                Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();

                    }
                });

                TextView down = dialogView.findViewById(R.id.down);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] +" "+topcloth[3];옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                tempset[0] -= 1;
                                mtlist[0] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[0] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[0] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[0] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[0] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[18] -= 1;
                                wtlist[0] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[0] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[0] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[0] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[0] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                tempset[0] += 1;
                                tempset[1] -= 1;
                                mtlist[0] = mtlist[0] + " " + cloth;
                                mtlist[1] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[1] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[1] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[1] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[1] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[18] += 1;
                                tempset[19] -= 1;
                                wtlist[0] = wtlist[0] + " " + cloth;
                                wtlist[1] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[1] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[1] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[1] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[1] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                tempset[1] += 1;
                                tempset[2] -= 1;
                                mtlist[1] = mtlist[1] + " " + cloth;
                                mtlist[2] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[2] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[2] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[2] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[2] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[19] += 1;
                                tempset[20] -= 1;
                                wtlist[1] = wtlist[1] + " " + cloth;
                                wtlist[2] = "";
                                for(int j = 0 ; j< topcloth.length; j++) {
                                    if (i != topcloth.length - 1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[2] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[2] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[2] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[2] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                tempset[2] += 1;
                                tempset[3] -= 1;
                                mtlist[2] = mtlist[2] + " " + cloth;
                                mtlist[3] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[3] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[3] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[3] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[3] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[20] += 1;
                                tempset[21] -= 1;
                                wtlist[2] = wtlist[2] + " " + cloth;
                                wtlist[3] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[3] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[3] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[3] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[3] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                tempset[3] += 1;
                                tempset[4] -= 1;
                                mtlist[3] = mtlist[3] + " " + cloth;
                                mtlist[4] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[4] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[4] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[4] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[4] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[21] += 1;
                                tempset[22] -= 1;
                                wtlist[3] = wtlist[3] + " " + cloth;
                                wtlist[4] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[4] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[4] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[4] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[4] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                tempset[4] += 1;
                                tempset[5] -= 1;
                                mtlist[4] = mtlist[4] + " " + cloth;
                                mtlist[5] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[5] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[5] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[5] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[5] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[22] += 1;
                                tempset[23] -= 1;
                                wtlist[4] = wtlist[4] + " " + cloth;
                                wtlist[5] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[5] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[5] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[5] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[5] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                tempset[5] += 1;
                                tempset[6] -= 1;
                                mtlist[5] = mtlist[5] + " " + cloth;
                                mtlist[6] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[6] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[6] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[6] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[6] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[23] += 1;
                                tempset[24] -= 1;
                                wtlist[5] = wtlist[5] + " " + cloth;
                                wtlist[6] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[6] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[6] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[6] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[6] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                tempset[6] += 1;
                                tempset[7] -= 1;
                                mtlist[6] = mtlist[6] + " " + cloth;
                                mtlist[7] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[7] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[7] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[7] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[7] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[24] += 1;
                                tempset[25] -= 1;
                                wtlist[6] = wtlist[6] + " " + cloth;
                                wtlist[7] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[7] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[7] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[7] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[7] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        } else {
                            if (sets == 1) {
                                tempset[7] += 1;
                                tempset[8] -= 1;
                                mtlist[7] = mtlist[7] + " " + cloth;
                                mtlist[8] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            mtlist[8] += topcloth[j];
                                        } else if (j != i) {
                                            mtlist[8] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mtlist[8] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            mtlist[8] += topcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[25] += 1;
                                tempset[26] -= 1;
                                wtlist[6] = wtlist[6] + " " + cloth;
                                wtlist[8] = "";
                                for(int j = 0 ; j< topcloth.length; j++){
                                    if(i != topcloth.length-1) {
                                        if (j == topcloth.length - 1) {
                                            wtlist[8] += topcloth[j];
                                        } else if (j != i) {
                                            wtlist[8] += topcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wtlist[8] += topcloth[j] + " ";
                                        }else if(j == i-1){
                                            wtlist[8] += topcloth[j];
                                        }
                                    }
                                }
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);
                        String tlist1 = "";
                        for(int i =0; i<mtlist.length;i++){
                            if(i == mtlist.length-1){
                                tlist1 += mtlist[i];
                            }
                            else{
                                tlist1 += mtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MTLIST", tlist1);

                        String tlist2 = "";
                        for(int i =0; i<wtlist.length;i++){
                            if(i == wtlist.length-1){
                                tlist2 += wtlist[i];
                            }
                            else{
                                tlist2 += wtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WTLIST", tlist2);

                        if(sets == 1){
                            botcloth = null;
                            botcloth = mblist[0].split(" ");
                        }else{
                            botcloth = null;
                            botcloth = wblist[0].split(" ");
                        }

                        Toast.makeText(getApplicationContext(),
                                "해당 의상의 체감온도를 내렸습니다.", Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();

                    }
                });
                /*Intent intent = new Intent(this, PopupActivity.class);
                startActivityForResult(intent, 1);*/
            }
        });

        topImageView[i].setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothdelete,
                        null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                TextView ok = dialogView.findViewById(R.id.ok);
                ImageView cancel = dialogView.findViewById(R.id.deletecancle);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] +" "+topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                topdelete(i,0,0,topcloth.length);
                            } else {
                                topdelete(i,18,0,topcloth.length);
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                topdelete(i,1,1,topcloth.length);
                            } else {
                                topdelete(i,19,1,topcloth.length);
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                topdelete(i,2,2,topcloth.length);
                            } else {
                                topdelete(i,20,2,topcloth.length);
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                topdelete(i,3,3,topcloth.length);
                            } else {
                                topdelete(i,21,3,topcloth.length);
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                topdelete(i,4,4,topcloth.length);
                            } else {
                                topdelete(i,22,4,topcloth.length);
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                topdelete(i,5,5,topcloth.length);
                            } else {
                                topdelete(i,23,5,topcloth.length);
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                topdelete(i,6,6,topcloth.length);
                                System.out.println(mtlist[6]);
                            } else {
                                topdelete(i,24,6,topcloth.length);
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                topdelete(i,7,7,topcloth.length);
                            } else {
                                topdelete(i,25,7,topcloth.length);
                            }
                        } else {
                            if (sets == 1) {
                                topdelete(i,8,8,topcloth.length);
                            } else {
                                topdelete(i,26,8,topcloth.length);
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);

                        String tlist1 = "";
                        for(int i =0; i<mtlist.length;i++){
                            if(i == mtlist.length-1){
                                tlist1 += mtlist[i];
                            }
                            else{
                                tlist1 += mtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MTLIST", tlist1);

                        String tlist2 = "";
                        for(int i =0; i<wtlist.length;i++){
                            if(i == wtlist.length-1){
                                tlist2 += wtlist[i];
                            }
                            else{
                                tlist2 += wtlist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WTLIST", tlist2);

                        Toast.makeText(getApplicationContext(), "삭제했습니다.",
                                Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();

                    }
                });

                return true;
            }
        });

        topTextView[i].setTextSize(10);
        topTextView[i].setTextColor(Color.WHITE);
        topTextView[i].setText(cloth);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.bottomMargin = 10;
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        topTextView[i].setLayoutParams(lp1);
        linearLayoutTopV[i].addView(topImageView[i],180,180);
        linearLayoutTopV[i].addView(topTextView[i]);
        linearLayoutTopV[i].setMinimumWidth(100);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.rightMargin = 70;
        linearLayoutTopV[i].setLayoutParams(lp2);
        linearLayoutTop.addView(linearLayoutTopV[i]);
    } // 위쪽 의상 이미지, 텍스트 그려주는 함수

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
                botImageView[i].setImageResource(R.drawable.custom2);
                break;

        }

        botImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        botImageView[i].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothcontrol,
                        null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                ImageView cancel = dialogView.findViewById(R.id.cancle);
                ImageView Weather = dialogView.findViewById(R.id.Weather);
                TextView ctext = dialogView.findViewById(R.id.setctext);
                ctext.setText(cloth);
                switch (cloth) {
                    case "옷":
                        Weather.setImageResource(R.drawable.cloth);
                        break;
                    case "청바지":
                        Weather.setImageResource(R.drawable.cloth_jean);
                        break;
                    case "조거팬츠":
                        Weather.setImageResource(R.drawable.cloth_joggerpants);
                        break;
                    case "기모조거팬츠":
                        Weather.setImageResource(R.drawable.cloth_joggerpants);
                        break;
                    case "기모청바지":
                        Weather.setImageResource(R.drawable.cloth_kimojean);
                        break;
                    case "두꺼운스타킹":
                        Weather.setImageResource(R.drawable.cloth_kimostocking);
                        break;
                    case "레깅스":
                        Weather.setImageResource(R.drawable.cloth_leggings);
                        break;
                    case "긴치마":
                        Weather.setImageResource(R.drawable.cloth_longskirt);
                        break;
                    case "면바지(남)":
                        Weather.setImageResource(R.drawable.cloth_mancotton);
                        break;
                    case "칠부청바지":
                        Weather.setImageResource(R.drawable.cloth_middlejean);
                        break;
                    case "짧은청바지":
                        Weather.setImageResource(R.drawable.cloth_shortjean);
                        break;
                    case "테니스치마":
                        Weather.setImageResource(R.drawable.cloth_shortskirt);
                        break;
                    case "슬렉스":
                        Weather.setImageResource(R.drawable.cloth_slacks);
                        break;
                    case "기모슬렉스":
                        Weather.setImageResource(R.drawable.cloth_slacks);
                        break;
                    case "스타킹":
                        Weather.setImageResource(R.drawable.cloth_stocking);
                        break;
                    case "얇은청바지":
                        Weather.setImageResource(R.drawable.cloth_thinjean);
                        break;
                    case "면바지(여)":
                        Weather.setImageResource(R.drawable.cloth_womancotton);
                        break;
                    default:
                        Weather.setImageResource(R.drawable.custom2);
                        break;
                }
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                TextView up = dialogView.findViewById(R.id.up);
                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                tempset[10] += 1;
                                tempset[9] -= 1;
                                mblist[1] = mblist[1] + " " + cloth;
                                mblist[0] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[0] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[0] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[0] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[0] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[28] += 1;
                                tempset[27] -= 1;
                                wblist[1] = wblist[1] + " " + cloth;
                                wblist[0] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[0] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[0] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[0] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[0] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                tempset[11] += 1;
                                tempset[10] -= 1;
                                mblist[2] = mblist[2] + " " + cloth;
                                mblist[1] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[1] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[1] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[1] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[1] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[29] += 1;
                                tempset[28] -= 1;
                                wblist[2] = wblist[2] + " " + cloth;
                                wblist[1] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[1] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[1] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[1] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[1] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                tempset[12] += 1;
                                tempset[11] -= 1;
                                mblist[3] = mblist[3] + " " + cloth;
                                mblist[2] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[2] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[2] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[2] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[2] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[30] += 1;
                                tempset[29] -= 1;
                                wblist[3] = wblist[3] + " " + cloth;
                                wblist[2] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[2] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[2] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[2] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[2] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                tempset[13] += 1;
                                tempset[12] -= 1;
                                mblist[4] = mblist[4] + " " + cloth;
                                mblist[3] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[3] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[3] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[3] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[3] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[31] += 1;
                                tempset[30] -= 1;
                                wblist[4] = wblist[4] + " " + cloth;
                                wblist[3] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[3] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[3] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[3] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[3] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                tempset[14] += 1;
                                tempset[13] -= 1;
                                mblist[5] = mblist[5] + " " + cloth;
                                mblist[4] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[4] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[4] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[4] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[4] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[32] += 1;
                                tempset[31] -= 1;
                                wblist[5] = wblist[5] + " " + cloth;
                                wblist[4] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[4] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[4] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[4] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[4] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                tempset[15] += 1;
                                tempset[14] -= 1;
                                mblist[6] = mblist[6] + " " + cloth;
                                mblist[5] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[5] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[5] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[5] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[5] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[33] += 1;
                                tempset[32] -= 1;
                                wblist[6] = wblist[6] + " " + cloth;
                                wblist[5] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[5] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[5] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[5] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[5] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                tempset[16] += 1;
                                tempset[15] -= 1;
                                mblist[7] = mblist[7] + " " + cloth;
                                mblist[6] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[6] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[6] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[6] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[6] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[34] += 1;
                                tempset[33] -= 1;
                                wblist[7] = wblist[7] + " " + cloth;
                                wblist[6] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[6] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[6] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[6] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[6] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                tempset[17] += 1;
                                tempset[16] -= 1;
                                mblist[8] = mblist[8] + " " + cloth;
                                mblist[7] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[7] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[7] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[7] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[7] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[35] += 1;
                                tempset[34] -= 1;
                                wblist[8] = wblist[8] + " " + cloth;
                                wblist[7] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[7] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[7] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[7] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[7] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else {
                            if (sets == 1) {
                                tempset[17] -= 1;
                                mblist[8] = "";
                                for (int j = 0; j < botcloth.length; j++) {
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[8] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[8] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[8] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[8] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[35] -= 1;
                                wblist[8] = "";
                                for (int j = 0; j < botcloth.length; j++) {
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[8] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[8] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[8] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[8] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);

                        String tlist3 = "";
                        for(int i =0; i<mblist.length;i++){
                            if(i == mblist.length-1){
                                tlist3 += mblist[i];
                            }
                            else{
                                tlist3 += mblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MBLIST", tlist3);

                        String tlist4 = "";
                        for(int i =0; i<wblist.length;i++){
                            if(i == wblist.length-1){
                                tlist4 += wblist[i];
                            }
                            else{
                                tlist4 += wblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WBLIST", tlist4);

                        Toast.makeText(getApplicationContext(),
                                "해당 의상의 체감온도를 올렸습니다.", Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();

                    }
                });

                TextView down = dialogView.findViewById(R.id.down);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] +" "+topcloth[3];옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                tempset[9] -= 1;
                                mblist[0] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[0] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[0] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[0] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[0] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[27] -= 1;
                                wblist[0] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[0] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[0] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[0] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[0] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                tempset[9] += 1;
                                tempset[10] -= 1;
                                mblist[0] = mblist[0] + " " + cloth;
                                mblist[1] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[1] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[1] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[1] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[1] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[27] += 1;
                                tempset[28] -= 1;
                                wblist[0] = wblist[0] + " " + cloth;
                                wblist[1] = "";
                                for(int j = 0 ; j< botcloth.length; j++) {
                                    if (i != botcloth.length - 1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[1] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[1] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[1] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[1] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                tempset[10] += 1;
                                tempset[11] -= 1;
                                mblist[1] = mblist[1] + " " + cloth;
                                mblist[2] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[2] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[2] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[2] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[2] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[28] += 1;
                                tempset[29] -= 1;
                                wblist[1] = wblist[1] + " " + cloth;
                                wblist[2] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[2] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[2] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[2] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[2] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                tempset[11] += 1;
                                tempset[12] -= 1;
                                mblist[2] = mblist[2] + " " + cloth;
                                mblist[3] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[3] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[3] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[3] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[3] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[29] += 1;
                                tempset[30] -= 1;
                                wblist[2] = wblist[2] + " " + cloth;
                                wblist[3] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[3] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[3] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[3] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[3] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                tempset[12] += 1;
                                tempset[13] -= 1;
                                mblist[3] = mblist[3] + " " + cloth;
                                mblist[4] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[4] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[4] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[4] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[4] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[30] += 1;
                                tempset[31] -= 1;
                                wblist[3] = wblist[3] + " " + cloth;
                                wblist[4] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[4] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[4] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[4] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[4] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                tempset[13] += 1;
                                tempset[14] -= 1;
                                mblist[4] = mblist[4] + " " + cloth;
                                mblist[5] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[5] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[5] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[5] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[5] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[31] += 1;
                                tempset[32] -= 1;
                                wblist[4] = wblist[4] + " " + cloth;
                                wblist[5] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[5] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[5] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[5] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[5] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                tempset[14] += 1;
                                tempset[15] -= 1;
                                mblist[5] = mblist[5] + " " + cloth;
                                mblist[6] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[6] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[6] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[6] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[6] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[32] += 1;
                                tempset[33] -= 1;
                                wblist[5] = wblist[5] + " " + cloth;
                                wblist[6] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[6] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[6] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[6] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[6] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                tempset[15] += 1;
                                tempset[16] -= 1;
                                mblist[6] = mblist[6] + " " + cloth;
                                mblist[7] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[7] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[7] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[7] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[7] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[33] += 1;
                                tempset[34] -= 1;
                                wblist[6] = wblist[6] + " " + cloth;
                                wblist[7] = "";
                                for(int j = 0 ; j< botcloth.length; j++){
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[7] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[7] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[7] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[7] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        } else {
                            if (sets == 1) {
                                tempset[16] += 1;
                                tempset[17] -= 1;
                                mblist[7] = mblist[7] + " " + cloth;
                                mblist[8] = "";
                                for (int j = 0; j < botcloth.length; j++) {
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            mblist[8] += botcloth[j];
                                        } else if (j != i) {
                                            mblist[8] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            mblist[8] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            mblist[8] += botcloth[j];
                                        }
                                    }
                                }
                            } else {
                                tempset[34] += 1;
                                tempset[35] -= 1;
                                wblist[6] = wblist[6] + " " + cloth;
                                wblist[8] = "";
                                for (int j = 0; j < botcloth.length; j++) {
                                    if(i != botcloth.length-1) {
                                        if (j == botcloth.length - 1) {
                                            wblist[8] += botcloth[j];
                                        } else if (j != i) {
                                            wblist[8] += botcloth[j] + " ";
                                        }
                                    }else{
                                        if (j < i-1) {
                                            wblist[8] += botcloth[j] + " ";
                                        }else if(j == i-1){
                                            wblist[8] += botcloth[j];
                                        }
                                    }
                                }
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);

                        String tlist3 = "";
                        for(int i =0; i<mblist.length;i++){
                            if(i == mblist.length-1){
                                tlist3 += mblist[i];
                            }
                            else{
                                tlist3 += mblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MBLIST", tlist3);

                        String tlist4 = "";
                        for(int i =0; i<wblist.length;i++){
                            if(i == wblist.length-1){
                                tlist4 += wblist[i];
                            }
                            else{
                                tlist4 += wblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WBLIST", tlist4);

                        Toast.makeText(getApplicationContext(),
                                "해당 의상의 체감온도를 내렸습니다.", Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();
                    }
                });


                /*Intent intent = new Intent(this, PopupActivity.class);
                startActivityForResult(intent, 1);*/
            }
        });
        botImageView[i].setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothdelete,
                        null);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(dialogView);

                final AlertDialog alertDialog = builder.create();
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                alertDialog.show();
                TextView ok = dialogView.findViewById(R.id.ok);
                ImageView cancel = dialogView.findViewById(R.id.deletecancle);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alertDialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] +" " +topcloth[3];옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                botdelete(i,9,0,botcloth.length);
                            } else {
                                botdelete(i,27,0,botcloth.length);
                            }
                        } else if (ShareT <= 8 && ShareT > 4) {
                            if (sets == 1) {
                                botdelete(i,10,1,botcloth.length);
                            } else {
                                botdelete(i,28,1,botcloth.length);
                            }
                        } else if (ShareT <= 12 && ShareT > 8) {
                            if (sets == 1) {
                                botdelete(i,11,2,botcloth.length);
                            } else {
                                botdelete(i,29,2,botcloth.length);
                            }
                        } else if (ShareT <= 16 && ShareT > 12) {
                            if (sets == 1) {
                                botdelete(i,12,3,botcloth.length);
                            } else {
                                botdelete(i,30,3,botcloth.length);
                            }
                        } else if (ShareT <= 19 && ShareT > 16) {
                            if (sets == 1) {
                                botdelete(i,13,4,botcloth.length);
                            } else {
                                botdelete(i,31,4,botcloth.length);
                            }
                        } else if (ShareT <= 22 && ShareT > 19) {
                            if (sets == 1) {
                                botdelete(i,14,5,botcloth.length);
                            } else {
                                botdelete(i,32,5,botcloth.length);
                            }
                        } else if (ShareT <= 24 && ShareT > 22) {
                            if (sets == 1) {
                                botdelete(i,15,6,botcloth.length);
                            } else {
                                botdelete(i,33,6,botcloth.length);
                            }
                        } else if (ShareT <= 29 && ShareT > 24) {
                            if (sets == 1) {
                                botdelete(i,16,7,botcloth.length);
                            } else {
                                botdelete(i,34,7,botcloth.length);
                            }
                        } else {
                            if (sets == 1) {
                                botdelete(i,17,8,botcloth.length);
                            } else {
                                botdelete(i,35,8,botcloth.length);
                            }
                        }

                        for(int i = 0; i<tempdata.length; i++){
                            tempdata[i] = Integer.toString(tempset[i]);
                        }
                        String text = "";
                        for(int i = 0; i<tempdata.length; i++){
                            if(i == tempdata.length-1){
                                text += tempdata[i];
                            }
                            else{
                                text += tempdata[i] + " ";
                            }

                        }
                        PreferenceManager.setString(mContext, "CLOTHSET", text);

                        String tlist3 = "";
                        for(int i =0; i<mblist.length;i++){
                            if(i == mblist.length-1){
                                tlist3 += mblist[i];
                            }
                            else{
                                tlist3 += mblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "MBLIST", tlist3);

                        String tlist4 = "";
                        for(int i =0; i<wblist.length;i++){
                            if(i == wblist.length-1){
                                tlist4 += wblist[i];
                            }
                            else{
                                tlist4 += wblist[i] + "  ";
                            }
                        }
                        PreferenceManager.setString(mContext, "WBLIST", tlist4);

                        Toast.makeText(getApplicationContext(), "삭제했습니다.",
                                Toast.LENGTH_LONG).show();

                        drawCloth();
                        alertDialog.dismiss();

                    }
                });

                return true;
            }
        });
        botTextView[i].setTextSize(10);
        botTextView[i].setTextColor(Color.WHITE);
        botTextView[i].setText(cloth);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.bottomMargin = 10;
        lp1.gravity = Gravity.CENTER_HORIZONTAL;
        botTextView[i].setLayoutParams(lp1);
        linearLayoutBotV[i].addView(botImageView[i],180,180);
        linearLayoutBotV[i].addView(botTextView[i]);
        linearLayoutBotV[i].setMinimumWidth(100);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.rightMargin = 70;
        linearLayoutBotV[i].setLayoutParams(lp2);
        linearLayoutBottom.addView(linearLayoutBotV[i]);
    } // 아래쪽 의상 이미지, 텍스트 그려주는 함수

    public void drawCloth(){
        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);
        linearLayoutTop.removeAllViews();
        linearLayoutBottom.removeAllViews();
        if(sets == 1) {
            if (ShareT <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                topcloth = mtlist[0].split(" ");
                for (int i = 0; i < tempset[0]; i++) {
                    setCloth_image_top(tempset[0], i, topcloth[i]);
                }
                botcloth = mblist[0].split(" ");
                for (int i = 0; i < tempset[9]; i++) {
                    setCloth_image_bot(tempset[9], i, botcloth[i]);
                }
            } else if (ShareT <= 8 && ShareT > 4) {
                topcloth = mtlist[1].split(" ");
                for (int i = 0; i < tempset[1]; i++) {
                    setCloth_image_top(tempset[1], i, topcloth[i]);
                }
                botcloth = mblist[1].split(" ");
                for (int i = 0; i < tempset[10]; i++) {
                    setCloth_image_bot(tempset[10], i, botcloth[i]);
                }
            } else if (ShareT <= 12 && ShareT > 8) {
                topcloth = mtlist[2].split(" ");
                for (int i = 0; i < tempset[2]; i++) {
                    setCloth_image_top(tempset[2], i, topcloth[i]);
                }
                botcloth = mblist[2].split(" ");
                for (int i = 0; i < tempset[11]; i++) {
                    setCloth_image_bot(tempset[11], i, botcloth[i]);
                }
            } else if (ShareT <= 16 && ShareT > 12) {
                topcloth = mtlist[3].split(" ");
                for (int i = 0; i < tempset[3]; i++) {
                    setCloth_image_top(tempset[3], i, topcloth[i]);
                }
                botcloth = mblist[3].split(" ");
                for (int i = 0; i < tempset[12]; i++) {
                    setCloth_image_bot(tempset[12], i, botcloth[i]);
                }
            } else if (ShareT <= 19 && ShareT > 16) {
                topcloth = mtlist[4].split(" ");
                for (int i = 0; i < tempset[4]; i++) {
                    setCloth_image_top(tempset[4], i, topcloth[i]);
                }
                botcloth = mblist[4].split(" ");
                for (int i = 0; i < tempset[13]; i++) {
                    setCloth_image_bot(tempset[13], i, botcloth[i]);
                }
            } else if (ShareT <= 22 && ShareT > 19) {
                topcloth = mtlist[5].split(" ");
                for (int i = 0; i < tempset[5]; i++) {
                    setCloth_image_top(tempset[5], i, topcloth[i]);
                }
                botcloth = mblist[5].split(" ");
                for (int i = 0; i < tempset[14]; i++) {
                    setCloth_image_bot(tempset[14], i, botcloth[i]);
                }
            } else if (ShareT <= 24 && ShareT > 22) {
                topcloth = mtlist[6].split(" ");
                for (int i = 0; i < tempset[6]; i++) {
                    setCloth_image_top(tempset[6], i, topcloth[i]);
                }
                botcloth = mblist[6].split(" ");
                for (int i = 0; i < tempset[15]; i++) {
                    setCloth_image_bot(tempset[15], i, botcloth[i]);
                }
            } else if (ShareT <= 29 && ShareT > 24) {
                topcloth = mtlist[7].split(" ");
                for (int i = 0; i < tempset[7]; i++) {
                    setCloth_image_top(tempset[7], i, topcloth[i]);
                }
                botcloth = mblist[7].split(" ");
                for (int i = 0; i < tempset[16]; i++) {
                    setCloth_image_bot(tempset[16], i, botcloth[i]);
                }
            } else {
                topcloth = mtlist[8].split(" ");
                for (int i = 0; i < tempset[8]; i++) {
                    setCloth_image_top(tempset[8], i, topcloth[i]);
                }
                botcloth = mblist[8].split(" ");
                for (int i = 0; i < tempset[17]; i++) {
                    setCloth_image_bot(tempset[17], i, botcloth[i]);
                }
            }
        }else{
            if(ShareT <= 4){
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
            }else if(ShareT<=8 && ShareT > 4){
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
            }else if(ShareT<=11 && ShareT > 8){
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
            }else if(ShareT<=16 && ShareT > 11){
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
            }else if(ShareT<=19 && ShareT > 16){
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
            }else if(ShareT<=22 && ShareT > 19){
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
            }else if(ShareT<=24 && ShareT > 22){
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
            }else if(ShareT<=29 && ShareT > 24){
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
    }  // 의상을 그려주는 함수 setCloth_image_ 함수들을 호출함

    public void botdelete(int i, int temp, int list, int length){
        if (sets == 1) {
            tempset[temp] -= 1;
            mblist[list] = "";
            for(int j = 0 ; j< length; j++){
                if(i != length-1) {
                    if (j == length - 1) {
                        mblist[list] += botcloth[j];
                    } else if (j != i) {
                        mblist[list] += botcloth[j] + " ";
                    }
                }else{
                    if (j < i-1) {
                        mblist[list] += botcloth[j] + " ";
                    }else if(j == i-1){
                        mblist[list] += botcloth[j];
                    }
                }
            }
        } else {
            tempset[temp] -= 1;
            wblist[list] = "";
            for(int j = 0 ; j< length; j++){
                if(i != length-1) {
                    if (j == length - 1) {
                        wblist[list] += botcloth[j];
                    } else if (j != i) {
                        wblist[list] += botcloth[j] + " ";
                    }
                }else{
                    if (j < i-1) {
                        wblist[list] += botcloth[j] + " ";
                    }else if(j == i-1){
                        wblist[list] += botcloth[j];
                    }
                }
            }
        }
    } // 아래 의상 삭제시 호출

    public void topdelete(int i, int temp, int list, int length){
        if (sets == 1) {
            tempset[temp] -= 1;
            mtlist[list] = "";
            for(int j = 0 ; j< length; j++){
                if(i != length-1) {
                    if (j == length - 1) {
                        mtlist[list] += topcloth[j];
                    } else if (j != i) {
                        mtlist[list] += topcloth[j] + " ";
                    }
                }else{
                    if (j < i-1) {
                        mtlist[list] += topcloth[j] + " ";
                    }else if(j == i-1){
                        mtlist[list] += topcloth[j];
                    }
                }
            }
        } else {
            tempset[temp] -= 1;
            wtlist[list] = "";
            for(int j = 0 ; j< length; j++){
                if(i != length-1) {
                    if (j == length - 1) {
                        wtlist[list] += topcloth[j];
                    } else if (j != i) {
                        wtlist[list] += topcloth[j] + " ";
                    }
                }else{
                    if (j < i-1) {
                        wtlist[list] += topcloth[j] + " ";
                    }else if(j == i-1){
                        wtlist[list] += topcloth[j];
                    }
                }
            }
        }
    } // 위쪽 의상 삭제시 호출
}
