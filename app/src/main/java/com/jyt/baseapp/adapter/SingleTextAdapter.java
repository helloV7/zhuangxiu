package com.jyt.baseapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.view.viewholder.SingleTextViewHolder;

/**
 * @author LinWei on 2017/11/1 14:14
 */
public class SingleTextAdapter extends RecyclerView.Adapter<SingleTextViewHolder> {
    public MapBean data ;
    private LayoutInflater mInflater;
    private android.content.Context context;
    public SingleTextAdapter(MapBean data , Context context){
        this.data=data;
        this.context=context;
        mInflater=LayoutInflater.from(context);
    }
    @Override
    public SingleTextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.item_singletext,parent,false);
        SingleTextViewHolder holder=new SingleTextViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final SingleTextViewHolder holder, final int position) {

        MapBean.Province province=data.mProvinces.get(position);
        holder.tv_SingleText.setText(province.ProvinceName);
        holder.tv_SingleText.setGravity(Gravity.CENTER_HORIZONTAL);
        if (province.isCheckProvince){
            holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.map_text2));
        }else {
            holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.text_color1));
        }
        holder.tv_SingleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(position,holder);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.mProvinces.size();
    }

    public interface OnProvinceClickListener{
        void onClick(int position,SingleTextViewHolder holder);
    }
    public OnProvinceClickListener listener;
    public void setOnProvinceClickListener(OnProvinceClickListener listener){
        this.listener=listener;
    }

    public void notifyData(MapBean data){
        this.data=data;
        notifyDataSetChanged();
    }
}
