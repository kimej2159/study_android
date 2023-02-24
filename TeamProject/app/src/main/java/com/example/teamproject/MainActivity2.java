package com.example.teamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {

    private Timer timerCall;
    private int nCnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                someWork();
            }
        };
        timerCall = new Timer();
        timerCall.schedule(timerTask,0,3000);

    }
    private void someWork(){

        Log.d("Test==>", nCnt + "Work!!!");
        if(nCnt >= 10){
            timerCall.cancel();
        }
        nCnt++;
    }
}