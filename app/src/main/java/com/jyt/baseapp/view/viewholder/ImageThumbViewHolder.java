package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.LocalMedia;
import com.jyt.baseapp.util.DensityUtil;
import com.jyt.baseapp.util.ScreenUtils;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/1/9.
 */

public class ImageThumbViewHolder extends BaseViewHolder<LocalMedia> {



    @BindView(R.id.img_thumbImg)
    ImageView imgThumbImg;
    @BindView(R.id.img_checker)
    ImageView imgChecker;


    OnViewHolderClickListener onCheckImageClickListener;
    OnViewHolderClickListener onBrowseImageClickListener;

    public ImageThumbViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_sel_images,parent,false));


        imgChecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data.setChecked(!data.isChecked());
                setChecked(data.isChecked());

                if (onCheckImageClickListener != null) {
                    onCheckImageClickListener.onClick(getHolder());
                }
            }
        });

        int width = ScreenUtils.getScreenWidth(itemView.getContext()) / 3 - DensityUtil.dpToPx(itemView.getContext(), 2);
        imgThumbImg.setLayoutParams(new RelativeLayout.LayoutParams(width, width));

        imgThumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBrowseImageClickListener!=null){
                    onBrowseImageClickListener.onClick(getHolder());
                }
            }
        });
    }

    @Override
    public void setData(LocalMedia data) {
        super.setData(data);
        Glide.with(itemView.getContext()).load(data.getPath()).into(imgThumbImg);
        setChecked(data.isChecked());
    }
    private void setChecked(boolean checked){
        if (checked) {
            imgChecker.setBackgroundDrawable(itemView.getResources().getDrawable(R.mipmap.check_sel));
        } else {
            imgChecker.setBackgroundDrawable(itemView.getResources().getDrawable(R.mipmap.check_nor));

        }
    }


    public void setOnCheckImageClickListener(OnViewHolderClickListener onCheckImageClickListener) {
        this.onCheckImageClickListener = onCheckImageClickListener;
    }

    public void setOnBrowseImageClickListener(OnViewHolderClickListener onBrowseImageClickListener) {
        this.onBrowseImageClickListener = onBrowseImageClickListener;
    }

}
