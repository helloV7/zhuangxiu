package com.jyt.baseapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.view.viewholder.SingleTextViewHolder;

import java.util.List;

/**
 * @author LinWei on 2017/11/2 10:31
 */
public class BrandAdapter extends RecyclerView.Adapter<SingleTextViewHolder> {
    private Context context;
    private LayoutInflater mInflater;
    private List<BrandBean> data;
    private int state;
    public BrandAdapter(Context context, List<BrandBean> data,int state){
        this.context=context;
        this.data=data;
        this.state=state;
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
        final BrandBean singleData=data.get(position);

        holder.tv_SingleText.setText(singleData.getBrandName());
        LinearLayout ll= (LinearLayout) holder.tv_SingleText.getParent();
        if (isCentenr){
            ll.setGravity(Gravity.CENTER_HORIZONTAL);
            ll.setPadding(0,0,0,0);
        }else {
            ll.setGravity(Gravity.LEFT);
            ll.setPadding(40,0,0,0);
        }
        if (state==0){
            if (singleData.isCheck()){
                holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.map_text2));
            }else {
                holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.text_color1));
            }
        }else if (state==1){
            if (singleData.isCheck()){
                holder.tv_SingleText.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_blue));
                holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.map_text2));
            }else {
                holder.tv_SingleText.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_trans));
                holder.tv_SingleText.setTextColor(context.getResources().getColor(R.color.map_text1));
            }
        }
        holder.tv_SingleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(singleData.getBrandId(),holder,position);
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

    public void notifyData(List<BrandBean> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public interface OnSingleClickListener{
        void onClick(String BrandSonID,SingleTextViewHolder holder,int position);
    }
    public OnSingleClickListener listener;
    public void setOnSingleClickListener(OnSingleClickListener listener){
        this.listener=listener;
    }
}
