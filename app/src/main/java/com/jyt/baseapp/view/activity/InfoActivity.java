package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.InfoModel;

import butterknife.BindView;

public class InfoActivity extends BaseActivity implements View.OnClickListener {

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

    private InfoModel mInfoModel;

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
        init();

    }

    private void init(){
        setTextTitle("消息");
    }

    private void initData(){

    }

    private void initListener(){
        mRlProgress.setOnClickListener(this);
        mRlNews.setOnClickListener(this);
        mRlEvaluation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           case R.id.rl_progress:
                IntentHelper.OpenInfoDetailActivity(InfoActivity.this,0);
                break;

            case R.id.rl_news:
                IntentHelper.OpenInfoDetailActivity(InfoActivity.this,1);
                break;

            case R.id.rl_evaluation:
                IntentHelper.OpenInfoDetailActivity(InfoActivity.this,2);
                break;

            default:
                break;
        }
    }


}
