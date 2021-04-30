package com.example.myapplication.setdata;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;

public class setdata_air {
    String reg[][] = new String[17][3];
    public String[][] setdata_air(){
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
        System.out.println(url);
        return url;
    }
    public String[][] setdata(Document document, int i){
        Elements links = document.select("body items item daegu");
        for (Element element : links) {
            reg[0][i] = element.text();
        }
        links = document.select("body items item chungnam");
        for (Element element : links) {
            reg[1][i] = element.text();
        }
        links = document.select("body items item incheon");
        for (Element element : links) {
            reg[2][i] = element.text();
        }
        links = document.select("body items item daejeon");
        for (Element element : links) {
            reg[3][i] = element.text();
        }
        links = document.select("body items item gyeongbuk");
        for (Element element : links) {
            reg[4][i] = element.text();
        }
        links = document.select("body items item sejong");
        for (Element element : links) {
            reg[5][i] = element.text();
        }
        links = document.select("body items item gwangju");
        for (Element element : links) {
            reg[6][i] = element.text();
        }
        links = document.select("body items item jeonbuk");
        for (Element element : links) {
            reg[7][i] = element.text();
        }links = document.select("body items item gangwon");
        for (Element element : links) {
            reg[8][i] = element.text();
        }
        links = document.select("body items item ulsan");
        for (Element element : links) {
            reg[9][i] = element.text();
        }
        links = document.select("body items item jeonnam");
        for (Element element : links) {
            reg[10][i] = element.text();
        }links = document.select("body items item seoul");
        for (Element element : links) {
            reg[11][i] = element.text();
        }
        links = document.select("body items item busan");
        for (Element element : links) {
            reg[12][i] = element.text();
        }
        links = document.select("body items item jeju");
        for (Element element : links) {
            reg[13][i] = element.text();
        }
        links = document.select("body items item chungbuk");
        for (Element element : links) {
            reg[14][i] = element.text();
        }
        links = document.select("body items item gyeongnam");
        for (Element element : links) {
            reg[15][i] = element.text();
        }
        links = document.select("body items item gyeonggi");
        for (Element element : links) {
            reg[16][i] = element.text();
        }

        return reg;
    }
}
