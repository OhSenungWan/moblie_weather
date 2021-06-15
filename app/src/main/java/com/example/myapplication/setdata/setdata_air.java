package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.Calendar;

public class setdata_air {
    String reg[] = new String[3];
    String city;
    public String[] setdata_air(String city){
        this.city = city;
        for(int i =0; i<3; i++)
        {
            switch (i){
                case 0:
                    reg[i] = "15";
                    break;
                case 1:
                    reg[i] = "15";
                    break;
                case 2:
                    reg[i] = "20";
                    break;
            }
        }
        try {
            String url = this.Link_URL("PM10");                 //미세먼지
            Document document = Jsoup.connect(url).timeout(15000).get();
            setdata(document, 0);
            url = this.Link_URL("PM25");
            document = Jsoup.connect(url).timeout(15000).get();
            setdata(document, 1);
            url = this.Link_URL("O3");
            document = Jsoup.connect(url).timeout(15000).get();
            setdata(document, 2);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return reg;
    }
    public String set_pm10(){
        String grade = "";
        int pm10 = Integer.parseInt(reg[0]);
        if(pm10>=0 && pm10<=30) grade = "좋음";
        else if(pm10>=31 && pm10<=80) grade = "보통";
        else if(pm10>=81 && pm10<=150) grade = "나쁨";
        else if(pm10>=151) grade = "매우나쁨";
        return grade;
    }
    public String set_pm25(){
        String grade = "";
        int pm25 = Integer.parseInt(reg[1]);

        if(pm25>=0 && pm25<=15) grade = "좋음";
        else if(pm25>=16 && pm25<=35) grade = "보통";
        else if(pm25>=36 && pm25<=75) grade = "나쁨";
        else if(pm25>=76) grade = "매우나쁨";
        return grade;
    }

    public String Link_URL(String itemCode){
        String apiUrl = "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst";
        // 홈페이지에서 받은 키
        String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
        String numOfRows = "1";       //한 페이지 결과 수
        String pageNo = "1";
        String dataGubun = "HOUR";    //데이터 구분
        String searchCondition = "MONTH";
        String url = apiUrl + "?" + "servicekey=" + serviceKey
                + "&" + "numOfRows=" + numOfRows + "&" + "pageNo=" + pageNo
                + "&" + "itemCode=" + itemCode + "&" + "dataGubun=" + dataGubun
                + "&" + "searchCondition=" + searchCondition;
        System.out.println(url);
        return url;
    }
    public String[] setdata(Document document, int i){
        Elements links;
        if(city.equals("서울특별시")) {
            links = document.select("body items item seoul");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }

        else if(city.equals("부산광역시")) {
            links = document.select("body items item busan");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("대구광역시")) {
            links = document.select("body items item daegu");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("인천광역시")) {
            links = document.select("body items item incheon");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("광주광역시")) {
            links = document.select("body items item gwangju");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("대전광역시")) {
            links = document.select("body items item daejeon");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("울산광역시")) {
            links = document.select("body items item ulsan");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경기도")) {
            links = document.select("body items item gyeonggi");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("강원도")) {
            links = document.select("body items item gangwon");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("충청북도")) {
            links = document.select("body items item chungbuk");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("충청남도")) {
            links = document.select("body items item chungnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("전라북도")) {
            links = document.select("body items item jeonbuk");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("전라남도")) {
            links = document.select("body items item jeonnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경상북도")) {
            links = document.select("body items item gyeongbuk");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경상남도")) {
            links = document.select("body items item gyeongnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("제주특별자치도")) {
            links = document.select("body items item jeju");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("세종특별자치시")) {
            links = document.select("body items item sejong");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        return reg;
    }

}
