package com.example.my18_fragment2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ViewerFragment extends Fragment {

    MainActivity activity;
    ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewgroup = (ViewGroup) inflater.inflate(R.layout.viewerfragment,
                container, false);
        activity = (MainActivity) getActivity();
        imageView = viewgroup.findViewById(R.id.imageView);
        return viewgroup;
    }
    // 메인에서 이미지를 바꿀 수 있도록 접근하는 매소드
    public void imageChange(int resId){
        imageView.setImageResource(resId);
    }

}
