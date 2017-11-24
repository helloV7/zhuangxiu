package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.SearchBean;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/8 14:32
 */
public class SearchAfterViewHolder extends BaseViewHolder<SearchBean> {
    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;
    @BindView(R.id.tv_result)
    TextView tv_result;
    public SearchAfterViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false));
    }

    @Override
    public void setData(SearchBean data) {
        super.setData(data);
        ll_parent.setBackgroundColor(itemView.getResources().getColor(R.color.white));
        tv_result.setText(data.getProjectName());
        tv_result.setTextColor(itemView.getResources().getColor(R.color.text_color1));
    }
}
