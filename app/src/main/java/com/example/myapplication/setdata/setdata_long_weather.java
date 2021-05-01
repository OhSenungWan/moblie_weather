package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

public class setdata_long_weather {
    String[][] fcstDate = new String[8][4];
    public String[][] setdata_longweather(String point_weather){
        try {
            Calendar cal = Calendar.getInstance();
            int year = cal.get(cal.YEAR);
            int month = cal.get(cal.MONTH) + 1;
            int date = cal.get(cal.DATE);
            int i = 0;
            int bdate = (year * 10000) + (month * 100) + date;
            String Dday;

            Dday = Integer.toString(bdate)+"0600";

            String apiUrl = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst";
            // 홈페이지에서 받은 키
            String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
            String pageNo = "1";
            String numOfRows = "1";       //한 페이지 결과 수
            String dataType = "xml";      //타입 xml, json 등등 .
            String regId = "11B00000";    //지역코드
            String tmFc = Dday;           //오늘날짜
            String url = apiUrl + "?" + "servicekey=" + serviceKey
                    + "&" + "pageNo=" + pageNo + "&" + "numOfRows=" + numOfRows
                    + "&" + "dataType=" + dataType + "&" + "regId=" + regId
                    + "&" + "tmFc=" + tmFc;
            Document document = Jsoup.connect(url).get();
            Elements links = document.select("body items item rnSt3Am");
            for (Element element : links) {
                fcstDate[0][0] = element.text();
            }
            links = document.select("body items item rnSt3Pm");
            for (Element element : links) {
                fcstDate[0][1] = element.text();
            }
            links = document.select("body items item wf3Am");
            for (Element element : links) {
                fcstDate[0][2] = element.text();
            }
            links = document.select("body items item wf3Pm");
            for (Element element : links) {
                fcstDate[0][3] = element.text();
            }
            links = document.select("body items item rnSt4Am");
            for (Element element : links) {
                fcstDate[1][0] = element.text();
            }
            links = document.select("body items item rnSt4Pm");
            for (Element element : links) {
                fcstDate[1][1] = element.text();
            }
            links = document.select("body items item wf4Am");
            for (Element element : links) {
                fcstDate[1][2] = element.text();
            }
            links = document.select("body items item wf4Pm");
            for (Element element : links) {
                fcstDate[1][3] = element.text();
            }
            links = document.select("body items item rnSt5Am");
            for (Element element : links) {
                fcstDate[2][0] = element.text();
            }
            links = document.select("body items item rnSt5Pm");
            for (Element element : links) {
                fcstDate[2][1] = element.text();
            }
            links = document.select("body items item wf5Am");
            for (Element element : links) {
                fcstDate[2][2] = element.text();
            }
            links = document.select("body items item wf5Pm");
            for (Element element : links) {
                fcstDate[2][3] = element.text();
            }
            links = document.select("body items item rnSt6Am");
            for (Element element : links) {
                fcstDate[3][0] = element.text();
            }
            links = document.select("body items item rnSt6Pm");
            for (Element element : links) {
                fcstDate[3][1] = element.text();
            }
            links = document.select("body items item wf6Am");
            for (Element element : links) {
                fcstDate[3][2] = element.text();
            }
            links = document.select("body items item wf6Pm");
            for (Element element : links) {
                fcstDate[3][3] = element.text();
            }
            links = document.select("body items item rnSt7Am");
            for (Element element : links) {
                fcstDate[4][0] = element.text();
            }
            links = document.select("body items item rnSt7Pm");
            for (Element element : links) {
                fcstDate[4][1] = element.text();
            }
            links = document.select("body items item wf7Am");
            for (Element element : links) {
                fcstDate[4][2] = element.text();
            }
            links = document.select("body items item wf7Pm");
            for (Element element : links) {
                fcstDate[4][3] = element.text();
            }
            links = document.select("body items item rnSt8");
            for (Element element : links) {
                fcstDate[5][0] = element.text();
            }
            links = document.select("body items item wf8");
            for (Element element : links) {
                fcstDate[5][2] = element.text();
            }
            links = document.select("body items item rnSt9");
            for (Element element : links) {
                fcstDate[6][0] = element.text();
            }
            links = document.select("body items item wf9");
            for (Element element : links) {
                fcstDate[6][2] = element.text();
            }
            links = document.select("body items item rnSt10");
            for (Element element : links) {
                fcstDate[7][0] = element.text();
            }
            links = document.select("body items item wf10");
            for (Element element : links) {
                fcstDate[7][2] = element.text();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fcstDate;
    }
}
