package com.huoer.okhttpuse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final String DATA_URL = "http://api.liwushuo.com/v2/channels/108/items_v2?gender=1&generation=2&limit=20&offset=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一步初始化OkHttpClient对象
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .readTimeout(5, TimeUnit.SECONDS)
                .build();
        //第二步 创建请求对象
        Request request = new Request.Builder().url(DATA_URL).build();
        //异步网络请求
        client.newCall(request).enqueue(new Callback() {
            //当请求失败的时候回调该方法
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.toString());
            }
            //当请求成功的时候会调的方法
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e(TAG, "onResponse: " + response.body().string());
            }
        });

    }
}
