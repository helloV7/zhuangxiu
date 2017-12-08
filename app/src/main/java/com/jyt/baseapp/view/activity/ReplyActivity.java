package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.BaseUtil;

import butterknife.BindView;

/**
 * @author LinWei on 2017/12/7 13:54
 */
public class ReplyActivity extends BaseActivity {
    @BindView(R.id.et_reply)
    EditText mEtReply;
    @BindView(R.id.btn_reply)
    Button mBtnReply;
    @BindView(R.id.rb_reply)
    RatingBar mRbReply;
    @BindView(R.id.rl_star)
    RelativeLayout mRlStar;

    private boolean isCanSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reply;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initListener();
    }

    private void init() {
        setTextTitle("评价回复");
    }

    private void initListener() {
        mEtReply.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mBtnReply.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
                    isCanSend = true;
                } else {
                    mBtnReply.setBackground(getResources().getDrawable(R.drawable.btn_add_off));
                    isCanSend = false;
                }
            }
        });
        mBtnReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCanSend) {
                    BaseUtil.makeText("评价内容不能为空!");
                    return;
                }
                //发送评价内容
            }
        });
    }
}
