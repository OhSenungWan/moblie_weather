package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int timeSet = 16;
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
                                Time_comment.setText("지금은 비가 와요.");
                                break;
                            case "snow":
                                Main_img.setImageResource(R.drawable.snow);
                                Time_comment.setText("밖은 지금 눈이 내려요.");
                                break;
                            case "sunny":
                                Main_img.setImageResource(R.drawable.sun);
                                Time_comment.setText("해가 뜨는 화창한 날씨에요.");
                                break;
                            case "cloud":
                                Main_img.setImageResource(R.drawable.cloud1);
                                Time_comment.setText("구름이 많은 날씨에요.");
                                break;
                            case "blur":
                                Main_img.setImageResource(R.drawable.cloud2);
                                Time_comment.setText("밖은 지금 매우 흐려요.");
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
        dynamicTimeWeather();

    }

    public void dynamicTimeWeather(){
        //탑 ,바텀 텍스트뷰 생성
        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);

        LinearLayout[] linearLayoutTopV = new LinearLayout[timeSet];
        LinearLayout[] linearLayoutBottomV = new LinearLayout[timeSet];

        TextView[] timeTextView = new TextView[timeSet];
        ImageView[] weatherImageView = new ImageView[timeSet];

        for (int i = 0; i < timeSet; i++) {
            linearLayoutTopV[i] = new LinearLayout(this);
            timeTextView[i] = new TextView(this);
            weatherImageView[i] = new ImageView(this);

            linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);
            timeTextView[i].setText("09");
            timeTextView[i].setTextSize(18);
            weatherImageView[i].setImageResource(R.drawable.rain);

            linearLayoutTopV[i].addView(timeTextView[i]);
            linearLayoutTopV[i].addView(weatherImageView[i], 100, 100);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.leftMargin = 95;
            linearLayoutTopV[i].setLayoutParams(lp);

            linearLayoutTop.addView(linearLayoutTopV[i]);
        }

        //그래프 생성
        makeGraph();
    }

    public void makeGraph(){

        LineChart lineChart = (LineChart)findViewById(R.id.lineChart);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < timeSet; i++) {
            entries.add(new Entry(i, i+1));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "온도");

        //lineDataSet.setDrawValues(false); //점에 데이터 출력
        lineDataSet.setLineWidth(1.75f); //선 두께
        lineDataSet.setCircleRadius(5f); //점 크기
        lineDataSet.setCircleHoleRadius(2.5f); // 점 구멍(빈 공간) 크기
        lineDataSet.disableDashedHighlightLine();

        //그래프 선, 점 색상들
        //lineDataSet.setColor(Color.WHITE);
        //lineDataSet.setCircleColor(Color.WHITE);
        //lineDataSet.setHighLightColor(Color.WHITE);

        //범례 사용 X
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.getDescription().setEnabled(false); //오른쪽 설명 제거
        lineChart.setPinchZoom(false);

        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getAxisLeft().setEnabled(false); //Y축 왼쪽 숫자 제거
        lineChart.getAxisRight().setEnabled(false); //Y축 오른쪽 숫자 제거
        lineChart.getXAxis().setEnabled(false); //위쪽 숫자 제거



        lineChart.invalidate();
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



