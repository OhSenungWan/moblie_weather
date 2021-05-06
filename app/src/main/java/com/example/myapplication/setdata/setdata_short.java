package com.example.myapplication.setdata;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

public class setdata_short {
    int day_of_week;
    data_short wd = new data_short();
    public String DOW= "null";
    public String[][][] setdata(String x, String y) {

        String[] category = new String[225];
        String[] fcstDate = new String[225];
        String[] fcstTime = new String[225];
        String[] fcstValue = new String[225];
        int i = 0;
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            int month = cal.get(cal.MONTH) + 1;
            int date = cal.get(cal.DATE);
            int hour = cal.get(cal.HOUR);
            int minute = cal.get(cal.MINUTE);
            int AMPM = cal.get(Calendar.AM_PM);
            day_of_week = cal.get(Calendar.DAY_OF_WEEK);
            Calendar yesterday = Calendar.getInstance();
            Calendar pday = Calendar.getInstance();
            Calendar ppday = Calendar.getInstance();
            pday.add(pday.DATE,+1);
            ppday.add(ppday.DATE,+2);
            yesterday.add(Calendar.DATE, -1);
            if (hour < 05 && AMPM == 0){
                year = yesterday.get(yesterday.YEAR);
                month = yesterday.get(yesterday.MONTH) + 1;
                date = yesterday.get(yesterday.DATE);
            }
            int bdate = (year * 10000) + (month * 100) + date;
            int pdate = (pday.get(cal.YEAR)*10000)+((pday.get(cal.MONTH)+1)*100)+(pday.get(cal.DATE));
            int ppdate = (ppday.get(cal.YEAR)*10000)+((ppday.get(cal.MONTH)+1)*100)+(ppday.get(cal.DATE));
            if (hour < 05 && AMPM == 0){
                pdate = (cal.get(cal.YEAR)*10000)+((cal.get(cal.MONTH)+1)*100)+(cal.get(cal.DATE));
                ppdate = (pday.get(cal.YEAR)*10000)+((pday.get(cal.MONTH)+1)*100)+(pday.get(cal.DATE));
            }
            String Dday, Pday, PPday;
            Dday = Integer.toString(bdate);
            Pday = Integer.toString(pdate);
            PPday = Integer.toString(ppdate);

            String apiUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
            // 홈페이지에서 받은 키
            String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
            String pageNo = "1";
            String numOfRows = "225";    //한 페이지 결과 수
            String dataType = "xml";    //타입 xml, json 등등 .
            String base_date = Dday;    //조회하고싶은 날짜 이 예제는 어제 날짜 입력해 주면 됨// .
            String base_time = "0500";    //API 제공 시간을 입력하면 됨
            String nx = x;    //위도
            String ny = y;    //경도
            String url = apiUrl + "?" + "servicekey=" + serviceKey
                    + "&" + "pageNo=" + pageNo + "&" + "numOfRows=" + numOfRows
                    + "&" + "dataType=" + dataType + "&" + "base_date=" + base_date
                    + "&" + "base_time=" + base_time + "&" + "nx=" + nx
                    + "&" + "ny=" + ny;
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
            DOW = setdata(day_of_week);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wd.savedata;
    }
    public String setdata(int day_of_week){
        String DOW = "null";
        DOW = wd.data(day_of_week);
        return DOW;
    }
}
