package com.example.myapplication.setdata;

public class data_short {
    public String[][][] savedata = new String[3][8][14];
    public void start()
    {
        for(int i = 0; i<3; i++)
            for(int j = 0; j<8; j++)
                for(int k = 0; k<14; k++)
                {
                    savedata[i][j][k] = "null";
                }
    }
    public String data(int day_of_week){
        String week_data = "null";
        switch (day_of_week){
            case 1:
                week_data = "sunday";
                break;
            case 2:
                week_data = "Monday";
                break;
            case 3:
                week_data = "Tuesday";
                break;
            case 4:
                week_data = "Wednesday";
                break;
            case 5:
                week_data = "Thursday";
                break;
            case 6:
                week_data = "Friday";
                break;
            case 7:
                week_data = "Saturday";
                break;
        }
        return week_data;
    }
    public void data(String fcstDate, String fcstTime, String category, String fcstValue, String Dday, String Pday, String PPday){
        if(fcstDate.equals(Dday))
        {
            switch (fcstTime){
                case "0000":
                    Wdata(category,fcstValue, 0, 0);
                    break;
                case "0300":
                    Wdata(category,fcstValue, 0, 1);
                    break;
                case "0600":
                    Wdata(category,fcstValue, 0, 2);
                    break;
                case "0900":
                    Wdata(category,fcstValue, 0, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 0, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 0, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 0, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 0, 7);
                    break;

            }
        }
        else if(fcstDate.equals(Pday))
        {
            switch (fcstTime){
                case "0000":
                    Wdata(category,fcstValue, 1, 0);
                    break;
                case "0300":
                    Wdata(category,fcstValue, 1, 1);
                    break;
                case "0600":
                    Wdata(category,fcstValue, 1, 2);
                    break;
                case "0900":
                    Wdata(category,fcstValue, 1, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 1, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 1, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 1, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 1, 7);
                    break;

            }
        }
        else if(fcstDate.equals(PPday))
        {
            switch (fcstTime){
                case "0000":
                    Wdata(category,fcstValue, 2, 0);
                    break;
                case "0300":
                    Wdata(category,fcstValue, 2, 1);
                    break;
                case "0600":
                    Wdata(category,fcstValue, 2, 2);
                    break;
                case "0900":
                    Wdata(category,fcstValue, 2, 3);
                    break;
                case "1200":
                    Wdata(category,fcstValue, 2, 4);
                    break;
                case "1500":
                    Wdata(category,fcstValue, 2, 5);
                    break;
                case "1800":
                    Wdata(category,fcstValue, 2, 6);
                    break;
                case "2100":
                    Wdata(category,fcstValue, 2, 7);
                    break;

            }
        }
        else
        {
            Wdata(category,fcstValue, 0, 0);
        }
    }
    public void Wdata(String category, String fcstValue, int i, int j){
        switch (category) {
            case "POP": //???????
                savedata[i][j][0] = fcstValue;
                break;
            case "PTY": //???????? 1???? 1?? 2??/?? 3?? 4????? 5????? 6?????/?????? 7??????
                savedata[i][j][1] = fcstValue;
                break;
            case "R06": //6?ð? ??????
                savedata[i][j][2] = fcstValue;
                break;
            case "REH": //????
                savedata[i][j][3] = fcstValue;
                break;
            case "S06": //6?ð? ??????
                savedata[i][j][4] = fcstValue;
                break;
            case "SKY": //?????? 1???? 3???????? 4??
                savedata[i][j][5] = fcstValue;
                break;
            case "T3H": //3?ð? ???
                savedata[i][j][6] = fcstValue;
                break;
            case "TMN": //??ħ ???????
                savedata[i][j][7] = fcstValue;
                break;
            case "TMX": //?? ?????
                savedata[i][j][8] = fcstValue;
                break;
            case "UUU": //???(????????) ??(+) ??(-)
                savedata[i][j][9] = fcstValue;
                break;
            case "VVV": //???(???????) ??(+) ??(-)
                savedata[i][j][10] = fcstValue;
                break;
            case "WAV": //???
                savedata[i][j][11] = fcstValue;
                break;
            case "VEC": //???
                savedata[i][j][12] = fcstValue;
                break;
            case "WSD": //???
                savedata[i][j][13] = fcstValue;
                break;
        }
    }
}
