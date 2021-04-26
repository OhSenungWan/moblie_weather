package com.example.myapplication.db;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class ShowListView extends AppCompatActivity {

    private ListView listView;
    ArrayAdapter<String> adapter;

    AppDatabase db;

    private List<String> city;
    private int order = 1;
    private String cityName = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_listview);

        listView = findViewById(R.id.listSecond);

        db = AppDatabase.getDbInstance(this.getApplicationContext());

        //처음 city 보여주기
        city = getFirstCity();


        //리스트뷰 보이기
        listViewUpdate();

        /** 클릭 리스너 **/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (order == 3) {
                    // 세번째 도시까지 클릭시 intent를 통해 값 이동
                    cityName += city.get(position);
                    int X = db.nationalWeatherInterface().getX(city.get(position));
                    int Y = db.nationalWeatherInterface().getY(city.get(position));

                    Intent intent = new Intent();
                    intent.putExtra("cityName", cityName);
                    intent.putExtra("x", X);
                    intent.putExtra("y", Y);

                    setResult(RESULT_OK, intent);
                    finish();
                    return;
                }

                cityName += city.get(position) + " ";

                city = getOtherCity(order++, city.get(position));

                listViewUpdate();
            }
        });

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
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, city);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
