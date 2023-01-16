package com.example.my23_tab3;

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
    String sendData, receiveData;

    TextView tv3;
    Person person3;

    //화면이 붙을 때 실행하는 메소드 : 초기화 시켜줌
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        //프래그먼트3에서 프래그먼트1으로 보낼 내가 만든 문자열
        sendData = "프래그먼트3에서 보내는 데이터입니다";
        person3 = new Person("GANG", 20);

        //프래그먼트1에서 받을 문자열 변수 초기화
        receiveData = "";
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3,
                container, false);

        tv3 = viewGroup.findViewById(R.id.textView3);

        //프래그먼트3에서 보낸 데이터 받기
        if(activity.mBundle !=null){
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person2 = (Person) bundle.getSerializable("person2");

//            String name = person1.getName();
//            int age = person1.getAge();
            tv3.setText(receiveData + "\n");
            tv3.append("name : " + person2.getName()
                    +"\nage :" + person2.getAge());
            activity.mBundle = null;
        }

        //프래그먼트 3에서 프래그먼트 1로 데이터 보내기 : Bundle
        viewGroup.findViewById(R.id.button3)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putString("sendData", sendData);
                        bundle.putInt("index", 2);
                        bundle.putSerializable("person3", person3);

                        //메인 액티비티에 번들 만들어서 보내기
                        activity.fragBtnclicked(bundle);
                        //메인 액티비티에 프래그먼트2로 화면 전환을 동적으로 요청
                        TabLayout.Tab tab = activity.tabs.getTabAt(0);
                        tab.select();
                    }
                });




        return viewGroup;
    }
}
