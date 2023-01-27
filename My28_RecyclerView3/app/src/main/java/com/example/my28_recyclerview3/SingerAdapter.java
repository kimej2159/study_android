package com.example.my28_recyclerview3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


// 1. OnsingeritemClicklistener를 인터페이스로 선언
// 2. 선언한 메소드를 오버라이드 시켜 구현
public class SingerAdapter extends
        RecyclerView.Adapter<SingerAdapter.ViewHolder> {

    private static final String TAG = "main:SingerAdapter";

    //3. 리스너 선언 :ㅣ 메인에서 사용하는거




    // 메인에게 넘겨받는것 -> 반드시 : Context, ArrayList<DTO>
    Context context;
    ArrayList<SingerDTO> dtos;

    LayoutInflater inflater;

    // 생성자로 메인에서 넘겨받은것들을 연결
    public SingerAdapter(Context context, ArrayList<SingerDTO> dtos) {
        this.context = context;
        this.dtos = dtos;
        // 화면 붙이는 객체를 생성
        inflater = LayoutInflater.from(context);
    }

    ////////////////////////////////////////////////////
    // 매소드는 여기에 만든다
    // dtos에 dto를 추가하는 매소드
    public void addDto(SingerDTO dto){
        dtos.add(dto);
    }

    // dtos의 특정 위치에 있는 dto를 삭제하는 매소드
    public void delDto(int position){
        dtos.remove(position);
    }




    // 9 . 메인에서 클릭한 우치에 있는 dto 가져오기
    public SingerDTO getItem (int position) {
        return dtos.get(position);
    }

    ////////////////////////////////////////////////////

    // 화면을 인플레이트 시킨다 (붙인다)

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.Fragment1,
                parent, false);

        return new ViewHolder(itemView);
    }

    // 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        // dtos에 있는 데이터를 각각 불러온다
        SingerDTO dto = dtos.get(position);
        // 불러온 데이터를 ViewHolder에 만들어 놓은 setDto를
        // 사용하여 데이터를 셋팅시킨다
        holder.setDto(dto);

/*        // 리싸이클러뷰에 항목을 선택했을때 그 항목을 가져오는 리스너
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingerDTO dto1 = dtos.get(position);
                Toast.makeText(context,
                        "이름 : " + dto1.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/

        // 쓰레기통을 클릭하여 항목 삭제 리스너
    }

    // dtos에 저장된 dto 갯수
    @Override
    public int getItemCount() {
        return dtos.size();
    }




    // 3. xml 파일에서 사용된 모든 변수를 adapter에서 클래스로 선언한다
    public class ViewHolder extends RecyclerView.ViewHolder{
        // singerview.xml 에서 사용된 모든 위젯을 정의한다
        TextView tvAge, tvAddress ,tvGender;
        ImageView ivImage;
        LinearLayout parentLayout;

        // singerview.xml에서 정의한 아이디를 찾아 연결시킨다(생성자)
        //6.클릭리스너를 추가한다
        public ViewHolder(@NonNull View itemView ) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            tvAge = itemView.findViewById(R.id.tvAge);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvGender = itemView.findViewById(R.id.tvGender);


        }

        // singerview에 데이터를 연결시키는 매소드를 만든다
        public void setDto(SingerDTO dto){
            tvAge.setText(dto.getAge());
            tvAddress.setText(dto.getAddress());
            tvGender.setText(dto.getGender());
            ivImage.setImageResource(dto.getResId());
        }

    }






}
