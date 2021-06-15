package com.example.myapplication.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {NationalWeatherTable.class, MidWeatherTable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract NationalWeatherInterface nationalWeatherInterface();
    public abstract MidWeatherInterface midWeatherInterface();

    private static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context){

        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "db")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
