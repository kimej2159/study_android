package com.example.my05_scrollview;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnChange;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChange = findViewById(R.id.btnChange);
        imageView = findViewById(R.id.imageView);

        // 버튼을 클릭하면 이미지를 바꾼다
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.image02);
            }
        });
        //위와 내용이 같지만 이렇게도 쓸 수있음
//        findViewById(R.id.btnChange).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imageView.setImageResource(R.drawable.image02);
//            }

        });
    }
}