package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/11/2 18:57
 */
public class AppendItem extends RelativeLayout {
    private RelativeLayout rl_item;
    private ImageView iv_complete;
    private TextView tv_msg;
    private TextView tv_time;
    private View line;
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
        View.inflate(context, R.layout.item_progress,this);
        rl_item= (RelativeLayout) this.findViewById(R.id.rl_progress_item);
        iv_complete= (ImageView) this.findViewById(R.id.iv_complete);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
        tv_time= (TextView) findViewById(R.id.tv_time);
        line=this.findViewById(R.id.line);
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

    public interface OnAppendOnclickListener{
        void onClick();
    }
    public void setLineVisible(boolean isShow){
        if (isShow){
            line.setVisibility(VISIBLE);
        }else {
            line.setVisibility(GONE);
        }
    }
    private OnAppendOnclickListener listener;
    public void setOnAppendOnclickListener(OnAppendOnclickListener listener){
        this.listener=listener;
    }
}
