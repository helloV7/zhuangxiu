package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.jyt.baseapp.R;

public class AboutUsActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于我们");
    }
}
