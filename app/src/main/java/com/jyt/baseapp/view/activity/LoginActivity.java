package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.UserBean;
import com.jyt.baseapp.model.LoginModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.MD5Util;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author LinWei on 2017/10/30 14:06
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.et__tel)
    EditText mEtTel;
    @BindView(R.id.et_pwd)
    EditText mEtPwd;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    private LoginModel mLoginModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideActionBar();
        if (BaseUtil.getSpBoolean(Const.UserLoginState)){
            startActivity(new Intent(LoginActivity.this,ContentActivity.class));
            finish();
        }
        mLoginModel=new LoginModel();
    }

    @OnClick(R.id.btn_submit)
    public void ToLogin(){
        String tel=mEtTel.getText().toString().trim();
        String pwd=mEtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(tel) || TextUtils.isEmpty(pwd)){
            BaseUtil.makeText("参数不全");
            return;
        }
        mLoginModel.ToLogin(tel, MD5Util.encrypt(pwd), new LoginModel.LoginResultListener() {
            @Override
            public void Result(boolean isSuccess, UserBean user) {
                if (isSuccess){
                    Const.KeepLoginState(user.getDepartmentId(),user.getNickName(),user.getPositionId(),user.getTokenSession());
                    startActivity(new Intent(LoginActivity.this,ContentActivity.class));
                    finish();
                }
            }
        });
    }

}
