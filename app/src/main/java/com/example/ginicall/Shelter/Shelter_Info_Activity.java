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

                // TODO : 경북대 중심에서 부터의 거리 -> 현재 위치에서의 거리
                int dist_meter = (int)((MainActivity)MainActivity.mContext).calculate_distance(35.88900, 128.6103, lat, lng);
                if (dist_meter >= 1000) dist.setText((dist_meter/ 1000.0) + "km (도보 ?분 소요)");
                else dist.setText(dist_meter + "m (도보 ?분 소요)");

                String phone = task.getResult().get("대피소전화번호").toString().replaceAll("-", "");
                String address = task.getResult().get("소재지 주소").toString();

                addr.setText("주소 : " + address);
                type.setText("대피소 유형 : " + task.getResult().get("지진해일대피소유형").toString() + " - " + task.getResult().get("지진해일대피소유형구분").toString());
                peocnt.setText("수용 인원 : " + decFormat.format(task.getResult().getLong("최대수용인원수")) + "명");
                area.setText("수용 면적 : " + decFormat.format(task.getResult().getLong("수용가능면적")) + "m²");

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
}