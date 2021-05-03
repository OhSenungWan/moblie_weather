package com.example.myapplication.setdata;

import java.util.Calendar;

public class set_weather {
    int Hour;
    int Time;
    public String set_weather(String[][][] Short_Data){
        String weather;
        Hour= 0;
        Calendar cal = Calendar.getInstance();
        Time = cal.get(cal.HOUR);
        int AMPM = cal.get(Calendar.AM_PM);
        if (AMPM == 1) {
            Time += 12;
        }
        if(Time <=12 && Time >=0){
            Hour = 3;
        }
        else if(Time <=15 && Time >=12){
            Hour = 4;
        }
        else if(Time <=18 && Time >=15){
            Hour = 5;
        }
        else if(Time <=21 && Time >=18){
            Hour = 6;
        }
        else if(Time <=24 && Time >=21){
            Hour = 7;
        }
        weather = set_wether(Short_Data,Hour);
        return weather;
    }
    public String set_wether(String[][][] Short_Data, int Hour){
        String weather = "null";
        if(Short_Data[0][Hour][1].equals("0")){
            switch (Short_Data[0][Hour][5]){
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
        else if(Short_Data[0][Hour][1].equals("3") || Short_Data[0][Hour][1].equals("7")){
            weather = "Snow";
        }
        else{
            weather = "Rain";
        }
        return weather;
    }
    public String set_temp(String[][][] Short_Data){
        String temp= "";
        temp = Short_Data[0][Hour][6];
        return temp;
    }
    public String set_pop(String[][][] Short_Data){
        String pop="";
        pop = Short_Data[0][Hour][0];
        return pop;
    }
    public String set_vec(String[][][] Short_Data){
        String vec="";
        double uuu = Double.parseDouble(Short_Data[0][Hour][9]);
        double vvv = Double.parseDouble(Short_Data[0][Hour][10]);
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
        wsd = Short_Data[0][Hour][13];
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
}
