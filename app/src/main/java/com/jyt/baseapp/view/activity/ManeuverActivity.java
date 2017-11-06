package com.jyt.baseapp.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.MapSelector;

import java.util.ArrayList;

import butterknife.BindView;

public class ManeuverActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_work)
    TextView mTvWork;
    @BindView(R.id.rv_people)
    RecyclerView mRvPeople;
    @BindView(R.id.selector_city)
    MapSelector mSelectorCity;
    @BindView(R.id.ll_work)
    LinearLayout mLlWork;
    @BindView(R.id.tv_hydropower)
    TextView mTvHydropower;
    @BindView(R.id.tv_carpentry)
    TextView mTvCarpentry;
    @BindView(R.id.tv_civil)
    TextView mTvCivil;
    @BindView(R.id.tv_welder)
    TextView mTvWelder;
    private MapModel mMapModel;
    private MapBean mMapBean;
    private int mtotalWidth;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int selecrotWorkHeight = BaseUtil.dip2px(240);
    private boolean isHideCity;
    private boolean isHideWork;
    private boolean isShowCity = true;
    private boolean isShowWork = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_maneuver;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapBean = new MapBean();
        mMapModel = new MapModel();
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        setTextTitle("机动人员");
        setIvFunction(R.mipmap.btn_add);
        setOnClickFunctionListener(new OnClickFunctionListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(ManeuverActivity.this, AddPersonActivity.class));
            }
        });
        initSelector();
        initData();
        initListener();
    }

    private void initSelector() {
        mSelectorCity.getLayoutParams().height = 0;
        mSelectorCity.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorCity.requestLayout();
        mLlWork.getLayoutParams().height = 0;
        mLlWork.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mLlWork.requestLayout();
        mSelectorCity.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, String ProvinceName) {
                ChangeProvince(ProvinceID);
            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {

            }

            @Override
            public void onClickBack() {

            }
        });


    }

    private void initListener() {
        mTvCity.setOnClickListener(this);
        mTvWork.setOnClickListener(this);
        mTvHydropower.setOnClickListener(this);
        mTvCarpentry.setOnClickListener(this);
        mTvCivil.setOnClickListener(this);
        mTvWelder.setOnClickListener(this);
    }

    private void initData() {
        mMapModel.getProvinceData(new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, ArrayList<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    mSelectorCity.setProvinceAdapter(mMapBean, ManeuverActivity.this);
                }
            }
        });
        mMapModel.getCityData(1, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess) {
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities = data;
                            mSelectorCity.setCityAdapter(mMapBean, ManeuverActivity.this);
                        }
                    });
                }
            }
        });
    }

    /**
     * 点击省，改变市级列表
     *
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID) {
        mMapModel.getCityData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess) {
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities = data;
                            mSelectorCity.notifyData(mMapBean);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_city:
                if (isShowCity) {
                    mTvCity.setText("城市∧");
                    mTvCity.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvWork.setText("工种∨");
                    mTvWork.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.VISIBLE);
                    mLlWork.setVisibility(View.GONE);
                    isShowCity = false;
                    isShowWork = true;
                    isHideCity = false;
                } else {
                    mTvCity.setText("城市∨");
                    mTvCity.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowCity = true;
                }
                setCitySelector();
                break;
            case R.id.tv_work:
                if (isShowWork) {
                    mTvWork.setText("工种∧");
                    mTvWork.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvCity.setText("城市∨");
                    mTvCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.GONE);
                    mLlWork.setVisibility(View.VISIBLE);
                    isShowWork = false;
                    isShowCity = true;
                    isHideWork = false;
                } else {
                    mTvWork.setText("工种∨");
                    mTvWork.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowWork = true;

                }
                setWorkSelector();
                break;
            case R.id.tv_hydropower:
                setWork(0);
                break;
            case R.id.tv_carpentry:
                setWork(1);
                break;
            case R.id.tv_civil:
                setWork(2);
                break;
            case R.id.tv_welder:
                setWork(3);
                break;
            default:
                break;
        }
    }

    public void setWork(int index){
        mTvHydropower.setTextColor(getResources().getColor(R.color.text_color1));
        mTvCarpentry.setTextColor(getResources().getColor(R.color.text_color1));
        mTvCivil.setTextColor(getResources().getColor(R.color.text_color1));
        mTvWelder.setTextColor(getResources().getColor(R.color.text_color1));
        switch (index) {
            case 0:
                mTvHydropower.setTextColor(getResources().getColor(R.color.map_text2));
                break;
            case 1:
                mTvCarpentry.setTextColor(getResources().getColor(R.color.map_text2));
                break;
            case 2:
                mTvCivil.setTextColor(getResources().getColor(R.color.map_text2));
                break;
            case 3:
                mTvWelder.setTextColor(getResources().getColor(R.color.map_text2));
                break;
            default:
                break;
        }
        mTvWork.performClick();
    }

    /**
     * 地图的选择器动画
     */
    private void setCitySelector() {
        ValueAnimator animator = null;
        if (!isHideCity) {
            animator = ValueAnimator.ofInt(0, selecrotHeight);
            isHideCity = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotHeight, 0);
            isHideCity = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSelectorCity.getLayoutParams().height = (int) animation.getAnimatedValue();
                mSelectorCity.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 工种的选择器动画
     */
    private void setWorkSelector() {
        ValueAnimator animator = null;
        if (!isHideWork) {
            animator = ValueAnimator.ofInt(0, selecrotWorkHeight);
            isHideWork = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotWorkHeight, 0);
            isHideWork = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLlWork.getLayoutParams().height = (int) animation.getAnimatedValue();
                mLlWork.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }
}
