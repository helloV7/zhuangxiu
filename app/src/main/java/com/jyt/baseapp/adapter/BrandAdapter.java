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

import java.util.List;

/**
 * @author LinWei on 2017/11/2 10:31
 */
public class BrandAdapter extends RecyclerView.Adapter<SingleTextViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<MapBean.Province> data;
    public BrandAdapter(Context context, List<MapBean.Province> data){
        this.context=context;
        this.data=data;
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
        MapBean.Province singleData=data.get(position);
        holder.tv_SingleText.setText(singleData.ProvinceName);
        if (isCentenr){
            holder.tv_SingleText.setGravity(Gravity.CENTER_HORIZONTAL);
        }else {
            holder.tv_SingleText.setGravity(Gravity.LEFT);
        }

        if (singleData.isCheckProvince){
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
    private boolean isCentenr;
    public void setCentenr(boolean isCentenr){
        this.isCentenr=isCentenr;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void notifyData(List<MapBean.Province> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public interface OnSingleClickListener{
        void onClick(int position,SingleTextViewHolder holder);
    }
    public OnSingleClickListener listener;
    public void setOnSingleClickListener(OnSingleClickListener listener){
        this.listener=listener;
    }
}
