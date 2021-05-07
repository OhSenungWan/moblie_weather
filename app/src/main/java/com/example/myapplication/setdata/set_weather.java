package com.example.myapplication.setdata;

import android.util.Log;

import java.util.Calendar;

public class set_weather {
    int Hour;
    int Time;
    int Date;
    public String set_weather(String[][][] Short_Data){
        String weather;
        Hour= 0;
        Calendar cal = Calendar.getInstance();
        Time = cal.get(cal.HOUR);
        int AMPM = cal.get(Calendar.AM_PM);
        if (AMPM == 1) {
            Time += 12;
        }
        if(Time <=3 && Time >0){
            Hour = 1;
            Date = 1;
        }
        if(Time <=6 && Time >3){
            Hour = 2;
            Date = 0;
        }
        else if(Time <=9 && Time >6){
            Hour = 3;
            Date = 0;
        }
        else if(Time <=12 && Time >9){
            Hour = 4;
            Date = 0;
        }
        else if(Time <=15 && Time >12){
            Hour = 5;
            Date = 0;
        }
        else if(Time <=18 && Time >15){
            Hour = 6;
            Date = 0;
        }
        else if(Time <=21 && Time >18){
            Hour = 7;
            Date = 0;
        }
        else if(Time <=24 && Time >21){
            Hour = 0;
            Date = 1;
        }
        weather = set_wether(Short_Data);
        return weather;
    }
    public String set_wether(String[][][] Short_Data){
        String weather = "null";
        if(Short_Data[Date][Hour][1].equals("0")){
            switch (Short_Data[Date][Hour][5]){

                case "1":
                    weather = "Sunny";
                    break;
                case "3":
                    weather = "Cloud";
                    break;
                case "4":
                    weather = "Blur";
                    break;
            }
        }
        else if(Short_Data[Date][Hour][1].equals("3") || Short_Data[Date][Hour][1].equals("7")){
            weather = "Snow";
        }
        else{
            weather = "Rain";
        }
        return weather;
    }
    public String set_temp(String[][][] Short_Data){
        String temp= "";
        temp = Short_Data[Date][Hour][6];
        return temp;
    }
    public String set_pop(String[][][] Short_Data){
        String pop="";
        pop = Short_Data[Date][Hour][0];
        return pop;
    }
    public String set_vec(String[][][] Short_Data){
        String vec="";
        System.out.println(Short_Data[1][1][9]);
        System.out.println("date = " + Date);
        System.out.println("time = " + Hour);
        double uuu = Double.parseDouble(Short_Data[Date][Hour][9]);
        double vvv = Double.parseDouble(Short_Data[Date][Hour][10]);
        if(vvv > 0){
            vec += "북";
        }
        else if(vvv <0){
            vec += "남";
        }
        if(uuu > 0){
            vec += "동";
        }
        else if(uuu <0){
            vec += "서";
        }
        if(uuu ==0 && vvv == 0)
        {
            vec = "바람 없음";
        }
        return vec;
    }
    public String set_wsd(String[][][] Short_Data){
        String wsd="";
        wsd = Short_Data[Date][Hour][13];
        return wsd;
    }
    public int set_background(){
        int background = 0;
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
        return background;
    }

    public int getTime() {
        return Time;
    }

    public int getHour() {
        return Hour;
    }
}
