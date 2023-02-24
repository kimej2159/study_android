package com.example.myprojectx;

import static com.example.myprojectx.Common.CommonMethod.ipConfig;
import static com.example.myprojectx.Common.CommonMethod.loginDto;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myprojectx.Common.CommonMethod;
import com.example.myprojectx.DTO.MemberDTO;
import com.example.myprojectx.adapter.MemberAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubActivity extends AppCompatActivity {
    private static final String TAG = "main:SubActivity";

    RecyclerView recyclerView;
    MemberAdapter adapter;
    ArrayList<MemberDTO> dtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        // recyclerView에서 반드시 아래와 같이 초기화를 해줘야 함
        LinearLayoutManager layoutManager = new
                LinearLayoutManager(
                this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 서버에 member들 데이터를 요청
        CommonMethod commonMethod = new CommonMethod();
        commonMethod.setParams("id", loginDto.getId());
        commonMethod.getData("selectMembers", new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.body().equals(null)){
                    Log.d(TAG, "onResponse: " + response.body());

                    // 서버에서 넘어온 데이터를 받는다
                    Gson gson = new Gson();
                    dtos = gson.fromJson(response.body(),
                            new TypeToken<ArrayList<MemberDTO>>() {}.getType());

                    // 제대로 데이터가 입력됐는지 확인
                    for(MemberDTO dto : dtos){
                        // profile 경로를 적어준다
                        dto.setProfile(ipConfig + "resources/" + dto.getProfile());
                    }

                    Log.d(TAG, "onResponse: dtos.size() => " + dtos.size());

                    // 어댑터 객체 생성
                    adapter = new MemberAdapter(SubActivity.this, dtos);
                    recyclerView.setAdapter(adapter);
                    //adapter.notifyDataSetChanged();

                }else {
                    Toast.makeText(SubActivity.this,
                            "넘어온 데이터가 없습니다", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });


    }
}