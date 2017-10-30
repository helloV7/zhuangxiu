package com.jyt.baseapp.view.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by chenweiqi on 2017/5/10.
 */

public class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    T data;


    OnViewHolderClickListener onViewHolderClickListener;


    OnViewHolderLongClickListener onViewHolderLongClickListener;

    public BaseViewHolder(View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onViewHolderClickListener!=null){
                    onViewHolderClickListener.onClick(BaseViewHolder.this);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onViewHolderLongClickListener!=null){
                    onViewHolderLongClickListener.onLongClick(BaseViewHolder.this);
                    return true;
                }
                return false;
            }
        });
    }

    public void setData(T data){
        this.data = data;
    }

    public T getData(){
        return data;
    }


    public void setOnViewHolderLongClickListener(OnViewHolderLongClickListener onViewHolderLongClickListener) {
        this.onViewHolderLongClickListener = onViewHolderLongClickListener;
    }

    public void setOnViewHolderClickListener(OnViewHolderClickListener onViewHolderClickListener) {
        this.onViewHolderClickListener = onViewHolderClickListener;
    }

    public interface OnViewHolderClickListener{
        void onClick(BaseViewHolder holder);
    }
    public interface OnViewHolderLongClickListener{
        void onLongClick(BaseViewHolder holder);
    }
}
