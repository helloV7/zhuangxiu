package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.view.fragment.BaseFragment;
import com.jyt.baseapp.view.fragment.FileFragment;
import com.jyt.baseapp.view.fragment.PicFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShareActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.vp_container)
    ViewPager mVpContainer;
    @BindView(R.id.iv_file)
    ImageView mIvFile;
    @BindView(R.id.tv_file)
    TextView mTvFile;
    @BindView(R.id.ll_file)
    LinearLayout mLlFile;
    @BindView(R.id.iv_pic)
    ImageView mIvPic;
    @BindView(R.id.tv_pic)
    TextView mTvPic;
    @BindView(R.id.ll_pic)
    LinearLayout mLlPic;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("共享资料");
        initVp();
        initListener();
    }

    private void initVp() {
        List<BaseFragment> flist=new ArrayList<>();
        flist.add(new FileFragment());
        flist.add(new PicFragment());
        FragmentViewPagerAdapter pagerAdapter=new FragmentViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.setFragments(flist);
        mVpContainer.setAdapter(pagerAdapter);
        mVpContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTabBg(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initListener() {
        mLlFile.setOnClickListener(this);
        mLlPic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_file:
                setTabBg(0);
                mVpContainer.setCurrentItem(0);
                break;
            case R.id.ll_pic:
                setTabBg(1);
                mVpContainer.setCurrentItem(1);
                break;

            default:
                break;
        }
    }

    public void setTabBg(int index){
        mIvFile.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jibenxinxi_off));
        mTvFile.setTextColor(getResources().getColor(R.color.vp_text));
        mIvPic.setImageDrawable(getResources().getDrawable(R.mipmap.icon_tupian_off));
        mTvPic.setTextColor(getResources().getColor(R.color.vp_text));
        switch (index) {
            case 0:
                mIvFile.setImageDrawable(getResources().getDrawable(R.mipmap.tab_jibenxinxi_on));
                mTvFile.setTextColor(getResources().getColor(R.color.white));
                break;
            case 1:
                mIvPic.setImageDrawable(getResources().getDrawable(R.mipmap.icon_tupian_on));
                mTvPic.setTextColor(getResources().getColor(R.color.white));
                break;

            default:
                break;
        }
    }
}
