package com.jyt.baseapp.view.activity;

import android.app.Activity;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jyt.baseapp.util.FinishActivityManager;

import java.lang.ref.WeakReference;
import java.util.Stack;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chenweiqi on 2017/5/10.
 */

public abstract class BaseActivity extends AppCompatActivity {

    FinishActivityManager manager = FinishActivityManager.getManager();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View contentView = getContentView();
        if (contentView!=null){
            setContentView(contentView);
        }
        int layoutId = getLayoutId();
        if (layoutId!=0){
            setContentView(layoutId);
        }

        ButterKnife.bind(this);
        manager.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.finishActivity(this);
    }

    abstract protected int getLayoutId();
    abstract protected View getContentView();
}
