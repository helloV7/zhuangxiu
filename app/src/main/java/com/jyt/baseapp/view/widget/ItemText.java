package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/11/2 15:55
 */
public class ItemText extends RelativeLayout {
    private RelativeLayout rl_contianer;
    private TextView tv_left;
    private TextView tv_right;
    private TextView tv_append;
    private ImageView iv_next;
    private boolean isShowIV;
    public ItemText(Context context) {
        super(context);
        init(context);
    }

    public ItemText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray typedArray=context.obtainStyledAttributes(attrs, R.styleable.ItemText);
        String string_left=typedArray.getString(R.styleable.ItemText_leftText);
        String string_right=typedArray.getString(R.styleable.ItemText_rightText);
        String string_append=typedArray.getString(R.styleable.ItemText_apppendText);
        int color_left=typedArray.getColor(R.styleable.ItemText_leftcolor,getResources().getColor(R.color.map_text1));
        int color_right=typedArray.getColor(R.styleable.ItemText_rightcolor,getResources().getColor(R.color.map_text1));
        int color_append=typedArray.getColor(R.styleable.ItemText_appendcolor,getResources().getColor(R.color.text_color1));
        isShowIV=typedArray.getBoolean(R.styleable.ItemText_showIV,false);
        if (string_left==null){
            string_left="";
        }
        if (string_right==null){
            string_right="";
        }
        if (string_append==null){
            string_append="";
        }
        tv_left.setText(string_left);
        tv_right.setText(string_right);
        tv_append.setText(string_append);
        tv_left.setTextColor(color_left);
        tv_right.setTextColor(color_right);
        tv_append.setTextColor(color_append);
        if (isShowIV){
            iv_next.setVisibility(VISIBLE);
        }else {
            iv_next.setVisibility(GONE);
        }
        rl_contianer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClick(v);
                }
            }
        });
        tv_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (calllistener!=null){
                    calllistener.onClick(v);
                }
            }
        });
    }

    public ItemText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_itemtext,this);
        rl_contianer= (RelativeLayout) this.findViewById(R.id.text_container);
        tv_left= (TextView) this.findViewById(R.id.text_left);
        tv_append= (TextView) this.findViewById(R.id.text_append);
        tv_right= (TextView) this.findViewById(R.id.text_right);
        iv_next= (ImageView) this.findViewById(R.id.text_next);
    }

    public interface OnClickItemListener{
        void onClick(View v);
    }
    private OnClickItemListener listener;
    public void setOnClickItemListener(OnClickItemListener listener){
        this.listener=listener;
    }

    public interface OnClickCallListener{
        void onClick(View v);
    }
    private OnClickCallListener calllistener;
    public void setOnClickItemListener(OnClickCallListener listener){
        this.calllistener=listener;
    }


    public void setLeftText(String txt){
        tv_left.setText(txt);
    }

    public void setRightText(String txt){
        tv_right.setText(txt);
        tv_right.setTag(txt);
    }

    public void setAppendrText(String txt){
        tv_append.setText(txt);
    }

    public void setRlTag(String tag){
        tv_right.setTag(tag);
    }



}
