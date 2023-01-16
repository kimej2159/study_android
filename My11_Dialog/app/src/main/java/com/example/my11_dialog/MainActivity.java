package com.example.my11_dialog;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btntalk;
    TextView tvdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btntalk = findViewById(R.id.btntalk);
        tvdialog = findViewById(R.id.tvdialog);

        btntalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMessage();
            }
        });
    }

    private void showMessage() {
        AlertDialog.Builder builder = new
                AlertDialog.Builder(MainActivity.this);
        builder.setTitle("안내");
        builder.setMessage("종료하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        // 예 버튼
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = "예 버튼이 눌렸습니다. : " + i;
                tvdialog.setText(msg);
            }
        });
        // 아니오 버튼
        builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = "아니오 버튼이 눌렸습니다. : " + i;
                tvdialog.setText(msg);
            }
        });
        builder.setNeutralButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String msg = "취소 버튼이 눌렸습니다. : " + i;
                tvdialog.setText(msg);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}