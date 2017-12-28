package com.jyt.baseapp.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.WorkAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;
import com.jyt.baseapp.view.widget.SingleSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;


/**
 * @author LinWei on 2017/10/30 15:04
 */
public class MapBrandFragment extends BaseFragment implements View.OnClickListener, GeocodeSearch.OnGeocodeSearchListener, DistrictSearch.OnDistrictSearchListener {

    @BindView(R.id.iv_arrow1)
    ImageView mIvArrow1;
    @BindView(R.id.iv_arrow2)
    ImageView mIvArrow2;
    Unbinder unbinder;
    @BindView(R.id.ll_city)
    LinearLayout mLlCity;
    @BindView(R.id.ll_brand)
    LinearLayout mLlBrand;
    @BindView(R.id.line_map)
    View mLineMap;
    @BindView(R.id.iv_brand)
    ImageView mIvBrand;
    @BindView(R.id.rv_work)
    RecyclerView mRvWork;
    @BindView(R.id.ll_brands)
    LinearLayout mLlBrands;
    private int mtotalWidth;
    private int mtotalHeight;
    @BindView(R.id.mapview_map)
    MapView mMapView;
    @BindView(R.id.tv_map_city)
    TextView tv_city;
    @BindView(R.id.tv_map_brand)
    TextView tv_brand;
    @BindView(R.id.ll_map_gan)
    LinearLayout line;
    @BindView(R.id.selector_city)
    MapSelector mMapSelector;
    @BindView(R.id.selector_brand)
    SingleSelector mBrandSelector;
    @BindView(R.id.fl_selector)
    FrameLayout fl_selector;
    private MapModel mMapModel;
    private MapBean mMapBean;

    private int selecrotHeight = BaseUtil.dip2px(380);
    private boolean isHideMapSelecotr; //是否展开pop
    private boolean isHideBrandSelecotr;
    private WorkAdapter mBrandAdapter;

    private boolean isShowMap = true;  //是否展开Map
    private boolean isShowBrand = true;    //是否展开Brand
    public AMapLocationClient mLocationClient = null;
    public AMapLocationListener mLocationListener;
    private AMap mMap;
    private boolean isLocation;
    private boolean isClickProvince;//是否点击省
    private List<Marker> mMarkerList;
    private List<WorkBean> mMapBrandList;
    private GeocodeSearch mGeocodeSearch;
    private DistrictSearch mSearch;
    private String str_province;//搜索的省份
    private String str_brandID;//搜索的品牌ID
    private String str_city;//搜索的城市
    private String str_area;//搜索的地区
    private int codeProvince;//城市编码
    private boolean isByMap;//是否通过省份-城市-地区搜索商店数据，true：定位到该地区；false：不移动


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);

        init();
        initBrand();
        initMap();
        initSelecotr();
        initData();
        initLocation();
        initListener();
    }

    private void init() {

        mMapBean = new MapBean();

        mMarkerList = new ArrayList<>();
        mMapModel = new MapModel();
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        mtotalHeight = wm.getDefaultDisplay().getHeight();
    }



    private void initMap() {
        mMap = mMapView.getMap();
        mGeocodeSearch = new GeocodeSearch(getActivity());
        mGeocodeSearch.setOnGeocodeSearchListener(this);
        mSearch = new DistrictSearch(getActivity());
        mMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
        mMap.getUiSettings().setRotateGesturesEnabled(false);//旋转
        mMap.getUiSettings().setTiltGesturesEnabled(false);//倾斜


    }

    /**
     * 重置
     */
    public void initSelecotr() {
        //当点击其他页面时，需要重置Selector
        isHideMapSelecotr = false;
        isHideBrandSelecotr = false;
        isShowMap = true;
        isShowBrand = true;
        mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
        tv_city.setTextColor(getResources().getColor(R.color.text_color1));
        mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
        tv_brand.setTextColor(getResources().getColor(R.color.text_color1));


        mMapSelector.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mMapSelector.getLayoutParams().height = 0;
        mMapSelector.requestLayout();
        mMapSelector.setHideDeleteIV(true);
        mLlBrands.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mLlBrands.getLayoutParams().height = 0;
        mLlBrands.requestLayout();
    }

    private void initBrand() {
        mBrandSelector.setVisibility(View.GONE);
        mBrandAdapter = new WorkAdapter();

        mMapBrandList = new ArrayList<>();
        mRvWork.setLayoutManager((new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false)));
        mRvWork.addItemDecoration(new SpacesItemDecoration(0, 5));
        mRvWork.setAdapter(mBrandAdapter);
        mIvBrand.setVisibility(View.VISIBLE);
        mIvBrand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.Logout(getContext());
                getActivity().finish();
            }
        });
    }


    /**
     * 定位
     */
    private void initLocation() {
        mLocationClient = new AMapLocationClient(getContext().getApplicationContext());
        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {

                    if (aMapLocation.getErrorCode() == 0) {
                        if (!isLocation) {
                            aMapLocation.getLocationType();
                            double v = aMapLocation.getLatitude();//获取纬度
                            double v1 = aMapLocation.getLongitude();//获取经度
                            LatLng latLng2 = new LatLng(v, v1);
                            mMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng2));
                            mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
                            isLocation = true;
                            //定位上传到后台-内部人员
                            mMapModel.pushLocation(latLng2, new BeanCallback<BaseJson>() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(BaseJson response, int id) {

                                }
                            });


                        }

                    }
                }
            }
        };

        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(false);
        option.setNeedAddress(true);
        option.setOnceLocation(false);
        option.setHttpTimeOut(2000);
        option.setWifiActiveScan(true);
        option.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        option.setInterval(2000);
        mLocationClient.setLocationListener(mLocationListener);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    private void initListener() {
        mLlCity.setOnClickListener(this);
        mLlBrand.setOnClickListener(this);
        mMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
                if (isHideMapSelecotr) {
                    setMapSelector();
                    isShowMap = true;
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                }
                if (isHideBrandSelecotr) {
                    setBrandSelector();
                    isShowBrand = true;
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_brand.setTextColor(getResources().getColor(R.color.text_color1));
                }
            }
        });
        mMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                //阻断全国搜索
                //                if (isBrandChange) {
                //                    return;
                //                }
                LatLng l1 = mMap.getProjection().fromScreenLocation(new Point(0, mtotalHeight));
                LatLng l2 = mMap.getProjection().fromScreenLocation(new Point(mtotalWidth, 0));
                //                Log.e("@#","longitude1="+l1.longitude+" latitude1="+l1.latitude);
                //                Log.e("@#","longitude2="+l2.longitude+" latitude2="+l2.latitude);
                //                if (!isFst){
                //                    getLocationShop(l1,l2);
                //                    isFst=true;
                //                }
                //                getLocationShop(l1, l2);
            }
        });
        //点击Marker进入商店详细界面
        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                SearchBean searchBean = (SearchBean) marker.getObject();
                IntentHelper.openShopActivity(getActivity(), searchBean);
                return true;
            }
        });
    }

    private void initData() {
//        SearchShop("null,null,null,null,null,null,null");//内部人员
        SearchShop("null,null,null,null,"+Const.getUserid()+",null,null");//品牌方
        mMapModel.getProvinceData(getActivity(), new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.add(0, new MapBean.Province("全部", -1));
                    str_province = mMapBean.mProvinces.get(0).ProvinceName;//默认第一个省份
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    mMapSelector.setProvinceAdapter(mMapBean, getActivity());
                }
            }
        });
        // 默认3
        mMapSelector.setCityAdapter(mMapBean, getActivity());
        //        mMapModel.getCityAreaData(-1, new MapModel.onResultCityListener() {
        //            @Override
        //            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
        //                if (isSuccess){
        //                    mMapBean.mCities=data;
        //                    mMapSelector.setCityAdapter(mMapBean,getActivity());
        //                }
        //            }
        //        });

        mMapModel.getBrandSonData(Const.getUserid(), new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess) {
                    mMapBrandList.clear();
                    if (brandData!=null && brandData.size()>0){

                        for (int i = 0; i < brandData.size(); i++) {
                            mMapBrandList.add(new WorkBean(brandData.get(i).getSubClassName(),brandData.get(i).getSubClassId()));
                        }
                        mMapBrandList.add(0,new WorkBean("全部","-1"));
                    }
                    mBrandAdapter.notifyData(mMapBrandList);
                }
            }
        });

        mBrandAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i < mMapBrandList.size(); i++) {
                    if (i == holder.getPosition()) {
                        mMapBrandList.get(i).setCheck(true);
                        if ("-1".equals(mMapBrandList.get(i).getId())) {
                            //搜全部
                            SearchShop("null,null,null,null,"+Const.getUserid()+",null,null");//品牌方
                        } else {
                            SearchShop("null,null,null,null," +Const.getUserid() +","+mMapBrandList.get(i).getId()+ ",null");
                        }
                        mLlBrand.performClick();
                        continue;
                    }
                    mMapBrandList.get(i).setCheck(false);
                }
                mBrandAdapter.notifyData(mMapBrandList);
            }
        });



        mMapSelector.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, String ProvinceName) {
                str_province = ProvinceName;
                codeProvince = ProvinceID;
                Log.e("@#", "ProvinceName=" + ProvinceName);
                ChangeProvince(ProvinceID);


            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                isByMap = true;
                //点击全省的搜索
                if (CityID == -2 && AreaID == -2) {
                    isClickProvince = true;
                    SearchShop("null," + str_province + ",null,null,"+Const.getUserid()+",null,null");
                    mLlCity.performClick();
                    return;
                }
                str_city = CityName;
                str_area = AreaName;
                if ("北京".equals(str_province)
                        || "上海".equals(str_province)
                        || "天津".equals(str_province)
                        || "重庆".equals(str_province)) {
                    str_city = str_province + "市";
                    str_area = CityName + AreaName;
                } else if ("台湾".equals(str_province)) {
                    str_city = AreaName;
                    str_area = CityName + AreaName;
                }
                SearchShop("null," + str_province + "," + CityName + "," + AreaName + ","+Const.getUserid()+",null,null");
                mLlCity.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });




        mBrandSelector.setRightAdapter(getActivity(), new ArrayList<BrandBean>());
        //6d78da2a-bd23-11e7-bb43-00ffaa44f255
        //        mMapModel.getBrandSonData("9ef3c864-b53d-11e7-9b64-00ffaa44255a", new MapModel.OngetBrandResultListener() {
        //            @Override
        //            public void Result(boolean isSuccess, List<BrandBean> brandData) {
        //                if (isSuccess){
        //                    mBrandSonBeen=brandData;
        //                    mBrandSelector.setRightAdapter(getActivity(),mBrandSonBeen);
        //                }
        //            }
        //        });

        mBrandSelector.setOnSingleClickListener(new SingleSelector.OnSingleClickListener() {

            @Override
            public void onClickBrand(String BrandID, String BrandName) {
                str_brandID = BrandID;
                ChangeBrand(BrandID);
            }

            @Override
            public void onClickDetail(String BrandSonID, String BrandSonName) {
                isByMap = false;
                mLlBrand.performClick();
                SearchShop("null,null,null,null," + str_brandID + "," + BrandSonID + ",null");
            }

            @Override
            public void onClickBack() {

            }
        });


    }


    /**
     * 点击省，改变市级列表
     *
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID) {
        //全部的查找
        if (ProcinveID == -1) {
            mMapBean.mCities.clear();
            mMapSelector.notifyData(mMapBean);
            isByMap = false;
            SearchShop("null,null,null,null,"+Const.getUserid()+",null,null");
            mMap.moveCamera(CameraUpdateFactory.zoomTo(4));
            mLlCity.performClick();
            return;
        }

        mMapModel.getCityAreaData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess) {
                    mMapBean.mCities = data;
                    ArrayList<MapBean.Area> areaList = new ArrayList<MapBean.Area>();
                    areaList.add(new MapBean.Area("全部", -2));
                    MapBean.City city = new MapBean.City("全部", -2, areaList);
                    mMapBean.mCities.add(0, city);
                    mMapSelector.notifyData(mMapBean);

                }
            }
        });
        //        isByMap=true;
        //        isClickProvince=true;
        //        SearchShop("null,"+str_province+",null,null,null,null,null");
    }

    /**
     * 改变品牌
     *
     * @param BrandID
     */
    private void ChangeBrand(String BrandID) {

    }

    /**
     * 屏幕滑动，通过左下右上两角找寻当前界面内的商店数据
     *
     * @param l1
     * @param l2
     */
    private void getLocationShop(LatLng l1, LatLng l2) {
        if (mMarkerList != null && mMarkerList.size() > 0) {
            //清除原有的数据
            for (Marker marker : mMarkerList) {
                marker.destroy();
            }
            mMarkerList.clear();
        }
        //添加后续新的数据
        mMapModel.getLocationShop(l1, l2, new MapModel.OnLocationShopResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> shops) {
                if (isSuccess) {
                    for (int i = 0; i < shops.size(); i++) {
                        View view = View.inflate(getActivity(), R.layout.layout_infowindow, null);
                        TextView tv = (TextView) view.findViewById(R.id.tv_text);
                        tv.setText(shops.get(i).getProjectName());
                        Bitmap b = convertViewToBitmap(view);
                        LatLng l = new LatLng(Double.valueOf(shops.get(i).getLatitude()), Double.valueOf(shops.get(i).getLongitude()));
                        Marker marker = mMap.addMarker(new MarkerOptions()
                                .position(l)
                                .infoWindowEnable(false)
                                .icon(BitmapDescriptorFactory.fromBitmap(b)));
                        marker.setObject(shops.get(i));
                        mMarkerList.add(marker);
                    }
                }

            }
        });
    }


    private boolean isBrandChange;//用于阻隔全国搜索某个品牌时，全部品牌搜索的触发

    private void SearchShop(String condition) {
        if (isByMap) {
            //地理编码 定位到该位置
            GeocodeQuery query = null;
            if (isClickProvince) {
                //点击省，查找全省
                //                query=new GeocodeQuery("",codeProvince+"");
                DistrictSearchQuery Proquery = new DistrictSearchQuery();
                Proquery.setKeywords(str_province);
                Proquery.setShowBoundary(true);
                mSearch.setQuery(Proquery);
                mSearch.setOnDistrictSearchListener(this);//绑定监听器
                mSearch.searchDistrictAnsy();//开始搜索
                isClickProvince = false;
            } else {
                query = new GeocodeQuery(str_area, str_city);
                mGeocodeSearch.getFromLocationNameAsyn(query);
            }

        } else {
            isBrandChange = true;
            mMap.moveCamera(CameraUpdateFactory.zoomTo(4));
        }
        //搜索该定位附近的店
        mMapModel.getBrandSearchData(condition, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                isBrandChange = false;
                if (isSuccess) {
                    setSearchShop(data);

                }
            }
        });
    }

    private void setSearchShop(List<SearchBean> data) {
        if (mMarkerList != null && mMarkerList.size() > 0) {
            //清除原有的数据
            for (Marker marker : mMarkerList) {
                marker.destroy();
            }
            mMarkerList.clear();
        }
        for (int i = 0; i < data.size(); i++) {
            SearchBean shop = data.get(i);
            View view = View.inflate(getActivity(), R.layout.layout_infowindow, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_text);
            tv.setText(shop.getProjectName());
            Bitmap b = convertViewToBitmap(view);
            LatLng l = new LatLng(Double.valueOf(shop.getLatitude()), Double.valueOf(shop.getLongitude()));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(l)
                    .infoWindowEnable(false)
                    .icon(BitmapDescriptorFactory.fromBitmap(b)));
            marker.setObject(shop);
            mMarkerList.add(marker);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_city:
                if (isShowMap) {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    tv_city.setTextColor(getResources().getColor(R.color.map_text2));
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_brand.setTextColor(getResources().getColor(R.color.text_color1));
                    mMapSelector.setVisibility(View.VISIBLE);
                    mLlBrands.setVisibility(View.GONE);
                    isShowMap = false;
                    isShowBrand = true;
                    isHideMapSelecotr = false;
                } else {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowMap = true;
                }
                setMapSelector();
                break;
            case R.id.ll_brand:
                if (isShowBrand) {
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    tv_brand.setTextColor(getResources().getColor(R.color.map_text2));
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                    mMapSelector.setVisibility(View.GONE);
                    mLlBrands.setVisibility(View.VISIBLE);
                    isShowBrand = false;
                    isShowMap = true;
                    isHideBrandSelecotr = false;
                } else {
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    tv_brand.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowBrand = true;

                }
                setBrandSelector();
                break;
        }
    }

    /**
     * 地图的选择器动画
     */
    private void setMapSelector() {
        ValueAnimator animator = null;
        if (!isHideMapSelecotr) {
            animator = ValueAnimator.ofInt(0, selecrotHeight);
            isHideMapSelecotr = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotHeight, 0);
            isHideMapSelecotr = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMapSelector.getLayoutParams().height = (int) animation.getAnimatedValue();
                mMapSelector.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 品牌的选择器动画
     */
    private void setBrandSelector() {
        ValueAnimator animator = null;
        if (!isHideBrandSelecotr) {
            animator = ValueAnimator.ofInt(0, selecrotHeight);
            isHideBrandSelecotr = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotHeight, 0);
            isHideBrandSelecotr = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLlBrands.getLayoutParams().height = (int) animation.getAnimatedValue();
                mLlBrands.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }


    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMapView != null) {
            mMapView.onDestroy();
        }
    }


    /**
     * 将view转为位图
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if ("钓鱼岛".equals(str_province)) {
            mMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(25.744676, 123.476492)));
            return;
        }
        LatLonPoint point = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
        LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    public void onDistrictSearched(DistrictResult districtResult) {
        if ("钓鱼岛".equals(str_province)) {
            mMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(25.744676, 123.476492)));
            return;
        }
        if (districtResult.getAMapException().getErrorCode() == 1000) {
            districtResult.getDistrict();
            LatLonPoint point = districtResult.getDistrict().get(0).getCenter();
            LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(8));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
