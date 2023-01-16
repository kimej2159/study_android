package com.example.my10_intentresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main: MainActivity";

    Button btnMain;
    TextView tvMain;

    int reqCode= 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMain = findViewById(R.id.btnMain);
        tvMain = findViewById(R.id.tvMain);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //객체를 생성한다
                Student hong = new Student("Hong", 27);

                // 1. Intent를 만들어 데이터를 붙이고 새창을 띄운다
                Intent intent = new
                        Intent(MainActivity.this, SubActivity.class);
                intent.putExtra( "id","kim");
                intent.putExtra("pw", 1234);
                intent.putExtra("hong", hong);
                startActivityForResult(intent, 1004);

            }
        });
    }
    //4. 서브에서 보낸 데이터 받는곳: 정해져 있음, 반드시 오버 리이드 시켜야 함

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //                      1004      ,     -1      서브에서 보낸 데이터
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + ", " + resultCode);

        if(requestCode ==  reqCode){
            if(data !=null){
                String key = data.getStringExtra("key");
                tvMain.setText(key);
            }
        }
        else if(requestCode == 1005){

        }

    }
}







