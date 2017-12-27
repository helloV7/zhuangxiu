package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.jyt.baseapp.R;

import butterknife.BindView;

/**
 * @author LinWei on 2017/12/27 14:41
 */
public class GuideActivity extends BaseActivity {

    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private int[] mLsit = {R.mipmap.page1, R.mipmap.page2, R.mipmap.page3, R.mipmap.page4};
    private GuideAdapter mGuideAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guide;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideActionBar();
        mGuideAdapter = new GuideAdapter(mLsit);
        mVpGuide.setAdapter(mGuideAdapter);
        mVpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == mLsit.length - 1) {
                    mBtnSubmit.setVisibility(View.VISIBLE);
                } else {
                    mBtnSubmit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                finish();
            }
        });

    }


    class GuideAdapter extends PagerAdapter {
        int[] list;

        public GuideAdapter(int[] list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = (ImageView) View.inflate(GuideActivity.this, R.layout.view_iv, null);
            iv.setBackground(getResources().getDrawable(list[position]));
            container.addView(iv);
            return iv;
        }

    }

}
