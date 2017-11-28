package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.EvaluateBean;
import com.jyt.baseapp.model.EvaluateModel;
import com.jyt.baseapp.util.BaseUtil;

import java.util.List;

import butterknife.BindView;

public class EvaluateDetailActivity extends BaseActivity {

    @BindView(R.id.tv_HostEvaluate)
    TextView mTvHostEvaluate;
    @BindView(R.id.rb_evaluate)
    RatingBar mRbEvaluate;
    @BindView(R.id.tv_Stime)
    TextView mTvStime;
    @BindView(R.id.tv_SEvaluation)
    TextView mTvSEvaluation;
    @BindView(R.id.tv_Btime)
    TextView mTvBtime;
    @BindView(R.id.tv_BEvaluation)
    TextView mTvBEvaluation;

    private EvaluateModel mEvaluateModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("评价详情");
        init();
        initData();
        initListener();
    }

    private void init() {
        mEvaluateModel = new EvaluateModel();
        String ProjectID = getIntent().getStringExtra("ProjectId");
        mEvaluateModel.getEvaluateData(BaseUtil.getSpString(Const.PositionID), ProjectID, new EvaluateModel.OngetEvaluateDataListener() {
            @Override
            public void Result(boolean isSuccess , List<EvaluateBean> data) {
                if (isSuccess && data.size()>0){
                    EvaluateBean bean =data.get(0);
                    mTvHostEvaluate.setText(bean.getEvalk());
                    mRbEvaluate.setRating(Integer.valueOf(bean.getStar()));
                    mTvSEvaluation.setText(bean.getEvals());
                    mTvStime.setText(BaseUtil.getTime(bean.getTimes()));
                    mTvBEvaluation.setText(bean.getEvalb());
                    mTvBtime.setText(BaseUtil.getTime(bean.getTimeb()));
                }
            }
        });
    }

    private void initData() {

    }

    private void initListener() {

    }
}
