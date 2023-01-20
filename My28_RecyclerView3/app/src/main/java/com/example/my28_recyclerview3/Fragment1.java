package com.example.my28_recyclerview3;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        //프래그먼트1에서 보낼 내가 만든 문자열



        //프래그먼트3에서 받을 문자열 변수 초기화
        receiveData = "";
    }





}
