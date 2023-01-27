package com.example.my35_alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer player;

    Button btnVib, btnSound, btnFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //진동으로 울리기
         findViewById(R.id.btnVib).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Vibrator vibrator =
                         (Vibrator) getSystemService(VIBRATOR_SERVICE);
                 if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                     //진동 시간, 진동 세기(1~255)
                     vibrator.vibrate(VibrationEffect.createOneShot(2000,10));
                 }else{
                     vibrator.vibrate(2000);
                 }
             }
         });

        //소리로 울리기
        findViewById(R.id.btnSound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
                ringtone.play();
            }
        });




        //파일로 울리기
        findViewById(R.id.btnFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player = MediaPlayer.create(getApplicationContext(), R.raw.m01);
                player.start();
            }
        });
    }

    protected void onStop(){
        super.onStop();

        if(player !=null){
            player.stop();
            player.release();
            player = null;
        }
    }
}