package com.jyt.baseapp.view.activity;


import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.DeliverGoods;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.model.impl.ProjectDetailModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.DeliverGoodsItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    private ProgressBean progressBean;
    private boolean enableView = false;

    private DeliverGoodsItem.OnSelBeginTimeClick onSelBeginTimeClick;
    private DeliverGoodsItem.OnSelEndTimeClick onSelEndTimeClick;
    private DeliverGoodsItem.OnSelDelClick onSelDelClick;

    ProjectDetailModel projectModel;

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

        Tuple tuple = IntentHelper.DeliverGoodsActivityGetPara(getIntent());
        type = (int) tuple.getItem1();
        progressBean = (ProgressBean) tuple.getItem2();

        projectModel = new ProjectDetailModelImpl();
        projectModel.onCreate(getContext());

        switch (type){
            case TYPE_WAITE_SEND:
                setTextTitle("待发货");
                enableView = true;
                break;
            case TYPE_SENT:
                setTextTitle("已发货");
                enableView = false;
                btnAddBatch.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.GONE);
                getLogisticList();
                break;
        }

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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        projectModel.onDestroy();
    }

    @OnClick(R.id.btn_addBatch)
    public void onAddBatchClick() {

        DeliverGoodsItem deliverGoodsItem = new DeliverGoodsItem(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        deliverGoodsItem.setLayoutParams(params);

        deliverGoodsItem.setOnSelBeginTimeClick(onSelBeginTimeClick);
        deliverGoodsItem.setOnSelEndTimeClick(onSelEndTimeClick);
        deliverGoodsItem.setOnSelDelClick(onSelDelClick);
        deliverGoodsItem.setEnabled(enableView);
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
        if (type == TYPE_WAITE_SEND){
            addInfo();
        }else if (type==TYPE_SENT){
            toNext();
        }
    }

    private void addInfo(){
        List logistic = new ArrayList();

        for (int i=0;i<vBatchLayout.getChildCount();i++){

            DeliverGoods deliverGoods = new DeliverGoods();

            DeliverGoodsItem deliverGoodsItem = (DeliverGoodsItem) vBatchLayout.getChildAt(i);

            deliverGoods.setLogisticsName(deliverGoodsItem.getGoodsName());

            deliverGoods.setLogisticsTime(deliverGoodsItem.getBeginTime()+" 00:00:00");

            deliverGoods.setArriveTime(deliverGoodsItem.getEndTime()+" 00:00:00");
            deliverGoods.setLogisticsCompany(deliverGoodsItem.getCompanyName());
            deliverGoods.setLogisticsTel(deliverGoodsItem.getCompanyTel());
            deliverGoods.setLogisticsNo(deliverGoodsItem.getOddNumbers());

            logistic.add(deliverGoods);
        }
        projectModel.addDeliverGoodsInfo(progressBean.getProjectId(), logistic, new BeanCallback<BaseJson>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson response, int id) {
                if (response.ret){
                    T.showShort(getContext(),"操作成功");
                    finish();
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });

    }

    private void toNext(){
        projectModel.clickToNextStep(progressBean.getSpeedId(), progressBean.getProjectId(), progressBean.getSpeedCode()+"", new BeanCallback<BaseJson>(getContext(),true) {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson response, int id) {
                if (response.ret){
                    T.showShort(getContext(),"操作成功");
                    finish();
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });
    }



    private void getLogisticList(){
        projectModel.getAllDeliverGoodsInfo(progressBean.getProjectId(), new BeanCallback<BaseJson<List<DeliverGoods>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson<List<DeliverGoods>> response, int id) {
                if (response.ret){
                    addViewByList(response.data);
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });

    }


    private void addViewByList(List<DeliverGoods> deliverGoodses){
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (DeliverGoods deliverGoods :
                deliverGoodses) {


            DeliverGoodsItem deliverGoodsItem = new DeliverGoodsItem(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            deliverGoodsItem.setLayoutParams(params);

            deliverGoodsItem.setOnSelBeginTimeClick(onSelBeginTimeClick);
            deliverGoodsItem.setOnSelEndTimeClick(onSelEndTimeClick);
            deliverGoodsItem.setOnSelDelClick(onSelDelClick);
            deliverGoodsItem.setEnabled(enableView);


            deliverGoodsItem.setGoodsName(deliverGoods.getLogisticsName());
            deliverGoodsItem.setBeginTime(deliverGoods.getLogisticsTime().split(" ")[0]   );
            deliverGoodsItem.setEndTime(deliverGoods.getArriveTime().split(" ")[0]);
            deliverGoodsItem.setCompanyName(deliverGoods.getLogisticsCompany());
            deliverGoodsItem.setCompanyTel(deliverGoods.getLogisticsTel());
            deliverGoodsItem.setOddNumbers(deliverGoods.getLogisticsNo());

            vBatchLayout.addView(deliverGoodsItem);



        }

//        vScrollView.smoothScrollTo(0, (int) vBatchLayout.getChildAt(vBatchLayout.getChildCount()-1).getY());

    }
}
