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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment2 extends Fragment {


    MainActivity activity;
    String sentdData , receiveData;


    TextView tv2;
    RecyclerView recyclerView;


    ArrayList<SingerDTO> dtos;

    SingerAdapter adapter;

    //화면이 붙을때 나오는 메서드
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);


        //프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        //프래그먼트 1에서 프래그먼트 2로 보낼 문자열
        sentdData = "data from fragment2";
        //프래그먼트3에서 받을 문자열
        receiveData = "";


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2 ,container , false);

        tv2 = viewGroup.findViewById(R.id.tvAddress);


        recyclerView = viewGroup.findViewById(R.id.recyclerView);



        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView =  viewGroup.findViewById(R.id.recyclerView) ;
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(
                activity, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체 생성
        adapter = new
                SingerAdapter(getActivity(), dtos);

        // 어댑터에 있는 ArrayList<SingerDTO> dtos에 dto 추가
        adapter.addDto(new DogDTO("1",
                "여" ,"사는곳 : 광주광역시 어딘가", R.mipmap.ic_launcher));

        adapter.addDto(new DogDTO("2",
                "남" ,"사는곳 : 광주광역시 어딘가", R.mipmap.ic_launcher));
        adapter.addDto(new DogDTO("3",
                "여" ,"사는곳 : 광주광역시 어딘가", R.mipmap.ic_launcher));
        adapter.addDto(new DogDTO("4",
                "남" ,"사는곳 : 광주광역시 어딘가", R.mipmap.ic_launcher));

        // 만든 어댑터를 리싸이클러뷰에 붙인다
        recyclerView.setAdapter(adapter);






        return viewGroup;
    }
}