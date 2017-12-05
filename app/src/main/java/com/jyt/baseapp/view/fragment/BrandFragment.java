package com.jyt.baseapp.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ProjectAdapter;
import com.jyt.baseapp.adapter.WorkAdapter;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.itemDecoration.RecycleViewDivider;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.SearchActivity;
import com.jyt.baseapp.view.activity.ShopActivity;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LinWei on 2017/12/1 14:43
 */
public class BrandFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.ll_input)
    LinearLayout mLlInput;
    @BindView(R.id.tv_map_city)
    TextView mTvMapCity;
    @BindView(R.id.tv_map_progress)
    TextView mTvMapProgress;
    @BindView(R.id.ll_map_gan)
    LinearLayout mLlMapGan;
    @BindView(R.id.rv_shop)
    RecyclerView mRvShop;
    @BindView(R.id.trl_lore)
    TwinklingRefreshLayout mTrlLore;
    @BindView(R.id.selector_city)
    MapSelector mSelectorCity;
    @BindView(R.id.rv_progress)
    RecyclerView mRvProgress;
    Unbinder unbinder;

    private int mtotalWidth;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int mPage=1;
    private MapModel mMapModel;
    private MapBean mMapBean;
    private ProjectAdapter mProjectAdapter;
    private WorkAdapter mWorkAdapter;
    private boolean isHideCity;
    private boolean isShowCity = true;
    private boolean isHideProgress;
    private boolean isShowProgress = true;
    private String str_province ;
    private String str_BrandID;
    private List<WorkBean> mProgresslist;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_brand;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initSelector();
        initData();
        initListener();

    }

    private void init(){
        mMapModel = new MapModel();
        mMapBean = new MapBean();
        mProgresslist = new ArrayList<>();
        mWorkAdapter = new WorkAdapter();
        mProjectAdapter =new ProjectAdapter();
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        mRvShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvShop.addItemDecoration(new SpacesItemDecoration(0, 3));
        mRvShop.setAdapter(mProjectAdapter);

    }

    private void initSelector() {
        mSelectorCity.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorCity.getLayoutParams().height = 0;
        mSelectorCity.requestLayout();
        mRvProgress.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mRvProgress.requestLayout();
        mSelectorCity.setHideDeleteIV(true);

        mSelectorCity.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, String ProvinceName) {
                ChangeProvince(ProvinceID);
                str_province = ProvinceName;
            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                SearchMapShop(str_province + "," + CityName + "," + AreaName);
                mTvMapCity.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });

    }

    private void initData() {

        getLRData(true);

        mMapModel.getProvinceData(getActivity(),new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    mSelectorCity.setProvinceAdapter(mMapBean, getActivity());
                }
            }
        });


        mMapModel.getCityAreaData(3, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
                if (isSuccess) {
                    mMapBean.mCities = data;
                    mSelectorCity.setCityAdapter(mMapBean, getActivity());
                }
            }
        });


        mProgresslist.add(new WorkBean("全部","null").firstCheck());
        mProgresslist.add(new WorkBean("丈量中","0"));
        mProgresslist.add(new WorkBean("设计报价","100"));
        mProgresslist.add(new WorkBean("客户审批","200"));
        mProgresslist.add(new WorkBean("店主确认中","300"));
        mProgresslist.add(new WorkBean("备货中","400"));
        mProgresslist.add(new WorkBean("物流中","500"));
        mProgresslist.add(new WorkBean("进程施工中","600"));
        mProgresslist.add(new WorkBean("完成施工","700"));
        mProgresslist.add(new WorkBean("完成结算","800"));

        mRvProgress.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRvProgress.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.VERTICAL));
        mWorkAdapter.setDataList(mProgresslist);
        mRvProgress.setAdapter(mWorkAdapter);

    }

    private void initListener() {
        mTvMapCity.setOnClickListener(this);
        mTvMapProgress.setOnClickListener(this);
        mLlInput.setOnClickListener(this);
        mEtInput.setOnClickListener(this);

        mWorkAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                //点击切换选中的工种颜色
                for (int i = 0; i <mProgresslist.size() ; i++) {
                    if (i==holder.getPosition()){
                        mProgresslist.get(i).setCheck(true);
                        mTvMapProgress.performClick();
                        SearchProgressShop(mProgresslist.get(i).getId());
                        continue;
                    }
                    mProgresslist.get(i).setCheck(false);
                }
                mWorkAdapter.notifyData(mProgresslist);
            }
        });

        mTrlLore.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getLRData(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getLRData(false);
            }
        });
    }

    public void getLRData( final  boolean isRefresh){
        if (isRefresh){
            mPage=1;
        }
        mMapModel.getLRData(mPage, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, final List<SearchBean> data) {
                if (isSuccess){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh){
                                //刷新
                                mProjectAdapter.notifyData(data);
                                mTrlLore.finishRefreshing();
                            }else {
                                //加载更多
                                mProjectAdapter.LoadMoreData(data);
                                mTrlLore.finishLoadmore();
                            }
                            mPage++;
                        }
                    }, 1500);

                }
            }
        });

        mProjectAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Intent intent = new Intent(getActivity(), ShopActivity.class);
                SearchBean ShopInfo = (SearchBean) holder.getData();
                intent.putExtra("shopinfo", ShopInfo);
                startActivity(intent);
            }
        });
    }


    /**
     * 改变省
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

    /**
     * 点击市内区域，获取信息
     *
     * @param condition
     */
    private void SearchMapShop(String condition) {
        mMapModel.getSearchData("null," + condition + ",null,null,null", new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                if (isSuccess) {
                    mProjectAdapter.notifyData(data);
                }
            }
        });
    }

    /**
     * 点击进度，获取信息
     *
     * @param condition
     */
    private void SearchProgressShop(String condition) {
        Log.e("@#",condition);
        mMapModel.getSearchData("null,null,null,null,null,null," + condition, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                if (isSuccess) {
                    mProjectAdapter.notifyData(data);
                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_map_city:
                if (isShowCity) {
                    mTvMapCity.setText("城市∧");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapProgress.setText("进程∨");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.VISIBLE);
                    mRvProgress.setVisibility(View.GONE);
                    isShowCity = false;
                    isShowProgress = true;
                    isHideCity = false;
                } else {
                    mTvMapCity.setText("城市∨");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowCity = true;
                }
                setCitySelector();
                break;
            case R.id.tv_map_progress:
                if (isShowProgress) {
                    mTvMapProgress.setText("进程∧");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapCity.setText("城市∨");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mRvProgress.setVisibility(View.VISIBLE);
                    mSelectorCity.setVisibility(View.GONE);
                    isShowProgress = false;
                    isShowCity = true;
                    isHideProgress = false;
                } else {
                    mTvMapProgress.setText("进程∨");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowProgress = true;
                }
                setProgressSelector();
                break;
            case R.id.et_input:
            case R.id.ll_input:
                getActivity().startActivity(new Intent(getActivity(), SearchActivity.class));
                break;

            default:
                break;
        }
    }


    /**
     * 城市的选择器动画
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
     * 进度的选择器动画
     */
    private void setProgressSelector() {
        ValueAnimator animator = null;
        if (!isHideProgress) {
            animator = ValueAnimator.ofInt(0, selecrotHeight);
            isHideProgress = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotHeight, 0);
            isHideProgress = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mRvProgress.getLayoutParams().height = (int) animation.getAnimatedValue();
                mRvProgress.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
