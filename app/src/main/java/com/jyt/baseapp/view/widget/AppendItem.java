package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.ProgressBean;

/**
 * @author LinWei on 2017/11/2 18:57
 */
public class AppendItem extends RelativeLayout {
    private ProgressBean mProgressBean;
    private LinearLayout ll_item;
    private ImageView iv_complete;
    private ImageView iv_next;
    private TextView tv_msg;
    private TextView tv_estimate;
    private TextView tv_time;
    private int Operate;
    private boolean isNext;
    private boolean isComplete;//是否已经操作完毕
    private int state;//0无任何操作 1操作完显示可见 2操作完显示不可见
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
                if (listener!=null){
                    if (mProgressBean!=null){
                        mProgressBean.setPermissionState(Operate);
                        listener.onClick(mProgressBean);
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

    public void setState(int state){
        this.state=state;
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
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) iv_next.getLayoutParams();
            params.gravity= Gravity.BOTTOM;
            params.setMargins(0,0,0,6);
            iv_next.setLayoutParams(params);
        }else {
            tv_estimate.setVisibility(GONE);
        }
    }

    private boolean isEditor;
    public void setEditor(){
        isEditor=true;
        iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.bianji_hui));
    }

    public void setComplete(boolean isComplete){
        this.isComplete=isComplete;
        if (isComplete){
            iv_complete.setVisibility(VISIBLE);
            iv_complete.setImageResource(R.mipmap.right_green);
            tv_time.setTextColor(getResources().getColor(R.color.map_text1));
            tv_estimate.setVisibility(GONE);
            tv_msg.setTextColor(getResources().getColor(R.color.map_text1));
            switch (state) {
                case 0:
                    iv_next.setVisibility(INVISIBLE);
                    break;
                case 1:
                    iv_next.setVisibility(VISIBLE);
                    iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.jiantou_hui));
                    break;
                case 2:
                    iv_next.setVisibility(INVISIBLE);
                    break;
                default:
                    break;
            }
        }else {
            iv_complete.setVisibility(INVISIBLE);
        }
    }

    //设置当前的进度点
    public void setCurrent(){
        isComplete=true; //特殊操作，当前进度处于未完成和完成之间，需要赋予点击事件
        iv_complete.setVisibility(VISIBLE);
        iv_complete.setImageResource(R.mipmap.oval);
        tv_msg.setTextColor(getResources().getColor(R.color.white));
        tv_estimate.setTextColor(getResources().getColor(R.color.white));
        tv_time.setTextColor(getResources().getColor(R.color.white));
        iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.jiantou_you_bai));
//        switch (state) {
//            case 0:
//                iv_next.setVisibility(INVISIBLE);
//                break;
//            case 1:
//                iv_next.setVisibility(VISIBLE);
//                iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.next_little_white));
//                break;
//            case 2:
//                iv_next.setVisibility(INVISIBLE);
//                break;
//            default:
//                break;
//        }
    }
    //设置当前进度点相同父类下的其他进度点
    public void setCurrentColor(int speed){

        if (speed<mProgressBean.getSpeedCode()){
            tv_msg.setTextColor(getResources().getColor(R.color.white_half));
            tv_estimate.setTextColor(getResources().getColor(R.color.white_half));
            tv_time.setTextColor(getResources().getColor(R.color.white_half));
        }else {
            tv_msg.setTextColor(getResources().getColor(R.color.white));
            tv_estimate.setTextColor(getResources().getColor(R.color.white));
            tv_time.setTextColor(getResources().getColor(R.color.white));
        }
        iv_complete.setImageDrawable(getResources().getDrawable(R.mipmap.right_blue));
        if (isComplete){
            iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.jiantou_you_bai));
        }else {
            iv_next.setImageDrawable(getResources().getDrawable(R.mipmap.bianji_bai));
        }
    }

    public ProgressBean getProgressBean() {
        return mProgressBean;
    }

    public void setProgressBean(ProgressBean progressBean) {
        mProgressBean = progressBean;
    }

    public int getOperate() {
        return Operate;
    }

    public void setOperate(int operate) {
        Operate = operate;
    }

    public interface OnAppendOnclickListener{
        void onClick(ProgressBean bean);
    }
    private OnAppendOnclickListener listener;
    public void setOnAppendOnclickListener(OnAppendOnclickListener listener){
        this.listener=listener;
    }
}
