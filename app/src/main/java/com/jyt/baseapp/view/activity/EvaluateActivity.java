package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.EvaluateModel;
import com.jyt.baseapp.view.widget.JumpItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class EvaluateActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.jt_j1)
    JumpItem mJtJ1;
    @BindView(R.id.jt_j2)
    JumpItem mJtJ2;
    @BindView(R.id.jt_j3)
    JumpItem mJtJ3;
    @BindView(R.id.jt_j4)
    JumpItem mJtJ4;
    private EvaluateModel mEvaluateModel;
    private boolean isPrepare;
    private List<Integer> stateList;
    private String mProjectId;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        initShop();
        initBrand();
        initListener();
    }


    private void init(){
        setTextTitle("项目评价");
        mEvaluateModel = new EvaluateModel();
        stateList = new ArrayList<>();
        mProjectId = getIntent().getStringExtra(IntentKey.PROJECTID);


    }

    private void initShop(){
        //店主
        mEvaluateModel.getKEvalIsFinish(mProjectId, new BeanCallback<BaseJson<List<Integer>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<List<Integer>> response, int id) {
                if (response.ret){
                    isPrepare=true;
                    stateList=response.data;
                }
            }
        });
    }

    private void initBrand(){
        //品牌方
        mEvaluateModel.getBEvalIsFinish(mProjectId, new BeanCallback<BaseJson<List<Integer>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<List<Integer>> response, int id) {
                if (response.ret){
                    isPrepare=true;
                    stateList=response.data;
                }
            }
        });
    }

    private void initListener() {
        mJtJ1.setOnClickListener(this);
        mJtJ2.setOnClickListener(this);
        mJtJ3.setOnClickListener(this);
        mJtJ4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!isPrepare){
            return;
        }
        switch (v.getId()) {
            case R.id.jt_j1:
                if (0==stateList.get(0)){
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,1,false);
                }else {
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,1,true);
                }
                break;
            case R.id.jt_j2:
                if (0==stateList.get(1)){
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,2,false);
                }else {
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,2,true);
                }
                break;
            case R.id.jt_j3:
                if (0==stateList.get(2)){
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,3,false);
                }else {
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,3,true);
                }
                break;
            case R.id.jt_j4:
                if (0==stateList.get(3)){
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,4,false);
                }else {
                    IntentHelper.OpenEvaluateDetailActivity(EvaluateActivity.this,mProjectId,4,true);
                }
                break;

            default:
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        initShop();
        initBrand();
    }


}
