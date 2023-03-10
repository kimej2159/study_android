package com.example.my31_captureintent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main: MainActivity";
    ImageView imageView;
    File imgFile = null;
    String imgFilePath = null;
    int reqPicCode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        imageView = findViewById(R.id.imageView);

        //사진찍기 버튼
       findViewById(R.id.btnPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //암묵적 인텐트 : 사진찍기(카메라창을 불러옴)
                Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 일단 이 인텐트가 사용가능한지 체크
                if(picIntent.resolveActivity(getPackageManager()) !=null){
                    imgFile = null;
                    // creatFile 매소드를 이용하여 임시파일을 만들것
                    imgFile = creatFile();

                    if(imgFile != null){
                        // API24 이상부터는 FileProvider를 제공해야 함
                        Uri imgUri = FileProvider.getUriForFile(
                                getApplicationContext(),
                                getApplicationContext().getPackageName()+".fileprovider",
                                imgFile
                        );
                        // 만약 API24 이상이면
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        }else{ //만약 API24
                            picIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(imgFile));
                        }
                        startActivityForResult(picIntent, reqPicCode);

                    }
                }

            }
        });
    }

    // 사진을 찍은 후 데이터 받기 (Main에다가 받아야 함) : 정해져 있음


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //정상적으로 종료가 되었을 때
        if(requestCode == reqPicCode && resultCode == RESULT_OK){
            Toast.makeText(this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();

            setPic();
        }

    }
    // 사진 저장처리를 하는 곳
    private void setPic() {
        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        // 사진의 해상도를 1/8로 지정
        options.inSampleSize = 8;
        // 비트맵 이미지를 생성
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
        // 이미지를 갤러리에 저장
        gelleryAddPic(bitmap);
        // 이미지를 이미지뷰에 세팅
        imageView.setImageBitmap(bitmap);

    }

    private void gelleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;

        //쓰기객체
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ //API 29
            ContentResolver resolver = getContentResolver();

            //맵구조를 가진 ContentValues: 파일 정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,
                    "Image_jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,
                    "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES + File.separator + "MyFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    contentValues
            );

            try {
                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);

                Toast.makeText(this, "fos 작업됨", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }

    }

    // creatFile 매소드
    private File creatFile() {
        //파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "My" + timestamp;
        //사진 파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;

        try{
            // 임시파일 생성(전체 경로를 줌)        1: 파일이름, 2:확장자 이름, 3: 경로
            curFile = File.createTempFile(imageFileName,
                    ".jpg", storageDir);

        }catch (Exception e){
            e.getMessage();
        }
        //스트링타입으로 임시파일이 있는 곳의 절대 경로를 저장
        imgFilePath = curFile.getAbsolutePath();
        Log.d(TAG, "creatFile: " + imgFilePath);

        return curFile;
    }

    // 위험권한 : 실행시 허용여부를 다시 물어봄
    private void checkDangerousPermissions() {
        String[] permissions = {
                // 위험권한 내용 : 메니페스트에 권한을 여기에 적음
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_MEDIA_LOCATION,
                android.Manifest.permission.CAMERA
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