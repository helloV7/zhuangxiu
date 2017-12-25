package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        mLoginModel=new LoginModel();
        Const.createFileMkdirs();
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
                    Const.KeepLoginState(user.getDepartmentId(),user.getNickName(),user.getPositionId(),user.getTokenSession(),user.getUserId(),user.getDepartmentName(),user.getStationName());
                    startActivity(new Intent(LoginActivity.this,ContentActivity.class));
                    finish();
                    BaseUtil.makeText("登录成功");
                }else {
                    BaseUtil.makeText("登录失败");
                }
            }
        });
    }


    /**
     * 双击退出
     */
    private long mPressedTime = 0;
    @Override
    public void onBackPressed() {
        long mNowTime = System.currentTimeMillis();//获取第一次按键时间
        if((mNowTime - mPressedTime) > 2000){//比较两次按键时间差
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mPressedTime = mNowTime;
        }
        else{//退出程序
            this.finish();
        }
    }

}
