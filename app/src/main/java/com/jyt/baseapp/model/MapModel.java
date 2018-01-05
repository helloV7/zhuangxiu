package com.jyt.baseapp.model;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.AreaBean;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.bean.CityBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/1 15:22
 */
public class MapModel {
    public void getProvinceData(final Activity activity, final onResultProvinceListener listener){

        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAllProvice")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",null)
                .build()
                .execute(new BeanCallback<BaseJson<List<MapBean.Province>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.ResultData(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<MapBean.Province>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.ResultData(true,null,response.data);
                            }else {
                                activity.finish();
                                activity.startActivity(new Intent(activity, LoginActivity.class));
                                Const.Logout(activity);
                                BaseUtil.makeText("登录失效，请重新登录");
                            }
                        }
                    }
                });
    }

    public void getCityAreaData(int ProvinceID,final onResultCityListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAllCityAndArea")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",ProvinceID+"")
                .build()
                .execute(new BeanCallback<BaseJson<List<MapBean.City>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.ResultData(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<MapBean.City>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                for (int i = 0; i < response.data.size(); i++) {
                                    if (response.data.get(i).mAreas.size()>1){
                                        response.data.get(i).mAreas.set(0,new MapBean.Area("全部",-3));
                                    }
                                }
                                listener.ResultData(true,null,response.data);
                            }else {

                            }
                        }
                    }
                });
    }

    AtomicInteger totalNum;
    public void getCityData(int ProvinceID, final onResultCityListener listener){


        totalNum=new AtomicInteger(0);
        OkHttpUtils
                .get()
                .url(Path.URL_CITY)
                .tag("City")
                .addParams("appkey","98a367c4465dd4efb99ca7b072033244")
                .addParams("parent_id",ProvinceID+"")
                .build()
                .execute(new BeanCallback<String>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.ResultData(false,e,null);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray CityJsons=new JSONObject(response)
                                    .getJSONObject("result")
                                    .getJSONObject("jingdong_areas_city_get_responce")
                                    .getJSONObject("baseAreaServiceResponse")
                                    .getJSONArray("data");
                            final ArrayList<CityBean> provinceList = new Gson().fromJson(CityJsons.toString(),new TypeToken<List<CityBean>>(){}.getType());
                            final ArrayList<MapBean.City> data=new ArrayList<>();
                            for (int i = 0; i < provinceList.size(); i++) {
                                getAreaData(provinceList.get(i).areaName,provinceList.get(i).areaId,data,provinceList.size()-1,listener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }



    public interface onResultCityListener{
        void ResultData(boolean isSuccess,Exception e ,List<MapBean.City> data);
    }

    public void getAreaData(final String CityName, final int CityID, final ArrayList<MapBean.City> result, final int num,final onResultCityListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_AREA)
                .addParams("appkey","98a367c4465dd4efb99ca7b072033244")
                .addParams("parent_id",CityID+"")
                .build()
                .execute(new BeanCallback<String>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONArray AreaJsons=new JSONObject(response)
                                    .getJSONObject("result")
                                    .getJSONObject("jingdong_areas_county_get_responce")
                                    .getJSONObject("baseAreaServiceResponse")
                                    .getJSONArray("data");
                            ArrayList<AreaBean> citylist = new Gson().fromJson(AreaJsons.toString(),new TypeToken<List<AreaBean>>(){}.getType());
                            ArrayList<MapBean.Area> AreaBean =new ArrayList<>();
                            for (int i = 0; i < citylist.size(); i++) {
                                MapBean.Area area=new MapBean.Area(citylist.get(i).areaName,citylist.get(i).areaId);
                                AreaBean.add(area);
                            }
                            result.add(new MapBean.City(CityName,CityID,AreaBean));
                            totalNum.set(totalNum.get()+1);
                            if (totalNum.get()==num || num==0){
                                if (listener!=null){
                                    listener.ResultData(true,null,result);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    public interface onResultProvinceListener{
        void ResultData(boolean isSuccess,Exception e ,List<MapBean.Province> data);
    }



    public void getBrandData(final OngetBrandResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getBrandListForSelect")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",null)
                .build()
                .execute(new BeanCallback<BaseJson<List<BrandBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<BrandBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public void getBrandSonData(String BrandID,final OngetBrandResultListener listener){
        OkHttpUtils
                .get()
                .tag("BrandSon")
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getSubClassSelect")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",BrandID)
                .build()
                .execute(new BeanCallback<BaseJson<List<BrandBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<BrandBean>> response, int id) {
                        if (listener!=null) {
                            if (response.ret) {
                                for (int i = 0; i < response.data.size(); i++) {
                                    response.data.get(i).TransData();
                                }
                                BrandBean first=new BrandBean();
                                first.setBrandName("全部");
                                first.setBrandId("null");
                                response.data.add(0,first);
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public void getMapBrandSonData(String BrandID,final OngetBrandResultListener listener){
        OkHttpUtils
                .get()
                .tag("BrandSon")
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getSubClassSelect")
                .addParams("page","0")
                .addParams("keyWord",null)
                .addParams("searchValue",BrandID)
                .build()
                .execute(new BeanCallback<BaseJson<List<BrandBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<BrandBean>> response, int id) {
                        if (listener!=null) {
                            if (response.ret) {

                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public interface OngetBrandResultListener{
        void Result(boolean isSuccess, List<BrandBean> brandData);
    }

    public void getLocationShop(LatLng l1,LatLng l2, final OnLocationShopResultListener listener){
        OkHttpUtils.getInstance().cancelTag(Const.Tag_LocationShop);
        OkHttpUtils
                .get()
                .tag(Const.Tag_LocationShop)
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAreaProject")
                .addParams("page","0")
                .addParams("searchValue",l1.latitude+","+l2.latitude+","+l1.longitude+","+l2.longitude)
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public interface OnLocationShopResultListener{
        void Result(boolean isSuccess,List<SearchBean> shops);
    }


    public void getSearchData(String condition, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("page","1")
                .addParams("keyWord",BaseUtil.getSpString(Const.PositionID))
                .addParams("searchValue",condition)
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public void getBrandSearchData(String condition, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("page","1")
                .addParams("keyWord","")
                .addParams("searchValue",condition)
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });

    }

    public void getLRData(int page, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("page",String.valueOf(page))
                .addParams("keyWord",BaseUtil.getSpString(Const.PositionID))
                .addParams("searchValue","null,null,null,null,null,null,null")
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public void getBrandLRData(int page, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("page",String.valueOf(page))
                .addParams("keyWord","")
                .addParams("searchValue","null,null,null,null,null,null,null")
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }






    public interface OnSearchResultListener{
        void Result(boolean isSuccess,List<SearchBean> data);
    }

    public void pushLocation(LatLng l, Callback callback){
        OkHttpUtils.post().url(Path.URL_PUSH_LOCATION)
                .addParams("token",Const.gettokenSession())
                .addParams("userId", Const.getUserid())
                .addParams("longitude",l.longitude+"")
                .addParams("latitude",l.latitude+"")
                .build()
                .execute(callback);
    }


}
