package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.savedata.PreferenceManager;

import org.w3c.dom.Text;

import java.util.Calendar;


public class SettingActivity extends Activity {

    private Context mContext;

    LinearLayout Background;
    LinearLayout cmin, c8, c12, c16, c19, c22, c24, c29, cmax;
    LinearLayout linearLayoutTop, linearLayoutBot;
    LinearLayout man, woman;

    TextView temptext;
    TextView tmin, t8, t12, t16, t19, t22, t24, t29, tmax;
    TextView tman, twoman;
    TextView set_top_text, set_bot_text;

    int tempset[];      // 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
    String mtlist[];
    String mblist[];
    String wtlist[];
    String wblist[];
    String topcloth[];  //split mantopclothlist[해당 온도의 정수번호]
    String botcloth[];
    String[] tempdata;

    int sets = 1;


    int background;
    int Time;
    double ShareT, temp;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("CLOTHING");
        mContext = this;

        setStart();

        new Thread(new Runnable() {
            @Override
            public void run() {
                man = (LinearLayout) findViewById(R.id.set_man);
                woman = (LinearLayout) findViewById(R.id.set_woman);
                tman = (TextView) findViewById(R.id.tset_man);
                twoman = (TextView) findViewById(R.id.tset_woman);
                set_bot_text = (TextView) findViewById(R.id.set_bot_text);
                set_top_text = (TextView) findViewById(R.id.set_top_text);

                SettingActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // 시간에따라 배경이 바뀌게 한다.
                        setBG();
                        // 끝

                        // 현재 기온에 맞도록 자동적으로 디폴트 조정
                        settemp();

                        set_temp_btn_match();

                        set_temp_btn(ShareT);
                        temp = ShareT;

                        // 아래 내용 변경 추가 작업 후 추가할 예정
                        draw_Cloth(ShareT);
                        //
                        // 끝 (기온)

                        // 온도를 선택시 아래 내용들이 변경
                        btn_click();
                            //남, 여 선택시 아래 내용이 변경
                        man.setOnClickListener(new View.OnClickListener() {
                        @Override
                            public void onClick(View view) {
                                 sets = 1;
                                 man.setBackgroundResource(R.drawable.change_background);
                                 tman.setTextColor(Color.parseColor("#777777"));
                                 woman.setBackgroundResource(0);
                                 twoman.setTextColor(Color.parseColor("#FFFFFF"));
                                 draw_Cloth(temp);
                            }
                        });
                        woman.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                sets = 0;
                                woman.setBackgroundResource(R.drawable.change_background);
                                twoman.setTextColor(Color.parseColor("#777777"));
                                man.setBackgroundResource(0);
                                tman.setTextColor(Color.parseColor("#FFFFFF"));
                                draw_Cloth(temp);
                            }
                        });
                            // 끝(남, 여)
                        //끝(온도 선택)
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

        temptext = (TextView) findViewById(R.id.temptext);

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

    public void set_temp_btn(double temp){
        System.out.println(temp);
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

        if (temp <= 4) {
            cmin.setBackgroundResource(R.drawable.whitecircle);
            tmin.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: ~4℃");
            set_top_text.setText("체감온도 4℃미만에서의 의상들입니다.");
            set_bot_text.setText("체감온도 4℃미만에서의 의상들입니다.");
        } else if (temp <= 8 && temp > 4) {
            c8.setBackgroundResource(R.drawable.whitecircle);
            t8.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 5℃~8℃");
            set_top_text.setText("체감온도 5~8℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 5~8℃에서의 의상들입니다.");
        } else if (temp <= 12 && temp > 8) {
            c12.setBackgroundResource(R.drawable.whitecircle);
            t12.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 9℃~12℃");
            set_top_text.setText("체감온도 9~12℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 9~12℃에서의 의상들입니다.");
        } else if (temp <= 16 && temp > 12) {
            c16.setBackgroundResource(R.drawable.whitecircle);
            t16.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 13℃~16℃");
            set_top_text.setText("체감온도 13~16℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 13~16℃에서의 의상들입니다.");
        } else if (temp <= 19 && temp > 16) {
            c19.setBackgroundResource(R.drawable.whitecircle);
            t19.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 17℃~19℃");
            set_top_text.setText("체감온도 17~19℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 17~19℃에서의 의상들입니다.");
        } else if (temp <= 22 && temp > 19) {
            c22.setBackgroundResource(R.drawable.whitecircle);
            t22.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 20℃~22℃");
            set_top_text.setText("체감온도 20~22℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 20~22℃에서의 의상들입니다.");
        } else if (temp <= 24 && temp > 22) {
            c24.setBackgroundResource(R.drawable.whitecircle);
            t24.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 23℃~24℃");
            set_top_text.setText("체감온도 23~24℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 23~24℃에서의 의상들입니다.");
        } else if (temp <= 29 && temp > 24) {
            c29.setBackgroundResource(R.drawable.whitecircle);
            t29.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 25℃~29℃");
            set_top_text.setText("체감온도 25~29℃에서의 의상들입니다.");
            set_bot_text.setText("체감온도 25~29℃에서의 의상들입니다.");
        }
        else {
            cmax.setBackgroundResource(R.drawable.whitecircle);
            tmax.setTextColor(Color.parseColor("#353535"));
            temptext.setText("체감온도: 29℃~");
            set_top_text.setText("체감온도 29℃이상 에서의 의상들입니다.");
            set_bot_text.setText("체감온도 29℃이상 에서의 의상들입니다.");
        }

    }  // 버튼 변경

    public void btn_click(){
        cmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 3.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 7.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 11.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 15.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 18.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 21.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 23.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        c29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 27.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
        cmax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp = 32.0;
                if(sets == 1) {
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }else{
                    set_temp_btn(temp);
                    draw_Cloth(temp);
                }
            }
        });
    }

    public void setStart(){
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

    }

    public void setCloth_image_top(int max, int i, String cloth, double temp){
        linearLayoutTop = findViewById(R.id.set_lay_top);
        LinearLayout[] linearLayoutTopV = new LinearLayout[max];
        TextView[] topTextView = new TextView[max];
        ImageView[] topImageView = new ImageView[max];

        linearLayoutTopV[i] = new LinearLayout(this);
        topTextView[i] = new TextView(this);
        topImageView[i] = new ImageView(this);

        linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);
        top_cloth_matching(topImageView[i], cloth);
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
                top_cloth_matching(Weather, cloth);

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
                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if(sets == 1){
                                tempset[1] += 1;
                                mtlist[1] = mtlist[1] + " " + cloth;
                                top_delete(i,0,0,topcloth.length);

                            }else{
                                tempset[19] += 1;
                                wtlist[1] = wtlist[1] + " " + cloth;
                                top_delete(i,18,0,topcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if(sets == 1){
                                tempset[2] += 1;
                                mtlist[2] = mtlist[2] + " " + cloth;
                                top_delete(i,1,1,topcloth.length);

                            }else{
                                tempset[20] += 1;
                                wtlist[2] = wtlist[2] + " " + cloth;
                                top_delete(i,19,1,topcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if(sets == 1){
                                tempset[3] += 1;
                                mtlist[3] = mtlist[3] + " " + cloth;
                                top_delete(i,2,2,topcloth.length);

                            }else{
                                tempset[21] += 1;
                                wtlist[3] = wtlist[3] + " " + cloth;
                                top_delete(i,20,2,topcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if(sets == 1){
                                tempset[4] += 1;
                                mtlist[4] = mtlist[4] + " " + cloth;
                                top_delete(i,3,3,topcloth.length);

                            }else{
                                tempset[22] += 1;
                                wtlist[4] = wtlist[4] + " " + cloth;
                                top_delete(i,21,3,topcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if(sets == 1){
                                tempset[5] += 1;
                                mtlist[5] = mtlist[5] + " " + cloth;
                                top_delete(i,4,4,topcloth.length);

                            }else{
                                tempset[23] += 1;
                                wtlist[5] = wtlist[5] + " " + cloth;
                                top_delete(i,22,4,topcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if(sets == 1){
                                tempset[6] += 1;
                                mtlist[6] = mtlist[6] + " " + cloth;
                                top_delete(i,5,5,topcloth.length);

                            }else{
                                tempset[24] += 1;
                                wtlist[6] = wtlist[6] + " " + cloth;
                                top_delete(i,23,5,topcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if(sets == 1){
                                tempset[7] += 1;
                                mtlist[7] = mtlist[7] + " " + cloth;
                                top_delete(i,6,6,topcloth.length);

                            }else{
                                tempset[25] += 1;
                                wtlist[7] = wtlist[7] + " " + cloth;
                                top_delete(i,24,6,topcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if(sets == 1){
                                tempset[8] += 1;
                                mtlist[8] = mtlist[8] + " " + cloth;
                                top_delete(i,7,7,topcloth.length);

                            }else{
                                tempset[26] += 1;
                                wtlist[8] = wtlist[8] + " " + cloth;
                                top_delete(i,25,7,topcloth.length);
                            }
                        } else {
                            if(sets == 1){
                                top_delete(i,8,8,topcloth.length);

                            }else{
                                top_delete(i,26,8,topcloth.length);
                            }
                        }
                        update();

                        Toast.makeText(getApplicationContext(), "해당 의상의 체감온도를 올렸습니다.", Toast.LENGTH_LONG).show();
                        draw_Cloth(temp);
                        alertDialog.dismiss();
                    }
                });

                TextView down = dialogView.findViewById(R.id.down);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if(sets == 1){
                                top_delete(i,0,0,topcloth.length);

                            }else{
                                top_delete(i,18,0,topcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if(sets == 1){
                                tempset[0] += 1;
                                mtlist[0] = mtlist[0] + " " + cloth;
                                top_delete(i,1,1,topcloth.length);

                            }else{
                                tempset[18] += 1;
                                wtlist[0] = wtlist[0] + " " + cloth;
                                top_delete(i,19,1,topcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if(sets == 1){
                                tempset[1] += 1;
                                mtlist[1] = mtlist[1] + " " + cloth;
                                top_delete(i,2,2,topcloth.length);

                            }else{
                                tempset[19] += 1;
                                wtlist[1] = wtlist[1] + " " + cloth;
                                top_delete(i,20,2,topcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if(sets == 1){
                                tempset[2] += 1;
                                mtlist[2] = mtlist[2] + " " + cloth;
                                top_delete(i,3,3,topcloth.length);

                            }else{
                                tempset[20] += 1;
                                wtlist[2] = wtlist[2] + " " + cloth;
                                top_delete(i,21,3,topcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if(sets == 1){
                                tempset[3] += 1;
                                mtlist[3] = mtlist[3] + " " + cloth;
                                top_delete(i,4,4,topcloth.length);

                            }else{
                                tempset[21] += 1;
                                wtlist[3] = wtlist[3] + " " + cloth;
                                top_delete(i,22,4,topcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if(sets == 1){
                                tempset[4] += 1;
                                mtlist[4] = mtlist[4] + " " + cloth;
                                top_delete(i,5,5,topcloth.length);

                            }else{
                                tempset[22] += 1;
                                wtlist[4] = wtlist[4] + " " + cloth;
                                top_delete(i,23,5,topcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if(sets == 1){
                                tempset[5] += 1;
                                mtlist[5] = mtlist[5] + " " + cloth;
                                top_delete(i,6,6,topcloth.length);

                            }else{
                                tempset[23] += 1;
                                wtlist[5] = wtlist[5] + " " + cloth;
                                top_delete(i,24,6,topcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if(sets == 1){
                                tempset[6] += 1;
                                mtlist[6] = mtlist[6] + " " + cloth;
                                top_delete(i,7,7,topcloth.length);

                            }else{
                                tempset[24] += 1;
                                wtlist[6] = wtlist[6] + " " + cloth;
                                top_delete(i,25,7,topcloth.length);
                            }
                        } else {
                            if(sets == 1){
                                tempset[7] += 1;
                                mtlist[7] = mtlist[7] + " " + cloth;
                                top_delete(i,8,8,topcloth.length);

                            }else{
                                tempset[25] += 1;
                                wtlist[7] = wtlist[7] + " " + cloth;
                                top_delete(i,26,8,topcloth.length);
                            }
                        }
                        update();

                        Toast.makeText(getApplicationContext(), "해당 의상의 체감온도를 내렸습니다.", Toast.LENGTH_LONG).show();
                        draw_Cloth(temp);
                        alertDialog.dismiss();
                    }
                });
            }
        });

        topImageView[i].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothdelete, null);

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

                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                top_delete(i,0,0,topcloth.length);
                            } else {
                                top_delete(i,18,0,topcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if (sets == 1) {
                                top_delete(i,1,1,topcloth.length);
                            } else {
                                top_delete(i,19,1,topcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if (sets == 1) {
                                top_delete(i,2,2,topcloth.length);
                            } else {
                                top_delete(i,20,2,topcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if (sets == 1) {
                                top_delete(i,3,3,topcloth.length);
                            } else {
                                top_delete(i,21,3,topcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if (sets == 1) {
                                top_delete(i,4,4,topcloth.length);
                            } else {
                                top_delete(i,22,4,topcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if (sets == 1) {
                                top_delete(i,5,5,topcloth.length);
                            } else {
                                top_delete(i,23,5,topcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if (sets == 1) {
                                top_delete(i,6,6,topcloth.length);
                                System.out.println(mtlist[6]);
                            } else {
                                top_delete(i,24,6,topcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if (sets == 1) {
                                top_delete(i,7,7,topcloth.length);
                            } else {
                                top_delete(i,25,7,topcloth.length);
                            }
                        } else {
                            if (sets == 1) {
                                top_delete(i,8,8,topcloth.length);
                            } else {
                                top_delete(i,26,8,topcloth.length);
                            }
                        }

                        update();

                        Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_LONG).show();

                        draw_Cloth(temp);
                        alertDialog.dismiss();

                    }
                });

                return true;
            }
        });

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
    public void setCloth_image_bot(int max, int i, String cloth, double temp){
        linearLayoutBot = findViewById(R.id.set_lay_tbot);
        LinearLayout[] linearLayoutBotV = new LinearLayout[max];
        TextView[] botTextView = new TextView[max];
        ImageView[] botImageView = new ImageView[max];

        linearLayoutBotV[i] = new LinearLayout(this);
        botTextView[i] = new TextView(this);
        botImageView[i] = new ImageView(this);

        linearLayoutBotV[i].setOrientation(LinearLayout.VERTICAL);
        bot_cloth_matching(botImageView[i], cloth);
        botImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);
        botImageView[i].setOnClickListener(new View.OnClickListener() {
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
                bot_cloth_matching(Weather, cloth);

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
                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if(sets == 1){
                                tempset[10] += 1;
                                mblist[1] = mblist[1] + " " + cloth;
                                bot_delete(i,9,0,botcloth.length);

                            }else{
                                tempset[28] += 1;
                                wblist[1] = wblist[1] + " " + cloth;
                                bot_delete(i,27,0,botcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if(sets == 1){
                                tempset[11] += 1;
                                mblist[2] = mblist[2] + " " + cloth;
                                bot_delete(i,10,1,botcloth.length);

                            }else{
                                tempset[29] += 1;
                                wblist[2] = wblist[2] + " " + cloth;
                               bot_delete(i,28,1,botcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if(sets == 1){
                                tempset[12] += 1;
                                mblist[3] = mblist[3] + " " + cloth;
                                bot_delete(i,11,2,botcloth.length);

                            }else{
                                tempset[30] += 1;
                                wblist[3] = wblist[3] + " " + cloth;
                                bot_delete(i,29,2,botcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if(sets == 1){
                                tempset[13] += 1;
                                mblist[4] = mblist[4] + " " + cloth;
                                bot_delete(i,12,3,botcloth.length);

                            }else{
                                tempset[31] += 1;
                                wblist[4] = wblist[4] + " " + cloth;
                                bot_delete(i,30,3,botcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if(sets == 1){
                                tempset[14] += 1;
                                mblist[5] = mblist[5] + " " + cloth;
                                bot_delete(i,13,4,botcloth.length);

                            }else{
                                tempset[32] += 1;
                                wblist[5] = wblist[5] + " " + cloth;
                                bot_delete(i,31,4,botcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if(sets == 1){
                                tempset[15] += 1;
                                mblist[6] = mblist[6] + " " + cloth;
                                bot_delete(i,14,5,botcloth.length);

                            }else{
                                tempset[33] += 1;
                                wblist[6] = wblist[6] + " " + cloth;
                                bot_delete(i,32,5,botcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if(sets == 1){
                                tempset[16] += 1;
                                mblist[7] = mblist[7] + " " + cloth;
                                bot_delete(i,15,6,botcloth.length);

                            }else{
                                tempset[34] += 1;
                                wblist[7] = wblist[7] + " " + cloth;
                                bot_delete(i,33,6,botcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if(sets == 1){
                                tempset[17] += 1;
                                mblist[8] = mblist[8] + " " + cloth;
                                bot_delete(i,16,7,botcloth.length);

                            }else{
                                tempset[35] += 1;
                                wblist[8] = wblist[8] + " " + cloth;
                                bot_delete(i,34,7,botcloth.length);
                            }
                        } else {
                            if(sets == 1){
                                bot_delete(i,17,8,botcloth.length);

                            }else{
                                bot_delete(i,35,8,botcloth.length);
                            }
                        }
                        update();

                        Toast.makeText(getApplicationContext(), "해당 의상의 체감온도를 올렸습니다.", Toast.LENGTH_LONG).show();
                        draw_Cloth(temp);
                        alertDialog.dismiss();
                    }
                });

                TextView down = dialogView.findViewById(R.id.down);
                down.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if(sets == 1){
                                bot_delete(i,9,0,botcloth.length);

                            }else{
                                bot_delete(i,27,0,botcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if(sets == 1){
                                tempset[9] += 1;
                                mblist[0] = mblist[0] + " " + cloth;
                                bot_delete(i,10,1,botcloth.length);

                            }else{
                                tempset[27] += 1;
                                wblist[0] = wblist[0] + " " + cloth;
                                bot_delete(i,28,1,botcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if(sets == 1){
                                tempset[10] += 1;
                                mblist[1] = mblist[1] + " " + cloth;
                                bot_delete(i,11,2,botcloth.length);

                            }else{
                                tempset[28] += 1;
                                wblist[1] = wblist[1] + " " + cloth;
                                bot_delete(i,29,2,botcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if(sets == 1){
                                tempset[11] += 1;
                                mblist[2] = mblist[2] + " " + cloth;
                                bot_delete(i,12,3,botcloth.length);

                            }else{
                                tempset[29] += 1;
                                wblist[2] = wblist[2] + " " + cloth;
                                bot_delete(i,30,3,botcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if(sets == 1){
                                tempset[12] += 1;
                                mblist[3] = mblist[3] + " " + cloth;
                                bot_delete(i,13,4,botcloth.length);

                            }else{
                                tempset[30] += 1;
                                wblist[3] = wblist[3] + " " + cloth;
                                bot_delete(i,31,4,botcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if(sets == 1){
                                tempset[13] += 1;
                                mblist[4] = mblist[4] + " " + cloth;
                                bot_delete(i,14,5,botcloth.length);

                            }else{
                                tempset[31] += 1;
                                wblist[4] = wblist[4] + " " + cloth;
                                bot_delete(i,32,5,botcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if(sets == 1){
                                tempset[14] += 1;
                                mblist[5] = mblist[5] + " " + cloth;
                                bot_delete(i,15,6,botcloth.length);

                            }else{
                                tempset[32] += 1;
                                wblist[5] = wblist[5] + " " + cloth;
                                bot_delete(i,33,6,botcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if(sets == 1){
                                tempset[15] += 1;
                                mblist[6] = mblist[6] + " " + cloth;
                                bot_delete(i,16,7,botcloth.length);

                            }else{
                                tempset[33] += 1;
                                wblist[6] = wblist[6] + " " + cloth;
                                bot_delete(i,34,7,botcloth.length);
                            }
                        } else {
                            if(sets == 1){
                                tempset[16] += 1;
                                mblist[7] = mblist[7] + " " + cloth;
                                bot_delete(i,17,8,botcloth.length);

                            }else{
                                tempset[34] += 1;
                                wblist[7] = wblist[7] + " " + cloth;
                                bot_delete(i,35,8,botcloth.length);
                            }
                        }
                        update();

                        Toast.makeText(getApplicationContext(), "해당 의상의 체감온도를 내렸습니다.", Toast.LENGTH_LONG).show();
                        draw_Cloth(temp);
                        alertDialog.dismiss();
                    }
                });
            }
        });

        botImageView[i].setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View dialogView = getLayoutInflater().inflate(R.layout.activity_clothdelete, null);

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

                        if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                            //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                            // 옷 더하기 mtlist[0] += " 추가할옷";
                            if (sets == 1) {
                                bot_delete(i,9,0,botcloth.length);
                            } else {
                                bot_delete(i,27,0,botcloth.length);
                            }
                        } else if (temp <= 8 && temp > 4) {
                            if (sets == 1) {
                                bot_delete(i,10,1,botcloth.length);
                            } else {
                                bot_delete(i,28,1,botcloth.length);
                            }
                        } else if (temp <= 12 && temp > 8) {
                            if (sets == 1) {
                                bot_delete(i,11,2,botcloth.length);
                            } else {
                                bot_delete(i,29,2,botcloth.length);
                            }
                        } else if (temp <= 16 && temp > 12) {
                            if (sets == 1) {
                                bot_delete(i,12,3,botcloth.length);
                            } else {
                                bot_delete(i,30,3,botcloth.length);
                            }
                        } else if (temp <= 19 && temp > 16) {
                            if (sets == 1) {
                                bot_delete(i,13,4,botcloth.length);
                            } else {
                                bot_delete(i,31,4,botcloth.length);
                            }
                        } else if (temp <= 22 && temp > 19) {
                            if (sets == 1) {
                                bot_delete(i,14,5,botcloth.length);
                            } else {
                                bot_delete(i,32,5,botcloth.length);
                            }
                        } else if (temp <= 24 && temp > 22) {
                            if (sets == 1) {
                                bot_delete(i,15,6,botcloth.length);
                            } else {
                                bot_delete(i,33,6,botcloth.length);
                            }
                        } else if (temp <= 29 && temp > 24) {
                            if (sets == 1) {
                                bot_delete(i,16,7,botcloth.length);
                            } else {
                                bot_delete(i,34,7,botcloth.length);
                            }
                        } else {
                            if (sets == 1) {
                                bot_delete(i,17,8,botcloth.length);
                            } else {
                                bot_delete(i,35,8,botcloth.length);
                            }
                        }

                        update();

                        Toast.makeText(getApplicationContext(), "삭제했습니다.", Toast.LENGTH_LONG).show();

                        draw_Cloth(temp);
                        alertDialog.dismiss();

                    }
                });

                return true;
            }
        });

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
        linearLayoutBot.addView(linearLayoutBotV[i]);
    }

    public void top_cloth_matching(ImageView topImageView, String cloth){
        switch (cloth) {
            case "옷":
                topImageView.setImageResource(R.drawable.cloth);
                break;
            case "가디건":
                topImageView.setImageResource(R.drawable.cloth_cardigun);
                break;
            case "얇은가디건":
                topImageView.setImageResource(R.drawable.cloth_cardigun);
                break;
            case "히트텍":
                topImageView.setImageResource(R.drawable.cloth_hitec);
                break;
            case "후드":
                topImageView.setImageResource(R.drawable.cloth_hood);
                break;
            case "기모후드":
                topImageView.setImageResource(R.drawable.cloth_hood);
                break;
            case "청자켓":
                topImageView.setImageResource(R.drawable.cloth_jeanjacket);
                break;
            case "기모맨투맨":
                topImageView.setImageResource(R.drawable.cloth_kimomtm1);
                break;
            case "가죽자켓":
                topImageView.setImageResource(R.drawable.cloth_leatherjacket);
                break;
            case "롱패딩":
                topImageView.setImageResource(R.drawable.cloth_longpadding);
                break;
            case "셔츠":
                topImageView.setImageResource(R.drawable.cloth_longshirts);
                break;
            case "점퍼":
                topImageView.setImageResource(R.drawable.cloth_ma1);
                break;
            case "블레이저":
                topImageView.setImageResource(R.drawable.cloth_manblazer);
                break;
            case "롱코트(남)":
                topImageView.setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "칠부티셔츠":
                topImageView.setImageResource(R.drawable.cloth_midlesleeve);
                break;
            case "맨투맨":
                topImageView.setImageResource(R.drawable.cloth_mtm3);
                break;
            case "얇은맨투맨":
                topImageView.setImageResource(R.drawable.cloth_mtm4);
                break;
            case "나시":
                topImageView.setImageResource(R.drawable.cloth_nasi);
                break;
            case "니트":
                topImageView.setImageResource(R.drawable.cloth_neat);
                break;
            case "두꺼운니트":
                topImageView.setImageResource(R.drawable.cloth_neat);
                break;
            case "원피스":
                topImageView.setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "긴원피스":
                topImageView.setImageResource(R.drawable.cloth_onepeicedress);
                break;
            case "숏패딩":
                topImageView.setImageResource(R.drawable.cloth_padding1);
                break;
            case "오리털패딩":
                topImageView.setImageResource(R.drawable.cloth_padding2);
                break;
            case "패딩조끼":
                topImageView.setImageResource(R.drawable.cloth_paddingvest);
                break;
            case "셔츠(중)":
                topImageView.setImageResource(R.drawable.cloth_shirts);
                break;
            case "반팔셔츠":
                topImageView.setImageResource(R.drawable.cloth_shortshirts);
                break;
            case "반팔티":
                topImageView.setImageResource(R.drawable.cloth_shortsleeve);
                break;
            case "스웨터":
                topImageView.setImageResource(R.drawable.cloth_sweater);
                break;
            case "트렌치코트":
                topImageView.setImageResource(R.drawable.cloth_trenchcoat);
                break;
            case "티셔츠":
                topImageView.setImageResource(R.drawable.cloth_tshirts);
                break;
            case "두꺼운티셔츠":
                topImageView.setImageResource(R.drawable.cloth_tshirts);
                break;
            case "기모코트(남)":
                topImageView.setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "기모코트(여)":
                topImageView.setImageResource(R.drawable.cloth_womanlongcoat1);
                break;
            case "모직코트(남)":
                topImageView.setImageResource(R.drawable.cloth_manlongcoat1);
                break;
            case "모직코트(여)":
                topImageView.setImageResource(R.drawable.cloth_womanlongcoat2);
                break;
            default:
                topImageView.setImageResource(R.drawable.cloth);
                break;

        }
    }   //의상 매칭

    public void bot_cloth_matching(ImageView botImageView, String cloth){
        switch (cloth) {
            case "옷":
                botImageView.setImageResource(R.drawable.cloth);
                break;
            case "청바지":
                botImageView.setImageResource(R.drawable.cloth_jean);
                break;
            case "조거팬츠":
                botImageView.setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "기모조거팬츠":
                botImageView.setImageResource(R.drawable.cloth_joggerpants);
                break;
            case "기모청바지":
                botImageView.setImageResource(R.drawable.cloth_kimojean);
                break;
            case "두꺼운스타킹":
                botImageView.setImageResource(R.drawable.cloth_kimostocking);
                break;
            case "레깅스":
                botImageView.setImageResource(R.drawable.cloth_leggings);
                break;
            case "긴치마":
                botImageView.setImageResource(R.drawable.cloth_longskirt);
                break;
            case "면바지(남)":
                botImageView.setImageResource(R.drawable.cloth_mancotton);
                break;
            case "칠부청바지":
                botImageView.setImageResource(R.drawable.cloth_middlejean);
                break;
            case "짧은청바지":
                botImageView.setImageResource(R.drawable.cloth_shortjean);
                break;
            case "테니스치마":
                botImageView.setImageResource(R.drawable.cloth_shortskirt);
                break;
            case "슬렉스":
                botImageView.setImageResource(R.drawable.cloth_slacks);
                break;
            case "기모슬렉스":
                botImageView.setImageResource(R.drawable.cloth_slacks);
                break;
            case "스타킹":
                botImageView.setImageResource(R.drawable.cloth_stocking);
                break;
            case "얇은청바지":
                botImageView.setImageResource(R.drawable.cloth_thinjean);
                break;
            case "면바지(여)":
                botImageView.setImageResource(R.drawable.cloth_womancotton);
                break;
            default:
                botImageView.setImageResource(R.drawable.cloth);
                break;

        }
    }   //의상 매칭

    public void top_delete(int i, int temp, int list, int length){
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
    }       //위 삭제

    public void bot_delete(int i, int temp, int list, int length){
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
    }        //아래 삭제

    public void update(){
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

    }

    public void draw_Cloth(double temp){
        LinearLayout linearLayoutTop = findViewById(R.id.set_lay_top);
        LinearLayout linearLayoutBottom = findViewById(R.id.set_lay_tbot);
        linearLayoutTop.removeAllViews();
        linearLayoutBottom.removeAllViews();
        if(sets == 1) {
            System.out.println("it's me");
            if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                topcloth = mtlist[0].split(" ");
                for (int i = 0; i < tempset[0]; i++) {
                    setCloth_image_top(tempset[0], i, topcloth[i], temp);
                }
                botcloth = mblist[0].split(" ");
                for (int i = 0; i < tempset[9]; i++) {
                    setCloth_image_bot(tempset[9], i, botcloth[i],temp);
                }
            } else if (temp <= 8 && temp > 4) {
                topcloth = mtlist[1].split(" ");
                for (int i = 0; i < tempset[1]; i++) {
                    setCloth_image_top(tempset[1], i, topcloth[i],temp);
                }
                botcloth = mblist[1].split(" ");
                for (int i = 0; i < tempset[10]; i++) {
                    setCloth_image_bot(tempset[10], i, botcloth[i],temp);
                }
            } else if (temp <= 12 && temp > 8) {
                topcloth = mtlist[2].split(" ");
                for (int i = 0; i < tempset[2]; i++) {
                    setCloth_image_top(tempset[2], i, topcloth[i],temp);
                }
                botcloth = mblist[2].split(" ");
                for (int i = 0; i < tempset[11]; i++) {
                    setCloth_image_bot(tempset[11], i, botcloth[i],temp);
                }
            } else if (temp <= 16 && temp > 12) {
                topcloth = mtlist[3].split(" ");
                for (int i = 0; i < tempset[3]; i++) {
                    setCloth_image_top(tempset[3], i, topcloth[i],temp);
                }
                botcloth = mblist[3].split(" ");
                for (int i = 0; i < tempset[12]; i++) {
                    setCloth_image_bot(tempset[12], i, botcloth[i], temp);
                }
            } else if (temp <= 19 && temp > 16) {
                topcloth = mtlist[4].split(" ");
                for (int i = 0; i < tempset[4]; i++) {
                    setCloth_image_top(tempset[4], i, topcloth[i], temp);
                }
                botcloth = mblist[4].split(" ");
                for (int i = 0; i < tempset[13]; i++) {
                    setCloth_image_bot(tempset[13], i, botcloth[i], temp);
                }
            } else if (temp <= 22 && temp > 19) {
                topcloth = mtlist[5].split(" ");
                for (int i = 0; i < tempset[5]; i++) {
                    setCloth_image_top(tempset[5], i, topcloth[i], temp);
                }
                botcloth = mblist[5].split(" ");
                for (int i = 0; i < tempset[14]; i++) {
                    setCloth_image_bot(tempset[14], i, botcloth[i] ,temp);
                }
            } else if (temp <= 24 && temp > 22) {
                topcloth = mtlist[6].split(" ");
                for (int i = 0; i < tempset[6]; i++) {
                    setCloth_image_top(tempset[6], i, topcloth[i], temp);
                }
                botcloth = mblist[6].split(" ");
                for (int i = 0; i < tempset[15]; i++) {
                    setCloth_image_bot(tempset[15], i, botcloth[i], temp);
                }
            } else if (temp <= 29 && temp > 24) {
                topcloth = mtlist[7].split(" ");
                for (int i = 0; i < tempset[7]; i++) {
                    setCloth_image_top(tempset[7], i, topcloth[i], temp);
                }
                botcloth = mblist[7].split(" ");
                for (int i = 0; i < tempset[16]; i++) {
                    setCloth_image_bot(tempset[16], i, botcloth[i], temp);
                }
            } else {
                topcloth = mtlist[8].split(" ");
                for (int i = 0; i < tempset[8]; i++) {
                    setCloth_image_top(tempset[8], i, topcloth[i], temp);
                }
                botcloth = mblist[8].split(" ");
                for (int i = 0; i < tempset[17]; i++) {
                    setCloth_image_bot(tempset[17], i, botcloth[i], temp);
                }
            }
        }else{
            if(temp <= 4){
                System.out.println(" no it's me");
                topcloth = wtlist[0].split(" ");
                for(int i =0; i<tempset[18]; i++)
                {
                    setCloth_image_top(tempset[18], i, topcloth[i], temp);
                }
                botcloth = wblist[0].split(" ");
                for(int i =0; i<tempset[27]; i++)
                {
                    setCloth_image_bot(tempset[27], i, botcloth[i], temp);
                }
            }else if(temp<=8 && temp > 4){
                topcloth = wtlist[1].split(" ");
                for(int i =0; i<tempset[19]; i++)
                {
                    setCloth_image_top(tempset[19], i, topcloth[i], temp);
                }
                botcloth = wblist[1].split(" ");
                for(int i =0; i<tempset[28]; i++)
                {
                    setCloth_image_bot(tempset[28], i, botcloth[i], temp);
                }
            }else if(temp<=11 && temp > 8){
                topcloth = wtlist[2].split(" ");
                for(int i =0; i<tempset[20]; i++)
                {
                    setCloth_image_top(tempset[20], i, topcloth[i], temp);
                }
                botcloth = wblist[2].split(" ");
                for(int i =0; i<tempset[29]; i++)
                {
                    setCloth_image_bot(tempset[29], i, botcloth[i], temp);
                }
            }else if(temp<=16 && temp > 11){
                topcloth = wtlist[3].split(" ");
                for(int i =0; i<tempset[21]; i++)
                {
                    setCloth_image_top(tempset[21], i, topcloth[i], temp);
                }
                botcloth = wblist[3].split(" ");
                for(int i =0; i<tempset[30]; i++)
                {
                    setCloth_image_bot(tempset[30], i, botcloth[i], temp);
                }
            }else if(temp<=19 && temp > 16){
                topcloth = wtlist[4].split(" ");
                for(int i =0; i<tempset[22]; i++)
                {
                    setCloth_image_top(tempset[22], i, topcloth[i], temp);
                }
                botcloth = wblist[4].split(" ");
                for(int i =0; i<tempset[31]; i++)
                {
                    setCloth_image_bot(tempset[31], i, botcloth[i], temp);
                }
            }else if(temp<=22 && temp > 19){
                topcloth = wtlist[5].split(" ");
                for(int i =0; i<tempset[23]; i++)
                {
                    setCloth_image_top(tempset[23], i, topcloth[i], temp);
                }
                botcloth = wblist[5].split(" ");
                for(int i =0; i<tempset[32]; i++)
                {
                    setCloth_image_bot(tempset[32], i, botcloth[i], temp);
                }
            }else if(temp<=24 && temp > 22){
                topcloth = wtlist[6].split(" ");
                for(int i =0; i<tempset[24]; i++)
                {
                    setCloth_image_top(tempset[24], i, topcloth[i], temp);
                }
                botcloth = wblist[6].split(" ");
                for(int i =0; i<tempset[33]; i++)
                {
                    setCloth_image_bot(tempset[33], i, botcloth[i], temp);
                }
            }else if(temp<=29 && temp > 24){
                topcloth = wtlist[7].split(" ");
                for(int i =0; i<tempset[25]; i++)
                {
                    setCloth_image_top(tempset[25], i, topcloth[i], temp);
                }
                botcloth = wblist[7].split(" ");
                for(int i =0; i<tempset[34]; i++)
                {
                    setCloth_image_bot(tempset[34], i, botcloth[i], temp);
                }
            }else{
                topcloth = wtlist[8].split(" ");
                for(int i =0; i<tempset[26]; i++)
                {
                    setCloth_image_top(tempset[26], i, topcloth[i], temp);
                }
                botcloth = wblist[8].split(" ");
                for(int i =0; i<tempset[35]; i++)
                {
                    setCloth_image_bot(tempset[35], i, botcloth[i], temp);
                }
            }
        }
    }

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

                    Toast.makeText(getApplicationContext(), "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (temp <= 4) {// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                        //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                        // 옷 더하기 mtlist[0] += " 추가할옷";
                        if (sets == 1) {
                            tempset[0] += 1;
                            mtlist[0] = mtlist[0] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[18] += 1;
                            wtlist[0] = wtlist[0] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 8 && temp > 4) {
                        if (sets == 1) {
                            tempset[1] += 1;
                            mtlist[1] = mtlist[1] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[19] += 1;
                            wtlist[1] = wtlist[1] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 12 && temp > 8) {
                        if (sets == 1) {
                            tempset[2] += 1;
                            mtlist[2] = mtlist[2] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[20] += 1;
                            wtlist[2] = wtlist[2] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 16 && temp > 12) {
                        if (sets == 1) {
                            tempset[3] += 1;
                            mtlist[3] = mtlist[3] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[21] += 1;
                            wtlist[3] = wtlist[3] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 19 && temp > 16) {
                        if (sets == 1) {
                            tempset[4] += 1;
                            mtlist[4] = mtlist[4] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[22] += 1;
                            wtlist[4] = wtlist[4] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 22 && temp > 19) {
                        if (sets == 1) {
                            tempset[5] += 1;
                            mtlist[5] = mtlist[5] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[23] += 1;
                            wtlist[5] = wtlist[5] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 24 && temp > 22) {
                        if (sets == 1) {
                            tempset[6] += 1;
                            mtlist[6] = mtlist[6] + " " + edtAdd.getText().toString();
                        } else {
                            tempset[24] += 1;
                            wtlist[6] = wtlist[6] + " " + edtAdd.getText().toString();
                        }
                    } else if (temp <= 29 && temp > 24) {
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
                    update();

                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

                    draw_Cloth(temp);
                    alertDialog.dismiss();

                }

            }
        });

        /*Intent intent = new Intent(this, PopupActivity.class);
        startActivityForResult(intent, 1);*/



        /*//데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopActivity.class);
        startActivityForResult(intent, 1);*/
    }
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

                    Toast.makeText(getApplicationContext(), "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                }

                else{
                    if(temp <= 4){// 0~8 mantop 9~17 manbot 18~26 womantop 27 ~ 35 womanbot
                        //mtlist[0] += topcloth[0] + " " + topcloth[1] + " " + topcloth[3]; 옷 빼기
                        // 옷 더하기 mtlist[0] += " 추가할옷";
                        if(sets == 1) {
                            tempset[9] += 1;
                            mblist[0] = mblist[0] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[27] += 1;
                            wblist[0] = wblist[0] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=8 && temp > 4){
                        if(sets == 1) {
                            tempset[10] += 1;
                            mblist[1] = mblist[1] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[28] += 1;
                            wblist[1] = wblist[1] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=12 && temp > 8){
                        if(sets == 1) {
                            tempset[11] += 1;
                            mblist[2] = mblist[2] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[29] += 1;
                            wblist[2] = wblist[2] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=16 && temp > 12){
                        if(sets == 1) {
                            tempset[12] += 1;
                            mblist[3] = mblist[3] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[30] += 1;
                            wblist[3] = wblist[3] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=19 && temp > 16){
                        if(sets == 1) {
                            tempset[13] += 1;
                            mblist[4] = mblist[4] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[31] += 1;
                            wblist[4] = wblist[4] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=22 && temp > 19){
                        if(sets == 1) {
                            tempset[14] += 1;
                            mblist[5] = mblist[5] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[32] += 1;
                            wblist[5] = wblist[5] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=24 && temp > 22){
                        if(sets == 1) {
                            tempset[15] += 1;
                            mblist[6] = mblist[6] + " " + edtAdd.getText().toString();
                        }else{
                            tempset[33] += 1;
                            wblist[6] = wblist[6] + " " + edtAdd.getText().toString();
                        }
                    }else if(temp<=29 && temp > 24){
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

                    update();

                    Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_LONG).show();

                    draw_Cloth(temp);
                    alertDialog.dismiss();

                }
            }
        });

        /*Intent intent = new Intent(this, PopupActivity.class);
        startActivityForResult(intent, 1);*/



        /*//데이터 담아서 팝업(액티비티) 호출
        Intent intent = new Intent(this, PopActivity.class);
        startActivityForResult(intent, 1);*/
    }


}