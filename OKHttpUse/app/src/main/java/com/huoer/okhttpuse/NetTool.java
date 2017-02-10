package com.huoer.okhttpuse;/*
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

public class NetTool implements NetInterface{
    private static NetTool ourInstance;
    private NetInterface mNetInterface;

    public static NetTool getInstance() {
        if(ourInstance == null){
            synchronized (NetTool.class){
                if(ourInstance == null){
                    ourInstance = new NetTool();
                }
            }
        }
        return ourInstance;
    }

    private NetTool() {
        mNetInterface = new OKTool();
    }

    @Override
    public void startRequest(String url, CallBack<String> callBack) {
        mNetInterface.startRequest(url, callBack);
    }

    @Override
    public <T> void startRequest(String url, Class<T> tClass, CallBack<T> callBack) {
        mNetInterface.startRequest(url, tClass, callBack);
    }
}
