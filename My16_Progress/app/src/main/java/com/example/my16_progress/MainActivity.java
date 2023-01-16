package com.example.my16_progress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    ProgressBar progressBar;
    EditText etinput;
    ProgressDialog dialog;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        etinput = findViewById(R.id.etinput);
        seekBar = findViewById(R.id.seekBar);

        //progressBar 설정
        progressBar.setIndeterminate(false);//정해진 값을 사용할 것
        progressBar.setMax(100);
        progressBar.setProgress(50);

       findViewById(R.id.btninput).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                int data = Integer.parseInt(etinput.getText().toString());
               if (data >= 0) {
                   if (data < 0) {
                       progressBar.setProgress(0);
                   } else if (data >= 100) {
                       progressBar.setProgress(100);
                   } else {
                       progressBar.setProgress(data);
                   }
               }
           }
       });

        // 보여주기
        findViewById(R.id.btnshow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터를 확인하는 중입니다...");
                // 아무데나 눌러도 사라지지 않게 하기
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        // 닫기
        findViewById(R.id.btndismis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                etinput.setText(""+progress);
                Log.d(TAG, "onProgressChanged: fromUser");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



    }
}