package com.jyt.baseapp.view.fragment;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.jyt.baseapp.bean.MapBean;
import com.jyt.baseapp.bean.ProjectBean;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.MapModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.InfoActivity;
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
    private ProjectAdapter mProjectAdapter;


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
        List<ProjectBean> list = new ArrayList<>();
        list.add(new ProjectBean("A", "轮胎", "暂停中", "广州-天河区"));
        list.add(new ProjectBean("A", "轮胎", "暂停中", "广州-天河区"));
        list.add(new ProjectBean("A", "轮胎", "暂停中", "广州-天河区"));
        list.add(new ProjectBean("A", "轮胎", "暂停中", "广州-天河区"));
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
            }

            @Override
            public void onClickArea(int CityID, String CityName, int AreaID, String AreaName) {

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
    }

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
