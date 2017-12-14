package com.jyt.baseapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.bean.ShopBean;
import com.jyt.baseapp.model.ShopModel;
import com.jyt.baseapp.view.activity.EvaluateDetailActivity;
import com.jyt.baseapp.view.widget.ItemText;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ch.ielse.view.SwitchView;
import okhttp3.Call;

/**
 * @author LinWei on 2017/11/2 13:53
 */
public class ShopNewsFragment extends BaseFragment {
    @BindView(R.id.tv_Progress)
    TextView mTvProgress;
    @BindView(R.id.tv_Reason)
    TextView mTvReason;
    @BindView(R.id.it_ShopName)
    ItemText mItShopName;
    @BindView(R.id.it_Number)
    ItemText mItNumber;
    @BindView(R.id.it_UserName)
    ItemText mItUserName;
    @BindView(R.id.it_ShopPhone)
    ItemText mItShopPhone;
    @BindView(R.id.it_Address)
    ItemText mItAddress;
    @BindView(R.id.it_Time)
    ItemText mItTime;
    @BindView(R.id.it_Brand)
    ItemText mItBrand;
    @BindView(R.id.it_BrandLevel)
    ItemText mItBrandLevel;
    @BindView(R.id.it_Commissioner)
    ItemText mItCommissioner;
    @BindView(R.id.it_CPhone)
    ItemText mItCPhone;
    @BindView(R.id.it_Forecaster1)
    ItemText mItForecaster1;
    @BindView(R.id.it_Forecaster2)
    ItemText mItForecaster2;
    @BindView(R.id.it_Forecaster3)
    ItemText mItForecaster3;
    @BindView(R.id.it_Forecaster4)
    ItemText mItForecaster4;
    @BindView(R.id.item_Evaluate)
    ItemText mItemEvaluate;
    @BindView(R.id.sv_Push)
    SwitchView mSvPush;
    @BindView(R.id.ll_reason)
    LinearLayout mLlReason;
    private SearchBean mInfo;
    private ShopModel mShopModel;
    private boolean isPush;//是否推送
    private boolean CanChange=true;
    private boolean isIn=true;//用户是否任职四个职位中的任何一个，默认true
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopnews;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initData();
        initListener();
    }

    private void init() {
        mShopModel = new ShopModel();
        mInfo = (SearchBean) getArguments().getSerializable("shopinfo");
    }

    private void initData() {
        mShopModel.getShopDetail(mInfo.getProjectId(), new ShopModel.OnShopDetailResultListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<ShopBean> shopBean) {
                if (isSuccess) {
                    setInfo(shopBean.get(0));
                }
            }
        });
    }

    private void setInfo(ShopBean data) {
        //暂停原因设置
        //如果工程暂停了，就显示原因，否则隐藏
        if ("0".equals(data.getIsfrozen())){
            mLlReason.setVisibility(View.GONE);
        }else {
            mLlReason.setVisibility(View.VISIBLE);
            mTvReason.setText(data.getReason());
        }
        //推送设置
        if (Const.getPositionName().equals("预算员")){
            if ("0".equals(data.getIspush())){
                mSvPush.setOpened(false);
                isPush=false;
            }else {
                isPush=true;
                mSvPush.setOpened(true);
            }
        }else if (Const.getPositionName().equals("设计师")){
            if ("0".equals(data.getDesignIsPush())){
                mSvPush.setOpened(false);
                isPush=false;
            }else {
                isPush=true;
                mSvPush.setOpened(true);
            }
        }else if (Const.getPositionName().equals("测量人员")){
            if ("0".equals(data.getMeasureIsPush())){
                mSvPush.setOpened(false);
                isPush=false;
            }else {
                isPush=true;
                mSvPush.setOpened(true);
            }
        }else if (Const.getPositionName().equals("项目经理")){
            if ("0".equals(data.getProIsPush())){
                mSvPush.setOpened(false);
                isPush=false;
            }else {
                isPush=true;
                mSvPush.setOpened(true);
            }
        }else {
            //不属于以上四者，则需要专门查询该用户的推送状态
            isIn=false;
            OkHttpUtils.get().url(Path.URL_MapDatas)
                    .addParams("token", Const.gettokenSession())
                    .addParams("method","getNotInPush")
                    .addParams("page","0")
                    .addParams("keyWord",mInfo.getProjectId())
                    .addParams("searchValue",Const.getUserid())
                    .build()
                    .execute(new BeanCallback<BaseJson<String>>() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            Log.e("@#","in=onError");
                        }

                        @Override
                        public void onResponse(BaseJson<String> response, int id) {
                            Log.e("@#","in="+response.data);
                            if ("0".equals(response.data)){
                                mSvPush.setOpened(false);
                                isPush=false;
                            }else {
                                isPush=true;
                                mSvPush.setOpened(true);
                            }
                        }
                    });

        }


        mItShopName.setRightText(data.getProjectName());
        mItNumber.setRightText(data.getProjectCode());
        mItUserName.setRightText(data.getShopkeeperName());
        mItShopPhone.setRightText(data.getShopkeepertel());
        mItAddress.setRightText(data.getAddress());
        mItTime.setRightText(data.getBeginTime());

        mItBrand.setRightText(data.getBrandName());
        mItBrandLevel.setRightText(data.getSubClassName());
        mItCommissioner.setRightText(data.getNickName());
        mItCPhone.setRightText(data.getBrandaccounttel());

        mItForecaster1.setLeftText(data.getButenickName());
        mItForecaster1.setAppendrText(data.getButenickName());
        mItForecaster1.setRightText(data.getButetel());
        mItForecaster2.setLeftText(data.getDesistationName());
        mItForecaster2.setAppendrText(data.getDesinickName());
        mItForecaster2.setRightText(data.getDesitel());
        mItForecaster3.setLeftText(data.getMeasstationName());
        mItForecaster3.setAppendrText(data.getMeasnickName());
        mItForecaster3.setRightText(data.getMeastel());
        mItForecaster4.setLeftText(data.getProstationName());
        mItForecaster4.setAppendrText(data.getPronickName());
        mItForecaster4.setRightText(data.getProtel());
    }

    private void initListener(){
        mItemEvaluate.setOnClickItemListener(new ItemText.OnClickItemListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EvaluateDetailActivity.class);
                intent.putExtra("ProjectId",mInfo.getProjectId());
                startActivity(intent);
            }
        });

        mSvPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CanChange){
                    if (isPush){
                        ChangePushState("0");
                    }else {
                        ChangePushState("1");
                    }
                }
            }
        });

    }

    private void ChangePushState(String state){
        if (isIn){
            //是四个职位
            mShopModel.ChangePushState(mInfo.getProjectId(), state, new ShopModel.OnChangeStateListener() {
                @Override
                public void Before() {
                    CanChange=false;
                }

                @Override
                public void Result(boolean isSuccess) {
                    CanChange=true;
                    if (isSuccess){
                        if (isPush){
                            isPush=false;
                            mSvPush.setOpened(false);
                        }else {
                            mSvPush.setOpened(true);
                            isPush=true;
                        }
                    }
                }
            });
        }else {
            mShopModel.ChangePushStateO(mInfo.getProjectId(), state, new ShopModel.OnChangeStateListener() {
                @Override
                public void Before() {
                    CanChange=false;
                }

                @Override
                public void Result(boolean isSuccess) {
                    CanChange=true;
                    if (isSuccess){
                        if (isPush){
                            isPush=false;
                            mSvPush.setOpened(false);
                        }else {
                            mSvPush.setOpened(true);
                            isPush=true;
                        }
                    }
                }
            });
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
