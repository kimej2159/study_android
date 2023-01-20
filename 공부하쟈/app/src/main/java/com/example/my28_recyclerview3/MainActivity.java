package com.example.my28_recyclerview3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabs;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;




    int position ;


    private static final String TAG = "main:MainActivity";

    Fragment selFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //액션바가 보이지 않게 하기위해서
        // 먼저 theme 에 가서 NoActionbar 를해준다

        //내가 만든 툴바를 액션바로 지정한다
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //기존 타이틀  글자가 안보이게 한다
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //fragment 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        //먼저 프래그먼트1 처음화면에 보이게 초기화
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain , fragment1).commit();

        //탭을 동적으로 생성한다
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("테스트1")); // 0
        tabs.addTab(tabs.newTab().setText("테스트2")); // 1
        tabs.addTab(tabs.newTab().setText("테스트3"));  // 2


        // 탭레이아웃엥 리스너를 달아준다
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            // 탭이 선택되었을때
            @Override //선택된탭
            public void onTabSelected(TabLayout.Tab tab) {
                // 선택된 탭의 인덱스를 알 수 있다
                int position = tab.getPosition();
                Log.d(TAG, "onTabSelected: " + position);


                if(position == 0 ){
                    selFragment = fragment1;
                }else if ( position == 1) {
                    selFragment = fragment2;
                }else if ( position == 2 ) {
                    selFragment = fragment3;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contain , selFragment).commit();

            }
            //탭이 선택되지않았을때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            //탭이다시 선택 되었을떄
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }



        });







    }



}