package com.example.my28_recyclerview3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment3 extends Fragment {

    MainActivity activity;
    String sentdData , receiveData;

  TextView tv3;



    //화면이 붙을때 나오는 메서드



   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        //프래그먼트 1에서 프래그먼트 2로 보낼 문자열
        sentdData = "data from fragment1";
        //프래그먼트3에서 받을 문자열
        receiveData = "";

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3 ,container , false);

        tv3 = viewGroup.findViewById(R.id.textView3);





        return viewGroup;
    }
}