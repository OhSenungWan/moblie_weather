package com.example.myapplication.setdata;

import java.util.Calendar;

public class set_weather {
    int Hour;
    public String set_weather(String[][][] Short_Data){
        String weather;
        Hour= 0;
        Calendar cal = Calendar.getInstance();
        int Time = cal.get(cal.HOUR);
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
                    weather = "sunny";
                    break;
                case "2":
                    weather = "cloud";
                    break;
                case "3":
                    weather = "blur";
                    break;
            }
        }
        else if(Short_Data[0][Hour][1].equals("3") || Short_Data[0][Hour][1].equals("7")){
            weather = "snow";
        }
        else{
            weather = "rain";
        }
        return weather;
    }
    public String set_temp(String[][][] Short_Data){
        String temp= "";
        temp = Short_Data[0][Hour][6];
        return temp;
    }
}
