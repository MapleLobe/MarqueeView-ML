package com.a95.utf_8.super_marquee_textview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private MarqueeView mv;
    private ArrayList<String> contents;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mv = (MarqueeView) findViewById(R.id.mv);
        tv = (TextView) findViewById(R.id.tv);

        contents = new ArrayList<>();

        contents.add("111111");
        contents.add("222222");
        contents.add("333333");
        contents.add("444444");
        contents.add("555555");
        contents.add("666666");
        contents.add("777777");
        contents.add("888888");

        mv.setContents(contents);
        mv.setOffsetWidth(100);
        mv.setTextColor(Color.RED);
        mv.setOnChangeTextListener(new MarqueeView.OnChangeTextListener() {
            @Override
            public void changerText(int position) {
                tv.setText(contents.get(position));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

        mv.start();
    }
}
