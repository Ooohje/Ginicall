package com.example.ginicall.Warning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ginicall.MainActivity;
import com.example.ginicall.R;

public class Warning_Activity extends AppCompatActivity {

    TextView body_tv, mt_tv, depth_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        body_tv = (TextView)findViewById(R.id.From_Text);
        mt_tv = (TextView)findViewById(R.id.Magnitude_Text);
        depth_tv = (TextView)findViewById(R.id.Depth_Text);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("eq_lat", 0);
        double lng = intent.getDoubleExtra("eq_lng", 0);
        double mt = intent.getDoubleExtra("eq_mt", 0);
        double depth = intent.getDoubleExtra("eq_depth", 0);
        String body = intent.getStringExtra("body");

        body_tv.setText(body);
        mt_tv.setText("규모 : "+mt);
        depth_tv.setText("진원 깊이 : "+depth+"km");

        double dist = ((MainActivity)MainActivity.mContext).calculate_distance(35.88900, 128.6103, lat, lng);

    }
}