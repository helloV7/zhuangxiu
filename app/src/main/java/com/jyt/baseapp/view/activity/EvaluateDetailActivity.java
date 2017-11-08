package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.jyt.baseapp.R;

public class EvaluateDetailActivity extends BaseActivity {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("评价详情");
    }
}
