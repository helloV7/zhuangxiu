package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private boolean enableView = false;

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
        switch (type){
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

        LTActualTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LTViewOpenDatePickerDialog(v);
            }
        });

        LTEstimateInShopTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LTViewOpenDatePickerDialog(v);
            }
        });

        LTWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        LTMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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


    private void setEnable(boolean enable){
         LTEstimateTime.setEnabled(enable);
         LTActualTime.setEnabled(enable);
         LTEstimateInShopTime.setEnabled(enable);
         LTWorker.setEnabled(enable);
         LTMonitor.setEnabled(enable);
    }
}
