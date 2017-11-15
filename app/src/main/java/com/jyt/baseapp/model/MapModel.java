package com.jyt.baseapp.model;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.AreaBean;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.bean.CityBean;
import com.jyt.baseapp.bean.LocationBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.ProvinceBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

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
    public void getProvinceData(final onResultProvinceListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_PROVINCE)
                .addParams("appkey","98a367c4465dd4efb99ca7b072033244")
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
                        if (listener!=null){
                            try {
                                JSONArray json=new JSONObject(response)
                                        .getJSONObject("result")
                                        .getJSONObject("jingdong_area_province_get_responce")
                                        .getJSONArray("province_areas");

                                ArrayList<ProvinceBean> provinceList = new Gson().fromJson(json.toString(),new TypeToken<List<ProvinceBean>>(){}.getType());
                                ArrayList<MapBean.Province> data=new ArrayList<>();
                                for (int i = 0; i < provinceList.size(); i++) {
                                    MapBean.Province province=new MapBean.Province(provinceList.get(i).name,provinceList.get(i).id);
                                    if (i==0){
                                        province.isCheckProvince=true;
                                    }
                                    data.add(province);
                                }
                                listener.ResultData(true,null,data);
                            } catch (JSONException e) {
                                e.printStackTrace();
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
        void ResultData(boolean isSuccess,Exception e ,ArrayList<MapBean.City> data);
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
                            if (totalNum.get()==num){
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
        void ResultData(boolean isSuccess,Exception e ,ArrayList<MapBean.Province> data);
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
                                response.data.get(0).setCheck(true);
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
                                first.setBrandId(null);
                                response.data.add(0,first);
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
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAreaProject")
                .addParams("page","0")
                .addParams("searchValue",l1.latitude+","+l2.latitude+","+l1.longitude+","+l2.longitude)
                .build()
                .execute(new BeanCallback<BaseJson<List<LocationBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<LocationBean>> response, int id) {
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
        void Result(boolean isSuccess,List<LocationBean> shops);
    }


    public void getSearchData(String condition, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("keyWord",null)
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

    public interface OnSearchResultListener{
        void Result(boolean isSuccess,List<SearchBean> data);
    }

}
