package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

public class set_data_cloth {
    data_short wd = new data_short();
    public String[] set_data_cloth(String x, String y) {
        String[][][] Short_Data = new String[3][8][14];
        String[] category = new String[250];
        String[] fcstDate = new String[250];
        String[] fcstTime = new String[250];
        String[] fcstValue = new String[250];
        String[] set_temp = new String[2];
        set_temp[0] = "null";
        set_temp[1] = "null";
        int i = 0;
        for(i=0;i<250;i++)
        {
            category[i] = "null";
            fcstDate[i] = "null";
            fcstTime[i] = "null";
            fcstValue[i] = "null";
        }
        i = 0;
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            int month = cal.get(cal.MONTH) + 1;
            int date = cal.get(cal.DATE);
            int hour = cal.get(cal.HOUR);
            int AMPM = cal.get(Calendar.AM_PM);

            Calendar yesterday = Calendar.getInstance();
            Calendar pday = Calendar.getInstance();
            Calendar ppday = Calendar.getInstance();
            pday.add(pday.DATE,+1);
            ppday.add(ppday.DATE,+2);
            yesterday.add(Calendar.DATE, -1);
            if (hour < 04 && AMPM == 0){
                year = yesterday.get(yesterday.YEAR);
                month = yesterday.get(yesterday.MONTH) + 1;
                date = yesterday.get(yesterday.DATE);
            }
            int bdate = (year * 10000) + (month * 100) + date;
            int pdate = (pday.get(cal.YEAR)*10000)+((pday.get(cal.MONTH)+1)*100)+(pday.get(cal.DATE));
            int ppdate = (ppday.get(cal.YEAR)*10000)+((ppday.get(cal.MONTH)+1)*100)+(ppday.get(cal.DATE));
            if (hour < 04 && AMPM == 0){
                pdate = (cal.get(cal.YEAR)*10000)+((cal.get(cal.MONTH)+1)*100)+(cal.get(cal.DATE));
                ppdate = (pday.get(cal.YEAR)*10000)+((pday.get(cal.MONTH)+1)*100)+(pday.get(cal.DATE));
            }
            if(AMPM == 1)
            {
                hour += 12;
            }
            int time = ((hour+2)/3)*3 - 4;
            if(time <0)
                time += 24;
            String Time= "0200";
            if(time == 20)
            {
                Time = "2000";
            }
            else if(time == 23)
            {
                Time = "2300";
            }
            else
            {
                Time = "0200";
            }
            String Dday, Pday, PPday;
            Dday = Integer.toString(bdate);
            Pday = Integer.toString(pdate);
            PPday = Integer.toString(ppdate);

            String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
            // 홈페이지에서 받은 키
            String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
            String pageNo = "1";
            String numOfRows = "250";    //한 페이지 결과 수
            String dataType = "xml";    //타입 xml, json 등등 .
            String base_date = Dday;    //조회하고싶은 날짜 이 예제는 어제 날짜 입력해 주면 됨// .
            String base_time = Time;    //API 제공 시간을 입력하면 됨
            String nx = x;    //위도
            String ny = y;    //경도
            String url = apiUrl + "?" + "servicekey=" + serviceKey
                    + "&" + "pageNo=" + pageNo + "&" + "numOfRows=" + numOfRows
                    + "&" + "dataType=" + dataType + "&" + "base_date=" + base_date
                    + "&" + "base_time=" + base_time + "&" + "nx=" + nx
                    + "&" + "ny=" + ny;
            System.out.println(url);
            int NOR = Integer.parseInt(numOfRows);
            wd.start();
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("body items item fcstDate");
            for (Element element : links) {
                fcstDate[i++] = element.text();
            }
            links = document.select("body items item fcstTime");
            i = 0;
            for (Element element : links) {
                fcstTime[i++] = element.text();
            }
            i = 0;
            links = document.select("body items item category");
            for (Element element : links) {
                category[i++] = element.text();
            }
            i = 0;
            links = document.select("body items item fcstValue");
            for (Element element : links) {
                fcstValue[i++] = element.text();
            }
            for (i = 0; i < NOR; i++) {
                wd.data(fcstDate[i], fcstTime[i], category[i], fcstValue[i], Dday, Pday, PPday);
            }
            Short_Data = wd.savedata;
            if(Time == "0200"){
                set_temp[0] = Short_Data[0][2][7];
                set_temp[1] = Short_Data[0][5][8];
            }else{
                set_temp[0] = Short_Data[1][2][7];
                set_temp[1] = Short_Data[1][5][8];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return set_temp;
    }
}
