package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.db.DBInit;
import com.example.myapplication.db.ShowListView;
import com.example.myapplication.setdata.setdata_short;
import com.example.myapplication.setdata.setdata_air;
import com.example.myapplication.setdata.setdata_long_Temp;
import com.example.myapplication.setdata.setdata_long_weather;
import com.example.myapplication.setdata.set_weather;

public class MainActivity extends AppCompatActivity {

    public SharedPreferences prefs;
    String DOW;
    String Short_Data[][][] = new String[3][8][14];  //날짜(0오늘 1내일 2모래 시간(0이 0시부터 3시단위 데이터
    String Long_Temp[][] = new String[8][2];         //날짜(3일부터) 기온(최소0 최대가 1)
    String Long_Weather[][] = new String[8][4];      //날짜(3일부터) 날씨정보(0오전 최소 1최대 2오후 최소 3최대)
    String Data_Air[] = new String[3];               //
    String city_data = "서울특별시 구로구 구로제1동";
    String[] city;
    String x_point = "58";
    String y_point = "125";
    String point_temp = "11B10101";
    String point_weather = "11B00000";
    String weather;
    String temp;
    String pop;
    String vec;
    String wsd;
    setdata_air sa;
    set_weather sw;
    TextView Location;
    TextView Comment;
    TextView Temp;
    TextView Days_of_week;
    ImageView Main_img;
    TextView Pop;
    TextView Wsd;
    TextView Vec;
    TextView Pm25;
    TextView Pm10;
    String pm10;
    String pm25;
    TextView Pm10_grade;
    TextView Pm25_grade;
    TextView Time_comment;
    LinearLayout Back;

    ImageView timeweimg[]=new ImageView[16];//시간 날씨 이미지
    Integer[] time_img={R.id.timeweimg1,R.id.timeweimg2,R.id.timeweimg3,R.id.timeweimg4,R.id.timeweimg5,R.id.timeweimg6,R.id.timeweimg7,R.id.timeweimg8,R.id.timeweimg9,R.id.timeweimg10,R.id.timeweimg11,
            R.id.timeweimg12,R.id.timeweimg13,R.id.timeweimg14,R.id.timeweimg15,R.id.timeweimg16};

    TextView timetemp[]=new TextView[16];//시간 온도 텍스트
    Integer[] time_temp={R.id.timetemp1,R.id.timetemp2,R.id.timetemp3,R.id.timetemp4,R.id.timetemp5,R.id.timetemp6,R.id.timetemp7,R.id.timetemp8,R.id.timetemp9,R.id.timetemp10,
            R.id.timetemp11,R.id.timetemp12,R.id.timetemp13,R.id.timetemp14,R.id.timetemp15,R.id.timetemp16};

    TextView aor[]=new TextView[16];//시간 강수량 텍스트
    Integer[] time_aor={R.id.aor1,R.id.aor2,R.id.aor3,R.id.aor4,R.id.aor5,R.id.aor6,R.id.aor7,R.id.aor8,R.id.aor9,R.id.aor10,R.id.aor11,R.id.aor12,R.id.aor13,R.id.aor14,R.id.aor15,R.id.aor16};

    TextView por[]=new TextView[16];//시간 강수량 텍스트
    Integer[] time_por={R.id.por1,R.id.por2,R.id.por3,R.id.por4,R.id.por5,R.id.por6,R.id.por7,R.id.por8,R.id.por9,R.id.por10,R.id.por11,R.id.por12,R.id.por13,R.id.por14,R.id.por15,R.id.por16};



    TextView day[]=new TextView[16];//주간 날짜 텍스트
    Integer[] day_count={R.id.day1,R.id.day2,R.id.day3,R.id.day4,R.id.day5,R.id.day6,R.id.day7,R.id.day8,R.id.day9,R.id.day10,R.id.day11,R.id.day12,R.id.day13,R.id.day14,R.id.day15,R.id.day16};

    ImageView dayweimg[]=new ImageView[16];//주간 날씨 이미지
    Integer[] day_img={R.id.dayweimg1,R.id.dayweimg2,R.id.dayweimg3,R.id.dayweimg4,R.id.dayweimg5,R.id.dayweimg6,R.id.dayweimg7,R.id.dayweimg8,R.id.dayweimg9,R.id.dayweimg10,R.id.dayweimg11,R.id.dayweimg12
            ,R.id.dayweimg13,R.id.dayweimg14,R.id.dayweimg15};

    TextView dayhightemp[]=new TextView[16];//주간 최고온도 텍스트
    Integer[] day_hightemp={R.id.dayhightemp1,R.id.dayhightemp2,R.id.dayhightemp3,R.id.dayhightemp4,R.id.dayhightemp5,R.id.dayhightemp6,R.id.dayhightemp7,R.id.dayhightemp8,R.id.dayhightemp9,R.id.dayhightemp10
            ,R.id.dayhightemp11,R.id.dayhightemp12,R.id.dayhightemp13,R.id.dayhightemp14,R.id.dayhightemp15,R.id.dayhightemp16};

    TextView daylowtemp[]=new TextView[16];//주간 최저온도 텍스트
    Integer[] day_lowtemp={R.id.daylowtemp1,R.id.daylowtemp2,R.id.daylowtemp3,R.id.daylowtemp4,R.id.daylowtemp5,R.id.daylowtemp6,R.id.daylowtemp7,R.id.daylowtemp8,R.id.daylowtemp9,R.id.daylowtemp10,R.id.daylowtemp11
            ,R.id.daylowtemp12,R.id.daylowtemp13,R.id.daylowtemp14,R.id.daylowtemp15,R.id.daylowtemp16};

    TextView dayaor[]=new TextView[16];//주간 강수량
    Integer[] day_aor={R.id.dayaor1,R.id.dayaor2,R.id.dayaor3,R.id.dayaor4,R.id.dayaor5,R.id.dayaor6,R.id.dayaor7,R.id.dayaor8,R.id.dayaor9,R.id.dayaor10,R.id.dayaor11,R.id.dayaor12,
            R.id.dayaor13,R.id.dayaor14,R.id.dayaor15,R.id.dayaor16};


    int back;

    public void setStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Back = (LinearLayout)findViewById(R.id.back);
                setdata_short cd = new setdata_short();
                sa = new setdata_air();
                sw = new set_weather();
                setdata_long_Temp slt = new setdata_long_Temp();
                setdata_long_weather slw = new setdata_long_weather();
                Short_Data = cd.setdata(x_point,y_point);
                DOW = cd.DOW;
                Data_Air = sa.setdata_air(city[0]);
                pm10 = sa.set_pm10();
                pm25 = sa.set_pm25();
                Long_Temp = slt.setdata_longtemp(point_temp);
                Long_Weather = slw.setdata_longweather(point_weather);
                weather = sw.set_weather(Short_Data);
                temp = sw.set_temp(Short_Data);
                pop = sw.set_pop(Short_Data);
                vec = sw.set_vec(Short_Data);
                wsd = sw.set_wsd(Short_Data);
                back = sw.set_background();
                Location = (TextView)findViewById(R.id.location);
                Comment = (TextView)findViewById(R.id.comment);
                Temp = (TextView)findViewById(R.id.temp);
                Days_of_week = (TextView)findViewById(R.id.days_of_week);
                Main_img = (ImageView)findViewById(R.id.main_img);
                Pop = (TextView)findViewById(R.id.pop);
                Wsd = (TextView)findViewById(R.id.wsd);
                Vec = (TextView)findViewById(R.id.vec);
                Pm25 = (TextView)findViewById(R.id.pm25);
                Pm10 = (TextView)findViewById(R.id.pm10);
                Pm10_grade = (TextView)findViewById(R.id.pm10grade);
                Pm25_grade = (TextView)findViewById(R.id.pm25grade);
                Time_comment = (TextView)findViewById(R.id.time_comment);

                //시간 날씨 이미지 배열 매칭
                for(int i=0;i<=15; i++){
                   timeweimg[i] = (ImageView) findViewById(time_img[i]);}
                 /*for(int i=0; i<timeweimg.length; i++){
                    final int INDEX;
                    INDEX = i;
                    timeweimg[INDEX].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    });
                }
            }*/
                //시간 온도 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    timetemp[i] = (TextView) findViewById(time_temp[i]);}
                //시간 강수량 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    aor[i] = (TextView) findViewById(time_aor[i]);}
                //시간 강수확률 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    por[i] = (TextView) findViewById(time_por[i]);}


                //주간 날짜 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    day[i] = (TextView) findViewById(day_count[i]);}
                //주간 날씨 이미지 배열 매칭
                for(int i=0;i<=15; i++){
                    dayweimg[i] = (ImageView) findViewById(day_img[i]);}
                //주간 최고온도 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    dayhightemp[i] = (TextView) findViewById(day_hightemp[i]);}
                //주간 최저온도 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    daylowtemp[i] = (TextView) findViewById(day_lowtemp[i]);}
                //주간 강수량 텍스트뷰 배열 매칭
                for(int i=0;i<=15; i++){
                    dayaor[i] = (TextView) findViewById(day_aor[i]);}


                System.out.println(x_point);
                System.out.println(y_point);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Location.setText(city_data);
                        Comment.setText(weather);
                        Temp.setText(temp+"℃");
                        Days_of_week.setText(DOW);
                        switch (weather){
                            case "rain":
                                Main_img.setImageResource(R.drawable.rain);
                                Time_comment.setText("지금은 비가 오는 날씨에요.");
                                break;
                            case "snow":
                                Main_img.setImageResource(R.drawable.snow);
                                Time_comment.setText("지금은 눈이 내려요.");
                                break;
                            case "sunny":
                                Main_img.setImageResource(R.drawable.sun);
                                Time_comment.setText("지금은 해가 뜨는 화창한 날씨에요.");
                                break;
                            case "cloud":
                                Main_img.setImageResource(R.drawable.cloud1);
                                Time_comment.setText("지금은 구름이 많은 날씨에요.");
                                break;
                            case "blur":
                                Main_img.setImageResource(R.drawable.cloud2);
                                Time_comment.setText("지금은 매우 흐린 날씨에요.");
                                break;
                        }
                        Pop.setText(pop+"%");
                        Wsd.setText(wsd+"m/s");
                        Vec.setText(vec);
                        Pm10.setText(Data_Air[0]+"㎍/m3");
                        Pm25.setText(Data_Air[1]+"㎍/m3");
                        Pm10_grade.setText(pm10);
                        Pm25_grade.setText(pm25);
                        switch (back){
                            case 1:
                                Back.setBackgroundResource(R.drawable.bg_1dawn);
                                break;
                            case 2:
                                Back.setBackgroundResource(R.drawable.bg_2afternoon);
                                break;
                            case 3:
                                Back.setBackgroundResource(R.drawable.bg_3sunset);
                                break;
                            case 4:
                                Back.setBackgroundResource(R.drawable.bg_4night);
                                break;
                        }

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
                city_data = data.getStringExtra("cityName");
                city = city_data.split(" ");
                x_point = Integer.toString(data.getIntExtra("x", 0));
                y_point = Integer.toString(data.getIntExtra("y", 0));
                point_temp = data.getStringExtra("code1");
                point_weather = data.getStringExtra("code2");
                setStart();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        city = city_data.split(" ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("Pref", MODE_PRIVATE);
        checkFirstRun();
        ImageButton Location_img = (ImageButton)findViewById(R.id.location_img);
        Location_img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), ShowListView.class);
                startActivityForResult(intent,1);
            }
        });
        setStart();

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



