package com.jyt.baseapp.view.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.DeliverGoodsItem;

import butterknife.BindView;
import butterknife.OnClick;
import cn.aigestudio.datepicker.views.DatePicker;

/** 待发货 已发货
 * Created by chenweiqi on 2017/11/2.
 */

public class DeliverGoodsActivity extends BaseActivity {
    @BindView(R.id.v_batchLayout)
    LinearLayout vBatchLayout;
    @BindView(R.id.btn_addBatch)
    TextView btnAddBatch;
    @BindView(R.id.btn_confirm)
    TextView btnConfirm;
    @BindView(R.id.v_scrollView)
    ScrollView vScrollView;

    public static final int TYPE_WAITE_SEND = 1;
    public static final int TYPE_SENT = 2;

    private int type =0;

    private DeliverGoodsItem.OnSelBeginTimeClick onSelBeginTimeClick;
    private DeliverGoodsItem.OnSelEndTimeClick onSelEndTimeClick;
    private DeliverGoodsItem.OnSelDelClick onSelDelClick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_deliver_goods;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        vBatchLayout.removeAllViews();

        onSelBeginTimeClick = new DeliverGoodsItem.OnSelBeginTimeClick() {
            @Override
            public void onClick(final int indexInParent) {

                DatePickerDialog datePicker = new DatePickerDialog(getContext());
                datePicker.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
                    @Override
                    public void onSelected(String date) {
                        DeliverGoodsItem item = (DeliverGoodsItem) vBatchLayout.getChildAt(indexInParent);
                        item.setBeginTime(date);
                    }
                });
                datePicker.show();
            }
        };
        onSelEndTimeClick = new DeliverGoodsItem.OnSelEndTimeClick() {
            @Override
            public void onClick(final int indexInParent) {
                DeliverGoodsItem item = (DeliverGoodsItem) vBatchLayout.getChildAt(indexInParent);
                DatePickerDialog datePicker = new DatePickerDialog(getContext());
                datePicker.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
                    @Override
                    public void onSelected(String date) {
                        DeliverGoodsItem item = (DeliverGoodsItem) vBatchLayout.getChildAt(indexInParent);
                        item.setEndTime(date);
                    }
                });
                datePicker.show();
            }
        };

        onSelDelClick = new DeliverGoodsItem.OnSelDelClick() {
            @Override
            public void onClick(int indexInParent) {
                int scrollTo;
                if (indexInParent!=0&&indexInParent==vBatchLayout.getChildCount()-1){
                    scrollTo = indexInParent-1;
                }else {
                    scrollTo = indexInParent;
                }
                vScrollView.scrollTo(0, (int) vBatchLayout.getChildAt(scrollTo).getTop());
                vBatchLayout.removeViewAt(indexInParent);

            }
        };

    }


    @OnClick(R.id.btn_addBatch)
    public void onAddBatchClick() {

        DeliverGoodsItem deliverGoodsItem = new DeliverGoodsItem(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deliverGoodsItem.setLayoutParams(params);

        deliverGoodsItem.setOnSelBeginTimeClick(onSelBeginTimeClick);
        deliverGoodsItem.setOnSelEndTimeClick(onSelEndTimeClick);
        deliverGoodsItem.setOnSelDelClick(onSelDelClick);
        deliverGoodsItem.setEnabled(false);
        vBatchLayout.addView(deliverGoodsItem);

        deliverGoodsItem.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                vScrollView.smoothScrollTo(0, (int) v.getY());
                v.removeOnLayoutChangeListener(this);
            }
        });





    }

    @OnClick(R.id.btn_confirm)
    public void onConfirmClick() {

    }

}
