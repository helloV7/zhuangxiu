package com.jyt.baseapp.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.amap.api.maps.model.MarkerOptions;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.MapSelector;
import com.jyt.baseapp.view.widget.SingleSelector;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * @author LinWei on 2017/10/30 15:04
 */
public class MapFragment extends BaseFragment implements View.OnClickListener{

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
    private PopupWindow pop_city;
    private PopupWindow pop_brand;
    private MapModel mMapModel;
    private MapBean mMapBean;
//    private MapSelector mMapSelector;
    private int selecrotHeight=BaseUtil.dip2px(380);
    private boolean isHideMapSelecotr; //是否展开pop
    private boolean isHideBrandSelecotr;
    private boolean isShowMap=true;  //是否展开Map
    private boolean isShowBrand=true;    //是否展开Brand
    public AMapLocationClient mLocationClient=null;
    public AMapLocationListener mLocationListener;
    private AMap mMap;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_map;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        init();
        initMap();
        initSelecotr();
//        initPopupWindow(mMapBean);
        initData();
        initLocation();
        initListener();
    }

    private void init(){
        mMapBean=new MapBean();
        mMapModel = new MapModel();
        WindowManager wm= (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth=wm.getDefaultDisplay().getWidth();
        mtotalHeight=wm.getDefaultDisplay().getHeight();
    }

    private void initMap(){
        mMap = mMapView.getMap();
        mMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
        mMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLng latLng1=mMap.getProjection().fromScreenLocation(new Point(0,0));
                LatLng latLng2=mMap.getProjection().fromScreenLocation(new Point(mtotalWidth,mtotalHeight));
                Log.e("@#","longitude1="+latLng1.longitude+" latitude1="+latLng1.latitude);
                Log.e("@#","longitude2="+latLng2.longitude+" latitude2="+latLng2.latitude);
            }
        });


    }

    private void initSelecotr(){
        mMapSelector.getLayoutParams().width= (int) (mtotalWidth*0.9);
        mMapSelector.getLayoutParams().height= 0;
        mMapSelector.requestLayout();
        mMapSelector.setHideDeleteIV(true);
        mBrandSelector.getLayoutParams().width= (int) (mtotalWidth*0.9);
        mBrandSelector.getLayoutParams().height= 0;
        mBrandSelector.requestLayout();
        mBrandSelector.setHideDeleteIV(true);

    }

    private void initPopupWindow(MapBean bean){
        View view=View.inflate(getActivity(),R.layout.pop_city,null);
        pop_city=CreatePopWindow(view);
        mMapSelector = (MapSelector) view.findViewById(R.id.selector_city);
        mMapSelector.getLayoutParams().width= (int) (mtotalWidth*0.8);
        mMapSelector.requestLayout();

    }
    private boolean isLocation;
    private void initLocation(){
        mLocationClient=new AMapLocationClient(getContext().getApplicationContext());
        mLocationListener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation!=null){

                    if (aMapLocation.getErrorCode()==0){
                        if (!isLocation){
                            aMapLocation.getLocationType();
                            double v=aMapLocation.getLatitude();//获取纬度
                            double v1=aMapLocation.getLongitude();//获取经度
                            LatLng latLng2=new LatLng(v,v1);
                            mMap.addMarker(new MarkerOptions()
                                    .position(latLng2)
                                    .icon(BitmapDescriptorFactory
                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            mMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng2));
                            isLocation=true;
                        }

                    }
                }
            }
        };
        AMapLocationClientOption option=new AMapLocationClientOption();
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

    private void initListener(){
        tv_city.setOnClickListener(this);
        tv_brand.setOnClickListener(this);
    }

    private void initData(){
        mMapModel.getProvinceData(new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, ArrayList<MapBean.Province> data) {
                if (isSuccess){
                    mMapBean.mProvinces=data;
                    mMapSelector.setProvinceAdapter(mMapBean,getActivity());
                }
            }
        });
        mMapModel.getCityData(1, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess){
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities=data;
                            mMapSelector.setCityAdapter(mMapBean,getActivity());
                        }
                    });
                }
            }
        });
    }

    /**
     * 点击省，改变市级列表
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID){
        mMapModel.getCityData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess){
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities=data;
                            mMapSelector.notifyData(mMapBean);
                        }
                    });
                }
            }
        });
    }


    /**
     * 生产PopupWindow
     * @param popView
     * @return
     */
    private PopupWindow CreatePopWindow(View popView) {
        final PopupWindow popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.getContentView().setFocusableInTouchMode(false);
        popupWindow.getContentView().setFocusable(true);
        popupWindow.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_MENU && event.getRepeatCount() == 0
                        && event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                    return true;
                }
                return false;
            }
        });
        return popupWindow;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_map_city:
                if (isShowMap){
                    tv_city.setText("城市∧");
                    tv_city.setTextColor(getResources().getColor(R.color.map_text2));
                    tv_brand.setText("品牌∨");
                    tv_brand.setTextColor(getResources().getColor(R.color.text_color1));
                    mMapSelector.setVisibility(View.VISIBLE);
                    mBrandSelector.setVisibility(View.GONE);
                    isShowMap=false;
                    isShowBrand=true;
                    isHideMapSelecotr=false;
                }else {
                    tv_city.setText("城市∨");
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowMap=true;
                }
                setMapSelector();
                break;
            case R.id.tv_map_brand:
                if (isShowBrand){
                    tv_brand.setText("品牌∧");
                    tv_brand.setTextColor(getResources().getColor(R.color.map_text2));
                    tv_city.setText("城市∨");
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                    mMapSelector.setVisibility(View.GONE);
                    mBrandSelector.setVisibility(View.VISIBLE);
                    isShowBrand=false;
                    isShowMap=true;
                    isHideBrandSelecotr=false;
                }else {
                    tv_brand.setText("品牌∨");
                    tv_brand.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowBrand=true;

                }
                setBrandSelector();
                break;
        }
    }

    /**
     * 地图的选择器动画
     */
    private void setMapSelector(){
        ValueAnimator animator=null;
        if (!isHideMapSelecotr){
            animator=ValueAnimator.ofInt(0,selecrotHeight);
            isHideMapSelecotr =true;
        }else {
            animator=ValueAnimator.ofInt(selecrotHeight,0);
            isHideMapSelecotr =false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mMapSelector.getLayoutParams().height= (int) animation.getAnimatedValue();
                mMapSelector.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 品牌的选择器动画
     */
    private void setBrandSelector(){
        ValueAnimator animator=null;
        if (!isHideBrandSelecotr){
            animator=ValueAnimator.ofInt(0,selecrotHeight);
            isHideBrandSelecotr =true;
        }else {
            animator=ValueAnimator.ofInt(selecrotHeight,0);
            isHideBrandSelecotr =false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mBrandSelector.getLayoutParams().height= (int) animation.getAnimatedValue();
                mBrandSelector.requestLayout();
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
        mMapView.onDestroy();
    }







}
