package com.example.my26_recyclerview;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /* 1. DB에 있는 테이블을 기본으로 하여 DTO를 만든다
    *  가정: 1. DB에 singerDTO라는 테이블이 있다
    *       2. singerDTO라는 테이블에 name, mobile, age, resID(이미지경로)의 칼럼이 있다
    *  2. 1에서  만든 DTO에서 내가 보여주고 싶은 데이터를 골라 xml파일을 만든다
    *  3. xml파일에서 사용된 모든 변수를 adapter에서 클래스로 선언한다
    *  4. 선언한 클래스와 xml파일을 이용하여 화면을 adapter에서 생성한다
    *  5. 만든 adpater를 RdcycleView에 붙여준다
    * */

    RecyclerView recyclerView;
    SingerAdapter adapter;
    ArrayList<SingerDTO> dtos;
    Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디바이스 사이즈 구하기
        Point size = getDeviceSize();

        //반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList();

        btnAdd = findViewById(R.id.btnAdd);

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(
                        this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 객체 생성
        adapter = new
                SingerAdapter(MainActivity.this, dtos);
        //어댑터에 있는 ArrayList<SingerDTO> dto에 추가
        adapter.addDto(new SingerDTO("신짱구", "010-1234-1234", 25, R.drawable.singer1));
        adapter.addDto(new SingerDTO("신짱아", "010-1234-7984", 1, R.drawable.singer2));
        adapter.addDto(new SingerDTO("신형식", "010-1234-1456", 34, R.drawable.singer3));
        adapter.addDto(new SingerDTO("봉미선", "010-1234-1974", 29, R.drawable.singer4));
        adapter.addDto(new SingerDTO("흰둥이", "010-1234-3021", 2, R.drawable.singer5));

        //만든 어댑터를 RecyclerView에 붙이면 됨
        recyclerView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addDto(new SingerDTO("봉미소", "010-1234-3021", 25, R.drawable.singer5));

                //추가 후 리스트 갱신
                adapter.notifyDataSetChanged();
            }
        });
    }
    // 디바이스 사이즈 구하는 메소드 만들기
    // getRealSize()는 status bar등 system insets을
    // 포함한 스크린 사이즈를 가져오는 방법
    // getSize()는 status bar등 system insets을 제외한 사이즈를 가져오는 메소드
    // 단위 : 픽셀
    private Point getDeviceSize(){
        Display display = getWindowManager().getDefaultDisplay();       // in Activity
        // getActivity.getWindowManager().getDefaultDisplay();      // in in fragment
        Point size = new Point();
        display.getRealSize(size);
        int width = size.x;
        int height = size.y;

        Log.d(TAG, "getDeviceSize: 넓이 : " + width
                        +",높이 : " + height);

        return size;
    }

}


