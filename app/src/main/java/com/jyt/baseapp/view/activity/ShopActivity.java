package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.view.fragment.BaseFragment;
import com.jyt.baseapp.view.fragment.ShopNewsFragment;
import com.jyt.baseapp.view.fragment.ShopProgressFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/2 13:42
 */
public class ShopActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.vp_shop_container)
    ViewPager mVpContainer;
    @BindView(R.id.iv_shop_news)
    ImageView mIvNews;
    @BindView(R.id.tv_shop_news)
    TextView mTvNews;
    @BindView(R.id.ll_shop_news)
    LinearLayout mLlNews;
    @BindView(R.id.iv_shop_progress)
    ImageView mIvProgress;
    @BindView(R.id.tv_shop_progress)
    TextView mTvProgress;
    @BindView(R.id.ll_shop_progress)
    LinearLayout mLlProgress;
    private List<BaseFragment> flist;
    private ShopNewsFragment mNewsFragment;
    private ShopProgressFragment mProgressFragment;
    private FragmentViewPagerAdapter mAdapter;
    private SearchBean mShopInfo;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initVP();
        initListener();
    }


    private void init(){
        mShopInfo = (SearchBean) getIntent().getSerializableExtra(IntentKey.SHOPINFO);
        if (mShopInfo==null){
            //当通过推送进入该界面时，要将工程ID赋予mShopInfo
            mShopInfo =new SearchBean();
            if (getIntent().getStringExtra(IntentKey.PROJECTID)==null || getIntent().getStringExtra(IntentKey.SHOPNAME)==null){
                //店主
                mShopInfo.setProjectName(Const.getProjectname());
                mShopInfo.setProjectId(Const.getProjectid());
            }else {
                //内部人员 品牌方
                mShopInfo.setProjectId(getIntent().getStringExtra(IntentKey.PROJECTID));
                mShopInfo.setProjectName(getIntent().getStringExtra(IntentKey.SHOPNAME));
            }
        }


        flist=new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putSerializable(IntentKey.SHOPINFO,mShopInfo);
        mNewsFragment=new ShopNewsFragment();
        mNewsFragment.setArguments(bundle);
        mProgressFragment=new ShopProgressFragment();
        mProgressFragment.setArguments(bundle);
        mAdapter=new FragmentViewPagerAdapter(getSupportFragmentManager());
        setTextTitle(mShopInfo.getProjectName());
    }

    private void initShop(){
        //店主界面设置
        setFunctionText("退出");
        setOnClickFunctionListener(new OnClickTvFunctionListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(ShopActivity.this,LoginActivity.class));
                Const.Logout(ShopActivity.this);
                finish();
            }
        });
    }

    private void initVP(){
        flist.add(mNewsFragment);
        flist.add(mProgressFragment);
        mAdapter.setFragments(flist);
        mVpContainer.setOffscreenPageLimit(2);
        mVpContainer.setAdapter(mAdapter);
        mVpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        setOffBg(0);
                        break;
                    case 1:
                        setOffBg(1);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public SearchBean getShopInfo(){
        return mShopInfo;
    }

    private void initListener(){
        mLlNews.setOnClickListener(this);
        mLlProgress.setOnClickListener(this);
    }

    public void setOffBg(int selecor){
        mIvNews.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jibenxinxi_off));
        mTvNews.setTextColor(getResources().getColor(R.color.vp_text));
        mIvProgress.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jindu_off));
        mTvProgress.setTextColor(getResources().getColor(R.color.vp_text));
        switch (selecor) {
            case 0:
                mIvNews.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jibenxinxi_on));
                mTvNews.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                mIvProgress.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jindu_on));
                mTvProgress.setTextColor(getResources().getColor(R.color.white));
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_shop_news:
                setOffBg(0);
                mVpContainer.setCurrentItem(0);
                break;
            case R.id.ll_shop_progress:
                setOffBg(1);
                mVpContainer.setCurrentItem(1);
                break;
            default:
                break;
        }
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        FinishActivityManager manager =FinishActivityManager.getManager();
//        if (!manager.IsActivityExist(ContentActivity.class)){
//            startActivity(new Intent(ShopActivity.this,ContentActivity.class));
//        }
//        finish();
//
//    }


}
