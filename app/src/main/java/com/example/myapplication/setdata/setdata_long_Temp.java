package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;
public class setdata_long_Temp {
    String[][] fcstDate = new String[8][2];

    public String[][] setdata_longtemp(String point_temp) {
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            int month = cal.get(cal.MONTH) + 1;
            int date = cal.get(cal.DATE);
            int hour = cal.get(cal.HOUR);
            int AMPM = cal.get(Calendar.AM_PM);
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DATE, -1);
            if (hour < 06 && AMPM == 0){
                year = yesterday.get(yesterday.YEAR);
                month = yesterday.get(yesterday.MONTH) + 1;
                date = yesterday.get(yesterday.DATE);
            }
            int i = 0;
            int bdate = (year * 10000) + (month * 100) + date;
            String Dday;

            Dday = Integer.toString(bdate) + "0600";

            String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa";
            // 홈페이지에서 받은 키
            String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
            String pageNo = "1";
            String numOfRows = "1";       //한 페이지 결과 수
            String dataType = "xml";      //타입 xml, json 등등 .
            String regId = point_temp;    //지역코드
            String tmFc = Dday;           //오늘날짜
            String url = apiUrl + "?" + "servicekey=" + serviceKey
                    + "&" + "pageNo=" + pageNo + "&" + "numOfRows=" + numOfRows
                    + "&" + "dataType=" + dataType + "&" + "regId=" + regId
                    + "&" + "tmFc=" + tmFc;
            System.out.println(url);

            Document document = Jsoup.connect(url).timeout(15000).get();
            Elements links = document.select("body items item taMin3");
            for (Element element : links) {
                fcstDate[0][0] = element.text();
            }
            links = document.select("body items item taMax3");
            for (Element element : links) {
                fcstDate[0][1] = element.text();
            }
            links = document.select("body items item taMin4");
            for (Element element : links) {
                fcstDate[1][0] = element.text();
            }
            links = document.select("body items item taMax4");
            for (Element element : links) {
                fcstDate[1][1] = element.text();
            }
            links = document.select("body items item taMin5");
            for (Element element : links) {
                fcstDate[2][0] = element.text();
            }
            links = document.select("body items item taMax5");
            for (Element element : links) {
                fcstDate[2][1] = element.text();
            }
            links = document.select("body items item taMin6");
            for (Element element : links) {
                fcstDate[3][0] = element.text();
            }
            links = document.select("body items item taMax6");
            for (Element element : links) {
                fcstDate[3][1] = element.text();
            }
            links = document.select("body items item taMin7");
            for (Element element : links) {
                fcstDate[4][0] = element.text();
            }
            links = document.select("body items item taMax7");
            for (Element element : links) {
                fcstDate[4][1] = element.text();
            }
            links = document.select("body items item taMin8");
            for (Element element : links) {
                fcstDate[5][0] = element.text();
            }
            links = document.select("body items item taMax8");
            for (Element element : links) {
                fcstDate[5][1] = element.text();
            }
            links = document.select("body items item taMin9");
            for (Element element : links) {
                fcstDate[6][0] = element.text();
            }
            links = document.select("body items item taMax9");
            for (Element element : links) {
                fcstDate[6][1] = element.text();
            }
            links = document.select("body items item taMin10");
            for (Element element : links) {
                fcstDate[7][0] = element.text();
            }
            links = document.select("body items item taMax10");
            for (Element element : links) {
                fcstDate[7][1] = element.text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fcstDate;
    }
}
