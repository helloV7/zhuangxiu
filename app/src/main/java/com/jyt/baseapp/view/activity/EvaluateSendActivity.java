package com.jyt.baseapp.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.EvaluateModel;
import com.jyt.baseapp.util.BaseUtil;

import butterknife.BindView;
import okhttp3.Call;

/**
 * @author LinWei on 2017/12/26 17:11
 */
public class EvaluateSendActivity extends BaseActivity {
    @BindView(R.id.tv_evaluate)
    EditText mEtEvaluate;
    @BindView(R.id.rb_evaluate)
    RatingBar mRbEvaluate;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;
    @BindView(R.id.rl_star)
    RelativeLayout mRlStar;
    private EvaluateModel mEvaluateModel;
    private int state;
    private int starNum;
    private boolean isShop;
    private String projectId;
    private boolean isPrepare;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_send;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
//        initShop();
        initBrand();
    }

    private void init() {
        setTextTitle("填写评价");
        mEvaluateModel = new EvaluateModel();
        state = getIntent().getIntExtra(IntentKey.STATE, 0);
        isShop = getIntent().getBooleanExtra(IntentKey.SHOP,false);
        projectId = getIntent().getStringExtra(IntentKey.PROJECTID);
        mEtEvaluate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    isPrepare = true;
                    mBtnSubmit.setBackground(getResources().getDrawable(R.drawable.btn_add_on));
                } else {
                    isPrepare = false;
                    mBtnSubmit.setBackground(getResources().getDrawable(R.drawable.btn_add_off));
                }
            }
        });

        mEtEvaluate.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == KeyEvent.KEYCODE_ENTER){
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if(imm.isActive()){
                        imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0 );
                    }
                    return true;

                }
                return false;
            }
        });

    }

    /**
     * 店主
     */
    private void initShop() {
        mRlStar.setVisibility(View.VISIBLE);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPrepare) {
                    mEvaluateModel.SendEvaluate1(state, mEtEvaluate.getText().toString().trim(), (int) mRbEvaluate.getRating(), projectId, new BeanCallback<BaseJson>() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(BaseJson response, int id) {
                            if (response.ret) {
                                BaseUtil.makeText("评论成功");
                                IntentHelper.OpenEvaluateDetailActivity(EvaluateSendActivity.this,projectId,state,true);
                                finish();
                            }else {
                                BaseUtil.makeText("评论失败");
                            }
                        }
                    });

                } else {
                    BaseUtil.makeText("请输入评价内容");
                }
            }
        });
    }

    /**
     * 品牌方
     */
    private void initBrand() {
        mRlStar.setVisibility(View.GONE);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPrepare) {
                    mEvaluateModel.SendEvaluate2(state, mEtEvaluate.getText().toString().trim(), projectId, new BeanCallback<BaseJson>() {
                        @Override
                        public void onError(Call call, Exception e, int id) {

                        }

                        @Override
                        public void onResponse(BaseJson response, int id) {
                            if (response.ret) {
                                BaseUtil.makeText("评论成功");
                                setResult(IntentKey.RESULT_SEND);
                                finish();
                            }else {
                                BaseUtil.makeText("评论失败");
                            }
                        }
                    });

                } else {
                    BaseUtil.makeText("请输入评价内容");
                }
            }
        });
    }


}
