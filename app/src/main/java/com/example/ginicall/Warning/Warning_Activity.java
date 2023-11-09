package com.example.ginicall.Warning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.ginicall.MainActivity;
import com.example.ginicall.R;

public class Warning_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warning);

        Intent intent = getIntent();
        double lat = intent.getDoubleExtra("eq_lat", 0);
        double lng = intent.getDoubleExtra("eq_lng", 0);
        double mt = intent.getDoubleExtra("eq_mt", 0);

        double dist = ((MainActivity)MainActivity.mContext).calculate_distance(35.88900, 128.6103, lat, lng);

        Toast.makeText(this, "("+lat+", "+lng+")-"+dist+" / "+mt, Toast.LENGTH_SHORT).show();
    }
}