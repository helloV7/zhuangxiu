package com.jyt.baseapp.view.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.bean.ShopBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.ShopModel;
import com.jyt.baseapp.util.BaseUtil;
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
    @BindView(R.id.rl_push)
    RelativeLayout mRlPush;
    private SearchBean projectBean;
    private ShopModel mShopModel;
    private boolean isPush;//是否推送
    private boolean CanChange = true;
    private boolean isIn = true;//用户是否任职四个职位中的任何一个，默认true
    private ShopBean mData;
    private String mCallPhone;
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
        projectBean = (SearchBean) getArguments().getSerializable(IntentKey.SHOPINFO);
//        mRlPush.setVisibility(View.GONE);//店主

    }



    private void initData() {
        mShopModel.getAsynShopDetail(projectBean.getProjectId(), new ShopModel.OnShopDetailResultListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<ShopBean> shopBean) {
                if (isSuccess && shopBean != null && shopBean.size() > 0) {
                    setProjectBean(shopBean.get(0));
                }
            }
        });
    }

    private void setProjectBean(ShopBean data) {
        mData = data;
        //暂停原因设置
        //如果工程暂停了，就显示原因，否则隐藏
        if ("0".equals(data.getIsfrozen())) {
            mLlReason.setVisibility(View.GONE);
        } else {
            mLlReason.setVisibility(View.VISIBLE);
            mTvReason.setText(data.getReason());
        }
        //推送设置
        if (Const.getPositionName().equals("预算员")) {
            if ("0".equals(data.getIspush())) {
                mSvPush.setOpened(false);
                isPush = false;
            } else {
                isPush = true;
                mSvPush.setOpened(true);
            }
        } else if (Const.getPositionName().equals("设计师")) {
            if ("0".equals(data.getDesignIsPush())) {
                mSvPush.setOpened(false);
                isPush = false;
            } else {
                isPush = true;
                mSvPush.setOpened(true);
            }
        } else if (Const.getPositionName().equals("测量人员")) {
            if ("0".equals(data.getMeasureIsPush())) {
                mSvPush.setOpened(false);
                isPush = false;
            } else {
                isPush = true;
                mSvPush.setOpened(true);
            }
        } else if (Const.getPositionName().equals("项目经理")) {
            if ("0".equals(data.getProIsPush())) {
                mSvPush.setOpened(false);
                isPush = false;
            } else {
                isPush = true;
                mSvPush.setOpened(true);
            }
        } else {
            //不属于以上四者，则需要专门查询该用户的推送状态,并隐藏它的评价
            isIn = false;
            mItemEvaluate.setVisibility(View.GONE);
            if ("0".equals(data.getIsfinish())) {
                mSvPush.setOpened(false);
                isPush = false;
            } else {
                isPush = true;
                mSvPush.setOpened(true);
            }

        }


        mItShopName.setRightText(data.getProjectName());
        mItNumber.setRightText(data.getProjectCode());
        mItUserName.setRightText(data.getShopkeeperName());
        mItShopPhone.setRightText(data.getShopkeepertel());
        mItAddress.setRightText(data.getCity()+"-"+data.getAddress());
        mItTime.setRightText(BaseUtil.getTime(data.getBeginTime()));

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

    private void initListener() {
        mItemEvaluate.setOnClickItemListener(new ItemText.OnClickItemListener() {
            @Override
            public void onClick(View v) {
                //内部人员
                IntentHelper.OpenEvaluateDetailActivity(getActivity(), projectBean.getProjectId(),0,false);
                //店主 品牌方
//                IntentHelper.OpenEvaluateActivity(getActivity(), projectBean.getProjectId());
            }
        });

        mSvPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CanChange) {
                    if (isPush) {
                        ChangePushState("0");
                    } else {
                        ChangePushState("1");
                    }
                }
            }
        });
        ItemText.OnClickCallListener callListener =new ItemText.OnClickCallListener() {
            @Override
            public void onClick(View v) {
                String tel = (String) v.getTag();
                if (!TextUtils.isEmpty(tel)){
                    call(tel);
                }
            }
        };
        //电话拨出
        mItShopPhone.setOnClickItemListener(callListener);
        mItCPhone.setOnClickItemListener(callListener);
        mItForecaster1.setOnClickItemListener(callListener);
        mItForecaster2.setOnClickItemListener(callListener);
        mItForecaster3.setOnClickItemListener(callListener);
        mItForecaster4.setOnClickItemListener(callListener);

    }

    private void ChangePushState(String state) {
        Log.e("@#","isIn="+isIn);
        if (isIn) {
            //是四个职位
            mShopModel.ChangePushState(projectBean.getProjectId(), state , "", new ShopModel.OnChangeStateListener() {
                @Override
                public void Before() {
                    CanChange = false;
                }

                @Override
                public void Result(boolean isSuccess) {
                    CanChange = true;
                    if (isSuccess) {
                        if (isPush) {
                            isPush = false;
                            mSvPush.setOpened(false);
                        } else {
                            mSvPush.setOpened(true);
                            isPush = true;
                        }
                    }
                }
            });
        } else {

            mShopModel.ChangePushState(projectBean.getProjectId(), state , "1", new ShopModel.OnChangeStateListener() {
                @Override
                public void Before() {
                    CanChange = false;
                }

                @Override
                public void Result(boolean isSuccess) {
                    CanChange = true;
                    if (isSuccess) {
                        if (isPush) {
                            isPush = false;
                            mSvPush.setOpened(false);
                        } else {
                            mSvPush.setOpened(true);
                            isPush = true;
                        }
                    }
                }
            });

//            mShopModel.ChangePushStateO(projectBean.getProjectId(), state, new ShopModel.OnChangeStateListener() {
//                @Override
//                public void Before() {
//                    CanChange = false;
//                }
//
//                @Override
//                public void Result(boolean isSuccess) {
//                    CanChange = true;
//                    if (isSuccess) {
//                        if (isPush) {
//                            isPush = false;
//                            mSvPush.setOpened(false);
//                        } else {
//                            mSvPush.setOpened(true);
//                            isPush = true;
//                        }
//                    }
//                }
//            });
        }

    }

    private void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
        startActivity(intent);
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
