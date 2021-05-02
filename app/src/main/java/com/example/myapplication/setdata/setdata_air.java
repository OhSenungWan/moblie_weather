package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class setdata_air {
    String reg[] = new String[3];
    String city;
    public String[] setdata_air(String city){
        this.city = city;
        System.out.println(city);
        try {
            String url = this.Link_URL("PM10");                 //미세먼지
            Document document = Jsoup.connect(url).get();
            setdata(document, 0);
            url = this.Link_URL("PM25");
            document = Jsoup.connect(url).get();
            setdata(document, 1);
            url = this.Link_URL("O3");
            document = Jsoup.connect(url).get();
            setdata(document, 2);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return reg;
    }
    public String Link_URL(String itemCode){
        String apiUrl = "http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureLIst";
        // 홈페이지에서 받은 키
        String serviceKey = "iBjzgZZ3CoomVMGYL8eQNbd7JCaJXC5qhWtN3hV4s3ZMcUMVbskatC7x2ADNcsPP7aZ4bXWd5BKd0ukxLuL25Q%3D%3D";
        String numOfRows = "1";       //한 페이지 결과 수
        String pageNo = "1";
        String dataGubun = "HOUR";    //데이터 구분
        String searchCondition = "WEEK";
        String url = apiUrl + "?" + "servicekey=" + serviceKey
                + "&" + "numOfRows=" + numOfRows + "&" + "pageNo=" + pageNo
                + "&" + "itemCode=" + itemCode + "&" + "dataGubun=" + dataGubun
                + "&" + "searchCondition=" + searchCondition;
        return url;
    }
    public String[] setdata(Document document, int i){
        Elements links;
        if(city.equals("서울특별시")) {
            links = document.select("body items item daegu");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }

        else if(city.equals("부산광역시")) {
            links = document.select("body items item chungnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("대구광역시")) {
            links = document.select("body items item incheon");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("인천광역시")) {
            links = document.select("body items item daejeon");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("광주광역시")) {
            links = document.select("body items item gyeongbuk");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("대전광역시")) {
            links = document.select("body items item sejong");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("울산광역시")) {
            links = document.select("body items item gwangju");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경기도")) {
            links = document.select("body items item jeonbuk");
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
            links = document.select("body items item ulsan");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("충청남도")) {
            links = document.select("body items item jeonnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("전라북도")) {
            links = document.select("body items item seoul");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("전라남도")) {
            links = document.select("body items item busan");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경상북도")) {
            links = document.select("body items item jeju");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("경상남도")) {
            links = document.select("body items item chungbuk");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("제주특별자치도")) {
            links = document.select("body items item gyeongnam");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        else if(city.equals("세종특별자치시")) {
            links = document.select("body items item gyeonggi");
            for (Element element : links) {
                reg[i] = element.text();
            }
        }
        System.out.println(reg[i]+"\n");
        return reg;
    }
}
