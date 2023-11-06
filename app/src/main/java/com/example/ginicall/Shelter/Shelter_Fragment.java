package com.example.ginicall.Shelter;

import static android.graphics.Bitmap.createBitmap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ginicall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelLayerOptions;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.label.LabelTextStyle;
import com.kakao.vectormap.label.LabelTransition;
import com.kakao.vectormap.label.OrderingType;
import com.kakao.vectormap.label.Transition;

public class Shelter_Fragment extends Fragment {
    private View view;
    private Context context;
    private KakaoMap kakaoMap;
    private LabelLayer labelLayer;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_shelter_, container, false);
        context = container.getContext();
        MapView mapView = view.findViewById(R.id.shelter_map_view);
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
            }

            @Override
            public void onMapError(Exception error) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.d("MapError", "onMapError: "+error.toString());
            }
        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap map) {
                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                kakaoMap = map;
                labelLayer = kakaoMap.getLabelManager().getLayer();
                locateMarker(map);
                moveCamera(LatLng.from(35.88900, 128.6103));
            }
        });

        return view;
    }

    private void locateMarker(KakaoMap map) {
        // TODO : 임시로 경북대 근처 좌표로 설정 (사용량 많이 나와서) -> 나중에는 북구 & 동구 데이터인 "Shelter_Info"로 수정하기
        db.collection("Shelter_for_test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        double lat = document.getDouble("위도");
                        double lng = document.getDouble("경도");
                        String name = document.getString("지진해일대피소명");

                        LatLng pos = LatLng.from(lat,lng);
                        LabelStyles styles = map.getLabelManager().addLabelStyles(LabelStyles.from(
                                LabelStyle.from(R.drawable.new_marker).setZoomLevel(5),
                                LabelStyle.from(R.drawable.new_marker).setTextStyles(LabelTextStyle.from(context, R.style.labelTextStyle_1)).setZoomLevel(15)));

                        // 라벨 생성
                        labelLayer.addLabel(LabelOptions.from(document.getId(), pos).setStyles(styles).setTexts(name));
                    }
                } else {
                    Log.w("GetDataFromFirestore", "Error getting documents.", task.getException());
                }
            }
        });

        kakaoMap.setOnLabelClickListener(new KakaoMap.OnLabelClickListener() {
            @Override
            public void onLabelClicked(KakaoMap kakaoMap, LabelLayer layer, Label label) {
                // 라벨 클릭 시 카메라 이동
                String documentID = label.getLabelId();

                Intent it = new Intent(context, Shelter_Info_Activity.class);
                it.putExtra("documentID", documentID);
                startActivity(it);
            }
        });
    }

    private void moveCamera(LatLng position) {
        kakaoMap.moveCamera(CameraUpdateFactory.newCenterPosition(position),CameraAnimation.from(10, false,false));
    }

}