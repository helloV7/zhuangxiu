package com.jyt.baseapp.view.activity;

import android.content.Context;
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
import com.jyt.baseapp.adapter.InfoAdapter;
import com.jyt.baseapp.bean.LocationBean;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.model.WorkerModel;
import com.jyt.baseapp.util.BaseUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class LocationActivity extends BaseActivity {

    @BindView(R.id.mapView_map)
    MapView mMapView;
    private int mtotalWidth;
    private int mtotalHeight;
    private AMap mMap;
    private boolean isLocation;
    private boolean isFst;
    private MapModel mMapModel;
    private WorkerModel mWorkerModel;
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
        mWorkerModel = new WorkerModel();
        mMarkerList = new ArrayList<>();
        WindowManager wm= (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth=wm.getDefaultDisplay().getWidth();
        mtotalHeight=wm.getDefaultDisplay().getHeight();

    }

    private void initMap(){
        mMap = mMapView.getMap();
        mMap.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
        mMap.getUiSettings().setRotateGesturesEnabled(false);//旋转
        mMap.getUiSettings().setTiltGesturesEnabled(false);//倾斜
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


//        mMap.setInfoWindowAdapter(new InfoAdapter(this));
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
                            //创建定位Marker
//                            mMap.addMarker(new MarkerOptions()
//                                    .position(latLng2)
//                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
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
     * 屏幕滑动，通过左下、右上两角找寻当前界面内的工作人员位置
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
       mWorkerModel.LocationWork(l1, l2, new WorkerModel.OngetWorkerListener() {
           @Override
           public void Result(boolean isSuccess, List<LocationBean> workers) {
               if (isSuccess && workers!=null ){
                   for (int i = 0; i < workers.size(); i++) {
                       View view = View.inflate(LocationActivity.this, R.layout.layout_luser, null);
                       TextView tv = (TextView) view.findViewById(R.id.tv_text);
                       tv.setText(workers.get(i).getNickName());
                       Bitmap b = convertViewToBitmap(view);
                       LatLng l=new LatLng(Double.valueOf(workers.get(i).getLatitude()),Double.valueOf(workers.get(i).getLongitude()));
                       Marker marker=mMap.addMarker(new MarkerOptions()
                               .position(l)
//                               .setInfoWindowOffset(50,48)
                               .infoWindowEnable(false)
                               .icon(BitmapDescriptorFactory.fromBitmap(b)));
//                               .title(workers.get(i).getNickName()));
                       mMarkerList.add(marker);
                       marker.showInfoWindow();
                   }
                   mMap.setInfoWindowAdapter(new InfoAdapter(LocationActivity.this));
               }
           }
       });
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
