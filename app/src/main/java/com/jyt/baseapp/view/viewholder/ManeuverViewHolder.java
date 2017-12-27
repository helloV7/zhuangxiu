package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.ManeuverBean;
import com.jyt.baseapp.view.widget.CircleImageView;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/27 17:33
 */
public class ManeuverViewHolder extends BaseViewHolder<ManeuverBean> {

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.tv_msg1)
    TextView mTvMsg1;
    @BindView(R.id.tv_msg2)
    TextView mTvMsg2;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_tel)
    TextView mTvTel;

    public ManeuverViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false));
    }

    @Override
    public void setData(ManeuverBean data) {
        super.setData(data);
        mIvNext.setVisibility(View.GONE);
        mTvMsg1.setText(data.getPersonalname());
        mTvMsg2.setText(data.getName()+"  "+data.getCity()+data.getArea());
        mTvTel.setText(data.getTel());
        mTvTel.setVisibility(View.VISIBLE);
        Glide.with(itemView.getContext())
                .load(data.getImage())
                .error(R.mipmap.icon_person)
                .into(mIvLogo);
    }
}
