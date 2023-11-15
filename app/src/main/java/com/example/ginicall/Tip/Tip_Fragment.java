package com.example.ginicall.Tip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ginicall.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Tip_Fragment extends Fragment {
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_tip_, container, false);


        view.findViewById(R.id.tip_normal_title1).setOnClickListener(v -> {
            if(view.findViewById(R.id.tip_normal_contents1).getVisibility() == v.GONE){
                view.findViewById(R.id.tip_normal_contents1).setVisibility(v.VISIBLE);
            }else{
                view.findViewById(R.id.tip_normal_contents1).setVisibility(v.GONE);
            }
        });

        view.findViewById(R.id.tip_normal_title2).setOnClickListener(v -> {
            if(view.findViewById(R.id.tip_normal_contents2).getVisibility() == v.GONE){
                view.findViewById(R.id.tip_normal_contents2).setVisibility(v.VISIBLE);
            }else{
                view.findViewById(R.id.tip_normal_contents2).setVisibility(v.GONE);
            }
        });

        view.findViewById(R.id.tip_normal_title3).setOnClickListener(v -> {
            if(view.findViewById(R.id.tip_normal_contents3).getVisibility() == v.GONE){
                view.findViewById(R.id.tip_normal_contents3).setVisibility(v.VISIBLE);
            }else{
                view.findViewById(R.id.tip_normal_contents3).setVisibility(v.GONE);
            }
        });

        view.findViewById(R.id.tip_normal_title4).setOnClickListener(v -> {
            if(view.findViewById(R.id.tip_normal_contents4).getVisibility() == v.GONE){
                view.findViewById(R.id.tip_normal_contents4).setVisibility(v.VISIBLE);
            }else{
                view.findViewById(R.id.tip_normal_contents4).setVisibility(v.GONE);
            }
        });

        view.findViewById(R.id.tip_normal_title5).setOnClickListener(v -> {
            if(view.findViewById(R.id.tip_normal_contents5).getVisibility() == v.GONE){
                view.findViewById(R.id.tip_normal_contents5).setVisibility(v.VISIBLE);
            }else{
                view.findViewById(R.id.tip_normal_contents5).setVisibility(v.GONE);
            }
        });




        return view;
    }
}