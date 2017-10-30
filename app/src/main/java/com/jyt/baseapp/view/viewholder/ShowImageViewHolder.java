package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.util.L;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class ShowImageViewHolder extends BaseViewHolder {
    @BindView(R.id.img_display)
    ImageView imgDisplay;
    @BindView(R.id.img_add)
    TextView imgAdd;
    @BindView(R.id.v_main)
    RelativeLayout vMain;

    public ShowImageViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_image, parent, false));

    }

    @Override
    public void setData(Object data) {
        super.setData(data);

        imgAdd.setVisibility((data instanceof Integer)? View.VISIBLE:View.GONE);
        imgDisplay.setVisibility(!(data instanceof Integer)? View.VISIBLE:View.GONE);


        if (data instanceof String){
            L.e(data.toString());
            Glide.with(itemView.getContext()).load(data).into(imgDisplay);
        }
    }
}
