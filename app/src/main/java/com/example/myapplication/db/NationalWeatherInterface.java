package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NationalWeatherInterface {

    @Query("SELECT * FROM NationalWeatherTable")
    List<NationalWeatherTable> getAll();

    @Query("SELECT DISTINCT city2 FROM NationalWeatherTable WHERE city1 = :firstCity")
    List<String> getCity2(String firstCity);

    @Query("SELECT DISTINCT city3 FROM NationalWeatherTable WHERE city2 = :secondCity")
    List<String> getCity3(String secondCity);

    @Query("SELECT x FROM NationalWeatherTable WHERE city3= :thirdCity")
    int getX(String thirdCity);

    @Query("SELECT y FROM NationalWeatherTable WHERE city3= :thirdCity")
    int getY(String thirdCity);

    @Insert
    void insert(NationalWeatherTable nationalWeatherTable);

    @Query("DELETE FROM NationalWeatherTable")
    void deleteAll();
}
