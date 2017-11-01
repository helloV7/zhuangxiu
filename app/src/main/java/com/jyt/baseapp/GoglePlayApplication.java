package com.jyt.baseapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.ViewConfiguration;

import java.lang.reflect.Field;


/**
 * Created by Administrator on 2016/11/20.
 */
public class GoglePlayApplication extends Application {
    private static Context context;
    private static Handler handler;
    private static int mainThreadid;

    @Override
    public void onCreate() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {
            // Ignore
        }
        super.onCreate();
        context = getApplicationContext();
        handler = new Handler();
        mainThreadid = android.os.Process.myTid();//主线程ID

    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadid() {
        return mainThreadid;
    }


}
