package com.example.ginicall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.ginicall.Shelter.Shelter_Fragment;
import com.example.ginicall.Temphouse.Temphouse_Fragment;
import com.example.ginicall.Tip.Tip_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView navbar;
    Shelter_Fragment shelter_frag = new Shelter_Fragment();
    Temphouse_Fragment temphous_frag = new Temphouse_Fragment();
    Tip_Fragment tip_frag = new Tip_Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //다크모드 해제
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        navbar = (BottomNavigationView) findViewById(R.id.activity_main_navbar);

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

}