package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.FileBean;

/**
 * @author LinWei on 2017/11/9 13:58
 */
public class FileViewHolder extends BaseViewHolder<FileBean> {

    public FileViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share,parent,false));
    }

    @Override
    public void setData(FileBean data) {
        super.setData(data);

    }
}
