package com.jyt.baseapp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.util.T;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by chenweiqi on 2017/11/6.
 */

public class FinishSteelHookActivity extends BaseActivity {
    @BindView(R.id.btn_submit)
    TextView btnSubmit;

    ProjectDetailModel projectDetailModel;
    ProgressBean progressBean;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_finish_steel_hook;
    }

    @Override
    protected View getContentView() {
        return null;
    }




    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        new AlertDialog.Builder(getContext()).setMessage("是否确认钢挂已完成？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {
                projectDetailModel.clickToNextStep(progressBean.getSpeedId(), progressBean.getProjectId(), progressBean.getSpeedCode()+"", new BeanCallback<BaseJson>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        T.showShort(getContext(),e.getMessage());
                    }

                    @Override
                    public void onResponse(BaseJson response, int id) {
                        if (response.ret){
                            dialog.dismiss();
                            finish();
                        }else{
                            T.showShort(getContext(),response.forUser);
                        }

                    }
                });
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
