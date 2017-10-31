package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.LocalMediaFolder;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/1/9.
 */

public class FolderThumbViewHolder extends BaseViewHolder<LocalMediaFolder> {
    @BindView(R.id.thumb)
    ImageView thumb;
    @BindView(R.id.name)
    TextView name;

    public FolderThumbViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_image_folder,parent,false));
    }


    @Override
    public void setData(LocalMediaFolder data) {
        super.setData(data);

        if (data.getImages()!=null){
            Glide.with(itemView.getContext()).load(data.getImages().get(0).getPath()).into(thumb);
            name.setText(data.getName()+"("+data.getImages().size()+")");
        }else {
            name.setText("");
        }

    }
}
