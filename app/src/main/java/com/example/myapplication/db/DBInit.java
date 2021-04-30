package com.example.myapplication.db;

import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBInit extends AppCompatActivity {

    AssetManager assetManager;
    AppDatabase db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataFromAssetAndInsertData();

        finish();
    }

    public void getDataFromAssetAndInsertData() {

        InputStream inputStream = null;
        assetManager = getResources().getAssets();
        db = AppDatabase.getDbInstance(this.getApplicationContext());
        db.nationalWeatherInterface().deleteAll();
        db.midWeatherInterface().deleteAll();

        String line = "";

        try {
            inputStream = assetManager.open("NationalWeatherDB.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                String[] stSplit = line.split("\t");
                db.nationalWeatherInterface().insert(new NationalWeatherTable(
                        Long.parseLong(stSplit[0]), stSplit[1], stSplit[2], stSplit[3], Integer.parseInt(stSplit[4]), Integer.parseInt(stSplit[5])));
            }

            inputStream = assetManager.open("MidWeatherDB.txt");
            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                String[] stSplit = line.split("\t");
                db.midWeatherInterface().insert(new MidWeatherTable(stSplit[0], stSplit[1], stSplit[2], stSplit[3]));
            }



        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

    }

}
