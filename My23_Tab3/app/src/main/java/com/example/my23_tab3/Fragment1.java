package com.example.my23_tab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment1 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;

    TextView tv1;
    Person person1;

    //화면이 붙을 때 실행하는 메소드 : 초기화 시켜줌
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        //프래그먼트1에서 보낼 내가 만든 문자열
        sendData = "프래그먼트1에서 보내는 데이터입니다";
        person1 = new Person("KIM", 25);

        //프래그먼트3에서 받을 문자열 변수 초기화
        receiveData = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1,
                container, false);
        tv1 = viewGroup.findViewById(R.id.textView1);


        //프래그먼트 3에서 보낸 데이터를 받는 부분
        if(activity.mBundle !=null){
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person3 = (Person) bundle.getSerializable("person3");

//            String name = person1.getName();
//            int age = person1.getAge();
            tv1.setText(receiveData + "\n");
            tv1.append("name : " + person3.getName()
                    +"\nage :" + person3.getAge());
            activity.mBundle = null;
        }

        //프래그먼트 1에서 프래그먼트 2로 데이터 보내기 : Bundle
        viewGroup.findViewById(R.id.button1)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("sendData", sendData);
                bundle.putInt("index", 0);
                bundle.putSerializable("person1", person1);

                //메인 액티비티에 번들 만들어서 보내기
                activity.fragBtnclicked(bundle);
                //메인 액티비티에 프래그먼트2로 화면 전환을 동적으로 요청
                TabLayout.Tab tab = activity.tabs.getTabAt(1);
                tab.select();
            }
        });

        //

        return viewGroup;
    }
}
