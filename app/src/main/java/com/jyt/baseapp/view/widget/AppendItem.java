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
    private int index;
    private int i1;
    private boolean isContainer;
    private boolean isOperate;
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
            iv_next.setVisibility(INVISIBLE);
        }
    }

    public void setEstimate(boolean isShow){
        if (isShow){
            tv_estimate.setVisibility(VISIBLE);
        }else {
            tv_estimate.setVisibility(GONE);
        }
    }
    private boolean isEditor;
    public void setEditor(){
        isEditor=true;
        iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.icon_edit_gay));
    }

    public void setComplete(boolean isComplete){
        if (isComplete){
            iv_complete.setVisibility(VISIBLE);
        }else {
            iv_complete.setVisibility(INVISIBLE);
        }
    }

    public void setCurrent(){
        iv_complete.setVisibility(VISIBLE);
        iv_complete.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ring_white));
    }

    public void setCurrentColor(){
        isContainer=true;
        tv_msg.setTextColor(getResources().getColor(R.color.white));
        tv_estimate.setTextColor(getResources().getColor(R.color.white));
        tv_time.setTextColor(getResources().getColor(R.color.white));
        iv_complete.setImageDrawable(getResources().getDrawable(R.mipmap.icon_check_white));
        if (isEditor){
            iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.icon_edit));
        }else {
            iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.next_little_white));
        }
    }

    public void setOperate(int currentIndex){
        if (isOperate){
            if (currentIndex>index){
                //已完成
                if(i1==0){
                    //本身不具备操作观看性
                    iv_next.setVisibility(INVISIBLE);
                } else if (i1==1){
                    //操作完不能看
                    iv_next.setVisibility(INVISIBLE);
                } else if (i1==2){
                    //操作完能看
                    iv_next.setVisibility(VISIBLE);
                    if (isContainer){
                        //与当前进度点处于同一个父节点内
                        iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.next_little_white));
                    }else {
                        iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.icon_next));
                    }
                }
            }
        }else {

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
