package com.jyt.baseapp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinWei on 2017/11/1 11:27
 */
public class MapBean {

    public List<Province> mProvinces = new ArrayList<>();
    public List<City> mCities = new ArrayList<>();
    //省
    public static class Province{
        public Province(String name,int ProvinceID){
            ProvinceName=name;
            this.ProvinceID=ProvinceID;
        }
        public Province(String BrandName,String brandId){

        }
        @SerializedName("name")
        public String ProvinceName;
        @SerializedName("id")
        public int ProvinceID;
        public boolean isCheckProvince;
//        public String brandId;
//        public String brandName;
    }
    //市
    public static class City {
        public City(String CityName,int CityID ,ArrayList<Area> mAreas){
            this.CityName = CityName;
            this.CityID=CityID;
            this.mAreas = mAreas;
        }
        @SerializedName("name")
        public String CityName;
        public boolean isCheckCity;
        @SerializedName("id")
        public int CityID;
        public int ProvinceID;
        @SerializedName("areaList")
        public List<Area> mAreas;
    }
    //区域
    public static class Area{
        public Area(String AreaName,int AreaID){
            this.AreaName=AreaName;
            this.AreaID=AreaID;
        }
        @SerializedName("name")
        public String AreaName;
        @SerializedName("id")
        public int AreaID;
        public boolean isCheckArea;
    }
}
