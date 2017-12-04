package com.jyt.baseapp.model;

import android.content.Context;

import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public interface ProjectDetailModel {
    public void onCreate(Context context);

    public void onDestroy();

    public void uploadImage(String detailId, String projectId,String sc,List<String> imageList, Callback callback);

    public void getProgressDetail(String detailId,Callback callback);

    void  getPersonById(String userId,Callback callback);

    void clickToNextStep(String detailId,String projectId,String sc,Callback callback);
}
