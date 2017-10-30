package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/10/30 15:04
 */
public class MapFragment extends BaseFragment {

    private MapView mMapView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView= (MapView) rootView.findViewById(R.id.mapview_map);
        mMapView.onCreate(savedInstanceState);
        AMap map=mMapView.getMap();
        map.getUiSettings().setZoomControlsEnabled(false);//隐藏缩放按钮
        return rootView;
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
