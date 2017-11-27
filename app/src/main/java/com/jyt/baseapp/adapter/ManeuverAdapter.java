package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.ManeuverViewHolder;

/**
 * @author LinWei on 2017/11/24 11:01
 */
public class ManeuverAdapter extends BaseRcvAdapter {

    BaseViewHolder.OnViewHolderClickListener listener;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ManeuverViewHolder holder = new ManeuverViewHolder(parent);
        holder.setOnViewHolderClickListener(listener);
        return holder;
    }

    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        this.listener=listener;
    }


}
