package com.example.ginicall.Temphouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginicall.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class Temphouse_Info_Activity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView title, dist, addr, type, peocnt, area;
    private LinearLayout mainlayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temphouse_info);

        String documentID = getIntent().getStringExtra("documentID");

        title = findViewById(R.id.temphouse_info_title_tv);
        dist = findViewById(R.id.temphouse_info_dist_tv);
        addr = findViewById(R.id.temphouse_info_address_tv);
        mainlayout = findViewById(R.id.temphouse_info_mainlayout);
        // 뒤로가기 버튼
        findViewById(R.id.temphouse_info_backButton).setOnClickListener(v -> finish());

        DocumentReference doc_ref = db.collection("Temphouse_Info").document(documentID);
        doc_ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                title.setText(task.getResult().get("위치명").toString());
                double tmp_dist = calculate_distance(35.88900, 128.6103, task.getResult().getDouble("경도"), task.getResult().getDouble("위도"));
                if (tmp_dist >= 1000) {
                    tmp_dist/=1000;
                    DecimalFormat df = new DecimalFormat("#.##");
                    tmp_dist= Double.parseDouble(df.format(tmp_dist));
                    dist.setText("현재 위치로부터 : " + tmp_dist + "km");
                } else {
                    DecimalFormat df = new DecimalFormat("#.##");
                    tmp_dist= Double.parseDouble(df.format(tmp_dist));
                    dist.setText("현재 위치로부터 : " + tmp_dist + "m");
                }
                addr.setText("주소 : " + task.getResult().get("주소").toString());
                mainlayout.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }
    /** 두 좌표(위도, 경도) 사이의 거리를 계산하는 함수
     *  @detail 참고 내용 : https://en.wikipedia.org/wiki/Haversine_formula
     *  @param latA a의 위도
     *  @param lngA a의 경도
     *  @param latB b의 위도
     *  @param lngB b의 경도
     *  @return 두 지점 사이의 거리 (단위 : m) */
    public double calculate_distance(double latA, double lngA, double latB, double lngB) {
        int R = 6371000; // 지구의 반지름 (단위 : m)
        double delta_lat = Math.toRadians(latB - latA);
        double delta_lon = Math.toRadians(lngB - lngA);

        double hav_lat = Math.sin(delta_lat / 2) * Math.sin(delta_lat / 2);
        double hav_lon = Math.sin(delta_lon / 2) * Math.sin(delta_lon / 2);

        double hav_theta = hav_lat + Math.cos(Math.toRadians(latA)) * Math.cos(Math.toRadians(latB)) * hav_lon;
        double theta = 2 * Math.atan2(Math.sqrt(hav_theta), Math.sqrt(1 - hav_theta));

        return (R * theta);
    }
}