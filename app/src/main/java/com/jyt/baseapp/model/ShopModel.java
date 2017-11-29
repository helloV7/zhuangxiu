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

    public void getStationRole(final OngetStationRoleListener listener){
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
                        if (listener!=null){
                            listener.Result(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            List<Integer> data= new ArrayList<Integer>();
                            JSONObject jsondata=new JSONObject(response);
                            JSONArray jsonArray=new JSONArray(jsondata.getString("data"));
                            //   0/1(无权限/有权限)
                            data.add(jsonArray.getJSONObject(1).getJSONArray("role").getJSONObject(0).getInt("测量中"));
//                            data.add(jsonArray.getJSONObject(1).getJSONArray("role").getJSONObject(1).getInt("测量完毕"));
//                            data.add(jsonArray.getJSONObject(2).getJSONArray("role").getJSONObject(2).getInt("设计完毕"));
//                            data.add(jsonArray.getJSONObject(3).getJSONArray("role").getJSONObject(1).getInt("客户已审阅"));
//                            data.add(jsonArray.getJSONObject(4).getJSONArray("role").getJSONObject(0).getInt("待店主确认"));
//                            data.add(jsonArray.getJSONObject(4).getJSONArray("role").getJSONObject(1).getInt("店主已确认"));
//                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(3).getInt("待审图纸"));
//                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(4).getInt("已审图纸"));
//                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(7).getInt("待生产品牌"));
//                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(9).getInt("待审材料单"));
//                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(10).getInt("已审材料单"));
//                            data.add(jsonArray.getJSONObject(8).getJSONArray("role").getJSONObject(0).getInt("施工完毕"));
                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(12).getInt("钢挂已完成"));
                            data.add(jsonArray.getJSONObject(5).getJSONArray("role").getJSONObject(13).getInt("所有材料已打包"));
                            data.add(jsonArray.getJSONObject(6).getJSONArray("role").getJSONObject(0).getInt("待发货"));
                            data.add(jsonArray.getJSONObject(6).getJSONArray("role").getJSONObject(1).getInt("已发货"));
                            data.add(jsonArray.getJSONObject(6).getJSONArray("role").getJSONObject(2).getInt("货到待施工"));
                            data.add(jsonArray.getJSONObject(6).getJSONArray("role").getJSONObject(3).getInt("安排施工人员完毕"));
                            data.add(jsonArray.getJSONObject(7).getJSONArray("role").getJSONObject(0).getInt("施工中"));
                            if (listener!=null){
                                listener.Result(true,null,data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public interface OngetStationRoleListener{
        void Result(boolean isSuccess,Exception e,List<Integer> data);
    }

    public void ChangePushState(String ProjectID,String state,final OnChangeStateListener listener){
        OkHttpUtils
                .post()
                .url(Path.URL_ChangeState)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("projectId",ProjectID)
                .addParams("ispush",state)
                .build()
                .execute(new BeanCallback<BaseJson<String>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener != null){
                            listener.Result(false);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<String> response, int id) {
                        if (listener != null){
                            listener.Result(true);
                        }else {
                            listener.Result(false);
                        }
                    }
                });
    }

    public interface OnChangeStateListener{
        void Result(boolean isSuccess);
    }
}
