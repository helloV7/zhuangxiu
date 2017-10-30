package com.jyt.baseapp.adapter;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.ViewTarget;
import com.jyt.baseapp.view.viewholder.TouchImageView;

import java.util.List;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class TouchImageAdapter extends PagerAdapter {

    public List getImages() {
        return images;
    }

    public void setImages(List images) {
        this.images = images;
    }

    List images;

    @Override
    public int getCount() {
        if (images!=null){
            return images.size();
        }
        return 0;
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        final TouchImageView img = new TouchImageView(container.getContext());
        Glide.with(container.getContext()).load(images.get(position)).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                img.setImageBitmap(resource);
            }
        });
                container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}