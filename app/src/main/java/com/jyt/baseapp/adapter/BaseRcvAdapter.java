package com.jyt.baseapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.List;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public abstract class BaseRcvAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    List dataList;

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (dataList!=null){
            return dataList.size();
        }
        return 0;
    }
}
