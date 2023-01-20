package com.example.my29_audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    String AUDIO_URL =
            "htts://sites.google.com/site/ubiaccessmobile/sample_audio.mp3";

    MediaPlayer mediaPlayer;
    int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnplay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //playAudio(AUDIO_URL);
                playAudio1(R.raw.m01);
                Toast.makeText(MainActivity.this,
                        "재생버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnstop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,
                        "정지버튼 눌림", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btnPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer !=null){
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                    Toast.makeText(MainActivity.this,
                            "일시정지버튼이 눌림", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.btnRestart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mediaPlayer가 null이 아니면서 재생중이 아니어야한다
                if(mediaPlayer !=null && !mediaPlayer.isPlaying())
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);

                Toast.makeText(MainActivity.this,
                        "재시작버튼이 눌림", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void playAudio(String audio_url) {
        //만약 재생 중이라면 기존의 플레이를 다시 시작한다
        killMediaPlayer();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(audio_url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void playAudio1(int resId) {
        //만약 재생 중이라면 기존의 플레이를 다시 시작한다
        killMediaPlayer();
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer
                    .create(MainActivity.this, resId);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void killMediaPlayer() {
        if(mediaPlayer !=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

    }
}