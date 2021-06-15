package com.example.myapplication.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ShowListView extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;

    AppDatabase db;

    private List<String> city;
    private int order = 1;

    /** 0 : city1 (ex 서울특별시)
     *  1 : city2 (ex 종로구)
     *  2 : city3 (ex 청운효자동)
     *  3 : 중기 기온 예보 코드
     *  4 : 중기 육상 예보 코드 **/
    private String data[] = new String[5];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_listview);

        listView = findViewById(R.id.listSecond);

        db = AppDatabase.getDbInstance(this.getApplicationContext());

        //처음 city 보여주기
        city = getFirstCity();

        //리스트뷰 보이기new
        listViewUpdate();

        /** 클릭 리스너 **/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data[order - 1] = city.get(position);
                if (order == 3) {
                    // 세번째 도시까지 클릭시 intent를 통해 값 이동
                    putExtra();
                    finish();
                    return;
                }

                city = getOtherCity(order++, city.get(position));

                listViewUpdate();
            }
        });

    }

    private void putExtra() {
        Intent intent = new Intent();
        String tempCity = "";

        int X = db.nationalWeatherInterface().getX(data[2]);
        int Y = db.nationalWeatherInterface().getY(data[2]);

        intent.putExtra("cityName", data[0]+ " "+ data[1]+ " "+ data[2]);
        intent.putExtra("x", X);
        intent.putExtra("y", Y);

        //중기 기온, 예보 코드

        if(!data[0].contains("도")){
            tempCity= data[0].substring(0, 2);
        }else{
            tempCity= data[1].substring(0, 2);
        }

        data[3] = db.midWeatherInterface().getCode1(tempCity + "%");
        data[4] = db.midWeatherInterface().getCode2(tempCity + "%");
        if(data[0].equals("광주광역시")){
            data[3] = "11F20501";
            data[4] = "11F20000";
        }

        intent.putExtra("code1", data[3]);
        intent.putExtra("code2", data[4]);

        setResult(RESULT_OK, intent);
    }

    public List<String> getFirstCity() {
        List<String> city = new ArrayList<String>();
        city.add("서울특별시");
        city.add("경기도");
        city.add("강원도");
        city.add("충청북도");
        city.add("충청남도");
        city.add("전라북도");
        city.add("전라남도");
        city.add("경상북도");
        city.add("경상남도");
        city.add("부산광역시");
        city.add("대구광역시");
        city.add("인천광역시");
        city.add("광주광역시");
        city.add("대전광역시");
        city.add("울산광역시");
        city.add("세종특별자치시");
        city.add("제주특별자치도");
        return city;
    }

    public List<String> getOtherCity(int order, String prevCity) {
        List<String> city = null;
        if (order == 1) {
            city = db.nationalWeatherInterface().getCity2(prevCity);
        } else {
            city = db.nationalWeatherInterface().getCity3(prevCity);
        }
        return city;
    }

    /**
     * 리스트 뷰 보이기
     **/
    public void listViewUpdate() {
        city.removeIf(n -> n.equals(""));
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, city);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
