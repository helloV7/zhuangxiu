package com.jyt.baseapp.view.activity;

/**
 * Created by chenweiqi on 2017/10/30.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.annotation.ActivityAnnotation;

import butterknife.BindView;

/**
 * 上传测量图片
 */
@ActivityAnnotation(showBack = true)
public class UpLoadImageActivity extends BaseActivity {
    @BindView(R.id.asd)
    TextView asd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_up_load_image;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
