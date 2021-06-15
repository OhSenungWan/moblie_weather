package com.example.myapplication.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NationalWeatherTable {
    @PrimaryKey
    private long code;
    private String city1;
    private String city2;
    private String city3;
    private int x;
    private int y;

    public NationalWeatherTable(long code, String city1, String city2, String city3, int x, int y) {
        this.code = code;
        this.city1 = city1;
        this.city2 = city2;
        this.city3 = city3;
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "NationalWeatherTable{" +
                "code=" + code +
                ", name1='" + city1 + '\'' +
                ", name2='" + city2 + '\'' +
                ", name3='" + city3 + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
    public long getCode() {
        return code;
    }
    public String getCity1() {
        return city1;
    }
    public String getCity2() {
        return city2;
    }
    public String getCity3() {
        return city3;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
