package com.jyt.baseapp.model.impl;

import android.content.Context;

import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public class ProjectDetailModelImpl implements ProjectDetailModel {
    Context mContext;

    @Override
    public void onCreate(Context context) {
        mContext = context;
    }

    @Override
    public void onDestroy() {
        OkHttpUtils.getInstance().cancelTag(mContext);
    }

    @Override
    public void uploadImage(String detailId, String projectId, String sc, List<String> imageList, Callback callback) {

        StringBuffer remoteUrlSB = new StringBuffer();
        StringBuffer suffixSB = new StringBuffer();
        StringBuffer contentName = new StringBuffer();
        for (int i=0;i<imageList.size();i++){
            remoteUrlSB.append(imageList.get(i));
            suffixSB.append( imageList.get(i).substring(imageList.get(i).lastIndexOf(".")+1));
            contentName.append(imageList.get(i).substring(imageList.get(i).lastIndexOf("/")+1,imageList.get(i).lastIndexOf(".")));
            if (i!=imageList.size()-1){
                remoteUrlSB.append(",");
                suffixSB.append(",");
                contentName.append(",");
            }
        }

        OkHttpUtils.post().url(Path.BasePath+Path.URL_ADD_PROJECT_CONTENT+"?token="+BaseUtil.getSpString(Const.UserToken))
                .addParams("detailId",detailId)
                .addParams("userId",BaseUtil.getSpString(Const.USERID))
                .addParams("contentSuffix",suffixSB.toString())
                .addParams("contentUrl",remoteUrlSB.toString())
                .addParams("contentName",contentName.toString())
                .addParams("sc",sc)
                .addParams("projectId",projectId)
                .tag(mContext)
        .build().execute(callback);

    }

    @Override
    public void getProgressDetail(String detailId, Callback callback) {
        OkHttpUtils.get().url(Path.BasePath+Path.URL_GET_PROJECT_CONTENT+"?token="+BaseUtil.getSpString(Const.UserToken)+"&method=getInitContent&page=0"+"&searchValue="+detailId)
                .tag(mContext)
                .build().execute(callback);
    }

    @Override
    public void getPersonById(String userId, Callback callback) {
        OkHttpUtils.get().url(Path.BasePath+Path.URL_GET_USER_SUB_INFO+"?token="+BaseUtil.getSpString(Const.UserToken)+"&userId="+userId)
                .tag(mContext)
                .build().execute(callback);

    }

    @Override
    public void clickToNextStep(String detailId, String projectId, String sc, Callback callback) {
        OkHttpUtils.post().url(Path.BasePath+Path.URL_PROGRESS_NEXT+"?token="+BaseUtil.getSpString(Const.UserToken))
                .addParams("detailId",detailId)
                .addParams("projectId",projectId)
                .addParams("sc",sc)
                .addParams("userId",BaseUtil.getSpString(Const.USERID))
                .tag(mContext)
                .build().execute(callback);
    }

    @Override
    public void getStatus(String ProjectID , String value, Callback callback) {
        OkHttpUtils.get().url(Path.BasePath)
                .addParams("tokenSession",Const.gettokenSession())
                .addParams("page","0")
                .addParams("keyWord",ProjectID)
                .addParams("searchValue",value)
                .build().execute(callback);
    }
}
