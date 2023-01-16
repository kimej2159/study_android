package com.example.my17_fragment1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MainFragment extends Fragment {
    // 내가 속한 액티비티를 개발자가 알게 해준다
    MainActivity activity;

    // 프래그먼트 화면 붙이기 : 반드시 onCreateView를 오버라이드 시켜야한다
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.mainfragment,
                container, false);
        activity = (MainActivity) getActivity();
        viewGroup.findViewById(R.id.btnmain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.onChangeFrag(1);
            }
        });
        return viewGroup;
    }
}
