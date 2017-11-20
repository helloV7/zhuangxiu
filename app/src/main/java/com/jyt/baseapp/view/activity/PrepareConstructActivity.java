package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentRequestCode;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.FreeDialog;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 货到待施工 安排施工人员完毕
 * Created by chenweiqi on 2017/11/3.
 */

public class PrepareConstructActivity extends BaseActivity {

    @BindView(R.id.LT_estimateTime)
    LabelAndTextItem LTEstimateTime;
    @BindView(R.id.LT_actualTime)
    LabelAndTextItem LTActualTime;
    @BindView(R.id.LT_estimateInShopTime)
    LabelAndTextItem LTEstimateInShopTime;
    @BindView(R.id.LT_worker)
    LabelAndTextItem LTWorker;
    @BindView(R.id.LT_monitor)
    LabelAndTextItem LTMonitor;
    @BindView(R.id.text_confirm)
    TextView textConfirm;

    public static final  int TYPE_PREPARE = 1;
    public static final int TYPE_PREPARE_FINISH =2;

    private int type;
    private boolean enableView = true;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_prepare_construct;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        switch (type) {
            case TYPE_PREPARE:
                setTextTitle("货到待施工");
                enableView = true;
                break;
            case TYPE_PREPARE_FINISH:
                setTextTitle("安排施工人员完毕");
                enableView = false;
                break;
        }
        setEnable(enableView);
    }

    @OnClick(R.id.LT_estimateInShopTime)
    public void onEstimateInShopTimeClick(View v){
        LTViewOpenDatePickerDialog(v);
    }
    @OnClick(R.id.LT_actualTime)
    public void onEstimateTime(View v){
        LTViewOpenDatePickerDialog(v);
    }

    @OnClick(R.id.LT_worker)
    public void onSelWorkerClick(){
        IntentHelper.openSelSingleWorkerActivityForResult(this);
    }
    @OnClick(R.id.LT_monitor)
    public void onSelMonitorClick(){
//        IntentHelper.openSelSingleMonitorActivityForResult(this);
        FreeDialog dialog =new FreeDialog(this, R.layout.dialog_input);
        dialog.show();
    }


    public void LTViewOpenDatePickerDialog(final View v){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
        datePickerDialog.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onSelected(String date) {
                ((LabelAndTextItem) v).setValueText(date);
            }
        });
        datePickerDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentRequestCode.CODE_SEL_SINGLE_MONITOR && resultCode == RESULT_OK){
//            Person person = (Person) IntentHelper.SelSinglePersonGetResult(data).getItem1();
            //TODO 获取员工 保存并赋值
        }else if (requestCode == IntentRequestCode.CODE_SEL_SINGLE_WORKER && resultCode == RESULT_OK){
            //TODO 获取员工 保存并赋值
        }
    }

    private void setEnable(boolean enable){
         LTEstimateTime.setEnabled(enable);
         LTActualTime.setEnabled(enable);
         LTEstimateInShopTime.setEnabled(enable);
         LTWorker.setEnabled(enable);
         LTMonitor.setEnabled(enable);
    }
}
