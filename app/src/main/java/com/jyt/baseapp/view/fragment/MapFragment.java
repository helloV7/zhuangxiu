package com.jyt.baseapp.view.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.MapSelector;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * @author LinWei on 2017/10/30 15:04
 */
public class MapFragment extends BaseFragment implements View.OnClickListener{

    private int mtotalWidth;
    @BindView(R.id.mapview_map)
    MapView mMapView;
    @BindView(R.id.tv_map_city)
    TextView tv_city;
    @BindView(R.id.tv_map_brand)
    TextView tv_brand;
    @BindView(R.id.ll_map_gan)
    LinearLayout line;
    private PopupWindow pop_city;
    private PopupWindow pop_brand;
    private MapModel mMapModel;
    private MapBean mMapBean;
    private MapSelector mMapSelector;

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
        initPopupWindow(mMapBean);
        initProvince();
        initListener();
    }

    private void initMap(){
        mMapBean=new MapBean();
        mMapModel = new MapModel();
        AMap map=mMapView.getMap();
        map.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
    }

    private void init(){
        WindowManager wm= (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth=wm.getDefaultDisplay().getWidth();
    }

    private void initPopupWindow(MapBean bean){
        View view=View.inflate(getActivity(),R.layout.pop_city,null);
        pop_city=CreatePopWindow(view);
        mMapSelector = (MapSelector) view.findViewById(R.id.selector_city);
        mMapSelector.getLayoutParams().width= (int) (mtotalWidth*0.8);
        mMapSelector.requestLayout();
        mMapSelector.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID) {
                notifyProvince(ProvinceID);
            }

            @Override
            public void onClickArea(int CityIndex, int AreaIndex) {

            }

            @Override
            public void onClickBack() {
                pop_city.dismiss();
            }
        });



    }

    private void initListener(){
        tv_city.setOnClickListener(this);
        tv_brand.setOnClickListener(this);
    }

    private void initProvince(){
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

    private void notifyProvince(int ProcinveID){
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
                if (!pop_city.isShowing()){
//                    pop_city.showAsDropDown(line,-10,-10);
                    pop_city.showAtLocation(line,Gravity.CENTER_HORIZONTAL, 0, -(line.getHeight()));
                    tv_city.setText("城市∧");
                    tv_city.setTextColor(getResources().getColor(R.color.map_text2));
                }else {
                    pop_city.dismiss();
                    tv_city.setText("城市∨");
                    tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                }
                break;
            case R.id.tv_map_brand:
                pop_city.dismiss();
                tv_city.setText("城市∨");
                tv_city.setTextColor(getResources().getColor(R.color.text_color1));
                break;
        }
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
