package com.jyt.baseapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.jyt.baseapp.R;


/**
 * @author LinWei on 2017/10/26 21:16
 */
public class InfoAdapter implements AMap.InfoWindowAdapter{
    private String agentName;
    private String snippet;
    private LatLng latLng;
    private Context context;
    private Marker marker;

    public InfoAdapter(Context context){
        this.context=context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        initData(marker);
        View view=initView();
        return view;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }

    private void initData(Marker marker) {
        latLng=marker.getPosition();
        agentName=marker.getTitle();
        snippet=marker.getSnippet();

    }


    public View initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.layout_infowindow,null);
        TextView tv= (TextView) view.findViewById(R.id.tv_text);
        tv.setText(agentName);
        return view;
    }



}
