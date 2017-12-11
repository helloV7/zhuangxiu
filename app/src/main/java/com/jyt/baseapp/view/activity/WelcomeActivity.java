package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.util.BaseUtil;


public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideActionBar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (BaseUtil.getSpBoolean(Const.UserLoginState)){
                    startActivity(new Intent(WelcomeActivity.this,ContentActivity.class));
                }else {
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                }
                finish();
                overridePendingTransition(0,0);
            }
        },1500);

    }
}
