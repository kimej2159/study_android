package com.example.my04_framelayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button btnChgImage;
    ImageView image1, image2, image3;
    int selImage = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnChgImage = findViewById(R.id.btnChgImage);

        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);

        btnChgImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selImage가 1이면 1번째 이미지로 연동...
                if(selImage == 1){
                    image1.setVisibility(View.VISIBLE);
                    image2.setVisibility(View.GONE);
                    image3.setVisibility(View.GONE);
                    selImage = 2;
                }
                else if (selImage == 2){
                    image1.setVisibility(View.GONE);
                    image2.setVisibility(View.VISIBLE);
                    image3.setVisibility(View.GONE);
                    selImage = 3;
                }
                else if (selImage == 3){
                    image1.setVisibility(View.GONE);
                    image2.setVisibility(View.GONE);
                    image3.setVisibility(View.VISIBLE);
                    selImage = 1;
                }
            }
        });

    }

}