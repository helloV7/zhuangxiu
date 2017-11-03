package com.jyt.baseapp.view.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.BaseUtil;

import java.util.List;

/**
 * @author LinWei on 2017/11/2 17:11
 */
public class ProgressLine extends RelativeLayout {
    private RelativeLayout rl_hide;
    private CircleImageView civ_light;
    private TextView tv_title;
    private TextView tv_station;
    private LinearLayout ll_append;
    public ProgressLine(Context context) {
        super(context);
        init(context);
    }

    public ProgressLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ProgressLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_progressline,this);
        rl_hide= (RelativeLayout) this.findViewById(R.id.rl_progress_hide);
        civ_light= (CircleImageView) this.findViewById(R.id.civ_light);
        tv_title= (TextView) this.findViewById(R.id.tv_progress_title);
        tv_station= (TextView) this.findViewById(R.id.tv_progress_station);
        ll_append= (LinearLayout) this.findViewById(R.id.ll_append);
        rl_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToAnimation();
            }
        });
    }

    private int AppendHeight;
    private boolean isShowAppend=true;
    public void ToAnimation(){
        ValueAnimator animator=null;
        Log.e("@#", BaseUtil.px2dip(AppendHeight)+"");
        if (!isShowAppend){
            animator=ValueAnimator.ofInt(0,AppendHeight);
            isShowAppend=true;
        }else {
            animator=ValueAnimator.ofInt(AppendHeight,0);
            isShowAppend=false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ll_append.getLayoutParams().height= (int) animation.getAnimatedValue();
                ll_append.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    public interface OnClickListener{
        void onClick();
    }
    private OnClickListener listener;
    public void setOnClickListener (OnClickListener listener){
        this.listener=listener;
    }

    public void setAppendData(List<String> data){
        for (int i = 0; i < data.size(); i++) {
            AppendItem item=new AppendItem(getContext());
            item.setTv_msg(data.get(i));
            if (i==data.size()-1){
                item.setLineVisible(false);
                item.measure(0,0);
                AppendHeight+=item.getMeasuredHeight();
            }
            ll_append.addView(item);
            item.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
                @Override
                public void onClick() {

                }
            });
        }
//        ll_append.measure(0,0);
//        AppendHeight=ll_append.getMeasuredHeight();
    }
}
