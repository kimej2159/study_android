package com.example.my19_pager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        // viewpager가 미리 로딩하는 페이지 수
        pager.setOffscreenPageLimit(3);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        FragmentManager manager = getSupportFragmentManager();
        MyPagerAdapter adapter = new MyPagerAdapter(manager);

        adapter.addItem(fragment1);
        adapter.addItem(fragment2);
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

        //두번째 화면 보여주기
        findViewById(R.id.btnMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(1);
            }
        });
    }

    class MyPagerAdapter extends FragmentStatePagerAdapter{
        //뷰페이저에 보일 프래그먼트들을 담는  공간
        ArrayList<Fragment> items = new ArrayList<>();

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }


        // ArrayList에 프래그먼트를 추가하는 매소드
        public void addItem(Fragment item){
            items.add(item);
        }

        // items 인덱스 위치에 있는 프래그먼트를 가져온다
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        //items가 가지고 있는 프래그 먼트 갯수
        @Override
        public int getCount() {
            return items.size();
        }

        // xml의 TabStrip에 넣을 문자
        public CharSequence getPageTitle(int position) {
            return "페이지" + (position + 1);
        }

    }



}