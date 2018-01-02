package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.EvaluateBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
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
    private int state;
    private String projectId;
    private boolean Type;
    private EvaluateModel mEvaluateModel;
    private boolean isResume;

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
        init();
        initData();
//        initSB();

    }

    private void init() {
        setTextTitle("评价详情");
        state = getIntent().getIntExtra(IntentKey.STATE,0);
        projectId =getIntent().getStringExtra(IntentKey.PROJECTID);

        mEvaluateModel = new EvaluateModel();

    }

    private void initData() {
        //内部人员
        mEvaluateModel.getEvaluateData(BaseUtil.getSpString(Const.PositionID), projectId, new EvaluateModel.OngetEvaluateDataListener() {
            @Override
            public void Result(boolean isSuccess , List<EvaluateBean> data) {
                if (isSuccess && data.size()>0){
                    EvaluateBean bean =data.get(0);
                    if (!TextUtils.isEmpty(bean.getEvalk())){
                        mTvHostEvaluate.setText(bean.getEvalk());
                    }

                    if (!TextUtils.isEmpty(bean.getStar())){
                        mRbEvaluate.setRating(Integer.valueOf(bean.getStar()));
                    }

                    if (!TextUtils.isEmpty(bean.getEvals())){
                        mTvSEvaluation.setText(bean.getEvals());
                    }

                    if (!TextUtils.isEmpty(bean.getTimes())){
                        mTvStime.setText(BaseUtil.getTime(bean.getTimes()));
                    }

                    if (!TextUtils.isEmpty(bean.getEvalb())){
                        mTvBEvaluation.setText(bean.getEvalb());
                    }

                    if (!TextUtils.isEmpty(bean.getTimeb())){
                        mTvBtime.setText(BaseUtil.getTime(bean.getTimeb()));
                    }

                    if (!TextUtils.isEmpty(bean.getStar())){
                        mRbEvaluate.setRating(Integer.valueOf(bean.getStar()));
                    }


                }
            }
        });

    }



    private void initSB(){
        //店主
//        if (!getIntent().getBooleanExtra(IntentKey.SEND_STATE,true)){
//            //未评价
//            setFunctionText("评价");
//            //店主
//            IntentHelper.OpenEvaluateSendActivity(EvaluateDetailActivity.this,projectId,state,true);
//            finish();
//        }else {
//            //已评价
//            setFunctionText("");
//        }
        //品牌方
        if (!getIntent().getBooleanExtra(IntentKey.SEND_STATE,true)){
            setFunctionText("评价");
        }else {
            setFunctionText("");
        }
        //店主 品牌方
        mEvaluateModel.getEvaluateShop(state, projectId, new EvaluateModel.OngetEvaluateSBListener() {
            @Override
            public void Result(boolean isSuccess, EvaluateBean data) {
                EvaluateBean bean =data;
                if (bean.getStar()!=null){
                    mRbEvaluate.setRating(Integer.valueOf(bean.getStar()));
                }
                if (bean.getEvalk()!=null){
                    mTvHostEvaluate.setText(bean.getEvalk());
                }

                if(bean.getTimes()!=null){
                    mTvSEvaluation.setText(bean.getEvals());
                    mTvStime.setText(BaseUtil.getTime(bean.getTimes()));
                }

                if (bean.getEvals()!=null){
                    mTvSEvaluation.setText(bean.getEvals());
                }

                if (bean.getTimeb()!=null){

                    mTvBtime.setText(BaseUtil.getTime(bean.getTimeb()));
                }

                if (bean.getEvalb()!=null){
                    mTvBEvaluation.setText(bean.getEvalb());
                }


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==IntentKey.REQUEST_SEND && resultCode==IntentKey.RESULT_SEND){
            Log.e("@#","ASD");
            hideFunction();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isResume){
            isResume=true;
            return;
        }
        //用于店主或品牌方发送消息后刷新界面
        initSB();

    }

    @Override
    public void onFunctionClick() {
        IntentHelper.OpenEvaluateSendActivity(EvaluateDetailActivity.this,projectId,state,false);
    }
}
