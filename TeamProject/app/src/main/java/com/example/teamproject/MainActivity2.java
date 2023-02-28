package com.example.teamproject;

import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity2 extends AppCompatActivity {

    private Timer timerCall;
    private int nCnt;
    TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


       nCnt = 0;
       timerCall = new Timer();

    }
    private void someWork(){

        Log.d("Test==>", nCnt + "Work!!!");
        nCnt++;
    }
    public void btnStart(View view) {
        Log.d("btnStart: ", nCnt + " work!!!");
        if (timerTask != null) {
            timerTask.cancel();
        }
        timerTask = new TimerTask() {
            @Override
            public void run() {
                someWork();
            }
        };
        timerCall.schedule(timerTask, 0, 3000);
     }
     public void btnStop(View view){
         Log.d( "btnStop: ", nCnt + "work!!!");
         if(timerTask != null){
             timerTask.cancel();
         }
    }
}