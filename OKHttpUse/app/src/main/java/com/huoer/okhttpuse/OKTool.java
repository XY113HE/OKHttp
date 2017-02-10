package com.huoer.okhttpuse;
/*
         |              |
         | \            | \
         |   | | | | | |    | | | | |||||\
         |                          |||||||\
         |         ( )              ||||||||
         |                           |||||/
         |                  | | | | | |||/
         |    |             |          |
         |    |             |          |
       / |   | |            |          |\
      |      |/             |          \|
       \ |                  |
         |                  |
           \ | | | | | | | /
             |       |            <-----辣鸡
             |       |
              |       |
*/

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKTool implements NetInterface{
    private Handler handler = new Handler(Looper.getMainLooper());
    private OkHttpClient client;
    private Gson gson;
    public OKTool(){
        //初始化OKHttpClient
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(5, TimeUnit.SECONDS)
                .cache(new Cache(Environment.getExternalStorageDirectory(), 10*1024*1024))
                .build();
        gson = new Gson();
    }

    @Override
    public void startRequest(String url, final CallBack<String> callBack) {
//        oldMode(url, callBack);

        startRequest(url, String.class, callBack);
    }

    @Override
    public <T> void startRequest(String url, final Class<T> tClass, final CallBack<T> callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                final T result = gson.fromJson(str, tClass);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(result);
                    }
                });
            }
        });
    }

    private void oldMode(String url, final CallBack<String> callBack) {
        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onError(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到网络数据
                final String str = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onSuccess(str);
                    }
                });
            }
        });
    }
}
