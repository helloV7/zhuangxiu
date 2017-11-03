package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.ProjectBean;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/3 15:42
 */
public class ProjectHolder extends BaseViewHolder<ProjectBean> {

    @BindView(R.id.tv_shopName)
    TextView mTvShopName;
    @BindView(R.id.tv_shopMsg)
    TextView mTvShopMsg;
    @BindView(R.id.tv_shopStation)
    TextView mTvShopStation;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_shopLocation)
    TextView mTvShopLocation;

    public ProjectHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void setData(ProjectBean data) {
        super.setData(data);
        mTvShopName.setText(data.getShopName());
        mTvShopMsg.setText(data.getShopMsg());
        mTvShopStation.setText(data.getStation());
        mTvShopLocation.setText(data.getLocation());
        if (data.isStop()){
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.color_stop));
        }else {
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.map_text2));
        }
        if (data.isNext()){
            mIvNext.setVisibility(View.VISIBLE);
            mTvShopStation.setVisibility(View.GONE);
            mTvShopLocation.setVisibility(View.GONE);
        }else {
            mIvNext.setVisibility(View.GONE);
            mTvShopStation.setVisibility(View.VISIBLE);
            mTvShopLocation.setVisibility(View.VISIBLE);
        }
    }
}
