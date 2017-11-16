package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.ShopBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/16 15:49
 */
public class ShopModel {

    public void getShopDetail(String projectId, final OnShopDetailResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectDetail")
                .addParams("page","0")
                .addParams("keyWord","")
                .addParams("searchValue",projectId)
                .build()
                .execute(new BeanCallback<BaseJson<List<ShopBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<ShopBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,null,response.data);
                            }else {
                            }
                        }
                    }
                });
    }

    public interface OnShopDetailResultListener{
        void Result(boolean isSuccess , Exception e , List<ShopBean> shopBean);
    }

    public void getProjectProgress(String projectId , final  OnProgressResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAllSpeedDetailById")
                .addParams("page","0")
                .addParams("keyWord","")
                .addParams("searchValue",projectId)
                .build()
                .execute(new BeanCallback<BaseJson<List<ProgressBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<ProgressBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,null,response.data);
                            }else {
                            }
                        }
                    }
                });

    }
    public interface OnProgressResultListener{
        void Result(boolean isSuccess , Exception e , List<ProgressBean> shopBean);
    }
}
