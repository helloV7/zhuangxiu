package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.ConstructionBean;
import com.jyt.baseapp.bean.ConstructionDetailBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.TitleAndFlowImages;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author LinWei on 2017/12/11 15:46
 */
public class ConstructionViewHolder extends BaseViewHolder<ConstructionBean> {

    @BindView(R.id.ti_af)
    TitleAndFlowImages mTiAf;

    public ConstructionViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sharepic, parent, false));
    }

    @Override
    public void setData(ConstructionBean data) {
        super.setData(data);
        List<String> picList= new ArrayList<>();
        for (ConstructionDetailBean d:data.getConstructionList()){
            picList.add(d.getConstructionUrl());
        }
        String type="";
        if ("1".equals(data.getConstructionList().get(0).getConstructionType())){
            type="施工中"+"\n"+data.getConstructionList().get(0).getConstructionNickName()+"   "+ BaseUtil.getTime(data.getConstructionList().get(0).getConstructionDate());
        }else {
            type="施工完毕"+"\n"+data.getConstructionList().get(0).getConstructionNickName()+"   "+BaseUtil.getTime(data.getConstructionList().get(0).getConstructionDate());
        }
        mTiAf.setBackground();
        mTiAf.setTextTitle(type);
        mTiAf.setTextTitleColor(R.color.text_color1);


        mTiAf.setImages(picList);
        mTiAf.setOnImageClickListener(listener);

    }

    private TitleAndFlowImages.OnImageClickListener listener;

    public void setOnImageClickListener(TitleAndFlowImages.OnImageClickListener listener){
        this.listener = listener;
    }
}
