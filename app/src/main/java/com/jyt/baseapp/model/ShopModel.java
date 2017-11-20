package com.jyt.baseapp.model;

import android.util.Log;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.ShopBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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

    public void getStationRole(){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getStationRole")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",BaseUtil.getSpString(Const.PositionID))
                .build()
                .execute(new BeanCallback<String>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("@#","onError");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            List<Integer> data= new ArrayList<Integer>();
                            JSONObject jsondata=new JSONObject(response);
                            JSONArray jsonArray=new JSONArray(jsondata.getString("data"));
                            data.add(jsonArray.getJSONObject(1).getJSONArray("role").getJSONObject(0).getInt("测量中"));
                            data.add(jsonArray.getJSONObject(1).getJSONArray("role").getJSONObject(1).getInt("测量完毕"));
                            data.add(jsonArray.getJSONObject(2).getJSONArray("role").getJSONObject(1).getInt("测量完毕"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
