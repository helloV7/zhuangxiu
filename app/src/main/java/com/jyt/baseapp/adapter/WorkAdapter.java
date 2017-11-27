package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.WorkViewHolder;

/**
 * @author LinWei on 2017/11/27 17:43
 */
public class WorkAdapter extends BaseRcvAdapter{
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        WorkViewHolder holder = new WorkViewHolder(parent);
        return holder;
    }
}
