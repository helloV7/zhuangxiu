package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 查看施工 施工中 施工完毕
 * Created by v7 on 2017/11/4.
 */

public class ConstructionActivity extends BaseActivity {
    @BindView(R.id.v_tabLayout)
    TabLayout vTabLayout;
    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_construction;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
