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

public interface CallBack<T> {
    //请求成功后的回调 形参就是我们最后解析好的数据
    //因为不知道具体的类型,所以我们用T范型代替
    void onSuccess(T respose);
    //失败的回调 形参我们填写的是异常对象
    void onError(Throwable e);
}
