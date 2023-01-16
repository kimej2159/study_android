package com.example.my10_intentresult;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

    private static final String TAG = "main: SubActivity";

    Button btnSub;
    TextView tvSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        btnSub = findViewById(R.id.btnSub);
        tvSub = findViewById(R.id.tvSub);

        //2. 메인에서 넘겨준 데이터를 받는다
        //   넘겨준 인텐트가 있으면 null이 아님
        Intent intent = getIntent();
        if(intent !=null){
            String id = intent.getStringExtra("id");
            int pw = intent.getIntExtra("pw", 9999);
            Student hong = (Student) intent.getSerializableExtra("hong");

            // 기존의 있는 데이터 내용을 모드 지우고 다시 쓴다
            tvSub.setText("id : " + id + "\npw : "+ pw);
            //기존에 있는 데이터에 내용을 추가한다
            tvSub.append("\n 이름: "+hong.getName() + "\n나이 : " + hong.getAge());
        }

        // 메인으로 돌아가기
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //3. 메인에게 인텐트를 만들어서 데이터 보내기
                Intent reIntent = new Intent();
                reIntent.putExtra("key", tvSub.getText().toString() + "\n뿅!");
                // 정상적으로 종료가 됐음을 알려줌
                setResult(RESULT_OK, reIntent);

                Log.d(TAG, tvSub.getText().toString() + "\n뿅!");
                //화면 종료
               finish();
            }
        });

    }
}