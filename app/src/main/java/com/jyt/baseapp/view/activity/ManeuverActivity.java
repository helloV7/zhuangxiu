package com.jyt.baseapp.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ManeuverAdapter;
import com.jyt.baseapp.adapter.WorkAdapter;
import com.jyt.baseapp.bean.ManeuverBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.itemDecoration.RecycleViewDivider;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.ManeuverModel;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

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
    @BindView(R.id.rv_container)
    RecyclerView mRvContainer;
    @BindView(R.id.trl_lore)
    TwinklingRefreshLayout mTrlLore;

    private MapModel mMapModel;
    private ManeuverModel mManeuverModel;
    private MapBean mMapBean;
    private ManeuverAdapter mManeuverAdapter;
    private int mtotalWidth;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int selecrotWorkHeight;
    private boolean isHideCity;
    private boolean isHideWork;
    private WorkAdapter mWorkAdapter;
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

    private void init() {
        mMapBean = new MapBean();
        mMapModel = new MapModel();
        mManeuverModel = new ManeuverModel();
        mWorkList = new ArrayList<>();
        mManeuverAdapter = new ManeuverAdapter();
        mWorkAdapter = new WorkAdapter();
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
        mRvContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvContainer.setAdapter(mWorkAdapter);
        mRvContainer.addItemDecoration(new SpacesItemDecoration(0,5));
        getAllPersonal(true,"null,null,null,null,null");


        mRvWork.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvWork.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvWork.setAdapter(mManeuverAdapter);

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

        mManeuverModel.getAllWorkType(new ManeuverModel.OngetAllWorkTypeListener() {
            @Override
            public void Result(boolean isSuccess, List<WorkBean> data) {
                if (isSuccess) {
                    mWorkList = data;
                    mManeuverAdapter.notifyData(mWorkList);
                }
            }
        });
        //        mWorkList.add(new WorkBean("水电工"));
        //        mWorkList.add(new WorkBean("木工"));
        //        mWorkList.add(new WorkBean("土建"));
        //        mWorkList.add(new WorkBean("焊工"));





    }

    private void initListener() {
        mTvCity.setOnClickListener(this);
        mTvWork.setOnClickListener(this);
        mManeuverAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i < mWorkList.size(); i++) {
                    if (i == holder.getPosition()) {
                        mWorkList.get(i).setCheck(true);
                        //                        Log.e("@#",mWorkList.get(i).)
                        continue;
                    }
                    mWorkList.get(i).setCheck(false);
                }
                mManeuverAdapter.notifyData(mWorkList);
                mTvWork.performClick();
            }
        });

        mTrlLore.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getAllPersonal(true,"null,null,null,null,null");
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getAllPersonal(false,"null,null,null,null,null");
            }
        });
    }
    private int mPage=1;
    private void getAllPersonal(final  boolean isRefresh,String condition){

        if (isRefresh){
            mPage=1;
        }
        mManeuverModel.getAllPersonal(condition, mPage, new ManeuverModel.OngetAllPersonalListener() {
            @Override
            public void Result(boolean isSuccess, final List<ManeuverBean> data) {
                if (isSuccess){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh){
                                //刷新
                                mWorkAdapter.notifyData(data);
                                mTrlLore.finishRefreshing();
                            }else {
                                //加载更多
                                mWorkAdapter.LoadMoreData(data);
                                mTrlLore.finishLoadmore();
                            }
                            mPage++;
                        }
                    }, 1500);
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
        mLlWork.measure(0, 0);
        selecrotWorkHeight = mLlWork.getMeasuredHeight();
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
