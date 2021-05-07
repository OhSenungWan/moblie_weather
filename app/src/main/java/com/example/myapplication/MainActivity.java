package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.db.DBInit;
import com.example.myapplication.db.ShowListView;
import com.example.myapplication.graph.MyValueFormatter;
import com.example.myapplication.savedata.PreferenceManager;
import com.example.myapplication.setdata.set_weather;
import com.example.myapplication.setdata.setdata_air;
import com.example.myapplication.setdata.setdata_long_Temp;
import com.example.myapplication.setdata.setdata_long_weather;
import com.example.myapplication.setdata.setdata_short;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    public static final int timeSet = 15;
    public static final int daySet = 5;
    public SharedPreferences prefs;
    String DOW;
    String Short_Data[][][] = new String[3][8][14];  //날짜(0오늘 1내일 2모래 시간(0이 0시부터 3시단위 데이터
    String Long_Temp[][] = new String[8][2];         //날짜(3일부터) 기온(최소0 최대가 1)
    String Long_Weather[][] = new String[8][4];      //날짜(3일부터) 날씨정보(0오전 최소 1최대 2오후 최소 3최대)
    String Data_Air[] = new String[3];               //
    String city_data;
    String[] city;
    String x_point;
    String y_point;
    String point_temp;
    String point_weather;
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
    TextView day_comment;
    LinearLayout Back;
    int PoP = 0;
    ImageButton btncloth;

    int back;

    public void setStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i<3;i++)
                {
                    for(int j = 0; j<8; j++)
                    {
                        for(int k = 0; k<14; k++)
                        {
                            Short_Data[i][j][k] = "";
                        }
                    }
                }
                for(int i = 0; i<8; i++){
                    for(int j = 0; j<2; j++){
                        Long_Temp[i][j] = "";
                    }
                }
                for(int i = 0; i<8; i++){
                    for(int j = 0; j<4; j++){
                        Long_Weather[i][j] = "";
                    }
                }
                for(int i = 0; i<3; i++){
                    Data_Air[i] = "";
                }
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
                day_comment = (TextView)findViewById(R.id.daycomment);
                //시간 날씨 이미지 배열 매칭
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dynamicTimeWeather();
                        dynamicDayWeather();

                        Location.setText(city_data);
                        Comment.setText(weather);
                        Temp.setText(temp+"℃");
                        Days_of_week.setText(DOW);
                        switch (weather){
                            case "Rain":
                                Main_img.setImageResource(R.drawable.rain);
                                Time_comment.setText("지금은 비가 오는 날씨에요.");
                                day_comment.setText("오늘은 전체적으로 비가 오는 날씨에요.");
                                break;
                            case "Snow":
                                Main_img.setImageResource(R.drawable.snow);
                                Time_comment.setText("지금은 눈이 내려요.");
                                day_comment.setText("오늘은 전체적으로 눈이 내려요.");
                                break;
                            case "Sunny":
                                Main_img.setImageResource(R.drawable.sunn);
                                Time_comment.setText("지금은 해가 뜨는 화창한 날씨에요.");
                                day_comment.setText("오늘은 전체적으로 해가 뜨는 화창한 날씨에요.");
                                break;
                            case "Cloud":
                                Main_img.setImageResource(R.drawable.cloud1);
                                Time_comment.setText("지금은 구름이 많은 날씨에요.");
                                day_comment.setText("오늘은 전체적으로 구름이 많은 날씨에요.");
                                break;
                            case "Blur":
                                Main_img.setImageResource(R.drawable.cloud2);
                                Time_comment.setText("지금은 매우 흐린 날씨에요.");
                                day_comment.setText("오늘은 전체적으로 매우 흐린 날씨에요.");
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String Data;
                city_data = data.getStringExtra("cityName");
                city = city_data.split(" ");
                x_point = Integer.toString(data.getIntExtra("x", 0));
                y_point = Integer.toString(data.getIntExtra("y", 0));
                point_temp = data.getStringExtra("code1");
                point_weather = data.getStringExtra("code2");
                setStart();
                Data = x_point + " " + y_point + " " + point_temp + " " + point_weather + " " + city_data;
                PreferenceManager.setString(mContext, "rebuild", Data);
                String intentdata = temp+"℃ " + pm10 + " "+weather;
                PreferenceManager.setString(mContext, "data", intentdata);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        String text = PreferenceManager.getString(mContext,"rebuild");
        if(text.equals("")) {
            text = "58 125 11B10101 11B00000 서울특별시 구로구 구로제1동";
            PreferenceManager.setString(mContext, "rebuild", text);
        }

        String[] data = text.split(" ");
        x_point = data[0];
        y_point = data[1];
        point_temp = data[2];
        point_weather = data[3];
        city_data = data[4] + " " + data[5] + " " +data[6];
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



        ImageButton btncloth=(ImageButton)findViewById(R.id.btncloth);
        btncloth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ClothingActivity.class);
                intent.putExtra("Temp", temp);
                intent.putExtra("Wsd", wsd);

                if(PoP > 30)
                {
                    intent.putExtra("Weather", "1");
                    intent.putExtra("Wtype" , weather);
                }
                else
                {
                    intent.putExtra("Weather", "0");
                    intent.putExtra("Wtype", "sunny");
                } // 내리는 것이 없을 때


                startActivity(intent);

            }
        });


        setStart();

        String intentdata = PreferenceManager.getString(mContext,"data");
        if(intentdata.equals("")) {
            intentdata = temp+"℃ " + pm10 + " "+weather;
            PreferenceManager.setString(mContext, "data", intentdata);
        }
    }

    public void dynamicTimeWeather(){
        //탑 ,바텀 텍스트뷰 생성

        LinearLayout linearLayoutTop = findViewById(R.id.layout_timeWeatherTop);
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_timeWeatherBottom);

        linearLayoutTop.removeAllViews();
        linearLayoutBottom.removeAllViews();
        LinearLayout[] linearLayoutTopV = new LinearLayout[timeSet];
        LinearLayout[] linearLayoutBottomV = new LinearLayout[timeSet];

        TextView[] timeTextView = new TextView[timeSet];
        ImageView[] weatherImageView = new ImageView[timeSet];

        //그래프 위 텍스트뷰 생성
        for (int i = 0; i < timeSet; i++) {
            linearLayoutTopV[i] = new LinearLayout(this);
            timeTextView[i] = new TextView(this);
            weatherImageView[i] = new ImageView(this);

            linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);
            int hour3 = sw.getHour()*3+i*3;
            if(hour3 >= 24){
                hour3 %=24;
            }
            timeTextView[i].setText(String.valueOf(hour3));
            timeTextView[i].setTextSize(14);
            timeTextView[i].setTextColor(Color.WHITE);

            int time = (sw.getHour() + i)%8;
            int day = (sw.getHour() + i)/8;
            if(sw.getHour() < 2){
                day = (sw.getHour() + i)/8 + 1;
            }
            String pty = Short_Data[day][time][1];
            String sky = Short_Data[day][time][5];
            if(pty.equals("0")){
                if(sky.equals("1")){
                    weatherImageView[i].setImageResource(R.drawable.list_sun);
                }else if(sky.equals("3")){
                    weatherImageView[i].setImageResource(R.drawable.list_cloud);
                }else {
                    weatherImageView[i].setImageResource(R.drawable.list_cloud1);
                }
            }else if(pty.equals("3") || pty.equals("7")){
                weatherImageView[i].setImageResource(R.drawable.list_snow);
            }else{
                weatherImageView[i].setImageResource(R.drawable.list_rain);
            }


            weatherImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.bottomMargin = 40;
            lp1.gravity = Gravity.CENTER_HORIZONTAL;
            timeTextView[i].setLayoutParams(lp1);

            linearLayoutTopV[i].addView(timeTextView[i]);
            linearLayoutTopV[i].addView(weatherImageView[i], 100, 100);
            linearLayoutTopV[i].setMinimumWidth(120);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.rightMargin = 70;
            linearLayoutTopV[i].setLayoutParams(lp2);


            linearLayoutTop.addView(linearLayoutTopV[i]);

        }

        TextView[] rainfallProbTextView = new TextView[timeSet];
        TextView[] rainfallTextView = new TextView[timeSet];
        //그래프 아래 텍스트뷰 생성
        for (int i = 0; i < timeSet; i++){
            linearLayoutBottomV[i] = new LinearLayout(this);
            rainfallProbTextView[i] = new TextView(this);
            rainfallTextView[i] = new TextView(this);

            linearLayoutBottomV[i].setOrientation(LinearLayout.VERTICAL);

            int time = (sw.getHour() + i)%8;
            int day = (sw.getHour() + i)/8;
            if(sw.getHour() < 2){
                day = (sw.getHour() + i)/8 + 1;
            }

            String pop = Short_Data[day][time][0];
            String r06 = Short_Data[day][time][2]+"mm";

            rainfallProbTextView[i].setText(pop + "%");
            rainfallProbTextView[i].setTextSize(14);
            rainfallProbTextView[i].setTextColor(Color.WHITE);

            if(r06.equals("nullmm")){
                r06= "";
            }

            rainfallTextView[i].setText(r06);
            rainfallTextView[i].setGravity(Gravity.CENTER);
            rainfallTextView[i].setTextSize(14);
            rainfallTextView[i].setTextColor(Color.WHITE);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.bottomMargin = 40;
            lp1.gravity = Gravity.CENTER_HORIZONTAL;
            rainfallProbTextView[i].setLayoutParams(lp1);

            linearLayoutBottomV[i].addView(rainfallProbTextView[i]);
            linearLayoutBottomV[i].addView(rainfallTextView[i]);
            linearLayoutBottomV[i].setMinimumWidth(120);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.rightMargin = 70;
            lp2.topMargin=20;

            linearLayoutBottomV[i].setLayoutParams(lp2);

            linearLayoutBottom.addView(linearLayoutBottomV[i]);
        }

        //그래프 생성
        makeTimeGraph();
    }

    public void makeTimeGraph(){

        LineChart lineChart = (LineChart)findViewById(R.id.lineChart);

        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < timeSet; i++) {
            int time = (sw.getHour() + i)%8;

            int day = (sw.getHour() + i)/8;
            if(sw.getHour() < 2){
                day = (sw.getHour() + i)/8 + 1;
            }
            String t3h = Short_Data[day][time][6];

            entries.add(new Entry(i, Integer.parseInt(t3h)));

        }

        LineDataSet lineDataSet = new LineDataSet(entries, "온도");

        lineDataSet.setColor(Color.WHITE);
        //lineDataSet.setDrawValues(false); //점에 데이터 출력
        lineDataSet.setLineWidth(1.75f); //선 두께
        lineDataSet.setCircleRadius(3f); //점 크기
        lineDataSet.setCircleHoleRadius(0f); // 점 구멍(빈 공간) 크기
        lineDataSet.setValueTextSize(13); //온도 글씨 크기
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueFormatter(new MyValueFormatter());
        //그래프 선, 점 색상들
        //lineDataSet.setColor(Color.GRAY);
        lineDataSet.setCircleColor(Color.YELLOW);

        //범례 사용 X
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        lineChart.setHighlightPerTapEnabled(false); // 클릭시 표시 제외
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.getDescription().setEnabled(false); //오른쪽 설명 제거
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.getAxisLeft().setEnabled(false); //Y축 왼쪽 숫자 제거
        lineChart.getAxisRight().setEnabled(false); //Y축 오른쪽 숫자 제거
        lineChart.getXAxis().setEnabled(false); //위쪽 숫자 제거
        lineChart.invalidate();
    }

    public void dynamicDayWeather(){
        //탑 ,바텀 텍스트뷰 생성
        LinearLayout linearLayoutTop = findViewById(R.id.layout_dayWeatherTop);
        LinearLayout linearLayoutBottom = findViewById(R.id.layout_dayWeatherBottom);

        linearLayoutTop.removeAllViews();
        linearLayoutBottom.removeAllViews();

        LinearLayout[] linearLayoutTopV = new LinearLayout[daySet];
        LinearLayout[] linearLayoutBottomV = new LinearLayout[daySet];

        TextView[] dayTextView = new TextView[daySet];
        ImageView[] weatherImageView = new ImageView[daySet];

        //그래프 위 텍스트뷰 생성
        for (int i = 0; i < daySet; i++) {
            linearLayoutTopV[i] = new LinearLayout(this);
            dayTextView[i] = new TextView(this);
            weatherImageView[i] = new ImageView(this);

            linearLayoutTopV[i].setOrientation(LinearLayout.VERTICAL);

            Calendar cal = Calendar.getInstance();
            SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd", Locale.KOREA);

            cal.add(cal.DATE, +i+3);

            dayTextView[i].setText(mFormat.format(cal.getTime()));
            dayTextView[i].setTextSize(13);
            dayTextView[i].setTextColor(Color.WHITE);

            String longWeather = Long_Weather[i][2];
            if(longWeather.equals("맑음")){
                weatherImageView[i].setImageResource(R.drawable.list_sun);
            }else if(longWeather.equals("구름많음")){
                weatherImageView[i].setImageResource(R.drawable.list_cloud);
            }else if(longWeather.equals("흐림")){
                weatherImageView[i].setImageResource(R.drawable.list_cloud1);
            }else if(longWeather.equals("흐리고 비") || longWeather.equals("구름많고 비")){
                weatherImageView[i].setImageResource(R.drawable.list_rain);
            }else{
                weatherImageView[i].setImageResource(R.drawable.list_snow);
            }

            weatherImageView[i].setForegroundGravity(Gravity.CENTER_HORIZONTAL);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.bottomMargin = 60;
            lp1.gravity = Gravity.CENTER_HORIZONTAL;
            dayTextView[i].setLayoutParams(lp1);

            linearLayoutTopV[i].addView(dayTextView[i]);
            linearLayoutTopV[i].addView(weatherImageView[i], 100, 100);
            linearLayoutTopV[i].setMinimumWidth(120);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.rightMargin = 70;
            linearLayoutTopV[i].setLayoutParams(lp2);


            linearLayoutTop.addView(linearLayoutTopV[i]);
        }

        TextView[] rainfallProbTextView = new TextView[daySet];
        //그래프 아래 텍스트뷰 생성
        for (int i = 0; i < daySet; i++){
            linearLayoutBottomV[i] = new LinearLayout(this);
            rainfallProbTextView[i] = new TextView(this);

            linearLayoutBottomV[i].setOrientation(LinearLayout.VERTICAL);

            String rainfallProbText = Long_Weather[i][0];

            rainfallProbTextView[i].setText(rainfallProbText + "%");
            rainfallProbTextView[i].setTextSize(14);
            rainfallProbTextView[i].setTextColor(Color.WHITE);

            LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp1.gravity = Gravity.CENTER_HORIZONTAL;
            rainfallProbTextView[i].setLayoutParams(lp1);

            linearLayoutBottomV[i].addView(rainfallProbTextView[i]);
            linearLayoutBottomV[i].setMinimumWidth(120);

            LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp2.rightMargin = 70;
            lp2.topMargin=60;

            linearLayoutBottomV[i].setLayoutParams(lp2);

            linearLayoutBottom.addView(linearLayoutBottomV[i]);
        }

        //그래프 생성
        makeDayGraph();
    }

    public void makeDayGraph(){

        LineChart lineChart = (LineChart)findViewById(R.id.dayLineChart);

        List<Entry> entries = new ArrayList<>();
        List<Entry> entries2 = new ArrayList<>();
        for (int i = 0; i < daySet; i++) {
            String tempMin = Long_Temp[i][0];
            String tempMax = Long_Temp[i][1];
            entries.add(new Entry(i, Integer.parseInt(tempMin)));
            entries2.add(new Entry(i, Integer.parseInt(tempMax)));
        }

        LineDataSet lineDataSet = new LineDataSet(entries, "온도");
        LineDataSet lineDataSet2 = new LineDataSet(entries2, "온도");

        lineDataSet.setColor(Color.WHITE);
        //lineDataSet.setDrawValues(false); //점에 데이터 출력
        lineDataSet.setLineWidth(1.75f); //선 두께
        lineDataSet.setCircleRadius(3f); //점 크기
        lineDataSet.setCircleHoleRadius(0f); // 점 구멍(빈 공간) 크기
        lineDataSet.setValueTextSize(13); //온도 글씨 크기
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setValueFormatter(new MyValueFormatter());
        //그래프 선, 점 색상들
        //lineDataSet.setColor(Color.GRAY);
        lineDataSet.setCircleColor(Color.DKGRAY);


        lineDataSet2.setColor(Color.WHITE);
        //lineDataSet.setDrawValues(false); //점에 데이터 출력
        lineDataSet2.setLineWidth(1.75f); //선 두께
        lineDataSet2.setCircleRadius(3f); //점 크기
        lineDataSet2.setCircleHoleRadius(0f); // 점 구멍(빈 공간) 크기
        lineDataSet2.setValueTextSize(13); //온도 글씨 크기
        lineDataSet2.setValueTextColor(Color.WHITE);
        lineDataSet2.setValueFormatter(new MyValueFormatter());
        //그래프 선, 점 색상들
        //lineDataSet.setColor(Color.GRAY);
        lineDataSet2.setCircleColor(Color.LTGRAY);


        //범례 사용 X
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        LineData lineData = new LineData(lineDataSet);
        lineData.addDataSet(lineDataSet2);
        lineChart.setData(lineData);

        lineChart.setHighlightPerTapEnabled(false); // 클릭시 표시 제외
        lineChart.setHighlightPerDragEnabled(false);
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



