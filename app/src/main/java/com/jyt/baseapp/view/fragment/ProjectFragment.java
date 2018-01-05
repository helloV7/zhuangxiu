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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ProjectAdapter;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.InfoActivity;
import com.jyt.baseapp.view.activity.SearchActivity;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;
import com.jyt.baseapp.view.widget.SingleSelector;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jpush.android.api.JPushInterface;

/**
 * @author LinWei on 2017/10/30 15:05
 */
public class ProjectFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.ll_input)
    LinearLayout mLlInput;
    @BindView(R.id.et_input)
    EditText mEtInput;
    @BindView(R.id.iv_noti)
    ImageView mIvNoti;
    @BindView(R.id.tv_map_city)
    TextView mTvMapCity;
    @BindView(R.id.tv_map_brand)
    TextView mTvMapBrand;
    @BindView(R.id.tv_map_progress)
    TextView mTvMapProgress;
    @BindView(R.id.ll_map_gan)
    LinearLayout mLlMapGan;
    @BindView(R.id.rv_shop)
    RecyclerView mRvShop;
    @BindView(R.id.selector_city)
    MapSelector mSelectorCity;
    @BindView(R.id.selector_brand)
    SingleSelector mSelectorBrand;
    @BindView(R.id.selector_progress)
    SingleSelector mSelectorProgress;
    Unbinder unbinder;
    @BindView(R.id.trl_lore)
    TwinklingRefreshLayout mTrlLore;
    @BindView(R.id.fl_main)
    FrameLayout mFlMain;
    @BindView(R.id.iv_arrow1)
    ImageView mIvArrow1;
    @BindView(R.id.ll_city)
    LinearLayout mLlCity;
    @BindView(R.id.iv_arrow2)
    ImageView mIvArrow2;
    @BindView(R.id.ll_brand)
    LinearLayout mLlBrand;
    @BindView(R.id.iv_arrow3)
    ImageView mIvArrow3;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.tv_num)
    TextView mTvNum;

    private int mtotalWidth;
    private MapModel mMapModel;
    private MapBean mMapBean;
    private int selecrotHeight = BaseUtil.dip2px(380);
    private int mPage = 1;
    private boolean isHideCity;
    private boolean isHideBrand;
    private boolean isHideProgress;
    private boolean isShowCity = true;
    private boolean isShowBrand = true;
    private boolean isShowProgress = true;
    private boolean isClickProvince = false;
    private boolean isClickProgress;
    private String str_province="null";//搜索的省份
    private String str_city="null";//搜索的城市
    private String str_area="null";//搜索的地区
    private String str_Brand="null";//搜索的品牌
    private String str_BrandSon="null";//搜索的子品牌
    private String str_progress="null";//搜索的进度
    private ProjectAdapter mProjectAdapter;
    private List<List<BrandBean>> mPData;
    private List<BrandBean> ProgressList;
    private List<BrandBean> Pson0;
    private List<BrandBean> Pson1;
    private List<BrandBean> Pson2;
    private List<BrandBean> Pson3;
    private List<BrandBean> Pson4;
    private List<BrandBean> Pson5;
    private List<BrandBean> Pson6;
    private List<BrandBean> Pson7;
    private List<BrandBean> Pson8;
    private List<BrandBean> Pson9;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initSelector();
        initData();
        initListener();
    }

    private void init() {
        mMapBean = new MapBean();
        mMapModel = new MapModel();
        mProjectAdapter = new ProjectAdapter();
        WindowManager wm = (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth = wm.getDefaultDisplay().getWidth();
        List<SearchBean> list = new ArrayList<>();
        mRvShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvShop.addItemDecoration(new SpacesItemDecoration(0, 3));

        mProjectAdapter.setDataList(list);
        mRvShop.setAdapter(mProjectAdapter);
        ProgressList = new ArrayList<>();
        //        ProgressList.add(new BrandBean("全部", "-1").setChecks(true));
        mPData=new ArrayList<>();
        ProgressList.add(new BrandBean("全部", "-1").setChecks(true));
        ProgressList.add(new BrandBean("丈量中", "0"));
        ProgressList.add(new BrandBean("设计报价", "1"));
        ProgressList.add(new BrandBean("客户审批", "2"));
        ProgressList.add(new BrandBean("店主确认中", "3"));
        ProgressList.add(new BrandBean("备货中", "4"));
        ProgressList.add(new BrandBean("物流中", "5"));
        ProgressList.add(new BrandBean("进程施工中", "6"));
        ProgressList.add(new BrandBean("完成施工", "7"));
        ProgressList.add(new BrandBean("完成结算", "8"));

        Pson0 = new ArrayList<>();
        Pson0.add(new BrandBean("", "-1").TransDataThis());


        Pson1 = new ArrayList<>();
        Pson1.add(new BrandBean("全部", "0").TransDataThis());
        Pson1.add(new BrandBean("测量中", "1").TransDataThis());
        Pson1.add(new BrandBean("测量完毕", "2").TransDataThis());
        mPData.add(Pson1);

        Pson2 = new ArrayList<>();
        Pson2.add(new BrandBean("全部", "100").TransDataThis());
        Pson2.add(new BrandBean("待设计", "101").TransDataThis());
        Pson2.add(new BrandBean("设计中", "102").TransDataThis());
        Pson2.add(new BrandBean("设计完毕", "103").TransDataThis());
        Pson2.add(new BrandBean("待报价", "104").TransDataThis());
        Pson2.add(new BrandBean("报价完毕", "105").TransDataThis());
        mPData.add(Pson2);

        Pson3 = new ArrayList<>();
        Pson3.add(new BrandBean("全部", "200").TransDataThis());
        Pson3.add(new BrandBean("待客户审批", "201").TransDataThis());
        Pson3.add(new BrandBean("客户已审批", "202").TransDataThis());
        mPData.add(Pson3);

        Pson4 = new ArrayList<>();
        Pson4.add(new BrandBean("全部", "300").TransDataThis());
        Pson4.add(new BrandBean("待店主确认", "301").TransDataThis());
        Pson4.add(new BrandBean("店主已确认", "302").TransDataThis());
        mPData.add(Pson4);

        Pson5 = new ArrayList<>();
        Pson5.add(new BrandBean("全部", "400").TransDataThis());
        Pson5.add(new BrandBean("预算确认下单", "401").TransDataThis());
        Pson5.add(new BrandBean("待下图纸", "402").TransDataThis());
        Pson5.add(new BrandBean("图纸下单", "403").TransDataThis());
        Pson5.add(new BrandBean("待审图纸", "404").TransDataThis());
        Pson5.add(new BrandBean("已审图纸", "405").TransDataThis());
        Pson5.add(new BrandBean("待预算复核图纸", "406").TransDataThis());
        Pson5.add(new BrandBean("预算已复核纸", "407").TransDataThis());
        Pson5.add(new BrandBean("待⽣产招牌", "408").TransDataThis());
        Pson5.add(new BrandBean("待下材料单", "409").TransDataThis());
        Pson5.add(new BrandBean("待审材料单", "410").TransDataThis());
        Pson5.add(new BrandBean("已审材料单", "411").TransDataThis());
        Pson5.add(new BrandBean("待备料", "412").TransDataThis());
        Pson5.add(new BrandBean("钢挂已完成", "413").TransDataThis());
        Pson5.add(new BrandBean("所有材料已打包", "414").TransDataThis());
        mPData.add(Pson5);

        Pson6 = new ArrayList<>();
        Pson6.add(new BrandBean("全部", "500").TransDataThis());
        Pson6.add(new BrandBean("待发货", "501").TransDataThis());
        Pson6.add(new BrandBean("已发货", "502").TransDataThis());
        Pson6.add(new BrandBean("货到待施⼯", "503").TransDataThis());
        Pson6.add(new BrandBean("安排施⼯⼈员完毕", "504").TransDataThis());
        mPData.add(Pson6);

        Pson7 = new ArrayList<>();
        Pson7.add(new BrandBean("施⼯中", "601").TransDataThis());
        mPData.add(Pson7);

        Pson8 = new ArrayList<>();
        Pson8.add(new BrandBean("施⼯完毕", "701").TransDataThis());
        mPData.add(Pson8);

        Pson9 = new ArrayList<>();
        Pson9.add(new BrandBean("全部", "800").TransDataThis());
        Pson9.add(new BrandBean("预算审核照⽚并已回访", "801").TransDataThis());
        Pson9.add(new BrandBean("待寄报销资料", "802").TransDataThis());
        Pson9.add(new BrandBean("已寄报销资料", "803").TransDataThis());
        Pson9.add(new BrandBean("已收款", "900").TransDataThis());
        mPData.add(Pson9);


    }


    /**
     * 重置状态
     */
    public void SetSelector() {
        isHideCity = false;
        isHideBrand = false;
        isHideProgress = false;
        isShowCity = true;
        isShowBrand = true;
        isShowProgress = true;
        mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
        mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
        mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
        mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
        mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
        mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));

        mSelectorCity.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorCity.getLayoutParams().height = 0;
        mSelectorCity.requestLayout();
        mSelectorCity.setHideDeleteIV(true);

        mSelectorBrand.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorBrand.getLayoutParams().height = 0;
        mSelectorBrand.requestLayout();
        mSelectorBrand.setHideDeleteIV(true);

        mSelectorProgress.getLayoutParams().width = (int) (mtotalWidth * 0.9);
        mSelectorProgress.getLayoutParams().height = 0;
        mSelectorProgress.requestLayout();
        mSelectorProgress.setHideDeleteIV(true);
    }

    private void initSelector() {
        SetSelector();
        //区域搜索
        mSelectorCity.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, String ProvinceName) {
                str_province = ProvinceName;
                ChangeProvince(ProvinceID);

            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                if (CityID == -2 && AreaID == -2) {
                    str_city="null";
                    str_area="null";
                }else{
                    str_city=CityName;
                    str_area=AreaName;
                }
                SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);
                mLlCity.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });

        //品牌搜索
        mSelectorBrand.setOnSingleClickListener(new SingleSelector.OnSingleClickListener() {
            @Override
            public void onClickBrand(String BrandID, String BrandName) {
                str_Brand=BrandID;
                ChangeBrand(BrandID);

            }

            @Override
            public void onClickDetail(String BrandSonID, String BrandSonName) {
                str_BrandSon=BrandSonID;
                SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);
                mLlBrand.performClick();

            }

            @Override
            public void onClickBack() {

            }
        });

        //进度搜索
        mSelectorProgress.setLeftAdapter(getActivity(), ProgressList);
        mSelectorProgress.setRightAdapter(getActivity(), Pson0);
        mSelectorProgress.setOnSingleClickListener(new SingleSelector.OnSingleClickListener() {
            @Override
            public void onClickBrand(String BrandID, String BrandName) {
                switch (BrandID) {
                    case "-1":
                        mSelectorProgress.notifyRightData(Pson0);
                        str_progress="null";
                        SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);
                        mLlProgress.performClick();
                        break;
                    case "0":
                        mSelectorProgress.notifyRightData(Pson1);
                        initPl(0);
                        break;
                    case "1":
                        mSelectorProgress.notifyRightData(Pson2);
                        initPl(1);
                        break;
                    case "2":
                        mSelectorProgress.notifyRightData(Pson3);
                        initPl(2);
                        break;
                    case "3":
                        mSelectorProgress.notifyRightData(Pson4);
                        initPl(3);
                        break;
                    case "4":
                        mSelectorProgress.notifyRightData(Pson5);
                        initPl(4);
                        break;
                    case "5":
                        mSelectorProgress.notifyRightData(Pson6);
                        initPl(5);
                        break;
                    case "6":
                        mSelectorProgress.notifyRightData(Pson7);
                        initPl(6);
                        break;
                    case "7":
                        mSelectorProgress.notifyRightData(Pson8);
                        initPl(7);
                        break;
                    case "8":
                        mSelectorProgress.notifyRightData(Pson9);
                        initPl(8);
                        break;
                }
            }

            @Override
            public void onClickDetail(String BrandSonID, String BrandSonName) {
                mLlProgress.performClick();
                str_progress=BrandSonID;
                Log.e("@#","code="+BrandSonID);
                SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);

            }

            @Override
            public void onClickBack() {

            }
        });

    }



    private void initData() {

        getLRData(true);

        mMapModel.getProvinceData(getActivity(), new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, List<MapBean.Province> data) {
                if (isSuccess) {
                    mMapBean.mProvinces = data;
                    mMapBean.mProvinces.add(0, new MapBean.Province("全部", -1));
                    mMapBean.mProvinces.get(0).isCheckProvince = true;
                    mSelectorCity.setProvinceAdapter(mMapBean, getActivity());
                }
            }
        });

        mSelectorCity.setCityAdapter(mMapBean, getActivity());
        //        mMapModel.getCityAreaData(3, new MapModel.onResultCityListener() {
        //            @Override
        //            public void ResultData(boolean isSuccess, Exception e, List<MapBean.City> data) {
        //                if (isSuccess) {
        //                    mMapBean.mCities = data;
        //                    mSelectorCity.setCityAdapter(mMapBean, getActivity());
        //                }
        //            }
        //        });

        mMapModel.getBrandData(new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess) {
                    brandData.add(0, new BrandBean("全部", "-1"));
                    brandData.get(0).setCheck(true);
                    mSelectorBrand.setLeftAdapter(getActivity(), brandData);
                }
            }
        });
        mSelectorBrand.setRightAdapter(getActivity(), new ArrayList<BrandBean>());
        //        mMapModel.getBrandSonData("9ef3c864-b53d-11e7-9b64-00ffaa44255a", new MapModel.OngetBrandResultListener() {
        //            @Override
        //            public void Result(boolean isSuccess, List<BrandBean> brandData) {
        //                if (isSuccess) {
        //                    mSelectorBrand.setRightAdapter(getActivity(), brandData);
        //                }
        //            }
        //        });
    }

    private void initListener() {
        mLlCity.setOnClickListener(this);
        mLlBrand.setOnClickListener(this);
        mLlProgress.setOnClickListener(this);
        mIvNoti.setOnClickListener(this);
        mLlInput.setOnClickListener(this);
        mEtInput.setOnClickListener(this);

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
        mRvShop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isHideCity) {
                    isShowCity = true;
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    setCitySelector();

                }
                if (isHideBrand) {
                    setBrandSelector();
                    isShowBrand = true;
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                }
                if (isHideProgress) {
                    setProgressSelector();
                    isShowProgress = true;
                    mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                }
                return false;
            }
        });

        mProjectAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                SearchBean ShopInfo = (SearchBean) holder.getData();
                IntentHelper.openShopActivity(getActivity(), ShopInfo);
            }
        });
    }


    /**
     * 改变省
     *
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID) {
        //全部的查找
        if (ProcinveID == -1) {
            mMapBean.mCities.clear();
            mSelectorCity.notifyData(mMapBean);
            str_province="null";
            str_city="null";
            str_area="null";
            SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);
            mLlCity.performClick();
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
     * 改变品牌
     *
     * @param BrandID
     */
    private void ChangeBrand(String BrandID) {
        if ("-1".equals(BrandID)) {
            str_Brand="null";
            str_BrandSon="null";
            SearchMapShop("null,"+str_province+","+ str_city +","+ str_area +"," + str_Brand + "," + str_BrandSon + ","+str_progress);
            List<BrandBean> brandData =new ArrayList<>();
            mSelectorBrand.notifyRightData(brandData);
            mLlBrand.performClick();
            return;
        }
        mMapModel.getBrandSonData(BrandID, new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess) {
                    mSelectorBrand.notifyRightData(brandData);
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

        mMapModel.getSearchData(condition , new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                if (isSuccess) {
                    mProjectAdapter.notifyData(data);
                }
            }
        });
    }

    /**
     * 点击子品牌，获取信息
     *
     * @param condition
     */
    private void SearchBrandShop(String condition) {
        mMapModel.getSearchData("null,null,null,null," + condition + ",null", new MapModel.OnSearchResultListener() {
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
        mMapModel.getSearchData("null,null,null,null,null,null," + condition, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                if (isSuccess) {
                    mProjectAdapter.notifyData(data);
                }
            }
        });
    }

    /**
     * 加载数据
     *
     * @param isRefresh true-刷新 false-加载更多
     */
    public void getLRData(final boolean isRefresh) {
        if (isRefresh) {
            mPage = 1;
        }
        mMapModel.getLRData(mPage, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, final List<SearchBean> data) {
                if (isSuccess) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (isRefresh) {
                                //刷新
                                mProjectAdapter.notifyData(data);
                                mTrlLore.finishRefreshing();
                            } else {
                                //加载更多
                                mProjectAdapter.LoadMoreData(data);
                                mTrlLore.finishLoadmore();
                            }
                            mPage++;
                        }
                    }, 1500);

                } else {
                    if (isRefresh) {
                        mTrlLore.finishRefreshing();
                    } else {
                        mTrlLore.finishLoadmore();
                    }
                }
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_city:
                if (isShowCity) {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapCity.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.VISIBLE);
                    mSelectorBrand.setVisibility(View.GONE);
                    mSelectorProgress.setVisibility(View.GONE);
                    isShowCity = false;
                    isShowBrand = true;
                    isShowProgress = true;
                    isHideCity = false;
                } else {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowCity = true;
                }
                setCitySelector();
                break;
            case R.id.ll_brand:
                if (isShowBrand) {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorBrand.setVisibility(View.VISIBLE);
                    mSelectorCity.setVisibility(View.GONE);
                    mSelectorProgress.setVisibility(View.GONE);
                    isShowBrand = false;
                    isShowCity = true;
                    isShowProgress = true;
                    isHideBrand = false;
                } else {
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowBrand = true;
                }
                setBrandSelector();
                break;
            case R.id.ll_progress:
                if (isShowProgress) {
                    mIvArrow1.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mIvArrow2.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_up_blue));
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorProgress.setVisibility(View.VISIBLE);
                    mSelectorBrand.setVisibility(View.GONE);
                    mSelectorCity.setVisibility(View.GONE);
                    isShowProgress = false;
                    isShowBrand = true;
                    isShowCity = true;
                    isHideProgress = false;
                } else {
                    mIvArrow3.setImageDrawable(getResources().getDrawable(R.mipmap.btn_down));
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowProgress = true;
                }
                setProgressSelector();
                break;
            case R.id.iv_noti:
                getActivity().startActivity(new Intent(getActivity(), InfoActivity.class));
                JPushInterface.clearAllNotifications(getActivity());
                Const.NUM=0;
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
     * 地图的选择器动画
     */
    private void setBrandSelector() {
        ValueAnimator animator = null;
        if (!isHideBrand) {
            animator = ValueAnimator.ofInt(0, selecrotHeight);
            isHideBrand = true;
        } else {
            animator = ValueAnimator.ofInt(selecrotHeight, 0);
            isHideBrand = false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSelectorBrand.getLayoutParams().height = (int) animation.getAnimatedValue();
                mSelectorBrand.requestLayout();
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
                mSelectorProgress.getLayoutParams().height = (int) animation.getAnimatedValue();
                mSelectorProgress.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Const.NUM>0){
            mTvNum.setVisibility(View.VISIBLE);
            mTvNum.setText(Const.NUM+"");
        }else {
            mTvNum.setVisibility(View.GONE);
        }
    }

    private void initPl(int index){
        for (int i = 0; i < mPData.size(); i++) {
            if (i==index){
                continue;
            }
            initPlData(mPData.get(i));
        }
    }

    private void initPlData(List<BrandBean> data){
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setCheck(false);
        }
    }
}
