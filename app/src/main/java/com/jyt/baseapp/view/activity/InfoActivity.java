package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;

public class InfoActivity extends BaseActivity {

    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.tv_Rtime)
    TextView mTvRtime;
    @BindView(R.id.rl_progress)
    RelativeLayout mRlProgress;
    @BindView(R.id.tv_news)
    TextView mTvNews;
    @BindView(R.id.tv_Ytime)
    TextView mTvYtime;
    @BindView(R.id.rl_news)
    RelativeLayout mRlNews;
    @BindView(R.id.tv_evaluation)
    TextView mTvEvaluation;
    @BindView(R.id.tv_Gtime)
    TextView mTvGtime;
    @BindView(R.id.rl_evaluation)
    RelativeLayout mRlEvaluation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_info;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("消息");
    }
}
