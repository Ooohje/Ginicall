package com.example.ginicall.Shelter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginicall.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Shelter_Info_Activity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView title, dist, addr, type, peocnt, area;
    private LinearLayout mainlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_info);

        String documentID = getIntent().getStringExtra("documentID");

        title = findViewById(R.id.shelter_info_title_tv);
        dist = findViewById(R.id.shelter_info_dist_tv);
        addr = findViewById(R.id.shelter_info_address_tv);
        type = findViewById(R.id.shelter_info_type_tv);
        peocnt = findViewById(R.id.shelter_info_peocnt_tv);
        area = findViewById(R.id.shelter_info_area_tv);
        mainlayout = findViewById(R.id.shelter_info_mainlayout);

        // 뒤로가기 버튼
        findViewById(R.id.shelter_info_backButton).setOnClickListener(v -> finish());

        // TODO : 임시로 경북대 근처 좌표로 설정 (사용량 많이 나와서) -> 나중에는 북구 & 동구 데이터인 "Shelter_Info"로 수정하기
        DocumentReference doc_ref = db.collection("Shelter_for_test").document(documentID);
        doc_ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                title.setText(task.getResult().get("지진해일대피소명").toString());
                dist.setText("?km (도보 ?분 소요)");
                addr.setText("주소 : " + task.getResult().get("소재지 주소").toString());
                type.setText("대피소 유형 : " + task.getResult().get("지진해일대피소유형").toString() + " - " + task.getResult().get("지진해일대피소유형구분").toString());
                peocnt.setText("수용 인원 : " + task.getResult().get("최대수용인원수").toString() + "명");
                area.setText("수용 면적 : " + task.getResult().get("수용가능면적").toString() + "m²");

                mainlayout.setVisibility(LinearLayout.VISIBLE);
            }
        });

        // TODO : 전화번호를 통해 연결

        // TODO : 지도 눌러서 길찾기 수행

    }
}