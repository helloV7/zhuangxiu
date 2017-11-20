package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
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
        mJtName.setMsg(BaseUtil.getSpString(Const.NickName));
//        mJtPhone.setMsg(BaseUtil.getSpString(Const.Tel));
        mJtDepartment.setMsg(BaseUtil.getSpString(Const.DepartmentId));
        mJtPosition.setMsg(BaseUtil.getSpString(Const.PositionID));
        mBtnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }


}
