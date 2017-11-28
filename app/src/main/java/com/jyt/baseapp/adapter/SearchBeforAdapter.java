package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.SearchBeforViewHolder;

/**
 * @author LinWei on 2017/11/8 14:37
 */
public class SearchBeforAdapter extends BaseRcvAdapter {


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchBeforViewHolder holder=new SearchBeforViewHolder(parent);
        holder.setOnViewHolderClickListener(listener);
        return holder;
    }
    BaseViewHolder.OnViewHolderClickListener listener;
    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        this.listener=listener;
    }
}
