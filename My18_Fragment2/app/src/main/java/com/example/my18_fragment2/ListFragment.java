package com.example.my18_fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {

    MainActivity activity;
    Button btnImg1, btnImg2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewgroup = (ViewGroup) inflater.inflate(R.layout.listfragment,
                container, false);
        activity = (MainActivity) getActivity();

        btnImg1 = viewgroup.findViewById(R.id.btnImg1);
        btnImg2 = viewgroup.findViewById(R.id.btnImg2);

        //이미지1
        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onImageSelected(R.drawable.dream01);
            }
        });

        // 이미지2
        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onImageSelected(R.drawable.dream02);
            }
        });


        return viewgroup;
    }



}
