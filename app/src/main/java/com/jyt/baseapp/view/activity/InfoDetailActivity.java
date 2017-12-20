package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.InfoDetailAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.InfoBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.InfoModel;
import com.jyt.baseapp.model.impl.InfoModelmpl;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

public class InfoDetailActivity extends BaseActivity {

    @BindView(R.id.rv_container)
    RecyclerView mRvContainer;
    @BindView(R.id.iv_bottombg)
    ImageView mIvBottombg;
    private InfoDetailAdapter mDetailAdapter;
    private InfoModel mInfoModel;
    private int mState;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_detail;
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
        initListener();

    }

    private void init() {
        mInfoModel = new InfoModelmpl();
        mState = getIntent().getIntExtra(IntentKey.STATE, 0);
        mDetailAdapter = new InfoDetailAdapter();
        mRvContainer.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvContainer.addItemDecoration(new SpacesItemDecoration(0,30));
        mRvContainer.setAdapter(mDetailAdapter);
    }

    private void initData(){
        switch (mState) {
            case 0:
                setInfoProgress();
                break;
            case 1:
                setInfoHint();
                break;
            case 2:
                setInfoEvaluate();
                break;
        }
    }

    private void setInfoProgress(){
        setTextTitle("项目进度");
        mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_blue2));
        mInfoModel.getLatProgress(new BeanCallback<BaseJson<List<InfoBean>>>() {

            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<List<InfoBean>> response, int id) {
                if (response.ret && response.data!=null && response.data.size()>0){
                    List<InfoBean> data = transList(0,response.data);
                    mDetailAdapter.notifyData(data);
                }
            }
        });
    }

    private void setInfoHint(){
        setTextTitle("工作提示");
        mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_yellow2));
        mInfoModel.getLastHint(new BeanCallback<BaseJson<List<InfoBean>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<List<InfoBean>> response, int id) {
                if (response.ret && response.data!=null && response.data.size()>0){
                    List<InfoBean> data = transList(1,response.data);
                    mDetailAdapter.notifyData(data);
                }

            }
        });
    }

    private void setInfoEvaluate(){
        setTextTitle("店主评价");
        mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_green2));
        mInfoModel.getLastEvaluate(new BeanCallback<BaseJson<List<InfoBean>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<List<InfoBean>> response, int id) {
                if (response.ret && response.data!=null && response.data.size()>0){
                    List<InfoBean> data = transList(2,response.data);
                    mDetailAdapter.notifyData(data);
                }
            }
        });
    }

    private void initListener(){
        mDetailAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                InfoBean bean= (InfoBean) holder.getData();
                IntentHelper.OpenShopActivityByID(InfoDetailActivity.this , bean.getProjectId());
            }
        });
    }

    /**
     * 根据三种列表赋予对应的状态
     * @param state
     * @param data
     * @return
     */
    private List<InfoBean> transList(int state, List<InfoBean> data){
        for (InfoBean info : data) {
            info.setState(state);
        }
        return data;
    }


}
