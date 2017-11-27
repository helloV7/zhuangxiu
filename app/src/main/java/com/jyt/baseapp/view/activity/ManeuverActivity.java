package com.jyt.baseapp.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ManeuverAdapter;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.itemDecoration.RecycleViewDivider;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ManeuverActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_city)
    TextView mTvCity;
    @BindView(R.id.tv_work)
    TextView mTvWork;
    @BindView(R.id.selector_city)
    MapSelector mSelectorCity;
    @BindView(R.id.ll_work)
    LinearLayout mLlWork;
    @BindView(R.id.rv_work)
    RecyclerView mRvWork;

    private MapModel mMapModel;
    private MapBean mMapBean;
    private ManeuverAdapter mManeuverAdapter;
    private int mtotalWidth;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int selecrotWorkHeight ;
    private boolean isHideCity;
    private boolean isHideWork;
    private List<WorkBean> mWorkList;
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
        init();
        initSelector();
        initData();
        initListener();
    }

    private void init(){
        mMapBean = new MapBean();
        mMapModel = new MapModel();
        mWorkList = new ArrayList<>();
        mManeuverAdapter = new ManeuverAdapter();
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

    }

    private void initSelector() {
        mSelectorCity.getLayoutParams().height = 0;
        mSelectorCity.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorCity.requestLayout();
        mLlWork.getLayoutParams().height = 0;
        mLlWork.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mLlWork.requestLayout();
        mSelectorCity.setHideDeleteIV(true);
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

    private void initData() {

        mMapModel.getProvinceData(new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    mSelectorCity.setProvinceAdapter(mMapBean, ManeuverActivity.this);
                }
            }
        });

        mMapModel.getCityAreaData(3, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess) {
                    mMapBean.mCities = data;
                    mSelectorCity.setCityAdapter(mMapBean, ManeuverActivity.this);
                }
            }
        });
        mWorkList.add(new WorkBean("水电工"));
        mWorkList.add(new WorkBean("木工"));
        mWorkList.add(new WorkBean("土建"));
        mWorkList.add(new WorkBean("焊工"));

        mManeuverAdapter.setDataList(mWorkList);
        mRvWork.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvWork.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.VERTICAL));
        mRvWork.setAdapter(mManeuverAdapter);

    }

    private void initListener() {
        mTvCity.setOnClickListener(this);
        mTvWork.setOnClickListener(this);
        mManeuverAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i <mWorkList.size() ; i++) {
                    if (i==holder.getPosition()){
                        mWorkList.get(i).setCheck(true);
                        continue;
                    }
                    mWorkList.get(i).setCheck(false);
                }
                mManeuverAdapter.notifyData(mWorkList);
                mTvWork.performClick();
            }
        });
    }

    /**
     * 点击省，改变市级列表
     *
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID) {
        mMapModel.getCityAreaData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess) {
                    mMapBean.mCities = data;
                    mSelectorCity.notifyData(mMapBean);
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
            default:
                break;
        }
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
        mLlWork.measure(0,0);
        selecrotWorkHeight =  mLlWork.getMeasuredHeight();
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
