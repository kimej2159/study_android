package com.example.my23_tab3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    Toolbar toolbar;
    TabLayout tabs;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    Fragment selFragment = null;
    Bundle mBundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 액션바가 보이지 않게 하기 위하여
        // 먼저 theme에 가서 NoActionBar로 수정한다

        // 내가 만든 툴바를 액션바로 지정한다
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //기존 타이틀 글자가 안보이게 한다
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        //프래그먼트 생성
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();


        //먼저 프래그먼트 1이 처음 화면에 보이게 초기화
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1). commit();


        //탭을 동적으로 생성한다
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("통화기록"));
        tabs.addTab(tabs.newTab().setText("스팸기록"));
        tabs.addTab(tabs.newTab().setText("연락처"));

        // 탭 레이아웃에 리스너를 달아준다
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            //탭이 선택되었을 때
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 선택된 탭의 인덱스를 알 수 있다
                int position = tab.getPosition();
                Log.d(TAG, "onTabSelected: " + position);


//                if(position == 0 ) {
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.contain, fragment1). commit();
//                }else if(position == 1 ){
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.contain, fragment2). commit();
//                }else if(position == 2 ){
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.contain, fragment3). commit();
//                }

//                getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.contain, fragment1). commit();

                if(position == 0) {
                    selFragment = fragment1;
                } else if(position == 1){
                    selFragment = fragment2;
                }else if(position == 2){
                    selFragment = fragment3;
                }

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.contain, selFragment). commit();

            }
            //탭이 선택되지 않았을 때
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            //탭이 다시 선택되었을 때
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    public void fragBtnclicked(Bundle bundle){
         this.mBundle = bundle;
    }
}