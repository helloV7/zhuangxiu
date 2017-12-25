package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.Construction;
import com.jyt.baseapp.bean.Manager;
import com.jyt.baseapp.bean.Monitor;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentRequestCode;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.model.impl.ProjectDetailModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.FreeDialog;
import com.jyt.baseapp.view.widget.LabelAndTextItem;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
    private EditText EtInput;
    private TextView TvSubmit;
    private TextView TvCancel;

    public static final int TYPE_PREPARE = 1;
    public static final int TYPE_PREPARE_FINISH = 2;

    private int type;
    ProgressBean progressBean;
    private boolean enableView = true;
    private String str_monitor;
    private FreeDialog mDialog;
    ProjectDetailModel projectDetailModel;

    Manager manager;
    Monitor monitor;

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

        Tuple tuple = IntentHelper.ConstructCompleteActivityGetPara(getIntent());
        type = (int) tuple.getItem1();
        progressBean = (ProgressBean) tuple.getItem2();
        projectDetailModel = new ProjectDetailModelImpl();
        projectDetailModel.onCreate(getContext());

        switch (type) {
            case TYPE_PREPARE:
                setTextTitle("货到待施工");
                enableView = true;
                break;
            case TYPE_PREPARE_FINISH:
                setTextTitle("安排施工人员完毕");
                enableView = false;

                viewConstrictionComplete();
                break;
        }
        //只有查看权限
        if (1!=progressBean.getPermissionState()){
            textConfirm.setVisibility(View.GONE);
        }
        //已操作完毕
        if (!"0".equals(progressBean.getIsfinish())){
            textConfirm.setVisibility(View.GONE);
        }
        setEnable(enableView);
        mDialog = new FreeDialog(this, R.layout.dialog_input);
        EtInput = (EditText) mDialog.getView().findViewById(R.id.et_input);
        TvSubmit = (TextView) mDialog.getView().findViewById(R.id.tv_submit);
        TvCancel = (TextView) mDialog.getView().findViewById(R.id.tv_cancel);
        TvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = EtInput.getText().toString().trim();
                if (!TextUtils.isEmpty(value)) {
                    str_monitor = value;
                    LTMonitor.isShowRightArrow(false);
                    LTMonitor.setValueText(value);

                }
                mDialog.dismiss();

            }
        });
        EtInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        TvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EtInput.setText("");
                mDialog.dismiss();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        projectDetailModel.onDestroy();
    }

    @OnClick(R.id.LT_actualTime)
    public void onActualTimeClick(View v) {
        LTViewOpenDatePickerDialog(v);
    }

    @OnClick(R.id.LT_estimateInShopTime)
    public void onEstimateInShopTimeClick(View v) {
        LTViewOpenDatePickerDialog(v);
    }

    @OnClick(R.id.LT_estimateTime)
    public void onEstimateTime(View v) {
        LTViewOpenDatePickerDialog(v);
    }

    @OnClick(R.id.LT_worker)
    public void onSelWorkerClick() {
        IntentHelper.openSelSingleManagerActivityForResult(this);
    }

    @OnClick(R.id.LT_monitor)
    public void onSelMonitorClick() {
        if (manager != null)
            IntentHelper.openSelSingleMonitorActivityForResult(this, manager.getUserId());
//        mDialog.show();
    }


    public void LTViewOpenDatePickerDialog(final View v) {
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
        if (requestCode == IntentRequestCode.CODE_SEL_SINGLE_MONITOR && resultCode == RESULT_OK) {
            Tuple tuple = IntentHelper.SelSinglePersonGetResult(data);
            monitor = (Monitor) tuple.getItem1();
            LTMonitor.setValueText(monitor.getMonitorName());
        } else if (requestCode == IntentRequestCode.CODE_SEL_SINGLE_WORKER && resultCode == RESULT_OK) {
            Tuple tuple = IntentHelper.SelSinglePersonGetResult(data);
            manager = (Manager) tuple.getItem1();
            LTWorker.setValueText(manager.getNickName());


        }
    }

    private void setEnable(boolean enable) {
        LTEstimateTime.setEnabled(enable);
        LTActualTime.setEnabled(enable);
        LTEstimateInShopTime.setEnabled(enable);
        LTWorker.setEnabled(enable);
        LTMonitor.setEnabled(enable);
    }

    @OnClick(R.id.text_confirm)
    public void onViewClicked() {
        if (type ==TYPE_PREPARE ) {
            if (TextUtils.isEmpty(LTActualTime.getValueText())) {
                return;
            }
            if (TextUtils.isEmpty(LTEstimateInShopTime.getValueText())) {
                return;
            }
            if (TextUtils.isEmpty(LTEstimateTime.getValueText())) {
                return;
            }
            if (TextUtils.isEmpty(LTMonitor.getValueText())) {
                return;
            }
            if (TextUtils.isEmpty(LTWorker.getValueText())) {
                return;
            }
            submit();
        }else {
            clickToNext();
        }
    }

    private void submit(){
        projectDetailModel.addConstriction(LTEstimateTime.getValueText(), LTActualTime.getValueText(), LTEstimateInShopTime.getValueText(), manager.getUserId(), monitor.getMonitorId(), progressBean.getProjectId(), new BeanCallback<BaseJson>() {
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

    private void clickToNext(){
        projectDetailModel.clickToNextStep(progressBean.getSpeedId(), progressBean.getProjectId(), progressBean.getSpeedCode() +"", new BeanCallback<BaseJson>() {
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

    private void viewConstrictionComplete(){
        projectDetailModel.getConstrictionComplete(progressBean.getProjectId(), new BeanCallback<BaseJson<List<Construction>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson<List<Construction>> response, int id) {
                if (response.ret){
                    LTEstimateTime.setValueText(response.data.get(0).getConstructionTime().split(" ")[0]);
                    LTActualTime.setValueText(response.data.get(0).getConstructionArr().split(" ")[0]);
                    LTEstimateInShopTime.setValueText(response.data.get(0).getConstructionStart().split(" ")[0]);
                    LTWorker.setValueText(response.data.get(0).getNickName());
                    LTMonitor.setValueText(response.data.get(0).getMonitorName());
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });
    }
}
