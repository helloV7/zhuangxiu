package com.jyt.baseapp.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.AreaBean;
import com.jyt.baseapp.bean.CityBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.Path;
import com.jyt.baseapp.bean.ProvinceBean;
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
//                            new Thread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    for (int i = 0; i < provinceList.size(); i++) {
//                                        getAreaData(provinceList.get(i).areaName,provinceList.get(i).areaId,data);
//                                        num.set(num.get()+1);
//                                        if (num.get()==provinceList.size()-1){
//                                            if (listener!=null){
//                                                listener.ResultData(true,null,data);
//                                            }
//                                        }
//                                    }
//                                }
//                            }).start();
                            for (int i = 0; i < provinceList.size(); i++) {
                                getAreaData(provinceList.get(i).areaName,provinceList.get(i).areaId,data,provinceList.size()-1,listener);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

//    private void getAreaData(final String CityName,final int CityID,  final ArrayList<MapBean.City> result){
//        try {
//            Response response=OkHttpUtils
//                    .get()
//                    .url(Path.URL_AREA)
//                    .addParams("appkey","98a367c4465dd4efb99ca7b072033244")
//                    .addParams("parent_id",CityID+"")
//                    .build()
//                    .execute();
//            try {
//                JSONArray AreaJsons=new JSONObject(response.body().string())
//                        .getJSONObject("result")
//                        .getJSONObject("jingdong_areas_county_get_responce")
//                        .getJSONObject("baseAreaServiceResponse")
//                        .getJSONArray("data");
//                ArrayList<AreaBean> citylist = new Gson().fromJson(AreaJsons.toString(),new TypeToken<List<AreaBean>>(){}.getType());
//                ArrayList<MapBean.Area> AreaBean =new ArrayList<>();
//                for (int i = 0; i < citylist.size(); i++) {
//                    MapBean.Area area=new MapBean.Area(citylist.get(i).areaName,citylist.get(i).areaId);
//                    AreaBean.add(area);
//                }
//                result.add(new MapBean.City(CityName,CityID,AreaBean));
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

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
//                                if (i==0){
//                                    MapBean.Area city=new MapBean.Area("全市",CityID);
//                                    AreaBean.add(city);
//                                }
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
}
