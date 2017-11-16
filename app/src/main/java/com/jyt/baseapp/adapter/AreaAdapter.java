package com.jyt.baseapp.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.viewholder.AreaViewHolder;

/**
 * @author LinWei on 2017/11/1 11:47
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaViewHolder> {
    private MapBean data;
    private LayoutInflater mInflater;
    private Context context;
    private int mTotalWidth;
    public AreaAdapter(MapBean data,android.content.Context context){
        this.data=data;
        this.context=context;
        mInflater=LayoutInflater.from(context);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        mTotalWidth = wm.getDefaultDisplay().getWidth();
    }
    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView=mInflater.inflate(R.layout.item_area,parent,false);
        AreaViewHolder holder=new AreaViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AreaViewHolder holder, final int position) {
        int height;
        int spaceH= BaseUtil.dip2px(15);//Flow 行距
        int spaceW=BaseUtil.dip2px(15);//Flow 控件间距
        int txtHeight=BaseUtil.dip2px(25);//假设的行高
        int lineNum=1;
        int width= (int) ((mTotalWidth*0.63)-10-spaceW*3)/3;
        MapBean.City City =data.mCities.get(position);
        //设置城市
        holder.tv_city.setText(City.CityName);
        if (data.mCities.get(position).isCheckCity) {
            holder.tv_city.setTextColor(context.getResources().getColor(R.color.map_text2));
        }else {
            holder.tv_city.setTextColor(context.getResources().getColor(R.color.text_color1));
        }
        //设置区域
        holder.fl_area.removeAllViews();
        for (int j = 0; j < City.mAreas.size(); j++) {
            TextView area=new TextView(context);
            area.setSingleLine();
            area.setEllipsize(TextUtils.TruncateAt.END);
            area.setGravity(Gravity.CENTER);
            area.setText( City.mAreas.get(j).AreaName);
            area.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_trans));
            area.setTextColor(context.getResources().getColor(R.color.map_text1));
            area.setTextSize(12);
            if (j>0 && j%3==0){
                lineNum++;
            }
            if (City.mAreas.get(j).isCheckArea){
                area.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_blue));
                area.setTextColor(context.getResources().getColor(R.color.map_text2));
            }else {
                area.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_trans));
                area.setTextColor(context.getResources().getColor(R.color.map_text1));
            }
            final int index=j;
            //区域点击事件
            area.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < holder.fl_area.getChildCount(); i++) {
                        TextView tv= (TextView) holder.fl_area.getChildAt(i);
                        tv.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_trans));
                        tv.setTextColor(context.getResources().getColor(R.color.map_text1));
                    }
                    for (int i = 0; i < getItemCount(); i++) {
                        //遍历全部的城市，置为false
                        data.mCities.get(i).isCheckCity=false;

                    }
                    //设置点中区域的城市，置为true
                    data.mCities.get(position).isCheckCity=true;
                    v.setBackground(context.getResources().getDrawable(R.drawable.bg_corner_blue));
                    TextView tv= (TextView) v;
                    tv.setTextColor(context.getResources().getColor(R.color.map_text2));
                    if (listener!=null){
                        listener.onClick(position,index,holder);
                    }
                }
            });
            holder.fl_area.addView(area);
            area.getLayoutParams().width=width;
            area.requestLayout();
        }
        height=lineNum*txtHeight+(lineNum-1)*spaceH;
        holder.fl_area.setRowSpacing(spaceH);
        holder.fl_area.setChildSpacing(spaceW);
        holder.fl_area.setPadding(5,0,5,0);
        holder.fl_area.getLayoutParams().height=height;
        holder.fl_area.requestLayout();
        if (position==data.mCities.size()-1){
            holder.line.setVisibility(View.GONE);
        }else {
            holder.line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        if (data.mCities==null){
            return 0;
        }else {
            return data.mCities.size();
        }
    }

    public interface onAreaClickListener{
        void onClick(int position,int AreaIndex,AreaViewHolder holder);
    }
    private onAreaClickListener listener;
    public void setonAreaClickListener(onAreaClickListener listener){
        this.listener=listener;
    }


    public void notifyData(MapBean data){
        this.data=data;
        notifyDataSetChanged();
    }
}
