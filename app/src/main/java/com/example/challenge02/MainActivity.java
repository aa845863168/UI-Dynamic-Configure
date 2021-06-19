package com.example.challenge02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.challenge02.Database.Img;
import com.example.challenge02.GSON.ParseData;
import com.google.gson.Gson;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public ParseData parseData;
    public final int REFRESH = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //设置标题栏
        List<Img> imgs = DataSupport.where("id = ?", "1").find(Img.class);//查询图片
        replaceFragment(new Page1(imgs.get(0).getCurrentPosition(), imgs.get(0).getImg())); //替换碎片
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                sendRequestWithOkHttp(REFRESH);//重新请求数据并更新数据
                List<Img> imgs = DataSupport.findAll(Img.class);
                replaceFragment(new Page1(imgs.get(0).getCurrentPosition(), imgs.get(0).getImg()));
                Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    //碎片替换
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main, fragment);
        transaction.commit();
    }

    //发送请求获取img.json数据
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
            for (ParseData.Data info : data) {
                String[] id = new String[]{"1", "2", "3", "4", "5"};
                img.setCurrentPosition(info.currentPosition);
                if (info.img == null)
                    img.setImg("null");
                else
                    img.setImg(info.img);
                img.updateAll("id = ?", id[i]);
                i++;
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDestroy();
    }
}
