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
public class SearchBeforViewHolder extends BaseViewHolder<SearchBean> {
    @BindView(R.id.ll_parent)
    LinearLayout ll_parent;
    @BindView(R.id.tv_result)
    TextView tv_result;
    public SearchBeforViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search,parent,false));
    }

    @Override
    public void setData(SearchBean data) {
        super.setData(data);
        ll_parent.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
        tv_result.setText(data.getProjectName());
        tv_result.setTextSize(16);
        tv_result.setTextColor(itemView.getResources().getColor(R.color.map_text1));
    }
}
