package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Calendar;

import static android.graphics.Color.DKGRAY;

public class MainActivity extends AppCompatActivity {

    TextView location,temp,comment;
    TextView time_color1,time_color2,time_color3,time_color4,time_color5;
    TextView we_element1,we_element2,we_element3,we_element4,we_element5;
    ImageView main_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStart();
    }
    public void setStart(){
        location=(TextView)findViewById(R.id.location);
        temp=(TextView)findViewById(R.id.temp);
        comment=(TextView)findViewById(R.id.comment);

        we_element1=(TextView)findViewById(R.id.we_element1);
        we_element2=(TextView)findViewById(R.id.we_element2);
        we_element3=(TextView)findViewById(R.id.we_element3);
        we_element4=(TextView)findViewById(R.id.we_element4);
        we_element5=(TextView)findViewById(R.id.we_element5);

        main_img=(ImageView)findViewById(R.id.main_img);


        main_img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                getWindow().getDecorView().setBackgroundColor(Color.rgb(135,140,150));


            }
        });

        we_element1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TextView가 클릭될 시 할 코드작성
                we_element1.setTypeface(we_element1.getTypeface(), Typeface.BOLD);


                // TextView가 클릭될 시 할 코드작성
                we_element2.setTypeface(we_element2.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element3.setTypeface(we_element3.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element4.setTypeface(we_element4.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element5.setTypeface(we_element5.getTypeface(), Typeface.ITALIC);

            }
        });
        we_element2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // TextView가 클릭될 시 할 코드작성
                we_element1.setTypeface(we_element1.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element2.setTypeface(we_element2.getTypeface(), Typeface.BOLD);


                // TextView가 클릭될 시 할 코드작성
                we_element3.setTypeface(we_element3.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element4.setTypeface(we_element4.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element5.setTypeface(we_element5.getTypeface(), Typeface.ITALIC);

            }
        });

        we_element3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // TextView가 클릭될 시 할 코드작성
                we_element1.setTypeface(we_element1.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element2.setTypeface(we_element2.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element3.setTypeface(we_element3.getTypeface(), Typeface.BOLD);


                // TextView가 클릭될 시 할 코드작성
                we_element4.setTypeface(we_element4.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element5.setTypeface(we_element5.getTypeface(), Typeface.ITALIC);

            }
        });
        we_element4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // TextView가 클릭될 시 할 코드작성
                we_element1.setTypeface(we_element1.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element2.setTypeface(we_element2.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element3.setTypeface(we_element3.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element4.setTypeface(we_element4.getTypeface(), Typeface.BOLD);


                // TextView가 클릭될 시 할 코드작성
                we_element5.setTypeface(we_element5.getTypeface(), Typeface.ITALIC);
            }});
        we_element5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // TextView가 클릭될 시 할 코드작성
                we_element1.setTypeface(we_element1.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element2.setTypeface(we_element2.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element3.setTypeface(we_element3.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element4.setTypeface(we_element4.getTypeface(), Typeface.ITALIC);


                // TextView가 클릭될 시 할 코드작성
                we_element5.setTypeface(we_element5.getTypeface(), Typeface.BOLD);
            }
        });
        
    }

}