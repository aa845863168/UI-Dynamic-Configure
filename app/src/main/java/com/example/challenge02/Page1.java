package com.example.challenge02;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.challenge02.Database.Img;

import org.litepal.crud.DataSupport;

import java.util.List;

public class Page1 extends Fragment implements View.OnClickListener{

    public int currentPosition;
    public String img;
    public Button next;
    public Button back;

    public Page1(int currentPosition,String img) {
        this.currentPosition = currentPosition;
        this.img = img;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_page1,container,false);
        ImageView imageView = view.findViewById(R.id.bg_img_1);
        String sImage = img;
        int image = getActivity().getResources().getIdentifier(sImage.substring(0, sImage.length()-4), "drawable", "com.example.challenge02");
        imageView.setImageDrawable(getResources().getDrawable(image));
        next = view.findViewById(R.id.next1);
        next.setOnClickListener(this);
        back = view.findViewById(R.id.back1);
        back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        List<Img> imgs = DataSupport.findAll(Img.class);
        switch (v.getId()) {
            case R.id.back1:
                mainActivity.replaceFragment(new Page5(imgs.get(4).getCurrentPosition(), imgs.get(4).getImg()));
                break;
            case R.id.next1:
                mainActivity.replaceFragment(new Page2(imgs.get(1).getCurrentPosition(), imgs.get(1).getImg()));
                break;
            case R.id.button1_1:
                break;
            case R.id.button1_2:
                break;
            case R.id.button1_3:
                break;
            case R.id.button1_4:
                break;
            case R.id.button1_5:
                break;
            default:
                break;
        }
    }
}
