package com.a95.utf_8.super_marquee_textview;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MarqueeView mv;
    private ArrayList<String> contents;
    private TextView tv;
    private MarqueeView mv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = (MarqueeView) findViewById(R.id.mv);
        mv2 = (MarqueeView) findViewById(R.id.mv2);
        tv = (TextView) findViewById(R.id.tv);

        contents = new ArrayList<>();

        contents.add("AAAAAA");
        contents.add("BBBBBB");
        contents.add("CCCCCC");
        contents.add("DDDDDD");
        contents.add("EEEEEE");
        contents.add("FFFFFF");
        contents.add("GGGGGG");
        contents.add("HHHHHH");

        mv.setContents(contents);
        mv.setOffsetWidth(100);
        mv.setTextColor(Color.RED);
        mv.setOnChangeTextListener(new MarqueeView.OnChangeTextListener() {
            @Override
            public void changerText(int position) {
                tv.setText(contents.get(position));
            }
        });

        mv2.setContents(contents);
        mv2.setTextColor(Color.WHITE);
        mv2.setDuration(500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mv2.start();
            }
        },3000);


    }
    @Override
    protected void onResume() {
        super.onResume();

        mv.start();
    }
}
