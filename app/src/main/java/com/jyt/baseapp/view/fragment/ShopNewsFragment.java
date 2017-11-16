package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.bean.ShopBean;
import com.jyt.baseapp.model.ShopModel;
import com.jyt.baseapp.view.widget.ItemText;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ch.ielse.view.SwitchView;

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
        if ("0".equals(data.getIsfrozen())){
            mLlReason.setVisibility(View.GONE);
        }else {
            mLlReason.setVisibility(View.VISIBLE);
            mTvReason.setText(data.getReason());
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
