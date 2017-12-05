package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.FileBean;
import com.jyt.baseapp.bean.PicBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;

import java.io.File;
import java.util.List;

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

    public void getShareFile(final OngetFileListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getShareNotImageApp")
                .addParams("page","0")
                .build()
                .execute(new BeanCallback<BaseJson<List<FileBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<FileBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                listener.Result(true,null);
                            }
                        }
                    }
                });
    }

    public interface OngetFileListener{
        void Result(boolean isSuccess , List<FileBean> data);
    }


    public void getSharePic(final OngetPicListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getShareImageApp")
                .addParams("page","0")
                .build()
                .execute(new BeanCallback<BaseJson<List<PicBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<List<PicBean>> response, int id) {
                        if (listener !=null){
                            if (response.ret){
                                listener.Result(true,response.data);
                            }
                        }
                    }
                });
    }

    public interface OngetPicListener{
        void Result(boolean isSuccess , List<PicBean> data);
    }
}
