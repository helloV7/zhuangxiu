package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.InfoBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.JumpItem;

import butterknife.BindView;

public class PersonInfoActivity extends BaseActivity {


    @BindView(R.id.jt_name)
    JumpItem mJtName;
    @BindView(R.id.jt_phone)
    JumpItem mJtPhone;
    @BindView(R.id.jt_department)
    JumpItem mJtDepartment;
    @BindView(R.id.jt_position)
    JumpItem mJtPosition;
    @BindView(R.id.btn_logout)
    Button mBtnLogout;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_person_info;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("个人资料");
        InfoBean bean= (InfoBean) getIntent().getSerializableExtra("Personinfo");

        if (bean!=null){
            mJtName.setMsg(bean.getNickName());
            mJtPhone.setMsg(bean.getTel());
            mJtPosition.setMsg(bean.getStationName());
            mJtDepartment.setMsg(bean.getDeparementName());
        }


        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Const.Logout(PersonInfoActivity.this);
                finish();
                BaseUtil.makeText("已退出登录");
            }
        });

    }


}
