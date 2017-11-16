package com.jyt.baseapp.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ProjectAdapter;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.InfoActivity;
import com.jyt.baseapp.view.activity.SearchActivity;
import com.jyt.baseapp.view.activity.ShopActivity;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.MapSelector;
import com.jyt.baseapp.view.widget.SingleSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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

    private int mtotalWidth;
    private MapModel mMapModel;
    private MapBean mMapBean;
    private int selecrotHeight=BaseUtil.dip2px(380);
    private boolean isHideCity;
    private boolean isHideBrand;
    private boolean isHideProgress;
    private boolean isShowCity=true;
    private boolean isShowBrand=true;
    private boolean isShowProgress=true;
    private String str_province="北京";
    private ProjectAdapter mProjectAdapter;
    private List<BrandBean> ProgressList;
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

    private void init(){
        mMapBean=new MapBean();
        mMapModel=new MapModel();
        mProjectAdapter=new ProjectAdapter();
        WindowManager wm= (WindowManager) BaseUtil.getContext().getSystemService(Context.WINDOW_SERVICE);
        mtotalWidth=wm.getDefaultDisplay().getWidth();
        List<SearchBean> list = new ArrayList<>();
        mRvShop.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRvShop.addItemDecoration(new SpacesItemDecoration(0, 3));
        mProjectAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                startActivity(new Intent(getActivity(), ShopActivity.class));
            }
        });
        mProjectAdapter.setDataList(list);
        mRvShop.setAdapter(mProjectAdapter);
        ProgressList = new ArrayList<>();
        ProgressList.add(new BrandBean("丈量中","0").setChecks(true));
        ProgressList.add(new BrandBean("设计报价","1"));
        ProgressList.add(new BrandBean("客户审批","2"));
        ProgressList.add(new BrandBean("店主确认中","3"));
        ProgressList.add(new BrandBean("备货中","4"));
        ProgressList.add(new BrandBean("物流中","5"));
        ProgressList.add(new BrandBean("进程施工中","6"));
        ProgressList.add(new BrandBean("完成施工","7"));
        ProgressList.add(new BrandBean("完成结算","8"));

        Pson1 = new ArrayList<>();
        Pson1.add(new BrandBean("测量中","1").TransDataThis());
        Pson1.add(new BrandBean("测量完毕","2").TransDataThis());

        Pson2 = new ArrayList<>();
        Pson2.add(new BrandBean("待设计","101").TransDataThis());
        Pson2.add(new BrandBean("设计中","102").TransDataThis());
        Pson2.add(new BrandBean("设计完毕","103").TransDataThis());
        Pson2.add(new BrandBean("待报价","104").TransDataThis());
        Pson2.add(new BrandBean("报价完毕","105").TransDataThis());

        Pson3 = new ArrayList<>();
        Pson3.add(new BrandBean("待客户审批","201").TransDataThis());
        Pson3.add(new BrandBean("客户已审批","202").TransDataThis());

        Pson4 = new ArrayList<>();
        Pson4.add(new BrandBean("待店主确认","301").TransDataThis());
        Pson4.add(new BrandBean("店主已确认","302").TransDataThis());

        Pson5 = new ArrayList<>();
        Pson5.add(new BrandBean("预算确认下单","401").TransDataThis());
        Pson5.add(new BrandBean("待下图纸","402").TransDataThis());
        Pson5.add(new BrandBean("图纸下单","403").TransDataThis());
        Pson5.add(new BrandBean("待审图纸","404").TransDataThis());
        Pson5.add(new BrandBean("已审图纸","405").TransDataThis());
        Pson5.add(new BrandBean("待预算复核图纸","406").TransDataThis());
        Pson5.add(new BrandBean("预算已复核纸","407").TransDataThis());
        Pson5.add(new BrandBean("待⽣产招牌","408").TransDataThis());
        Pson5.add(new BrandBean("待下材料单","409").TransDataThis());
        Pson5.add(new BrandBean("待审材料单","410").TransDataThis());
        Pson5.add(new BrandBean("已审材料单","411").TransDataThis());
        Pson5.add(new BrandBean("待备料","412").TransDataThis());
        Pson5.add(new BrandBean("钢挂已完成","413").TransDataThis());
        Pson5.add(new BrandBean("所有材料已打包","414").TransDataThis());

        Pson6 = new ArrayList<>();
        Pson6.add(new BrandBean("待发货","501").TransDataThis());
        Pson6.add(new BrandBean("已发货","502").TransDataThis());
        Pson6.add(new BrandBean("货到待施⼯","503").TransDataThis());
        Pson6.add(new BrandBean("安排施⼯⼈员完毕","504").TransDataThis());

        Pson7 = new ArrayList<>();
        Pson7.add(new BrandBean("施⼯中","601").TransDataThis());

        Pson8 = new ArrayList<>();
        Pson8.add(new BrandBean("施⼯完毕","701").TransDataThis());

        Pson9 = new ArrayList<>();
        Pson9.add(new BrandBean("预算审核照⽚并已回访","801").TransDataThis());
        Pson9.add(new BrandBean("待寄报销资料","802").TransDataThis());
        Pson9.add(new BrandBean("已寄报销资料","803").TransDataThis());
        Pson9.add(new BrandBean("已收款","804").TransDataThis());


    }

    private void initSelector(){
        mSelectorCity.getLayoutParams().width= (int) (mtotalWidth*0.9);
        mSelectorCity.getLayoutParams().height= 0;
        mSelectorCity.requestLayout();
        mSelectorCity.setHideDeleteIV(true);

        mSelectorBrand.getLayoutParams().width= (int) (mtotalWidth*0.9);
        mSelectorBrand.getLayoutParams().height= 0;
        mSelectorBrand.requestLayout();
        mSelectorBrand.setHideDeleteIV(true);

        mSelectorProgress.getLayoutParams().width= (int) (mtotalWidth*0.9);
        mSelectorProgress.getLayoutParams().height= 0;
        mSelectorProgress.requestLayout();
        mSelectorProgress.setHideDeleteIV(true);

        mSelectorCity.setOnMapClickListener(new MapSelector.OnMapClickListener() {
            @Override
            public void onClickProvince(int ProvinceID, String ProvinceName) {
                ChangeProvince(ProvinceID);
                if ("北京".equals(ProvinceName)
                        || "上海".equals(ProvinceName)
                        || "天津".equals(ProvinceName)
                        || "重庆".equals(ProvinceName)){
                    str_province=ProvinceName+"市";
                }else {
                    str_province=ProvinceName+"省";
                }
            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {
                SearchMapShop(","+str_province+","+CityName+","+AreaName+",null,null,null");
                mTvMapCity.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });

        mMapModel.getBrandData(new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess){
                    mSelectorBrand.setLeftAdapter(getActivity(),brandData);
                }
            }
        });
        mMapModel.getBrandSonData("9ef3c864-b53d-11e7-9b64-00ffaa44255a", new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess){
                    mSelectorBrand.setRightAdapter(getActivity(),brandData);
                }
            }
        });
        mSelectorBrand.setOnSingleClickListener(new SingleSelector.OnSingleClickListener() {
            @Override
            public void onClickBrand(String BrandID, String BrandName) {
                ChangeBrand(BrandID);
            }

            @Override
            public void onClickDetail(String BrandSonID, String BrandSonName) {
                mTvMapBrand.performClick();
            }

            @Override
            public void onClickBack() {

            }
        });

        mSelectorProgress.setLeftAdapter(getActivity(),ProgressList);
        mSelectorProgress.setRightAdapter(getActivity(),Pson1);
        mSelectorProgress.setOnSingleClickListener(new SingleSelector.OnSingleClickListener() {
            @Override
            public void onClickBrand(String BrandID, String BrandName) {
                switch (BrandID){
                    case "0":
                        mSelectorProgress.notifyRightData(Pson1);
                        break;
                    case "1":
                        mSelectorProgress.notifyRightData(Pson2);
                        break;
                    case "2":
                        mSelectorProgress.notifyRightData(Pson3);
                        break;
                    case "3":
                        mSelectorProgress.notifyRightData(Pson4);
                        break;
                    case "4":
                        mSelectorProgress.notifyRightData(Pson5);
                        break;
                    case "5":
                        mSelectorProgress.notifyRightData(Pson6);
                        break;
                    case "6":
                        mSelectorProgress.notifyRightData(Pson7);
                        break;
                    case "7":
                        mSelectorProgress.notifyRightData(Pson8);
                        break;
                    case "8":
                        mSelectorProgress.notifyRightData(Pson9);
                        break;
                }
            }

            @Override
            public void onClickDetail(String BrandSonID, String BrandSonName) {
                Log.e("@#","id="+BrandSonID);
            }

            @Override
            public void onClickBack() {

            }
        });

    }

    private void initData(){
        mMapModel.getProvinceData(new MapModel.onResultProvinceListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, ArrayList<MapBean.Province> data) {
                if (isSuccess){
                    mMapBean.mProvinces=data;
                    mSelectorCity.setProvinceAdapter(mMapBean,getActivity());
                }
            }
        });
        mMapModel.getCityData(1, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess){
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities=data;
                            mSelectorCity.setCityAdapter(mMapBean,getActivity());
                        }
                    });
                }
            }
        });
    }

    private void initListener(){
        mTvMapCity.setOnClickListener(this);
        mTvMapBrand.setOnClickListener(this);
        mTvMapProgress.setOnClickListener(this);
        mIvNoti.setOnClickListener(this);
        mLlInput.setOnClickListener(this);
        mEtInput.setOnClickListener(this);
    }

    /**
     * 改变省
     * @param ProcinveID
     */
    private void ChangeProvince(int ProcinveID){
        mMapModel.getCityData(ProcinveID, new MapModel.onResultCityListener() {
            @Override
            public void ResultData(boolean isSuccess, Exception e, final ArrayList<MapBean.City> data) {
                if (isSuccess){
                    BaseUtil.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMapBean.mCities=data;
                            mSelectorCity.notifyData(mMapBean);
                        }
                    });
                }
            }
        });
    }

    /**
     * 改变品牌
     * @param BrandID
     */
    private void ChangeBrand(String BrandID){
        mMapModel.getBrandSonData(BrandID, new MapModel.OngetBrandResultListener() {
            @Override
            public void Result(boolean isSuccess, List<BrandBean> brandData) {
                if (isSuccess){
                    mSelectorBrand.notifyRightData(brandData);
                }
            }
        });
    }

    private void  SearchMapShop(String condition){
        mMapModel.getSearchData(condition, new MapModel.OnSearchResultListener() {
            @Override
            public void Result(boolean isSuccess, List<SearchBean> data) {
                if (isSuccess){
                    Log.e("@#",data.size()+"");
                    mProjectAdapter.notifyData(data);
                }
            }
        });
    }

    private void SearchBrandShop(String condition){

    }

    private void SearchProgressShop(String condition){

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
            case R.id.tv_map_city:
                if (isShowCity){
                    mTvMapCity.setText("城市∧");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapBrand.setText("品牌∨");
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapProgress.setText("进程∨");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorCity.setVisibility(View.VISIBLE);
                    mSelectorBrand.setVisibility(View.GONE);
                    mSelectorProgress.setVisibility(View.GONE);
                    isShowCity=false;
                    isShowBrand=true;
                    isShowProgress=true;
                    isHideCity=false;
                }else {
                    mTvMapCity.setText("城市∨");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowCity=true;
                }
                setCitySelector();
                break;
            case R.id.tv_map_brand:
                if (isShowBrand){
                    mTvMapBrand.setText("品牌∧");
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapCity.setText("城市∨");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapProgress.setText("进程∨");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorBrand.setVisibility(View.VISIBLE);
                    mSelectorCity.setVisibility(View.GONE);
                    mSelectorProgress.setVisibility(View.GONE);
                    isShowBrand=false;
                    isShowCity=true;
                    isShowProgress=true;
                    isHideBrand=false;
                }else {
                    mTvMapBrand.setText("品牌∨");
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowBrand=true;
                }
                setBrandSelector();
                break;
            case R.id.tv_map_progress:
                if (isShowProgress){
                    mTvMapProgress.setText("进程∧");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.map_text2));
                    mTvMapCity.setText("城市∨");
                    mTvMapCity.setTextColor(getResources().getColor(R.color.text_color1));
                    mTvMapBrand.setText("品牌∨");
                    mTvMapBrand.setTextColor(getResources().getColor(R.color.text_color1));
                    mSelectorProgress.setVisibility(View.VISIBLE);
                    mSelectorBrand.setVisibility(View.GONE);
                    mSelectorCity.setVisibility(View.GONE);
                    isShowProgress=false;
                    isShowBrand=true;
                    isShowCity=true;
                    isHideProgress=false;
                }else {
                    mTvMapProgress.setText("进程∨");
                    mTvMapProgress.setTextColor(getResources().getColor(R.color.text_color1));
                    isShowProgress=true;
                }
                setProgressSelector();
                break;
            case R.id.iv_noti:
                getActivity().startActivity(new Intent(getActivity(), InfoActivity.class));
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
    private void setCitySelector(){
        ValueAnimator animator=null;
        if (!isHideCity){
            animator=ValueAnimator.ofInt(0,selecrotHeight);
            isHideCity =true;
        }else {
            animator=ValueAnimator.ofInt(selecrotHeight,0);
            isHideCity =false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSelectorCity.getLayoutParams().height= (int) animation.getAnimatedValue();
                mSelectorCity.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 地图的选择器动画
     */
    private void setBrandSelector(){
        ValueAnimator animator=null;
        if (!isHideBrand){
            animator=ValueAnimator.ofInt(0,selecrotHeight);
            isHideBrand =true;
        }else {
            animator=ValueAnimator.ofInt(selecrotHeight,0);
            isHideBrand =false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSelectorBrand.getLayoutParams().height= (int) animation.getAnimatedValue();
                mSelectorBrand.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }

    /**
     * 地图的选择器动画
     */
    private void setProgressSelector(){
        ValueAnimator animator=null;
        if (!isHideProgress){
            animator=ValueAnimator.ofInt(0,selecrotHeight);
            isHideProgress =true;
        }else {
            animator=ValueAnimator.ofInt(selecrotHeight,0);
            isHideProgress =false;
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mSelectorProgress.getLayoutParams().height= (int) animation.getAnimatedValue();
                mSelectorProgress.requestLayout();
            }
        });
        animator.setDuration(500);
        animator.start();
    }


}
