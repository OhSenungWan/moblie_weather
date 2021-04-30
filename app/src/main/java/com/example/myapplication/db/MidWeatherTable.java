package com.example.myapplication.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MidWeatherTable {
    private String state;
    private String city;
    @PrimaryKey
    @NonNull
    private String code1;
    private String code2;

    public MidWeatherTable(String state, String city, @NonNull String code1, String code2) {
        this.state = state;
        this.city = city;
        this.code1 = code1;
        this.code2 = code2;
    }

    @Override
    public String toString() {
        return "MidWeatherTable{" +
                "state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", code1='" + code1 + '\'' +
                ", code2='" + code2 + '\'' +
                '}';
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getCode1() {
        return code1;
    }

    public String getCode2() {
        return code2;
    }
}
