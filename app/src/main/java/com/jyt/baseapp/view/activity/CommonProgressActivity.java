package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.view.widget.WorkerNameAndDateTime;

import butterknife.BindView;

/**
 * 测量完毕 设计完毕 客户已审批 待店主确认 店主已确认 待审图纸 已审图纸 待生产招牌 待审材料 已审材料
 * Created by chenweiqi on 2017/11/1.
 */

public class CommonProgressActivity extends BaseActivity {
    @BindView(R.id.text_confirm)
    TextView textConfirm;
    @BindView(R.id.v_workerAndTime)
    WorkerNameAndDateTime vWorkerAndTime;
    @BindView(R.id.v_fileLayout)
    LinearLayout vFileLayout;
    @BindView(R.id.v_imageLayout)
    LinearLayout vImageLayout;


    public static final int TYPE_MEASURE_FINISH = 1;
    public static final int TYPE_DESIGN_FINISH = 2;
    public static final int TYPE_CUSTOMER_VERIFIED = 3;
    public static final int TYPE_WAIT_SHOPKEEPER_CONFIRM = 4;
    public static final int TYPE_SHOPKEEPER_CONFIRMED = 5;
    public static final int TYPE_WAIT_VERIFIED_DRAWING = 6;
    public static final int TYPE_VERIFIED_DRAWING = 7;
    public static final int TYPE_WAIT_PRODUCE_SIGN = 8;
    public static final int TYPE_WAIT_VERIFY_MATERIAL = 9;
    public static final int TYPE_VERIFIED_MATERIAL = 10;


    private int type;
    private Object project;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_progress;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tuple data = IntentHelper.CommonProgressActivityGetPara(getIntent());
        type = (int) data.getItem1();
        project = data.getItem2();
        switch (type){
            case TYPE_MEASURE_FINISH:
                setTextTitle("测量完毕");
                break;
            case TYPE_DESIGN_FINISH :
                setTextTitle("设计完毕");
                break;
            case TYPE_CUSTOMER_VERIFIED :
                setTextTitle("客户已审批");
                break;
            case TYPE_WAIT_SHOPKEEPER_CONFIRM:
                setTextTitle("待店主确认");
                break;
            case TYPE_SHOPKEEPER_CONFIRMED:
                setTextTitle("店主已确认");
                break;
            case TYPE_WAIT_VERIFIED_DRAWING:
                setTextTitle("待审图纸");
                break;
            case TYPE_VERIFIED_DRAWING:
                setTextTitle("已审图纸");

                break;
            case TYPE_WAIT_PRODUCE_SIGN:
                setTextTitle("待生产招牌");

                break;
            case TYPE_WAIT_VERIFY_MATERIAL:
                setTextTitle("待审材料");
                break;
            case TYPE_VERIFIED_MATERIAL:
                setTextTitle("已审材料");
                break;
            default:
                finish();

        }
    }
}
