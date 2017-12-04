package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.PicBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.TitleAndFlowImages;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author LinWei on 2017/12/4 15:53
 */
public class PicViewHolder extends BaseViewHolder<PicBean> {
    @BindView(R.id.ti_af)
    TitleAndFlowImages mTiAf;

    public PicViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sharepic, parent, false));
    }

    @Override
    public void setData(PicBean data) {
        super.setData(data);
        mTiAf.setTextTitle(BaseUtil.getTime(data.getShareDate()));
        List<String> piclist = new ArrayList<>();
        for (int i = 0; i < data.getShareImage().size(); i++) {
            piclist.add(data.getShareImage().get(i).shareUrl);
        }
        mTiAf.setImages(piclist);
        mTiAf.setOnImageClickListener(listener);


    }

    private TitleAndFlowImages.OnImageClickListener listener;

    public void setOnImageClickListener(TitleAndFlowImages.OnImageClickListener listener){
        this.listener = listener;
    }


}
