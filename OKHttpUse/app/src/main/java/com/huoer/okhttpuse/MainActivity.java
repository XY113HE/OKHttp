package com.huoer.okhttpuse;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements ListViewAdapter.ImgClickListener{
    private static final String TAG = "MainActivity";
    private static final String DATA_URL = "http://api.liwushuo.com/v2/channels/108/items_v2?gender=1&generation=2&limit=20&offset=0";
    //保证handler的操作都回到主线程中执行
    private Handler handler = new Handler(Looper.getMainLooper());
    private String data;
    private ListView listView;
    private List<OKBean.DataBean.ItemsBean> itemsBeanList;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.main_listview);
//        morningCode();
        NetTool.getInstance().startRequest(DATA_URL, OKBean.class, new CallBack<OKBean>() {
            @Override
            public void onSuccess(OKBean respose) {
                itemsBeanList = respose.getData().getItems();
                adapter = new ListViewAdapter();
                //设置数据在主线程中进行
                sendToUiThread();

                adapter.setOnImgClickListener(MainActivity.this);

                listView.setAdapter(adapter);

            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    private void morningCode() {
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
                //response.body().string()只允许一次
//                Log.e(TAG, "onResponse: " + response.body().string());
                data = response.body().string();

                jsonSyncData(data);

//                sendToUiThread();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ((TextView)findViewById(R.id.text_view)).setText(data);
//                    }
//                });

            }
        });
    }

    private void jsonSyncData(String data) {
        //创建Gson对象
        Gson gson = new Gson();
        //解析json数据
        OKBean okBean = gson.fromJson(data, OKBean.class);
//        for (OKBean.DataBean.ItemsBean itemBean :  okBean.getData().getItems()) {
//            Log.e(TAG, itemBean.getIntroduction());
//        }

        itemsBeanList = okBean.getData().getItems();

        adapter = new ListViewAdapter();
        //设置数据在主线程中进行
        sendToUiThread();

        listView.setAdapter(adapter);

    }

    private void sendToUiThread() {
        //设置数据在主线程中进行
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.setContext(MainActivity.this);
                adapter.setItemsBeanList(itemsBeanList);
            }
        });
    }

    @Override
    public void onImgClick(String clickedImgUrl) {
        Intent intent = new Intent(this, ImgActivity.class);
        intent.putExtra("img", clickedImgUrl);
        startActivity(intent);
    }
}
