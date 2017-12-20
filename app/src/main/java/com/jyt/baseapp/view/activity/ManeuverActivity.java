package com.jyt.baseapp.view.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.jyt.baseapp.view.widget.LoadingDialog;
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
    @BindView(R.id.iv_arrow1)
    ImageView mIvArrow1;
    @BindView(R.id.ll_city)
    LinearLayout mLlCity;
    @BindView(R.id.iv_arrow2)
    ImageView mIvArrow2;
    @BindView(R.id.ll_type)
    LinearLayout mLlType;

    private MapModel mMapModel;
    private ManeuverModel mManeuverModel;
    private LoadingDialog mLoadingDialog;
    private MapBean mMapBean;
    private WorkAdapter mWorkAdapter;
    private ManeuverAdapter mManeuverAdapter;
    private int mtotalWidth;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int selecrotWorkHeight;
    private boolean isHideCity;
    private boolean isHideWork;
    private List<WorkBean> mWorkList;
    private String str_province;
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
        mLoadingDialog = new LoadingDialog(this);
        mWorkList = new ArrayList<>();
        mWorkAdapter = new WorkAdapter();//工种
        mManeuverAdapter = new ManeuverAdapter();//机动人员
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        setTextTitle("机动人员");
        setIvFunction(R.mipmap.btn_add);
        mLoadingDialog.show();

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
                str_province = ProvinceName;
                ChangeProvince(ProvinceID);

            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                if (CityID == -2 && AreaID == -2) {
                    NotifySearchType(str_province + ",null,null,null,null");
                } else {
                    NotifySearchType(str_province + "," + CityName + "," + AreaName + ",null,null");
                }
                mLlCity.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });


    }

    private void initData() {
        mRvContainer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvContainer.setAdapter(mManeuverAdapter);
        mRvContainer.addItemDecoration(new SpacesItemDecoration(0, 5));
        getAllPersonal(true);


        mRvWork.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRvWork.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRvWork.setAdapter(mWorkAdapter);

        mMapModel.getProvinceData(this, new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.add(0, new MapBean.Province("全部", -1));
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    str_province = mMapBean.mProvinces.get(0).ProvinceName;//默认第一个
                    mSelectorCity.setProvinceAdapter(mMapBean, ManeuverActivity.this);
                }
            }
        });
        mSelectorCity.setCityAdapter(mMapBean, this);
        //        mMapModel.getCityAreaData(3, new MapModel.onResultCityListener() {
        //            @Override
        //            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
        //                if (isSuccess) {
        //                    mMapBean.mCities = data;
        //                    mSelectorCity.setCityAdapter(mMapBean, ManeuverActivity.this);
        //                }
        //            }
        //        });

        mManeuverModel.getAllWorkType(new ManeuverModel.OngetAllWorkTypeListener() {
            @Override
            public void Result(boolean isSuccess, List<WorkBean> data) {
                if (isSuccess) {
                    mWorkList = data;
                    mWorkList.add(0, new WorkBean("全部", "-1"));
                    mWorkAdapter.notifyData(mWorkList);
                }
            }
        });


    }

    private void initListener() {
        mLlCity.setOnClickListener(this);
        mLlType.setOnClickListener(this);
        mWorkAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i < mWorkList.size(); i++) {
                    if (i == holder.getPosition()) {
                        mWorkList.get(i).setCheck(true);
                        if ("-1".equals(mWorkList.get(i).getId())) {
                            NotifySearchType("null,null,null,null,null");
                        } else {
                            NotifySearchType("null,null,null," + mWorkList.get(i).getId() + ",null");
                        }
                        mLlType.performClick();
                        continue;
                    }
                    mWorkList.get(i).setCheck(false);
                }
                mWorkAdapter.notifyData(mWorkList);

            }
        });

        mTrlLore.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getAllPersonal(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getAllPersonal(false);
            }
        });

        setOnClickFunctionListener(new OnClickFunctionListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(ManeuverActivity.this, AddPersonActivity.class));
            }
        });

        mRvContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isHideCity){
                    setCitySelector();
                    isShowCity=true;
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvCity.setTextColor(getResources().getColor(R.color.text_color1));
                }
                if (isHideWork){
                    setWorkSelector();
                    isShowWork=true;
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvWork.setTextColor(getResources().getColor(R.color.text_color1));
                }
                return false;
            }
        });

    }

    private int mPage = 1;

    private void getAllPersonal(final boolean isRefresh) {

        if (isRefresh) {
            mPage = 1;
        }
        mManeuverModel.getAllPersonal("null,null,null,null,null", mPage, new ManeuverModel.OngetAllPersonalListener() {
            @Override
            public void Result(boolean isSuccess, final List<ManeuverBean> data) {
                if (isSuccess) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh) {
                                //刷新
                                if (mLoadingDialog.isShowing()) {
                                    mLoadingDialog.dismiss();
                                }
                                mManeuverAdapter.notifyData(data);
                                mTrlLore.finishRefreshing();
                            } else {
                                //加载更多
                                mManeuverAdapter.LoadMoreData(data);
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
        if (ProcinveID == -1) {
            mMapBean.mCities.clear();
            mSelectorCity.notifyData(mMapBean);
            return;
        }
        mMapModel.getCityAreaData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess) {
                    mMapBean.mCities = data;
                    ArrayList<MapBean.Area> areaList = new ArrayList<MapBean.Area>();
                    areaList.add(new MapBean.Area("全部", -2));
                    MapBean.City city = new MapBean.City("全部", -2, areaList);
                    mMapBean.mCities.add(0, city);
                    mSelectorCity.notifyData(mMapBean);
                }
            }
        });
    }

    /**
     * 改变地点、工种类型进行搜索
     *
     * @param condition
     */
    private void NotifySearchType(String condition) {
        mManeuverModel.getAllPersonal(condition, 1, new ManeuverModel.OngetAllPersonalListener() {
            @Override
            public void Result(boolean isSuccess, final List<ManeuverBean> data) {
                if (isSuccess) {
                    mManeuverAdapter.notifyData(data);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_city:
                if (isShowCity) {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    mTvCity.setTextColor(getResources().getColor(R.color.map_text2));
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvWork.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.VISIBLE);
                    mLlWork.setVisibility(View.GONE);
                    isShowCity = false;
                    isShowWork = true;
                    isHideCity = false;
                } else {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvCity.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowCity = true;
                }
                setCitySelector();
                break;
            case R.id.ll_type:
                if (isShowWork) {
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    mTvWork.setTextColor(getResources().getColor(R.color.map_text2));
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.GONE);
                    mLlWork.setVisibility(View.VISIBLE);
                    isShowWork = false;
                    isShowCity = true;
                    isHideWork = false;
                } else {
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
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
