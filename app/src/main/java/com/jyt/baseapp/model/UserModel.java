package com.jyt.baseapp.model;

import android.content.Context;

import com.zhy.http.okhttp.callback.Callback;

/**
 * Created by chenweiqi on 2017/12/8.
 */

public interface UserModel {

    void onCreate(Context context);

    void onDestroy();

    /**
     * 获取经理列表
     * @param callback
     */
    void getManagerList(Callback callback);

    /**
     * 获取班长列表
     * @param userId
     * @param callback
     */
    void getMonitorList(String userId,Callback callback);
}
