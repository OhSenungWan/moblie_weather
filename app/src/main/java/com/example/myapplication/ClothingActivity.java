package com.example.myapplication;

import android.app.Activity;
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
import com.example.myapplication.setdata.set_data_cloth;
import com.example.myapplication.setdata.data_short;
import com.example.myapplication.setdata.set_weather;
public class ClothingActivity extends Activity {

    final int man04top = 7;
    final int man04bot = 2;
    final int woman04top = 7;
    final int woman04bot = 3;

    final int man58top = 7;
    final int man58bot = 3;
    final int woman58top = 7;
    final int woman58bot = 5;

    final int man911top = 7;
    final int man911bot = 4;
    final int woman911top = 7;
    final int woman911bot = 6;

    final int man1216top = 7;
    final int man1216bot = 3;
    final int woman1216top = 7;
    final int woman1216bot = 4;

    final int man1719top = 7;
    final int man1719bot = 3;
    final int woman1719top = 6;
    final int woman1719bot = 5;

    final int man2022top = 5;
    final int man2022bot = 3;
    final int woman2022top = 5;
    final int woman2022bot = 6;

    final int man23top = 4;
    final int man23bot = 3;
    final int woman23top = 4;
    final int woman23bot = 5;

    LinearLayout BackGround;
    int back;
    RadioGroup RG;
    RadioButton Rman, Rwoman;
    Button btn;
    TextView pmch, winch, tempch, rainch;
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
                set_weather sw = new set_weather();
                set_data_cloth sdc = new set_data_cloth();
                BackGround = (LinearLayout)findViewById(R.id.background);
                pmch = (TextView)findViewById(R.id.daycheckpm10);
                winch = (TextView)findViewById(R.id.daycheckwind);
                tempch = (TextView)findViewById(R.id.daychecktemp);
                rainch = (TextView)findViewById(R.id.daycheckrain);
                RG = (RadioGroup)findViewById(R.id.RG);
                Rman = (RadioButton)findViewById(R.id.RBman);
                Rwoman = (RadioButton)findViewById(R.id.RBwoman);
                btn = (Button)findViewById(R.id.cchbtn);
                back = sw.set_background();
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
                        switch (back) {
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
                        btn.setOnClickListener(new View.OnClickListener(){
                            public void onClick(View v){
                                LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
                                LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);
                                linearLayoutTop.removeAllViews();
                                linearLayoutBottom.removeAllViews();
                                if(finalT <= 4){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(7,0,"히트텍", "cloth_hitec");
                                            setCloth_image_top(7,1,"기모 후드", "cloth_hood");
                                            setCloth_image_top(7,2,"롱패딩", "cloth_longpadding");
                                            setCloth_image_top(7,3,"기모 코트", "cloth_manlongcoat1");
                                            setCloth_image_top(7,4,"기모 맨투맨", "cloth_kimomtm1");
                                            setCloth_image_top(7,5,"오리털 패딩", "cloth_padding2");
                                            setCloth_image_top(7,6,"스웨터", "cloth_sweater");

                                            setCloth_image_bot(2,0,"기모 조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(2,1,"기모청바지", "cloth_kimojean");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(7,0,"히트텍", "cloth_hitec");
                                            setCloth_image_top(7,1,"기모 후드", "cloth_hood");
                                            setCloth_image_top(7,2,"기모 코트", "cloth_longcoat1");
                                            setCloth_image_top(7,3,"롱패딩", "cloth_longpadding");
                                            setCloth_image_top(7,4,"기모 맨투맨", "cloth_kimomtm1");
                                            setCloth_image_top(7,5,"오리털 패딩", "cloth_padding1");
                                            setCloth_image_top(7,6,"스웨터", "cloth_sweater");

                                            setCloth_image_bot(3,0,"기모 조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(3,1,"기모청바지", "cloth_kimojean");
                                            setCloth_image_bot(3,2,"기모스타킹", "cloth_kimostocking");


                                            break;
                                    }
                                }else if(finalT<=8 && finalT > 4){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(7,0,"히트텍", "cloth_hitec");
                                            setCloth_image_top(7,1,"기모 후드", "cloth_hood");
                                            setCloth_image_top(7,2,"모직 코트", "cloth_manlongcoat1");
                                            setCloth_image_top(7,3,"기모 맨투맨", "cloth_kimomtm1");
                                            setCloth_image_top(7,4,"숏패딩", "cloth_padding2");
                                            setCloth_image_top(7,5,"두꺼운 니트", "cloth_neat");
                                            setCloth_image_top(7,6,"가죽자켓", "cloth_leatherjacket");

                                            setCloth_image_bot(3,0,"기모 조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(3,1,"기모 청바지", "cloth_kimojean");
                                            setCloth_image_bot(3,2,"기모 슬렉스", "cloth_slacks");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(7,0,"히트텍", "cloth_hitec");
                                            setCloth_image_top(7,1,"기모 후드", "cloth_hood");
                                            setCloth_image_top(7,2,"모직 코트", "cloth_womanlongcoat2");
                                            setCloth_image_top(7,3,"기모 맨투맨", "cloth_kimomtm1");
                                            setCloth_image_top(7,4,"숏패딩", "cloth_padding1");
                                            setCloth_image_top(7,5,"두꺼운 니트", "cloth_neat");
                                            setCloth_image_top(7,6,"가죽 자켓", "cloth_leatherjacket");

                                            setCloth_image_bot(5,0,"기모 조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(5,1,"기모 청바지", "cloth_kimojean");
                                            setCloth_image_bot(5,2,"두꺼운 스타킹", "cloth_kimostocking");
                                            setCloth_image_bot(5,3,"레깅스", "cloth_leggings");
                                            setCloth_image_bot(5,4,"기모 슬렉스", "cloth_slacks");
                                            break;
                                    }

                                }else if(finalT<=11 && finalT > 8){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(7,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(7,1,"후드", "cloth_hood");
                                            setCloth_image_top(7,2,"맨투맨", "cloth_mtm3");
                                            setCloth_image_top(7,3,"니트", "cloth_neat");
                                            setCloth_image_top(7,4,"가죽자켓", "cloth_leatherjacket");
                                            setCloth_image_top(7,5,"패딩조끼", "cloth_paddingvest");
                                            setCloth_image_top(7,6,"트렌치코트", "cloth_trenchcoat");

                                            setCloth_image_bot(4,0,"조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(4,1,"청바지", "cloth_jean");
                                            setCloth_image_bot(4,2,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(4,3,"면바지", "cloth_mancotton");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(7,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(7,1,"후드", "cloth_hood");
                                            setCloth_image_top(7,2,"맨투맨", "cloth_mtm3");
                                            setCloth_image_top(7,3,"니트", "cloth_neat");
                                            setCloth_image_top(7,4,"가죽자켓", "cloth_leatherjacket");
                                            setCloth_image_top(7,5,"패딩조끼", "cloth_paddingvest");
                                            setCloth_image_top(7,6,"트렌치코트", "cloth_trenchcoat");

                                            setCloth_image_bot(6,0,"조거팬츠", "cloth_joggerpants");
                                            setCloth_image_bot(6,1,"청바지", "cloth_jean");
                                            setCloth_image_bot(6,2,"스타킹", "cloth_stocking");
                                            setCloth_image_bot(6,3,"레깅스", "cloth_leggings");
                                            setCloth_image_bot(6,4,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(6,5,"면바지", "cloth_womancotton");

                                            break;
                                    }

                                }else if(finalT<=16 && finalT > 11){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(7,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(7,1,"후드", "cloth_hood");
                                            setCloth_image_top(7,2,"맨투맨", "cloth_mtm3");
                                            setCloth_image_top(7,3,"패딩", "cloth_paddingvest");
                                            setCloth_image_top(7,4,"청자켓", "cloth_jeanjacket");
                                            setCloth_image_top(7,5,"가죽자켓", "cloth_leatherjacket");
                                            setCloth_image_top(7,6,"가디건", "cloth_cardigun");

                                            setCloth_image_bot(3,0,"청바지", "cloth_jean");
                                            setCloth_image_bot(3,1,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(3,2,"면바지", "cloth_mancotton");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(7,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(7,1,"후드", "cloth_hood");
                                            setCloth_image_top(7,2,"맨투맨", "cloth_mtm3");
                                            setCloth_image_top(7,3,"패딩조끼", "cloth_paddingvest");
                                            setCloth_image_top(7,4,"청자켓", "cloth_jeanjacket");
                                            setCloth_image_top(7,5,"가죽자켓", "cloth_leatherjacket");
                                            setCloth_image_top(7,6,"가디건", "cloth_cardigun");

                                            setCloth_image_bot(4,0,"청바지", "cloth_jean");
                                            setCloth_image_bot(4,1,"스타킹", "cloth_stocking");
                                            setCloth_image_bot(4,2,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(4,3,"면바지", "cloth_womancotton");
                                            break;
                                    }

                                }else if(finalT<=19 && finalT > 16){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(7,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(7,1,"후드", "cloth_hood");
                                            setCloth_image_top(7,2,"얇은 맨투맨", "cloth_mtm4");
                                            setCloth_image_top(7,3,"블레이저", "cloth_manblazer");
                                            setCloth_image_top(7,4,"얇은 가디건", "cloth_cardigun");
                                            setCloth_image_top(7,5,"두꺼운 티셔츠", "cloth_tshirts");
                                            setCloth_image_top(7,6,"셔츠", "cloth_longshirts");

                                            setCloth_image_bot(3,0,"청바지", "cloth_jean");
                                            setCloth_image_bot(3,1,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(3,2,"면바지", "cloth_mancotton");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(6,0,"점퍼", "cloth_ma1");
                                            setCloth_image_top(6,1,"후드", "cloth_hood");
                                            setCloth_image_top(6,2,"얇은 맨투맨", "cloth_mtm4");
                                            setCloth_image_top(6,3,"얇은 가디건", "cloth_cardigun");
                                            setCloth_image_top(6,4,"두꺼운 티셔츠", "cloth_tshirts");
                                            setCloth_image_top(6,5,"셔츠", "cloth_longshirts");

                                            setCloth_image_bot(5,0,"", "cloth_jean");
                                            setCloth_image_bot(5,1,"", "cloth_stocking");
                                            setCloth_image_bot(5,2,"", "cloth_slacks");
                                            setCloth_image_bot(5,3,"", "cloth_womancotton");
                                            setCloth_image_bot(5,4,"", "cloth_longskirt");
                                            break;
                                    }

                                }else if(finalT<=22 && finalT > 19){
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(5,0,"얇은 맨투맨", "cloth_mtm4");
                                            setCloth_image_top(5,1,"블레이저", "cloth_manblazer");
                                            setCloth_image_top(5,2,"티셔츠", "cloth_tshirts");
                                            setCloth_image_top(5,3,"칠부 티셔츠", "cloth_midlesleeve");
                                            setCloth_image_top(5,4,"셔츠", "cloth_longshirts");

                                            setCloth_image_bot(3,0,"얇은 청바지", "cloth_thinjean");
                                            setCloth_image_bot(3,1,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(3,2,"면바지", "cloth_mancotton");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(5,0,"얇은 맨투맨", "cloth_mtm4");
                                            setCloth_image_top(5,1,"티셔츠", "cloth_tshirts");
                                            setCloth_image_top(5,2,"셔츠", "cloth_longshirts");
                                            setCloth_image_top(5,3,"칠부 티셔츠", "cloth_midlesleeve");
                                            setCloth_image_top(5,4,"긴 원피스", "cloth_onepeicedress");

                                            setCloth_image_bot(6,0,"청바지", "cloth_jean");
                                            setCloth_image_bot(6,1,"스타킹", "cloth_stocking");
                                            setCloth_image_bot(6,2,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(6,3,"면바지", "cloth_womancotton");
                                            setCloth_image_bot(6,4,"긴 치마", "cloth_longskirt");
                                            setCloth_image_bot(6,5,"테니스 치마", "cloth_shortskirt");
                                            break;

                                    }

                                }else{
                                    switch (RG.getCheckedRadioButtonId()){
                                        case R.id.RBman:
                                            setCloth_image_top(4,0,"칠부 티셔츠", "cloth_midlesleeve");
                                            setCloth_image_top(4,1,"반팔티", "cloth_shortsleeve");
                                            setCloth_image_top(4,2,"반팔셔츠", "cloth_shortshirts");
                                            setCloth_image_top(4,3,"나시", "cloth_nasi");

                                            setCloth_image_bot(3,0,"얇은 청바지", "cloth_thinjean");
                                            setCloth_image_bot(3,1,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(3,2,"칠부 청바지", "cloth_middlejean");
                                            break;
                                        case R.id.RBwoman:
                                            setCloth_image_top(4,0,"칠부 티셔츠", "cloth_midlesleeve");
                                            setCloth_image_top(4,1,"원피스", "cloth_onepeicedress");
                                            setCloth_image_top(4,2,"반팔티", "cloth_shortsleeve");
                                            setCloth_image_top(4,3,"반팔셔츠", "cloth_shortshirts");

                                            setCloth_image_bot(5,0,"얇은 청바지", "cloth_thinjean");
                                            setCloth_image_bot(5,1,"슬렉스", "cloth_slacks");
                                            setCloth_image_bot(5,2,"테니스 치마", "cloth_shortskirt");
                                            setCloth_image_bot(5,3,"칠부 청바지", "cloth_middlejean");
                                            setCloth_image_bot(5,4,"짧은 청바지", "cloth_shortjean");
                                            break;
                                    }
                                }
                            }
                        });
                    }
                });
            }
        }).start();
   }
    public void setCloth_image_top(int max, int i, String cloth, String clothimg){
        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
        LinearLayout[] linearLayoutTopV = new LinearLayout[max];
        TextView[] topTextView = new TextView[max];
        ImageView[] topImageView = new ImageView[max];

        linearLayoutTopV[i] = new LinearLayout(this);
        topTextView[i] = new TextView(this);
        topImageView[i] = new ImageView(this);

        linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);
        switch (clothimg) {
            case "cloth":
                topImageView[i].setImageResource(R.drawable.cloth);
                break;
            case "cloth_cardigun":
                topImageView[i].setImageResource(R.drawable.cloth_cardigun);
                break;
            case "cloth_hitec":
                topImageView[i].setImageResource(R.drawable.cloth_hitec);
                break;
            case "cloth_hood":
                topImageView[i].setImageResource(R.drawable.cloth_hood);
                break;
            case "cloth_jean":
                topImageView[i].setImageResource(R.drawable.cloth_jean);
                break;
            case "cloth_jeanjacket":
                topImageView[i].setImageResource(R.drawable.cloth_jeanjacket);
                break;
            case "cloth_joggerpants":
                topImageView[i].setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "cloth_kimojean":
                topImageView[i].setImageResource(R.drawable.cloth_kimojean);
                break;
            case "cloth_kimomtm1":
                topImageView[i].setImageResource(R.drawable.cloth_kimomtm1);
                break;
            case "cloth_kimostocking":
                topImageView[i].setImageResource(R.drawable.cloth_kimostocking);
                break;
            case "cloth_leatherjacket":
                topImageView[i].setImageResource(R.drawable.cloth_leatherjacket);
                break;
            case "cloth_leggings":
                topImageView[i].setImageResource(R.drawable.cloth_leggings);
                break;
            case "cloth_longpadding":
                topImageView[i].setImageResource(R.drawable.cloth_longpadding);
                break;
            case "cloth_longshirts":
                topImageView[i].setImageResource(R.drawable.cloth_longshirts);
                break;
            case "cloth_longskirt":
                topImageView[i].setImageResource(R.drawable.cloth_longskirt);
                break;
            case "cloth_ma1":
                topImageView[i].setImageResource(R.drawable.cloth_ma1);
                break;
            case "cloth_manblazer":
                topImageView[i].setImageResource(R.drawable.cloth_manblazer);
                break;
            case "cloth_mancotton":
                topImageView[i].setImageResource(R.drawable.cloth_mancotton);
                break;
            case "cloth_manlongcoat1":
                topImageView[i].setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "cloth_middlejean":
                topImageView[i].setImageResource(R.drawable.cloth_middlejean);
                break;
            case "cloth_midlesleeve":
                topImageView[i].setImageResource(R.drawable.cloth_midlesleeve);
                break;
            case "cloth_mtm3":
                topImageView[i].setImageResource(R.drawable.cloth_mtm3);
                break;
            case "cloth_mtm4":
                topImageView[i].setImageResource(R.drawable.cloth_mtm4);
                break;
            case "cloth_nasi":
                topImageView[i].setImageResource(R.drawable.cloth_nasi);
                break;
            case "cloth_neat":
                topImageView[i].setImageResource(R.drawable.cloth_neat);
                break;
            case "cloth_onepeicedress":
                topImageView[i].setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "cloth_padding1":
                topImageView[i].setImageResource(R.drawable.cloth_padding1);
                break;
            case "cloth_padding2":
                topImageView[i].setImageResource(R.drawable.cloth_padding2);
                break;
            case "cloth_paddingvest":
                topImageView[i].setImageResource(R.drawable.cloth_paddingvest);
                break;
            case "cloth_shirts":
                topImageView[i].setImageResource(R.drawable.cloth_shirts);
                break;
            case "cloth_shortjean":
                topImageView[i].setImageResource(R.drawable.cloth_shortjean);
                break;
            case "cloth_shortshirts":
                topImageView[i].setImageResource(R.drawable.cloth_shortshirts);
                break;
            case "cloth_shortskirt":
                topImageView[i].setImageResource(R.drawable.cloth_shortskirt);
                break;
            case "cloth_shortsleeve":
                topImageView[i].setImageResource(R.drawable.cloth_shortsleeve);
                break;
            case "cloth_slacks":
                topImageView[i].setImageResource(R.drawable.cloth_slacks);
                break;
            case "cloth_stocking":
                topImageView[i].setImageResource(R.drawable.cloth_stocking);
                break;
            case "cloth_sweater":
                topImageView[i].setImageResource(R.drawable.cloth_sweater);
                break;
            case "cloth_thinjean":
                topImageView[i].setImageResource(R.drawable.cloth_thinjean);
                break;
            case "cloth_trenchcoat":
                topImageView[i].setImageResource(R.drawable.cloth_trenchcoat);
                break;
            case "cloth_tshirts":
                topImageView[i].setImageResource(R.drawable.cloth_tshirts);
                break;
            case "cloth_womancotton":
                topImageView[i].setImageResource(R.drawable.cloth_womancotton);
                break;
            case "cloth_womanlongcoat1":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat1);
                break;
            case "cloth_womanlongcoat2":
                topImageView[i].setImageResource(R.drawable.cloth_womanlongcoat2);
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
    public void setCloth_image_bot(int max, int i, String cloth, String clothimg){
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);

        LinearLayout[] linearLayoutBotV = new LinearLayout[max];
        TextView[] botTextView = new TextView[max];
        ImageView[] botImageView = new ImageView[max];

        linearLayoutBotV[i] = new LinearLayout(this);
        botTextView[i] = new TextView(this);
        botImageView[i] = new ImageView(this);

        linearLayoutBotV[i].setOrientation(LinearLayout.VERTICAL);
        switch (clothimg) {
            case "cloth":
                botImageView[i].setImageResource(R.drawable.cloth);
                break;
            case "cloth_cardigun":
                botImageView[i].setImageResource(R.drawable.cloth_cardigun);
                break;
            case "cloth_hitec":
                botImageView[i].setImageResource(R.drawable.cloth_hitec);
                break;
            case "cloth_hood":
                botImageView[i].setImageResource(R.drawable.cloth_hood);
                break;
            case "cloth_jean":
                botImageView[i].setImageResource(R.drawable.cloth_jean);
                break;
            case "cloth_jeanjacket":
                botImageView[i].setImageResource(R.drawable.cloth_jeanjacket);
                break;
            case "cloth_joggerpants":
                botImageView[i].setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "cloth_kimojean":
                botImageView[i].setImageResource(R.drawable.cloth_kimojean);
                break;
            case "cloth_kimomtm1":
                botImageView[i].setImageResource(R.drawable.cloth_kimomtm1);
                break;
            case "cloth_kimostocking":
                botImageView[i].setImageResource(R.drawable.cloth_kimostocking);
                break;
            case "cloth_leatherjacket":
                botImageView[i].setImageResource(R.drawable.cloth_leatherjacket);
                break;
            case "cloth_leggings":
                botImageView[i].setImageResource(R.drawable.cloth_leggings);
                break;
            case "cloth_longpadding":
                botImageView[i].setImageResource(R.drawable.cloth_longpadding);
                break;
            case "cloth_longshirts":
                botImageView[i].setImageResource(R.drawable.cloth_longshirts);
                break;
            case "cloth_longskirt":
                botImageView[i].setImageResource(R.drawable.cloth_longskirt);
                break;
            case "cloth_ma1":
                botImageView[i].setImageResource(R.drawable.cloth_ma1);
                break;
            case "cloth_manblazer":
                botImageView[i].setImageResource(R.drawable.cloth_manblazer);
                break;
            case "cloth_mancotton":
                botImageView[i].setImageResource(R.drawable.cloth_mancotton);
                break;
            case "cloth_manlongcoat1":
                botImageView[i].setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "cloth_middlejean":
                botImageView[i].setImageResource(R.drawable.cloth_middlejean);
                break;
            case "cloth_midlesleeve":
                botImageView[i].setImageResource(R.drawable.cloth_midlesleeve);
                break;
            case "cloth_mtm3":
                botImageView[i].setImageResource(R.drawable.cloth_mtm3);
                break;
            case "cloth_mtm4":
                botImageView[i].setImageResource(R.drawable.cloth_mtm4);
                break;
            case "cloth_nasi":
                botImageView[i].setImageResource(R.drawable.cloth_nasi);
                break;
            case "cloth_neat":
                botImageView[i].setImageResource(R.drawable.cloth_neat);
                break;
            case "cloth_onepeicedress":
                botImageView[i].setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "cloth_padding1":
                botImageView[i].setImageResource(R.drawable.cloth_padding1);
                break;
            case "cloth_padding2":
                botImageView[i].setImageResource(R.drawable.cloth_padding2);
                break;
            case "cloth_paddingvest":
                botImageView[i].setImageResource(R.drawable.cloth_paddingvest);
                break;
            case "cloth_shirts":
                botImageView[i].setImageResource(R.drawable.cloth_shirts);
                break;
            case "cloth_shortjean":
                botImageView[i].setImageResource(R.drawable.cloth_shortjean);
                break;
            case "cloth_shortshirts":
                botImageView[i].setImageResource(R.drawable.cloth_shortshirts);
                break;
            case "cloth_shortskirt":
                botImageView[i].setImageResource(R.drawable.cloth_shortskirt);
                break;
            case "cloth_shortsleeve":
                botImageView[i].setImageResource(R.drawable.cloth_shortsleeve);
                break;
            case "cloth_slacks":
                botImageView[i].setImageResource(R.drawable.cloth_slacks);
                break;
            case "cloth_stocking":
                botImageView[i].setImageResource(R.drawable.cloth_stocking);
                break;
            case "cloth_sweater":
                botImageView[i].setImageResource(R.drawable.cloth_sweater);
                break;
            case "cloth_thinjean":
                botImageView[i].setImageResource(R.drawable.cloth_thinjean);
                break;
            case "cloth_trenchcoat":
                botImageView[i].setImageResource(R.drawable.cloth_trenchcoat);
                break;
            case "cloth_tshirts":
                botImageView[i].setImageResource(R.drawable.cloth_tshirts);
                break;
            case "cloth_womancotton":
                botImageView[i].setImageResource(R.drawable.cloth_womancotton);
                break;
            case "cloth_womanlongcoat1":
                botImageView[i].setImageResource(R.drawable.cloth_womanlongcoat1);
                break;
            case "cloth_womanlongcoat2":
                botImageView[i].setImageResource(R.drawable.cloth_womanlongcoat2);
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
