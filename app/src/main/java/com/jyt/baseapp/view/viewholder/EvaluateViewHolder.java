package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/7 10:35
 */
public class EvaluateViewHolder extends BaseViewHolder<String> {
    @BindView(R.id.tv_rater)
    TextView mTvRater;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_evaluation)
    TextView mTvEvaluation;
    public EvaluateViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_evaluate,parent,false));

    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }
}
