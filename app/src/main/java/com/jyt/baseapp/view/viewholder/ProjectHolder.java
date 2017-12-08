package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.SearchBean;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/3 15:42
 */
public class ProjectHolder extends BaseViewHolder<SearchBean> {

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
    public void setData(SearchBean data) {
        super.setData(data);
        mTvShopName.setText(data.getProjectName());
        mTvShopMsg.setText(data.getBrandName()+" "+data.getSubClassName());
        mTvShopStation.setText(data.getSchedule());
        if("暂停中".equals(data.getAddress())){
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.color_stop));
        }else {
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.map_text2));
        }
        mTvShopLocation.setText(data.getAddress());
    }
}
