package com.example.ginicall.Warning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ginicall.R;

public class Warning_Tip_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning_tip);

        getWindow().setStatusBarColor(getResources().getColor(R.color.sub_color));

        findViewById(R.id.tip_title1).setOnClickListener(v -> {
            if(findViewById(R.id.tip_contents1).getVisibility() == v.GONE){
                findViewById(R.id.tip_contents1).setVisibility(v.VISIBLE);
            }else{
                findViewById(R.id.tip_contents1).setVisibility(v.GONE);
            }
        });

        findViewById(R.id.tip_title2).setOnClickListener(v -> {
            if(findViewById(R.id.tip_contents2).getVisibility() == v.GONE){
                findViewById(R.id.tip_contents2).setVisibility(v.VISIBLE);
            }else{
                findViewById(R.id.tip_contents2).setVisibility(v.GONE);
            }
        });

        findViewById(R.id.tip_title3).setOnClickListener(v -> {
            if(findViewById(R.id.tip_contents3).getVisibility() == v.GONE){
                findViewById(R.id.tip_contents3).setVisibility(v.VISIBLE);
            }else{
                findViewById(R.id.tip_contents3).setVisibility(v.GONE);
            }
        });

        findViewById(R.id.tip_title4).setOnClickListener(v -> {
            if(findViewById(R.id.tip_contents4).getVisibility() == v.GONE){
                findViewById(R.id.tip_contents4).setVisibility(v.VISIBLE);
            }else{
                findViewById(R.id.tip_contents4).setVisibility(v.GONE);
            }
        });

        findViewById(R.id.tip_title5).setOnClickListener(v -> {
            if(findViewById(R.id.tip_contents5).getVisibility() == v.GONE){
                findViewById(R.id.tip_contents5).setVisibility(v.VISIBLE);
            }else{
                findViewById(R.id.tip_contents5).setVisibility(v.GONE);
            }
        });
    }
}