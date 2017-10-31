package com.tzduan.study.fragment;

import android.app.Application;
import android.content.Context;

/**
 * Created by tzduan on 17/10/30.
 */

public class BaseApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
