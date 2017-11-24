package com.jyt.baseapp.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
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
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.jyt.baseapp.view.fragment.MapFragment.convertViewToBitmap;

public class LocationActivity extends BaseActivity {

    @BindView(R.id.mapView_map)
    MapView mMapView;
    private int mtotalWidth;
    private int mtotalHeight;
    private AMap mMap;
    private boolean isLocation;
    private boolean isFst;
    private MapModel mMapModel;
    private List<Marker> mMarkerList;
    public AMapLocationClient mLocationClient=null;
    public AMapLocationListener mLocationListener;

    @Override
    protected int getLayoutId() {

        return R.layout.activity_location;
    }

    @Override
    protected View getContentView() {

        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        init();
        initMap();
        initLocation();

    }

    private void init(){

        setTextTitle("查看定位");
        mMapModel = new MapModel();
        mMarkerList = new ArrayList<>();
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
                LatLng l1=mMap.getProjection().fromScreenLocation(new Point(0,mtotalHeight));
                LatLng l2=mMap.getProjection().fromScreenLocation(new Point(mtotalWidth,0));
                Log.e("@#","longitude1="+l1.longitude+" latitude1="+l1.latitude);
                Log.e("@#","longitude2="+l2.longitude+" latitude2="+l2.latitude);
                if (!isFst){

                    isFst=true;
                }
                getLocationShop(l1,l2);
            }
        });

        mMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                SearchBean localData= (SearchBean) marker.getObject();
                Log.e("@#","local:"+localData.getProjectName());
                Intent intent = new Intent(LocationActivity.this,ShopActivity.class);
                intent.putExtra("shopinfo",localData);
                startActivity(intent);
                return true;
            }
        });
    }

    /**
     * 定位
     */
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
                            //                            mMap.addMarker(new MarkerOptions()
                            //                                    .position(latLng2)
                            //                                    .icon(BitmapDescriptorFactory
                            //                                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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

    /**
     * 屏幕滑动，通过左下右上两角找寻当前界面内的商店数据
     * @param l1
     * @param l2
     */
    private void getLocationShop(LatLng l1,LatLng l2){
        if (mMarkerList!=null &&mMarkerList.size()>0){
            //清除原有的数据
            for (Marker marker:mMarkerList){
                marker.destroy();
            }
            mMarkerList.clear();
        }
        //添加后续新的数据
        mMapModel.getLocationShop(l1, l2, new MapModel.OnLocationShopResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> shops) {
                if (isSuccess){
                    for (int i = 0; i < shops.size(); i++) {
                        View view=View.inflate(LocationActivity.this,R.layout.layout_infowindow,null);
                        TextView tv= (TextView) view.findViewById(R.id.tv_text);
                        tv.setText(shops.get(i).getProjectName());
                        Bitmap b=convertViewToBitmap(view);
                        LatLng l=new LatLng(Double.valueOf(shops.get(i).getLatitude()),Double.valueOf(shops.get(i).getLongitude()));
                        Marker marker=mMap.addMarker(new MarkerOptions()
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
}
