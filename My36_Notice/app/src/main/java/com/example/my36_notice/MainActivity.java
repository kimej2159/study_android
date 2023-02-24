package com.example.my36_notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnNoti, btnNotiClick, btnManyNoti;
    NotificationManager manager;

    //오레오 버진(O:API26) 이후는 알림채널을 생성해 줘야 한다
    String CHANNEL_ID1 = "channel1";
    String CHANNEL_NAME1 = "channel1";

    String CHANNEL_ID2 = "channel2";
    String CHANNEL_NAME2 = "channel2";

    String CHANNEL_ID3 = "channel3";
    String CHANNEL_NAME3 = "channel3";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        // 1. 알림 띄우기 : 단순한 알림
        findViewById(R.id.btnNoti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoti1();
            }
        });

        // 2. 알림 띄우고 클릭하기
        findViewById(R.id.btnNotiClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoti2();
            }
        });

        // 3.많은 글자 알림 띄우기
        findViewById(R.id.btnManyNoti).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoti3();
            }
        });
    }

    // 1. 알림 띄우기 : 단순한 알림
    private void showNoti1() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= 26){
            if(manager.getNotificationChannel(CHANNEL_ID1) == null){
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID1,
                        CHANNEL_NAME1,
                        NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            builder = new NotificationCompat.Builder(this,CHANNEL_ID1);

        }else{
            builder = new NotificationCompat.Builder(this);
        }
        builder.setContentTitle("간단 알림")        // 알림 제목
                .setContentText("간단 알림 메세지입니다")     // 알림 내용
                .setSmallIcon(android.R.drawable.ic_menu_view);   // 아이콘
        Notification noti = builder.build();
        manager.notify(1, noti);

    }

    // 2. 알림 띄우고 클릭하기
    private void showNoti2() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if(Build.VERSION.SDK_INT >= 26){
            if(manager.getNotificationChannel(CHANNEL_ID2) == null){
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID2,
                        CHANNEL_NAME2,
                        NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            builder = new NotificationCompat.Builder(this,CHANNEL_ID2);

        }else{
            builder = new NotificationCompat.Builder(this);
        }
        // 펜딩인텐트 객체에 띄울 액티비티를 파라메터로 보낸다
        // 펜팅인텐트는 특정 시점에서 어떤 행동을 하도록 할 수 있다
        Intent intent = new Intent(this, SubActivity.class);
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 1004, intent,
                PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        //FLAG_UPDATE_CURRENT : 기존의 펜딩인텐트를 모두 새로운 것으로 업데이트

        builder.setContentTitle("간단 알림 클릭")
                .setContentText("클릭 알림 메세지 입니다")
                .setSmallIcon(android.R.drawable.ic_menu_view)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);
        Notification noti = builder.build();
        manager.notify(2, noti);

    }

    // 3.많은 글자 알림 띄우기
    private void showNoti3() {
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= 26) {
            if (manager.getNotificationChannel(CHANNEL_ID3) == null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID3,
                        CHANNEL_NAME3,
                        NotificationManager.IMPORTANCE_DEFAULT
                ));
            }
            builder = new NotificationCompat.Builder(this, CHANNEL_ID3);

        } else {
            builder = new NotificationCompat.Builder(this);
        }

        NotificationCompat.BigTextStyle style =
                new NotificationCompat.BigTextStyle();
        style.bigText("많은 글자들 입니다");
        style.setBigContentTitle("제목입니다");
        style.setSummaryText("요약글입니다");

        builder = new NotificationCompat
                .Builder(this,CHANNEL_ID3)
                .setSmallIcon(android.R.drawable.ic_menu_send)
                .setStyle(style);

        Notification noti = builder.build();
        manager.notify(3, noti);

    }




    // 위험권한 : 실행시 허용여부를 다시 물어봄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음
                Manifest.permission.POST_NOTIFICATIONS
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}