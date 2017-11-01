package com.jyt.baseapp.view.viewholder;

import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;

/**
 * @author LinWei on 2017/11/1 13:58
 */
public class SingleTextViewHolder extends BaseViewHolder<MapBean.City> {
    public TextView tv_SingleText;
    public SingleTextViewHolder(View itemView) {
        super(itemView);
        tv_SingleText= (TextView) itemView.findViewById(R.id.tv_left_province);
    }
}
