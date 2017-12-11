package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ConstructionBean;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.activity.ConstructionActivity;
import com.jyt.baseapp.view.dialog.DatePickerDialog;
import com.jyt.baseapp.view.widget.JumpItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * 查看施工
 * Created by v7 on 2017/11/4.
 */

public class ViewConstructFragment extends BaseFragment {
    @BindView(R.id.v_emptyMsg)
    RelativeLayout vEmptyMsg;
    @BindView(R.id.jt_time)
    JumpItem mJtTime;
    @BindView(R.id.rv_data)
    RecyclerView mRvData;
    @BindView(R.id.btn_push)
    Button mBtnPush;
    Unbinder unbinder;

    private ProgressBean mBean;
    private String FinishTime;
    private boolean isFinish;


    ProjectDetailModel projectDetailModel;
    private DatePickerDialog mDatePicker;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_construct;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initFace();
        initData();
        initListener();

    }

    private void init() {
        mBean = getArguments().getParcelable(IntentKey.PROGRESS);
        mDatePicker = new DatePickerDialog(getContext());

    }

    private void initData() {

    }

    private void initListener(){
       mJtTime.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (isFinish){
                   mDatePicker.show();
               }

           }
       });
        mDatePicker.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onSelected(String date) {
                mJtTime.setNext(false,date);
                FinishTime = date+" 0000";
            }
        });

        mBtnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(FinishTime)){
                    BaseUtil.makeText("请输入预计到货日期");
                    return;
                }

                projectDetailModel.setFinishTime(mBean.getProjectId(), FinishTime, new BeanCallback<BaseJson>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson response, int id) {
                        Log.e("@#","iii");
                        if (response.ret){
                            initFace();
                            ConstructionActivity activity= (ConstructionActivity) getActivity();
                            activity.notifyPage();
                        }
                    }
                });

            }
        });
    }

    private void initFace(){
        //初始化界面
        projectDetailModel.getConstructionData(mBean.getProjectId(), new BeanCallback<BaseJson<ConstructionBean>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<ConstructionBean> response, int id) {
                if (response.data.getFinishTime() != null) {
                    vEmptyMsg.setVisibility(View.GONE);
                    mJtTime.setNext(false,BaseUtil.getTime(response.data.getFinishTime()));
                    isFinish=false;
                } else {
                    vEmptyMsg.setVisibility(View.VISIBLE);
                    isFinish=true;
                }
            }
        });
    }

    public void setProjectDetailModel(ProjectDetailModel projectDetailModel) {
        this.projectDetailModel = projectDetailModel;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
