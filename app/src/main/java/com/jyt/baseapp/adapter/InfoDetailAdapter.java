package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.InfoDetailViewHolder;

/**
 * @author LinWei on 2017/11/7 13:59
 */
public class InfoDetailAdapter extends BaseRcvAdapter {

    BaseViewHolder.OnViewHolderClickListener listener;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        InfoDetailViewHolder holder=new InfoDetailViewHolder(parent);
        holder.setOnViewHolderClickListener(listener);
        return holder;
    }

    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        this.listener=listener;
    }

}
