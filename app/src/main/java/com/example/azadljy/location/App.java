package com.example.azadljy.location;

import android.app.Application;

import com.iflytek.cloud.SpeechUtility;

/**
 * 作者：Ljy on 2016/11/12.
 * 邮箱：enjoy_azad@sina.com
 */

public class App extends Application {
    @Override
    public void onCreate() {
        SpeechUtility.createUtility(App.this, "appid=58268a01");
        super.onCreate();
    }
}
