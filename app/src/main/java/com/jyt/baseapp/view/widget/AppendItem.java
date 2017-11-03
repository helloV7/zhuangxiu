package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/11/2 18:57
 */
public class AppendItem extends RelativeLayout {
    private LinearLayout ll_item;
    private ImageView iv_complete;
    private ImageView iv_next;
    private TextView tv_msg;
    private TextView tv_estimate;
    private TextView tv_time;
    private boolean isNext;
    public AppendItem(Context context) {
        super(context);
        init(context);
    }

    public AppendItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AppendItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.item_append,this);
        ll_item= (LinearLayout) this.findViewById(R.id.ll_progress_item);
        iv_complete= (ImageView) this.findViewById(R.id.iv_complete);
        iv_next= (ImageView) this.findViewById(R.id.iv_progress_next);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        tv_estimate= (TextView) findViewById(R.id.tv_estimate);
        tv_time= (TextView) findViewById(R.id.tv_time);
        ll_item.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNext){
                    if (listener!=null){
                        listener.onClick();
                    }
                }
            }
        });

    }


    public void setComplete(boolean isComplete){
        if (isComplete){

        }
    }

    public void setTv_msg(String msg){
        tv_msg.setText(msg);
    }

    public void setTv_time(String time){
        tv_time.setText(time);
    }

    public void setNext(boolean isNext){
        this.isNext=isNext;
        if (isNext){
            iv_next.setVisibility(VISIBLE);
        }else {
            iv_next.setVisibility(GONE);
        }
    }

    public void setEstimate(boolean isShow){
        if (isShow){
            tv_estimate.setVisibility(VISIBLE);
        }else {
            tv_estimate.setVisibility(GONE);
        }
    }

    public interface OnAppendOnclickListener{
        void onClick();
    }
    private OnAppendOnclickListener listener;
    public void setOnAppendOnclickListener(OnAppendOnclickListener listener){
        this.listener=listener;
    }
}
