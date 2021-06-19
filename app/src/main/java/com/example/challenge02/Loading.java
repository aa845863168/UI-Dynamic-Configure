package com.example.challenge02;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.challenge02.Database.Img;
import com.example.challenge02.GSON.ParseData;
import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Loading extends AppCompatActivity {

    public ParseData parseData;
    public final int REFRESH = 1;
    public final int NO_REFRESH = 0;
    public ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);    //加载标志
        sendRequestWithOkHttp(NO_REFRESH);
    }

    //发送请求获取Main.JSON数据
    private void sendRequestWithOkHttp(final int type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://10.0.2.2/Img.json").build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData, type);    //传入获取的JSON数据进行解析
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //解析JSON数据
    private void parseJSONWithGSON(String jsonData, int type) {
        Gson gson = new Gson();
        parseData = gson.fromJson(jsonData, ParseData.class);
        List<ParseData.Data> data = parseData.data;
        //更新数据
        if (type == REFRESH) {
            Img img = new Img();
            int i = 0;
            for (ParseData.Data info : data){
                String[] id = new String[]{"1","2","3","4","5"};
                img.setCurrentPosition(info.currentPosition);
                img.setImg(info.img);
                img.updateAll("id = ?", id[i]);
                i++;
            }
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        //插入数据
        else if (type == NO_REFRESH) {
            List<Img> imgs = DataSupport.findAll(Img.class);
            if (imgs.size() == 0){  //防止每运行一次插入一次数据
                for (ParseData.Data info : data) {
//            Log.d("Check","img is " + info.img);
//            Log.d("Check","currentPosition is " + info.currentPosition);
                    Img img = new Img();
                    img.setImg(info.img);
                    img.setCurrentPosition(info.currentPosition);
                    img.save();
                }
            }
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
}
