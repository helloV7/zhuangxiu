package com.jyt.baseapp.model;

import com.jyt.baseapp.api.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/28 19:01
 */
public class ShareModel {
    public void DownloadFile(final OnDownloadFileListener listener){
        OkHttpUtils
                .get()
                .url("http://mingya-oss.oss-cn-shenzhen.aliyuncs.com/upload-file//%E9%98%BF%E9%87%8C%E7%9F%AD%E4%BF%A1demo%E5%8F%8Asdk20171128191132zip")
                .build()
                .execute(new FileCallBack(Const.mMainFile,"A") {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        int p= (int) (progress*100);
                        listener.Result(true,p);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if (listener!=null){
                            listener.Result(true,100);
                        }
                    }
                });
    }

    public interface OnDownloadFileListener{
        void Result(boolean isSuccess,int progress);
    }
}
