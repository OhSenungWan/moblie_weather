package com.example.myapplication.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MidWeatherInterface {

    @Query("SELECT * FROM MidWeatherTable")
    List<MidWeatherTable> getAll();

    @Insert
    void insert(MidWeatherTable midWeatherTable);

    @Query("SELECT code1 FROM MidWeatherTable WHERE city LIKE :tempCity")
    String getCode1(String tempCity);

    @Query("SELECT code2 FROM MidWeatherTable WHERE city LIKE :tempCity")
    String getCode2(String tempCity);

    @Query("DELETE FROM MidWeatherTable")
    void deleteAll();

}
