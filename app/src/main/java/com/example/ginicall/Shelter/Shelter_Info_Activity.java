package com.example.ginicall.Shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginicall.MainActivity;
import com.example.ginicall.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

public class Shelter_Info_Activity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView title, dist, addr, type, peocnt, area;
    private LinearLayout mainlayout, callBtn, mapBtn;
    private DecimalFormat decFormat = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        String documentID = getIntent().getStringExtra("documentID");
        String collectionName = getIntent().getStringExtra("collectionName");

        title = findViewById(R.id.shelter_info_title_tv);
        dist = findViewById(R.id.shelter_info_dist_tv);
        addr = findViewById(R.id.shelter_info_address_tv);
        type = findViewById(R.id.shelter_info_type_tv);
        peocnt = findViewById(R.id.shelter_info_peocnt_tv);
        area = findViewById(R.id.shelter_info_area_tv);
        mainlayout = findViewById(R.id.shelter_info_mainlayout);
        callBtn = findViewById(R.id.shelter_info_call_btn);
        mapBtn = findViewById(R.id.shelter_info_map_btn);

        // 뒤로가기 버튼
        findViewById(R.id.shelter_info_backButton).setOnClickListener(v -> finish());

        DocumentReference doc_ref = db.collection(collectionName).document(documentID);
        doc_ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                title.setText(task.getResult().get("지진해일대피소명").toString());

                double lat = task.getResult().getDouble("위도"), lng = task.getResult().getDouble("경도");

                String phone = task.getResult().get("대피소전화번호").toString().replaceAll("-", "");
                String address = task.getResult().get("소재지 주소").toString();

                addr.setText("주소 : " + address);
                type.setText("대피소 유형 : " + task.getResult().get("지진해일대피소유형").toString() + " - " + task.getResult().get("지진해일대피소유형구분").toString());
                peocnt.setText("수용 인원 : " + decFormat.format(task.getResult().getLong("최대수용인원수")) + "명");
                area.setText("수용 면적 : " + decFormat.format(task.getResult().getLong("수용가능면적")) + "m²");

                double tmp_dist = calculate_distance(35.88900, 128.6103, task.getResult().getDouble("위도"), task.getResult().getDouble("경도"));
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


                callBtn.setOnClickListener(v -> {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                    dialIntent.setData(Uri.parse("tel:" + phone));
                    startActivity(dialIntent);
                });

                mapBtn.setOnClickListener(v -> {
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lng + "?q=" + address));
                    startActivity(mapIntent);
                });

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