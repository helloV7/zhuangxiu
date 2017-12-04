package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.model.PersonModel;

import butterknife.BindView;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.tv_msg)
    TextView mTvMsg;

    private PersonModel mPersonModel;

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
        mPersonModel = new PersonModel();
        mPersonModel.getAboutUs(new PersonModel.OngetAboutUsListener() {
            @Override
            public void Result(boolean isSuccess, String data) {
                if (isSuccess){
                    mTvMsg.setText(data);
                }
            }
        });

    }
}
