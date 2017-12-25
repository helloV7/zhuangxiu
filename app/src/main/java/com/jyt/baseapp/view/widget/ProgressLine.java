package com.jyt.baseapp.view.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LinWei on 2017/11/2 17:11
 */
public class ProgressLine extends RelativeLayout {
    private LinearLayout ll_parent;
    private RelativeLayout rl_hide;
    private CircleImageView civ_light;
    private TextView tv_title;
    private TextView tv_station;
    private ImageView iv_arrow;
    private LinearLayout ll_append;
    private List<AppendItem> appendList;
    public ProgressLine(Context context) {
        super(context);
        init(context);
    }

    public ProgressLine(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.ProgressLine);
        String title=typedArray.getString(R.styleable.ProgressLine_progress);
        if (title==null){
            title="";
        }
        tv_title.setText(title);
    }

    public ProgressLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        appendList=new ArrayList<>();
        View.inflate(context, R.layout.layout_progressline,this);
        ll_parent= (LinearLayout) this.findViewById(R.id.ll_parent);
        rl_hide= (RelativeLayout) this.findViewById(R.id.rl_progress_hide);
        iv_arrow = (ImageView) this.findViewById(R.id.iv_arrow);
        civ_light= (CircleImageView) this.findViewById(R.id.civ_light);
        tv_title= (TextView) this.findViewById(R.id.tv_progress_title);
        tv_station= (TextView) this.findViewById(R.id.tv_progress_station);
        ll_append= (LinearLayout) this.findViewById(R.id.ll_append);
        ll_append.getLayoutParams().height=0;
        ll_append.requestLayout();
        rl_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToAnimation();
            }
        });
    }

    private int AppendHeight;
    private boolean isShowAppend;
    public void ToAnimation(){
        ll_append.measure(0,0);
        AppendHeight=ll_append.getMeasuredHeight()+10;//+10预留显示空间
        ValueAnimator animator;
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
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (isShowAppend){
                    //开
                    if (isCurrent){
                        iv_arrow.setImageResource(R.mipmap.jiantoushang_bai);
                    }else {
                        iv_arrow.setImageResource(R.mipmap.jiantou_shang);
                    }
                }else {
                    //关
                    if (isCurrent){
                        iv_arrow.setImageResource(R.mipmap.jiantou_xia_bai);
                    }else {
                        iv_arrow.setImageResource(R.mipmap.jiantou_xia_hui);
                    }
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(500);
        animator.start();
    }

    public void setTitle(String title){
        tv_title.setText(title);
    }

    public void setFinishStation(boolean station){
        ll_parent.setDividerDrawable(getResources().getDrawable(R.drawable.linex));
        ll_parent.setBackground(getResources().getDrawable(R.drawable.bg_white));
        if (station){
            tv_title.setTextColor(getResources().getColor(R.color.text_color1));
            civ_light.setImageResource(R.mipmap.oval_w);
            tv_station.setText("已完成");
            tv_station.setTextColor(getResources().getColor(R.color.map_text1));
        }else {
            tv_title.setTextColor(getResources().getColor(R.color.map_text1));
            civ_light.setImageResource(R.mipmap.oval_h);
            tv_station.setText("");
            tv_station.setTextColor(getResources().getColor(R.color.map_text1));
        }
    }
    //当前进行到的位置 字体白色 背景蓝色 圆标白色
    //内部AppendItem同样
    private boolean isCurrent=false;
    public void setCurrent(int speedCode){
        isCurrent=true;
        tv_station.setText("未完成");
        tv_title.setTextColor(getResources().getColor(R.color.white));
        tv_station.setTextColor(getResources().getColor(R.color.white));
        civ_light.setImageResource(R.mipmap.oval);
        iv_arrow.setImageResource(R.mipmap.jiantou_xia_bai);
        ll_parent.setBackground(getResources().getDrawable(R.drawable.bg_blue));
        ll_parent.setDividerDrawable(getResources().getDrawable(R.drawable.line_white30));
        for (AppendItem item: appendList) {
            item.setCurrentColor(speedCode);
        }
    }

    public void setCurrent(boolean current){
        this.isCurrent=current;
    }

//    public interface OnClickListener{
//        void onClick();
//    }
//    private OnClickListener listener;
//    public void setOnClickListener (OnClickListener listener){
//        this.listener=listener;
//    }




    public void addAppendItem(AppendItem item){
        if (item!=null){
            ll_append.addView(item);
            appendList.add(item);
        }
    }
}
