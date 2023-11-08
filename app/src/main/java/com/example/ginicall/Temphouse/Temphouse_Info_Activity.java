package com.example.ginicall.Temphouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ginicall.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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
                dist.setText("?km (도보 ?분 소요)");
                addr.setText("주소 : " + task.getResult().get("주소").toString());
                mainlayout.setVisibility(LinearLayout.VISIBLE);
            }
        });
    }
}