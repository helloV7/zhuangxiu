package com.jyt.baseapp.bean;

import java.util.ArrayList;

/**
 * @author LinWei on 2017/11/1 11:27
 */
public class MapBean {

    public ArrayList<Province> mProvinces = new ArrayList<>();
    public ArrayList<City> mCities = new ArrayList<>();
    //省
    public static class Province{
        public Province(String name,int ProvinceID){
            ProvinceName=name;
            this.ProvinceID=ProvinceID;
        }
        public String ProvinceName;
        public int ProvinceID;
        public boolean isCheckProvince;
    }
    //市
    public static class City {
        public City(String CityName,int CityID ,ArrayList<Area> mAreas){
            this.CityName = CityName;
            this.CityID=CityID;
            this.mAreas = mAreas;
        }
        public String CityName;
        public boolean isCheckCity;
        public int CityID;
        public ArrayList<Area> mAreas;
    }
    //区域
    public static class Area{
        public Area(String AreaName,int AreaID){
            this.AreaName=AreaName;
            this.AreaID=AreaID;
        }
        public String AreaName;
        public int AreaID;
        public boolean isCheckArea;
    }
}
