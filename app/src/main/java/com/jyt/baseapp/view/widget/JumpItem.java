package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/11/3 18:00
 */
public class JumpItem extends LinearLayout {
    private TextView tv_title;
    private ImageView iv_next;
    private TextView tv_msg;
    public JumpItem(Context context) {
        super(context);
        init(context);
    }

    public JumpItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.JumpItem);
        String title=typedArray.getString(R.styleable.JumpItem_title);
        String msg=typedArray.getString(R.styleable.JumpItem_msg);
        int color_left=typedArray.getColor(R.styleable.JumpItem_colortitle,getResources().getColor(R.color.map_text1));
        int color_right=typedArray.getColor(R.styleable.JumpItem_colormsg,getResources().getColor(R.color.map_text1));
        boolean isJump=typedArray.getBoolean(R.styleable.JumpItem_Jump,false);
        if(title==null){
            title="";
        }
        if (msg==null){
            msg="";
        }
        if (isJump){
            tv_msg.setVisibility(GONE);
            iv_next.setVisibility(VISIBLE);
        }else {
            iv_next.setVisibility(GONE);
            tv_msg.setVisibility(VISIBLE);
        }
        tv_title.setText(title);
        tv_title.setTextColor(color_left);
        tv_msg.setText(msg);
        tv_msg.setTextColor(color_right);
        iv_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(v);
                }
            }
        });
    }

    public JumpItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_jump,this);
        tv_title= (TextView) findViewById(R.id.tv_title);
        iv_next= (ImageView) findViewById(R.id.iv_next);
        tv_msg= (TextView) findViewById(R.id.tv_msg);
    }

    public interface OnNextClickListener{
        void onClick(View v);
    }
    private OnNextClickListener listener;
    public void setOnNextClickListener(OnNextClickListener listener){
        this.listener=listener;
    }

    public void setMsg(String msg){
        tv_msg.setText(msg);
    }

    public void setNext(boolean isShow,String msg){
        if(isShow){
            iv_next.setVisibility(VISIBLE);
            tv_msg.setVisibility(GONE);
        }else {
            iv_next.setVisibility(GONE);
            tv_msg.setVisibility(VISIBLE);
            tv_msg.setText(msg);
        }
    }
}
