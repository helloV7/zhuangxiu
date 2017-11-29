package com.jyt.baseapp.model;

import com.jyt.baseapp.api.Const;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;

import okhttp3.Call;
import okhttp3.Request;

/**
 * @author LinWei on 2017/11/28 19:01
 */
public class ShareModel {
    public void DownloadFile(String fileUrl , String FileName,final OnDownloadFileListener listener){
        OkHttpUtils
                .get()
                .tag(Const.Tag_Download)
                .url(fileUrl)
                .build()
                .execute(new FileCallBack(Const.mMainFile,FileName) {

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        if (listener!=null){

                            listener.Before(Const.Tag_Download);


                        }
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        super.inProgress(progress, total, id);
                        if (listener!=null){
                            int p= (int) (progress*100);
                            listener.Progress(p);
                        }
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false);
                        }
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        if (listener!=null){
                            listener.Result(true);
                        }
                    }
                });
    }

    public interface OnDownloadFileListener{
        void Before( String tag);
        void Progress(int progress);
        void Result(boolean isSuccess);
    }
}
