package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.ShopModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.widget.AppendItem;
import com.jyt.baseapp.view.widget.ProgressLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LinWei on 2017/11/2 13:54
 */
public class ShopProgressFragment extends BaseFragment {
    @BindView(R.id.pl_measure)
    ProgressLine mPlMeasure;
    @BindView(R.id.pl_offer)
    ProgressLine mPlOffer;
    @BindView(R.id.pl_approval)
    ProgressLine mPlApproval;
    @BindView(R.id.pl_confirm)
    ProgressLine mPlConfirm;
    @BindView(R.id.pl_stocking)
    ProgressLine mPlStocking;
    @BindView(R.id.pl_logistics)
    ProgressLine mPlLogistics;
    @BindView(R.id.pl_construction)
    ProgressLine mPlConstruction;
    @BindView(R.id.pl_complete)
    ProgressLine mPlComplete;
    @BindView(R.id.pl_settlement)
    ProgressLine mPlSettlement;
    Unbinder unbinder;
    private SearchBean mInfo;
    private ShopModel mShopModel;
    private AppendItem at_Measure;
    private AppendItem at_Measured;
    private AppendItem at_Design;
    private AppendItem at_Designing;
    private AppendItem at_Designed;
    private AppendItem at_Offer;
    private AppendItem at_Offered;
    private AppendItem at_Approval;
    private AppendItem at_Approvaled;
    private AppendItem at_Confirm;
    private AppendItem at_Confirmed;
    private AppendItem at_BudgetConfirm;
    private AppendItem at_Paper1;
    private AppendItem at_Paper2;
    private AppendItem at_Paper3;
    private AppendItem at_Paper4;
    private AppendItem at_Paper5;
    private AppendItem at_Paper6;
    private AppendItem at_Material1;
    private AppendItem at_Material2;
    private AppendItem at_Material3;
    private AppendItem at_Material4;
    private AppendItem at_Material5;
    private AppendItem at_Material6;
    private AppendItem at_Material7;
    private AppendItem at_Logistics1;
    private AppendItem at_Logistics2;
    private AppendItem at_Logistics3;
    private AppendItem at_Logistics4;
    private AppendItem at_Construction;
    private AppendItem at_Complete;
    private AppendItem at_Settlement1;
    private AppendItem at_Settlement2;
    private AppendItem at_Settlement3;
    private AppendItem at_Settlement4;
    private List<AppendItem> mAppendList;
    private HashMap<Integer,ProgressLine> mProgressMap;
    List<ProgressBean> progressBeanList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopprogress;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initMeasure();
        initOffer();
        initApproval();
        initConfirm();
        initStock();
        initLogistics();
        initConstruction();
        initComplete();
        initSettlement();
        initListener();
    }

    private void init() {
        mInfo = (SearchBean) getArguments().getSerializable("shopinfo");
        mShopModel = new ShopModel();
        mProgressMap = new HashMap<>();
        mAppendList = new ArrayList<>();

        at_Measure = new AppendItem(getActivity());
        at_Measured = new AppendItem(getActivity());
        mAppendList.add(at_Measure);
        mAppendList.add(at_Measured);
        mProgressMap.put(0,mPlMeasure);

        at_Design = new AppendItem(getActivity());
        at_Designing = new AppendItem(getActivity());
        at_Designed = new AppendItem(getActivity());
        at_Offer = new AppendItem(getActivity());
        at_Offered = new AppendItem(getActivity());
        mAppendList.add(at_Design);
        mAppendList.add(at_Designing);
        mAppendList.add(at_Designed);
        mAppendList.add(at_Offer);
        mAppendList.add(at_Offered);
        mProgressMap.put(100,mPlOffer);

        at_Approval = new AppendItem(getActivity());
        at_Approvaled = new AppendItem(getActivity());
        mAppendList.add(at_Approval);
        mAppendList.add(at_Approvaled);
        mProgressMap.put(200,mPlApproval);

        at_Confirm = new AppendItem(getActivity());
        at_Confirmed = new AppendItem(getActivity());
        mAppendList.add(at_Confirm);
        mAppendList.add(at_Confirmed);
        mProgressMap.put(300,mPlConfirm);

        at_BudgetConfirm = new AppendItem(getActivity());
        at_Paper1 = new AppendItem(getActivity());
        at_Paper2 = new AppendItem(getActivity());
        at_Paper3 = new AppendItem(getActivity());
        at_Paper4 = new AppendItem(getActivity());
        at_Paper5 = new AppendItem(getActivity());
        at_Paper6 = new AppendItem(getActivity());
        at_Material1 = new AppendItem(getActivity());
        at_Material2 = new AppendItem(getActivity());
        at_Material3 = new AppendItem(getActivity());
        at_Material4 = new AppendItem(getActivity());
        at_Material5 = new AppendItem(getActivity());
        at_Material6 = new AppendItem(getActivity());
        at_Material7 = new AppendItem(getActivity());
        mAppendList.add(at_BudgetConfirm);
        mAppendList.add(at_Paper1);
        mAppendList.add(at_Paper2);
        mAppendList.add(at_Paper3);
        mAppendList.add(at_Paper4);
        mAppendList.add(at_Paper5);
        mAppendList.add(at_Paper6);
        mAppendList.add(at_Material1);
        mAppendList.add(at_Material2);
        mAppendList.add(at_Material3);
        mAppendList.add(at_Material4);
        mAppendList.add(at_Material5);
        mAppendList.add(at_Material6);
        mAppendList.add(at_Material7);
        mProgressMap.put(400,mPlStocking);

        at_Logistics1 = new AppendItem(getActivity());
        at_Logistics2 = new AppendItem(getActivity());
        at_Logistics3 = new AppendItem(getActivity());
        at_Logistics4 = new AppendItem(getActivity());
        mAppendList.add(at_Logistics1);
        mAppendList.add(at_Logistics2);
        mAppendList.add(at_Logistics3);
        mAppendList.add(at_Logistics4);
        mProgressMap.put(500,mPlLogistics);

        at_Construction = new AppendItem(getActivity());
        mAppendList.add(at_Construction);
        mProgressMap.put(600,mPlConstruction);

        at_Complete = new AppendItem(getActivity());
        mAppendList.add(at_Complete);
        mProgressMap.put(700,mPlComplete);

        at_Settlement1 = new AppendItem(getActivity());
        at_Settlement2 = new AppendItem(getActivity());
        at_Settlement3 = new AppendItem(getActivity());
        at_Settlement4 = new AppendItem(getActivity());
        mAppendList.add(at_Settlement1);
        mAppendList.add(at_Settlement2);
        mAppendList.add(at_Settlement3);
        mAppendList.add(at_Settlement4);
        mProgressMap.put(800,mPlSettlement);

        mShopModel.getStationRole(new ShopModel.OngetStationRoleListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<Integer> data) {
                if (isSuccess){
                    at_Measure.setOperate(data.get(0));
                    at_Material6.setOperate(data.get(1));
                    at_Material7.setOperate(data.get(2));
                    at_Logistics1.setOperate(data.get(3));
                    at_Logistics2.setOperate(data.get(4));
                    at_Logistics3.setOperate(data.get(5));
                    at_Logistics4.setOperate(data.get(6));
                    at_Construction.setOperate(data.get(7));

                }
            }
        });

        mShopModel.getProjectProgress(mInfo.getProjectId(), new ShopModel.OnProgressResultListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<ProgressBean> shopBean) {
                if (isSuccess){
                    progressBeanList = shopBean;
                    setProgress(shopBean);
                }
            }
        });
    }

    //遍历全部的子节点，根据0/1权限设置是否可见完成勾选的img，当且仅当遇到第一个0（即当前进行到的位置）
    //需要设置可见，且将img改为白色圆，此节点所在的大点要改为蓝色背景白色字体，且内部子节点都改为白色字体
    private boolean isIndex;
    private void setProgress(List<ProgressBean> data){
        for (int i = 0; i < data.size(); i++) {
            mAppendList.get(i).setProgressBean(data.get(i));//加载对象
            if (!"0".equals(data.get(i).getIsfinish())){
               //完成
                mAppendList.get(i).setComplete(true);
            }else {
                //未完成
                mAppendList.get(i).setComplete(false);
                if (!isIndex){
                    for (Map.Entry<Integer,ProgressLine> entry : mProgressMap.entrySet()) {
                        int index=entry.getKey();
                        if (data.get(i).getSpeedUpCode()>index){
                            //完成
                            entry.getValue().setFinishStation(true);
                        }else if (data.get(i).getSpeedUpCode()<index){
                            //未完成
                            entry.getValue().setFinishStation(false);
                        }else {
                            //当前进度
                            entry.getValue().setCurrent();
                        }
                    }
                    //用于标记进度目前到达的位置
                    mAppendList.get(i).setEstimate(true);
                    mAppendList.get(i).setCurrent();
                    isIndex=true;
                }

            }
            //---------------------------------------------------
            Log.e("@#",""+i+" == "+data.get(i).getFinishTime());
            if (data.get(i).getFinishTime()!=null){
                mAppendList.get(i).setTv_time(BaseUtil.getTime(data.get(i).getFinishTime()));
            }else {
//                Log.e("@#","ASD");
            }

        }

    }

    private void initMeasure() {
        at_Measure.setTv_msg("测量中");
        at_Measure.setEditor();
        at_Measure.setState(2);//操作后不可见
        at_Measured.setTv_msg("测量完毕");
        at_Measured.setEditor();
        at_Measured.setState(1);//操作后可见
        mPlMeasure.addAppendItem(at_Measure);
        mPlMeasure.addAppendItem(at_Measured);
    }

    private void initOffer(){
        at_Design.setTv_msg("待设计");
        at_Design.setNext(false);
        at_Designing.setTv_msg("设计中");
        at_Designing.setNext(false);
        at_Designed.setTv_msg("设计完毕");
        at_Designed.setEditor();
        at_Designed.setState(1);//操作后可见
        at_Offer.setTv_msg("待报价");
        at_Offer.setNext(false);
        at_Offered.setTv_msg("报价完毕");
        at_Offered.setNext(false);

        mPlOffer.addAppendItem(at_Design);
        mPlOffer.addAppendItem(at_Designing);
        mPlOffer.addAppendItem(at_Designed);
        mPlOffer.addAppendItem(at_Offer);
        mPlOffer.addAppendItem(at_Offered);
    }

    private void initApproval(){
        at_Approval.setTv_msg("待客户审批");
        at_Approval.setNext(false);
        at_Approvaled.setTv_msg("客户已审批");
        at_Approvaled.setEditor();
        at_Approvaled.setState(1);//操作后可见

        mPlApproval.addAppendItem(at_Approval);
        mPlApproval.addAppendItem(at_Approvaled);
    }

    private void initConfirm(){
        at_Confirm.setTv_msg("待店主确认");
        at_Confirm.setEditor();
        at_Confirm.setState(1);//操作后可见
        at_Confirmed.setTv_msg("店主已确认");
        at_Confirmed.setEditor();
        at_Confirmed.setState(1);//操作后可见

        mPlConfirm.addAppendItem(at_Confirm);
        mPlConfirm.addAppendItem(at_Confirmed);
    }

    private void initStock(){
        at_BudgetConfirm.setTv_msg("预算确认下单");
        at_BudgetConfirm.setNext(false);
        at_Paper1.setTv_msg("待下图纸");
        at_Paper1.setNext(false);
        at_Paper2.setTv_msg("图纸下单");
        at_Paper2.setNext(false);
        at_Paper3.setTv_msg("待审图纸");
        at_Paper3.setEditor();
        at_Paper3.setState(1);//操作后可见
        at_Paper4.setTv_msg("已审图纸");
        at_Paper4.setEditor();
        at_Paper4.setState(1);//操作后可见
        at_Paper5.setTv_msg("待预算复核图纸");
        at_Paper5.setNext(false);
        at_Paper6.setTv_msg("预算已复核纸");
        at_Paper6.setNext(false);
        at_Material1.setTv_msg("待⽣产招牌");
        at_Material1.setEditor();
        at_Material1.setState(1);//操作后可见
        at_Material2.setTv_msg("待下材料单");
        at_Material2.setNext(false);
        at_Material3.setTv_msg("待审材料单");
        at_Material3.setEditor();
        at_Material3.setState(1);//操作后可见
        at_Material4.setTv_msg("已审材料单");
        at_Material4.setEditor();
        at_Material4.setState(1);//操作后可见
        at_Material5.setTv_msg("待备料");
        at_Material5.setNext(false);
        at_Material6.setTv_msg("钢挂已完成");
        at_Material6.setEditor();
        at_Material6.setState(2);
        at_Material7.setTv_msg("所有材料已打包");
        at_Material7.setEditor();
        at_Material7.setState(1);//操作后可见

        mPlStocking.addAppendItem(at_BudgetConfirm);
        mPlStocking.addAppendItem(at_Paper1);
        mPlStocking.addAppendItem(at_Paper2);
        mPlStocking.addAppendItem(at_Paper3);
        mPlStocking.addAppendItem(at_Paper4);
        mPlStocking.addAppendItem(at_Paper5);
        mPlStocking.addAppendItem(at_Paper6);
        mPlStocking.addAppendItem(at_Material1);
        mPlStocking.addAppendItem(at_Material2);
        mPlStocking.addAppendItem(at_Material3);
        mPlStocking.addAppendItem(at_Material4);
        mPlStocking.addAppendItem(at_Material5);
        mPlStocking.addAppendItem(at_Material6);
        mPlStocking.addAppendItem(at_Material7);
    }

    private void initLogistics(){
        at_Logistics1.setTv_msg("待发货");
        at_Logistics1.setEditor();
        at_Logistics1.setState(1);//操作后可见
        at_Logistics2.setTv_msg("已发货");
        at_Logistics2.setEditor();
        at_Logistics2.setState(1);//操作后可见
        at_Logistics3.setTv_msg("货到待施工");
        at_Logistics3.setEditor();
        at_Logistics3.setState(1);//操作后可见
        at_Logistics4.setTv_msg("安排施工人员完毕");
        at_Logistics4.setEditor();
        at_Logistics4.setState(1);//操作后可见
        mPlLogistics.addAppendItem(at_Logistics1);
        mPlLogistics.addAppendItem(at_Logistics2);
        mPlLogistics.addAppendItem(at_Logistics3);
        mPlLogistics.addAppendItem(at_Logistics4);
    }

    private void initConstruction(){
        at_Construction.setTv_msg("施工中");
        at_Construction.setEditor();
        at_Construction.setState(1);//操作后可见
        mPlConstruction.addAppendItem(at_Construction);
    }

    private void initComplete(){
        at_Complete.setTv_msg("施工完毕");
        at_Complete.setEditor();
        at_Complete.setState(1);//操作后可见
        mPlComplete.addAppendItem(at_Complete);
    }

    private void initSettlement(){
        at_Settlement1.setTv_msg("预算审核照⽚并已回访");
        at_Settlement1.setNext(false);
        at_Settlement2.setTv_msg("待寄报销资料");
        at_Settlement2.setNext(false);
        at_Settlement3.setTv_msg("已寄报销资料");
        at_Settlement3.setNext(false);
        at_Settlement4.setTv_msg("已收款");
        at_Settlement4.setNext(false);
        mPlSettlement.addAppendItem(at_Settlement1);
        mPlSettlement.addAppendItem(at_Settlement2);
        mPlSettlement.addAppendItem(at_Settlement3);
        mPlSettlement.addAppendItem(at_Settlement4);
    }

    private void initListener(){
        at_Measure.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                Log.e("@#",bean.getPermissionState()+"");
                if ("0".equals(bean.getIsfinish())) {
                    IntentHelper.openUploadImagesActivityForResult(getContext(), bean);
                }
            }
        });
        AppendItem.OnAppendOnclickListener browserSimpleContent = new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                L.e("click ");
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
               if (beforeFinish!=null){
                   IntentHelper.openCommonProgressActivity(getContext(), bean,beforeFinish);
               }


            }
        };
        at_Measured.setOnAppendOnclickListener(browserSimpleContent);
        at_Designed.setOnAppendOnclickListener(browserSimpleContent);
        at_Approvaled.setOnAppendOnclickListener(browserSimpleContent);
        at_Confirm.setOnAppendOnclickListener(browserSimpleContent);
        at_Confirmed.setOnAppendOnclickListener(browserSimpleContent);
        at_Paper3.setOnAppendOnclickListener(browserSimpleContent);
        at_Paper4.setOnAppendOnclickListener(browserSimpleContent);
        at_Material1.setOnAppendOnclickListener(browserSimpleContent);
        at_Material3.setOnAppendOnclickListener(browserSimpleContent);
        at_Material4.setOnAppendOnclickListener(browserSimpleContent);
        at_Complete.setOnAppendOnclickListener(browserSimpleContent);


        at_Logistics1.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                IntentHelper.openWaitSendActivity(getContext(),bean);
            }
        });
        at_Logistics2.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                IntentHelper.openSentActivity(getContext(),bean);
            }
        });

        at_Material6.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
//                if (beforeFinish!=null) {
                    IntentHelper.openFinishSteelHookActivity(getContext(), bean);
//                }
            }
        });
    }




private ProgressBean beforeItemIsFinish(ProgressBean current){
    for (int i = 0; i < progressBeanList.size();i++){
        if ( progressBeanList.get(i).getSpeedCode()==current.getSpeedCode()){
            if (i!=0 && "0".equals(progressBeanList.get(i-1).getIsfinish())){
                return null;
            }
            return  progressBeanList.get(i-1);
        }
    }
    return null;
}



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
