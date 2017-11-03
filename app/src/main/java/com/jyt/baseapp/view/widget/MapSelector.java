package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.AreaAdapter;
import com.jyt.baseapp.adapter.SingleTextAdapter;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.view.viewholder.AreaViewHolder;
import com.jyt.baseapp.view.viewholder.SingleTextViewHolder;

/**
 * @author LinWei on 2017/11/1 13:45
 */
public class MapSelector extends LinearLayout {
    RecyclerView rv_left;
    RecyclerView rv_right;
    LinearLayout ll_parent;
    ImageView iv_back;

    private AreaAdapter mCityAdapter;
    private SingleTextAdapter mProvinceAdapter;
    public MapSelector(Context context) {
        super(context);
        init(context);
    }

    public MapSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MapSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_mapselector,this);
        ll_parent= (LinearLayout) this.findViewById(R.id.ll_parent);
        rv_left= (RecyclerView) this.findViewById(R.id.rv_select_left);
        rv_right= (RecyclerView) this.findViewById(R.id.rv_select_right);
        iv_back= (ImageView) this.findViewById(R.id.iv_select_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickBack();
                }
            }
        });
    }

    public void setProvinceAdapter(final MapBean bean,Context context){
        if (bean.mProvinces!=null && bean.mProvinces.size()>0){
            mProvinceAdapter=new SingleTextAdapter(bean,context);
            rv_left.setAdapter(mProvinceAdapter);
            rv_left.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            mProvinceAdapter.notifyDataSetChanged();
            mProvinceAdapter.setOnProvinceClickListener(new SingleTextAdapter.OnProvinceClickListener() {
                @Override
                public void onClick(int position, SingleTextViewHolder holder) {
                    int ProvinceID=0;
                    String ProvinceName=null;
                    for (int i = 0; i < bean.mProvinces.size(); i++) {
                        if (position==i){
                            bean.mProvinces.get(i).isCheckProvince=true;
                            ProvinceID=  bean.mProvinces.get(i).ProvinceID;
                            ProvinceName =   bean.mProvinces.get(i).ProvinceName;
                        }else {
                            bean.mProvinces.get(i).isCheckProvince=false;
                        }
                    }
                    mProvinceAdapter.notifyDataSetChanged();
                    if (listener!=null){
                        listener.onClickProvince(ProvinceID,ProvinceName);
                    }
                }
            });
            mProvinceAdapter.notifyDataSetChanged();
            rv_left.addItemDecoration(new SpacesItemDecoration(0,60));
        }
    }

    public void setCityAdapter(final MapBean bean,Context context){

        if (bean.mCities!=null && bean.mCities.size()>0){
            mCityAdapter =new AreaAdapter(bean,context);
            rv_right.setAdapter(mCityAdapter);
            rv_right.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
            mCityAdapter.setonAreaClickListener(new AreaAdapter.onAreaClickListener() {
                @Override
                public void onClick(int position, int AreaIndex, AreaViewHolder holder) {
                    //position-城市索引 AreaIndex-区域索引
                    int CityID=0;
                    int AreaID=0;
                    String CityName=null;
                    String AreaName=null;
                    for (int i = 0; i < bean.mCities.size(); i++) {
                        for (int j = 0; j < bean.mCities.get(i).mAreas.size(); j++) {
                            if (i==position && j==AreaIndex){
                                bean.mCities.get(i).mAreas.get(j).isCheckArea=true;
                                CityID = bean.mCities.get(i).CityID;
                                CityName = bean.mCities.get(i).CityName;
                                AreaID = bean.mCities.get(i).mAreas.get(j).AreaID;
                                AreaName = bean.mCities.get(i).mAreas.get(j).AreaName;
                            }else {
                                bean.mCities.get(i).mAreas.get(j).isCheckArea=false;
                            }
                        }

                    }
                    mCityAdapter.notifyDataSetChanged();
                    if (listener!=null){
                        listener.onClickArea(CityID,CityName,AreaID,AreaName);
                    }
                }
            });
            mCityAdapter.notifyDataSetChanged();
        }
    }

    public void notifyData(MapBean bean){
        if (bean.mProvinces!=null && bean.mProvinces.size()>0 && mProvinceAdapter!=null){
            mProvinceAdapter.notifyData(bean);
        }
        if (bean.mCities!=null && bean.mCities.size()>0 && mCityAdapter!=null){
            mCityAdapter.notifyData(bean);
        }


    }

    public interface OnMapClickListener{
        void onClickProvince(int ProvinceID ,String ProvinceName);
        void onClickArea(int CityID,String CityName,int AreaID,String AreaName);
        void onClickBack();
    }
    private OnMapClickListener listener;
    public void setOnMapClickListener(OnMapClickListener listener){
        this.listener=listener;
    }

    public int getRvHeight(){
        rv_right.measure(0,0);
        return rv_right.getMeasuredHeight();
    }

    public void setHideDeleteIV(boolean isHide){
        if (isHide){
            iv_back.setVisibility(GONE);
        }else {
            iv_back.setVisibility(VISIBLE);
        }
    }

}
