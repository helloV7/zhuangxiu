package com.jyt.baseapp;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.ViewConfiguration;

import com.jyt.baseapp.api.OkHttpPostInterceptor;
import com.jyt.baseapp.util.L;
import com.orhanobut.hawk.Hawk;
import com.orhanobut.hawk.LogInterceptor;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class App  extends Application{
    private String TAG="@#";
    private static Context context;
    private static Handler handler;
    private static int mainThreadid;
    public boolean isDebug() {
        return isDebug;
    }
    public void setDebug(boolean debug) {
        isDebug = debug;
    }

    private boolean isDebug = true;


    @Override
    public void onCreate() {
        super.onCreate();

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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

        initUtil();
    }


    private void initUtil(){
        Hawk.init(getApplicationContext()).setLogInterceptor(new LogInterceptor() {
            @Override
            public void onLog(String message) {
                if (isDebug()){
                    L.e(message);
                }
            }
        }).build();


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        LoggerInterceptor interceptor = new LoggerInterceptor("--HTTP--",true);

        builder.addInterceptor(new OkHttpPostInterceptor());
        builder.addInterceptor(interceptor ).hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        }).sslSocketFactory(createSSLSocketFactory());

        OkHttpUtils.initClient(builder.build());
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}
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
