package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.SearchAfterViewHolder;

/**
 * @author LinWei on 2017/11/8 14:37
 */
public class SearchAfterAdapter extends BaseRcvAdapter {


    BaseViewHolder.OnViewHolderClickListener listener;
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchAfterViewHolder holder=new SearchAfterViewHolder(parent);
        holder.setOnViewHolderClickListener(listener);
        return holder;
    }

    public void setOnViewHolderClickListener(BaseViewHolder.OnViewHolderClickListener listener){
        this.listener=listener;
    }

}
