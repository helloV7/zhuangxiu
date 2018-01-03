package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.ScreenUtils;
import com.nex3z.flowlayout.FlowLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 显示 标题 图标集合 控件
 * Created by chenweiqi on 2017/10/31.
 */

public class TitleAndFlowImages extends FrameLayout {
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.v_flowLayout)
    FlowLayout vFlowLayout;
    @BindView(R.id.ll_parent)
    LinearLayout mLlParent;

    OnImageClickListener onImageClickListener;
    List<String> images;
    float marginPercent = 0.01f;
    int windowWidth;
    int imageMargin;
    int imageWidth;
    int columnCount = 3;

    OnClickListener onClickListener;


    public TitleAndFlowImages(@NonNull Context context) {
        this(context, null);
    }

    public TitleAndFlowImages(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.widget_title_and_flow_images, this, true);
        ButterKnife.bind(this);
        windowWidth = ScreenUtils.getScreenWidth(getContext());
        imageMargin = (int) (windowWidth * marginPercent);
        imageWidth = (int) ((windowWidth - (imageMargin * (columnCount + 1))) / columnCount);

        vFlowLayout.setChildSpacing(imageMargin);
        vFlowLayout.setRowSpacing(imageMargin);
        vFlowLayout.setPadding(imageMargin, 0, imageMargin, 0);

        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                L.e(v.getTag(R.id.tag_image_path).toString());
                if (onImageClickListener != null) {
                    onImageClickListener.onImaegClick(v.getTag(R.id.tag_image_path).toString());
                }
            }
        };
    }

    public void setTextTitle(String text) {
        textTitle.setText(text);
    }

    public void setTextTitleColor(int color){
        textTitle.setTextColor(getResources().getColor(color));
    }

    public void setImages(List<String> images) {
        this.images = images;
        vFlowLayout.removeAllViews();
        int num = 0;
        for (int i = 0; i < images.size(); i++, num++) {
            ImageView imageView = new ImageView(getContext());
            FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(imageWidth, imageWidth);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setTag(R.id.tag_image_path, images.get(i));
            Glide.with(getContext()).load(images.get(i)).asBitmap().into(imageView);
            imageView.setOnClickListener(onClickListener);
            vFlowLayout.addView(imageView);
        }
        if (num<=3 && num>0){
            num=1;
        }else if (num%3==0){
            num =num/3;
        }else if (num%3>0){
            num =num/3+1;
        }


        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vFlowLayout.getLayoutParams();
        params.height = num * imageWidth + 3;
        vFlowLayout.setLayoutParams(params);
    }

    public void setOnImageClickListener(OnImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public void setHideTitle(){
        textTitle.setVisibility(GONE);
    }


    public interface OnImageClickListener {
        void onImaegClick(String imagPath);
    }

    public void setBackground(){
        mLlParent.setBackgroundColor(getResources().getColor(R.color.white));
    }


}
