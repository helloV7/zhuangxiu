package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.ProjectHolder;

/**
 * @author LinWei on 2017/11/3 15:29
 */
public class ProjectAdapter extends BaseRcvAdapter {

    BaseViewHolder.OnViewHolderClickListener listener;

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ProjectHolder holder=new ProjectHolder(parent);
        holder.setOnViewHolderClickListener(listener);
        return holder;
    }

    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        this.listener=listener;
    }
}
