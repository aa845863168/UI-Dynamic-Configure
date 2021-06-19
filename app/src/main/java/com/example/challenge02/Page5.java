package com.example.challenge02;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.challenge02.Database.Img;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Page5 extends Fragment implements View.OnClickListener {

    public int currentPosition;
    public String img;

    public Page5(int currentPosition, String img) {
        this.currentPosition = currentPosition;
        this.img = img;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page5, container, false);
        ImageView imageView = view.findViewById(R.id.bg_img_5);
        String sImage = img;
        int image = getActivity().getResources().getIdentifier(sImage.substring(0, sImage.length() - 4), "drawable", "com.example.challenge02");
        imageView.setImageDrawable(getResources().getDrawable(image));
        Button back = view.findViewById(R.id.back_5);
        Button next = view.findViewById(R.id.next_5);
        back.setOnClickListener(this);
        next.setOnClickListener(this);
        Log.d("Check", "Position is Page5");
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        List<Img> imgs = DataSupport.findAll(Img.class);
        switch (v.getId()) {
            case R.id.back_5:
                mainActivity.replaceFragment(new Page4(imgs.get(3).getCurrentPosition(), imgs.get(3).getImg()));
                break;
            case R.id.next_5:
                mainActivity.replaceFragment(new Page1(imgs.get(0).getCurrentPosition(), imgs.get(0).getImg()));
                break;
            case R.id.button5_1:
                break;
            case R.id.button5_2:
                break;
            case R.id.button5_3:
                break;
            case R.id.button5_4:
                break;
            case R.id.button5_5:
                break;
            default:
                break;
        }
    }
}
