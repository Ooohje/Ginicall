package com.example.ginicall;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.Manifest;
import android.widget.Toast;

import com.example.ginicall.Shelter.Shelter_Fragment;
import com.example.ginicall.Temphouse.Temphouse_Fragment;
import com.example.ginicall.Tip.Tip_Fragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    Shelter_Fragment shelter_frag = new Shelter_Fragment();
    Temphouse_Fragment temphous_frag = new Temphouse_Fragment();
    Tip_Fragment tip_frag = new Tip_Fragment();
    public static Context mContext;
    private final String TAG = "MyFirebaseMsgService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        //다크모드 해제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        navbar = (BottomNavigationView) findViewById(R.id.activity_main_navbar);

        //위치 권한 확인 & 요청
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }
        // 알림 권한 확인 & 요청
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_DENIED){
            Log.d("권한" , "알림 권한 없음");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 0);
        }

        // FCM 토큰 가져오기
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                    return;
                }

                String token = task.getResult();
                Log.d(TAG, "FCM registration token : " + token);
            }
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.main_color));

        changeFragment(R.id.main_fragment, shelter_frag);
        navbar.setOnItemSelectedListener(item -> {
            FragmentManager fm = getSupportFragmentManager();
            for (int i = 0; i < fm.getBackStackEntryCount(); i++)
                fm.popBackStack();

            int itemId = item.getItemId();
            if (itemId == R.id.shelter) {
                changeFragment(R.id.main_fragment, shelter_frag);
                return true;
            } else if (itemId == R.id.temphouse) {
                changeFragment(R.id.main_fragment, temphous_frag);
                return true;
            } else if (itemId == R.id.tip) {
                changeFragment(R.id.main_fragment, tip_frag);
                return true;
            }
            return false;
        });
    }

    /** address주소의 프래그먼트 슬롯을 frag라는 프래그먼트로 교체
     *  @param address 프래그먼트 슬롯의 주소
     *  @param frag 변경할 프래그먼트   */
    private void changeFragment(int address, Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(address, frag);
        ft.commit();
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

        return R * theta;
    }
}