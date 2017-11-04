package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.PersonViewHolder;

/**
 * Created by v7 on 2017/11/4.
 */

public class SelPersonAdapter extends BaseRcvAdapter {
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PersonViewHolder holder = new PersonViewHolder(parent);
        holder.setOnViewHolderClickListener(onViewHolderClickListener);
        return holder;
    }

}
