package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class FinishSteelHookActivity extends BaseActivity {
    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_finish_steel_hook;
    }

    @Override
    protected View getContentView() {
        return null;
    }




    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {

    }
}
