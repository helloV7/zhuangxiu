package com.jyt.baseapp.adapter;

import android.view.ViewGroup;

import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.viewholder.PicViewHolder;
import com.jyt.baseapp.view.widget.TitleAndFlowImages;

/**
 * @author LinWei on 2017/12/4 16:01
 */
public class PicAdapter extends BaseRcvAdapter{
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        PicViewHolder holder =new PicViewHolder(parent);
        holder.setOnImageClickListener(onImageClickListener);
        return holder;
    }

    private TitleAndFlowImages.OnImageClickListener onImageClickListener;

    public void setOnImageClickListener(TitleAndFlowImages.OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

}
