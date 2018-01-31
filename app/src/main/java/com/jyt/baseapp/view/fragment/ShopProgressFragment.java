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
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.ShopModel;
import com.jyt.baseapp.util.BaseUtil;
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
    int current = -1;
    private boolean isLink;

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
        initData();
//        initShop();
        initListener();
    }

    private void init() {
        mInfo = (SearchBean) getArguments().getSerializable(IntentKey.SHOPINFO);
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

    }

    //遍历全部的子节点，根据0/1权限设置是否可见完成勾选的img，当且仅当遇到第一个0（即当前进行到的位置）
    //需要设置可见，且将img改为白色圆，此节点所在的大点要改为蓝色背景白色字体，且内部子节点都改为白色字体
    private boolean isIndex;
    private void setProgress(List<ProgressBean> data){
        for (int i=0 ; i<data.size();i++){
            mAppendList.get(i).setProgressBean(data.get(i));//加载对象
        }

        for (int i = 0; i < data.size(); i++) {
            String time=data.get(i).getFinishTime();
            if (!"0".equals(data.get(i).getIsfinish())){
               //完成
                mAppendList.get(i).setComplete(true,time);
            }else {
                //未完成
                mAppendList.get(i).setComplete(false,time);
                if (!isIndex){
                    //只触发一次，即当前进度点触发

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
                            entry.getValue().setCurrent(data.get(i).getSpeedCode());
                            current=index;
                        }
                    }
                    for (Map.Entry<Integer,ProgressLine> entry : mProgressMap.entrySet()) {
                        int index=entry.getKey();
                        if (current!=index){
                            entry.getValue().setCurrent(false);
                        }
                    }
                    //用于标记进度目前到达的位置
                    mAppendList.get(i).setCurrent(time);
                    isIndex=true;
                }

            }
            //---------------------------------------------------
            if (data.get(i).getFinishTime()!=null){
                mAppendList.get(i).setTv_time(BaseUtil.getTime(data.get(i).getFinishTime().split(" ")[0]));
                //版本一
//                mAppendList.get(i).setEstimate(true);
            }else {
                //版本一
//                mAppendList.get(i).setEstimate(false);
            }

        }

    }

    private void initData(){


        //操作权限赋予
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

                    List<AppendItem> appendItemList1 =new ArrayList<AppendItem>();
                    appendItemList1.add(at_Measure);
                    appendItemList1.add(at_Material6);
                    appendItemList1.add(at_Material7);
                    appendItemList1.add(at_Logistics1);
                    appendItemList1.add(at_Logistics2);
                    appendItemList1.add(at_Logistics3);
                    appendItemList1.add(at_Logistics4);
                    appendItemList1.add(at_Construction);

                    for (int i = 0; i < appendItemList1.size(); i++) {
                        if (data.get(i)==0){
                            appendItemList1.get(i).setImgVisible(false);
                        }
                    }


                    List<AppendItem> appendItemList2 =new ArrayList<AppendItem>();
                    appendItemList2.add(at_Measured);
                    appendItemList2.add(at_Designed);
                    appendItemList2.add(at_Approvaled);
                    appendItemList2.add(at_Confirm);
                    appendItemList2.add(at_Confirmed);
                    appendItemList2.add(at_Paper3);
                    appendItemList2.add(at_Paper4);
                    appendItemList2.add(at_Material1);
                    appendItemList2.add(at_Material3);
                    appendItemList2.add(at_Material4);
                    appendItemList2.add(at_Complete);
                    for (int i = 0; i < appendItemList2.size(); i++) {
                        if (data.get(i+8)==0){
                            appendItemList2.get(i).setImgVisible(false);
                        }
                    }

                    at_Measured.setOperate(data.get(8));
                    at_Designed.setOperate(data.get(9));
                    at_Approvaled.setOperate(data.get(10));
                    at_Confirm.setOperate(data.get(11));
                    at_Confirmed.setOperate(data.get(12));
                    at_Paper3.setOperate(data.get(13));
                    at_Paper4.setOperate(data.get(14));
                    at_Material1.setOperate(data.get(15));
                    at_Material3.setOperate(data.get(16));
                    at_Material4.setOperate(data.get(17));
                    at_Complete.setOperate(data.get(18));


                    mShopModel.getProjectProgress(mInfo.getProjectId(), new ShopModel.OnProgressResultListener() {
                        @Override
                        public void Result(boolean isSuccess, Exception e, List<ProgressBean> shopBean) {
                            if (isSuccess){
                                isLink = true;
                                progressBeanList = shopBean;
                                setProgress(shopBean);
                            }
                        }
                    });
                }else {

                }
            }
        });


    }

    private void initMeasure() {
        at_Measure.setTv_msg("测量中");
        at_Measure.setEditorImg();
        at_Measure.setCanEstimate(true);
        at_Measure.setState(2);//操作后不可见
        at_Measured.setTv_msg("测量完毕");
        at_Measured.setNext(true);
        at_Measured.setState(1);//操作后可见
        mPlMeasure.addAppendItem(at_Measure);
        mPlMeasure.addAppendItem(at_Measured);


    }

    private void initOffer(){
        at_Design.setTv_msg("待设计");
        at_Design.setNext(false);
        at_Designing.setTv_msg("设计中");
        at_Designing.setCanEstimate(true);
        at_Designing.setNext(false);
        at_Designed.setTv_msg("设计完毕");
        at_Designed.setEditorImg();
        at_Designed.setState(1);//操作后可见
        at_Offer.setTv_msg("待报价");
        at_Offer.setCanEstimate(true);
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
        at_Approvaled.setNext(true);
        at_Approvaled.setState(1);//操作后可见

        mPlApproval.addAppendItem(at_Approval);
        mPlApproval.addAppendItem(at_Approvaled);


    }

    private void initConfirm(){
        at_Confirm.setTv_msg("待店主确认");
        at_Confirm.setNext(true);
        at_Confirm.setState(1);//操作后可见
        at_Confirmed.setTv_msg("店主已确认");
        at_Confirmed.setCanEstimate(true);
        at_Confirmed.setNext(true);
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
        at_Paper3.setTv_msg("组长待审图纸");
        at_Paper3.setNext(true);
        at_Paper3.setState(1);//操作后可见
        at_Paper4.setTv_msg("已审图纸");
        at_Paper4.setNext(true);
        at_Paper4.setState(1);//操作后可见
        at_Paper5.setTv_msg("待预算复核图纸");
        at_Paper5.setNext(false);
        at_Paper6.setTv_msg("预算已复核纸");
        at_Paper6.setNext(false);
        at_Material1.setTv_msg("待⽣产招牌");
        at_Material1.setCanEstimate(true);
        at_Material1.setNext(true);
        at_Material1.setState(1);//操作后可见
        at_Material2.setTv_msg("待下材料单");
        at_Material2.setNext(false);
        at_Material3.setTv_msg("待审材料单");
        at_Material3.setNext(true);
        at_Material3.setState(1);//操作后可见
        at_Material4.setTv_msg("已审材料单");
        at_Material4.setNext(true);
        at_Material4.setState(1);//操作后可见
        at_Material5.setTv_msg("待备料");
        at_Material5.setCanEstimate(true);
        at_Material5.setNext(false);
        at_Material6.setTv_msg("钢挂已完成");
        at_Material6.setEditorImg();
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
        at_Logistics1.setCanEstimate(true);
        at_Logistics1.setReSet(true);
        at_Logistics1.setEditor();
        at_Logistics1.setState(1);//操作后可见
        at_Logistics2.setTv_msg("已发货");
        at_Logistics2.setState(1);//操作后可见
        at_Logistics2.setEditor();
        at_Logistics3.setTv_msg("货到待施工");
        at_Logistics3.setCanEstimate(true);
        at_Logistics3.setEditor();
        at_Logistics3.setState(2);//操作后不可见
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
        at_Complete.setNext(true);
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

    private void initShop(){

        //店主 品牌方
        mShopModel.getProjectProgress(mInfo.getProjectId(), new ShopModel.OnProgressResultListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<ProgressBean> shopBean) {
                if (isSuccess){
                    isLink = true;
                    progressBeanList = shopBean;
                    setProgress(shopBean);
                }else {
                    for (Map.Entry<Integer,ProgressLine> entry : mProgressMap.entrySet()) {
                        entry.getValue().setNextVisible(false);
                    }
                }
            }
        });

        mPlMeasure.setSb(true);//店主 品牌方
        mPlOffer.setSb(true);//店主 品牌方
        mPlApproval.setSb(true);//店主 品牌方
        mPlConfirm.setSb(true);//店主 品牌方
        mPlStocking.setSb(false);
        mPlLogistics.setSb(false);
        mPlConstruction.setSb(true);//店主 品牌方
        mPlComplete.setSb(true);//店主 品牌方
        mPlSettlement.setSb(false);
        mPlMeasure.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                //店主
//                if ("0".equals(progressBeanList.get(0).getIsfinish())){
//                    //未完成-进入测量中界面
//                    IntentHelper.openUploadImagesActivityForResult(getContext(), progressBeanList.get(0),20);
//                }else {
//                    //已完成-进入测量完毕界面
//                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(1), progressBeanList.get(0));
//                }
                //品牌方
                if ("0".equals(progressBeanList.get(0).getIsfinish())){
                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(1), progressBeanList.get(0));
                }
            }
        });

        mPlOffer.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                if ("0".equals(progressBeanList.get(4).getIsfinish())){
                    return;
                }
                IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(4), progressBeanList.get(3));
            }
        });

        mPlApproval.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                if ("0".equals(progressBeanList.get(8).getIsfinish())){
                    return;
                }
                IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(8), progressBeanList.get(7));
            }
        });

        mPlConfirm.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                if ("0".equals(progressBeanList.get(9).getIsfinish())){
                    return;
                }
                if ("0".equals(progressBeanList.get(10).getIsfinish())){
                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(9), null,false);
                }else {
                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(9), null,true);
                }

            }
        });

        mPlConstruction.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                if ("0".equals(progressBeanList.get(29).getIsfinish())){
                    return;
                }
                IntentHelper.openConstructionActivity(getContext(),progressBeanList.get(29));
            }
        });

        mPlComplete.setOnPlClickListener(new ProgressLine.OnClickListener() {
            @Override
            public void onClick() {
                if (!isLink){
                    return;
                }
                if ("0".equals(progressBeanList.get(30).getIsfinish())){
                    return;
                }
                IntentHelper.openCompleteCommonProgressActivity(getContext(),progressBeanList.get(30), progressBeanList.get(29));
            }
        });



    }

    private void initListener(){
        at_Measure.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                if ("0".equals(bean.getIsfinish())) {
                    IntentHelper.openUploadImagesActivityForResult(getContext(), bean,20);
                }
            }
        });

        at_Material7.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if ( beforeFinish!=null ) {
                    if ("0".equals(bean.getIsfinish())){
                        IntentHelper.openUploadImagesActivityForResult(getContext(), bean,9);
                    }else {
                        IntentHelper.openCommonProgressActivity(getContext(),bean, null);
                    }

                }

            }
        });

        at_Construction.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if( beforeFinish!=null){
                    IntentHelper.openConstructionActivity(getContext(),bean);
                }

            }
        });

        AppendItem.OnAppendOnclickListener browserSimpleContent = new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null ){
                   IntentHelper.openCommonProgressActivity(getContext(),bean, null);
               }


            }
        };

        AppendItem.OnAppendOnclickListener beforeSimpleContent = new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null){
                    IntentHelper.openCommonProgressActivity(getContext(),bean, beforeFinish);
                }


            }
        };

        AppendItem.OnAppendOnclickListener PaperSimpleContent = new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null){
                    IntentHelper.openCommonProgressActivity(getContext(),bean, progressBeanList.get(12));
                }
            }
        };

        AppendItem.OnAppendOnclickListener DownloadSimpleContent = new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null){
                    IntentHelper.openCommonProgressActivity(getContext(),bean, progressBeanList.get(19));
                }
            }
        };

        at_Designed.setOnAppendOnclickListener(beforeSimpleContent);
        at_Measured.setOnAppendOnclickListener(beforeSimpleContent);

        at_Approvaled.setOnAppendOnclickListener(beforeSimpleContent);

        at_Confirm.setOnAppendOnclickListener(browserSimpleContent);
        at_Confirmed.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish==null || 0==bean.getPermissionState()){
                    return;
                }
                if ("0".equals(bean.getIsfinish())){
                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(9), null,false);
                }else {
                    IntentHelper.openCommonProgressActivity(getContext(),progressBeanList.get(9), null,true);
                }
            }
        });
        at_Complete.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null && 0!=bean.getPermissionState()){
                    IntentHelper.openCompleteCommonProgressActivity(getContext(),bean, beforeFinish);
                }
            }
        });




        at_Paper3.setOnAppendOnclickListener(PaperSimpleContent);
        at_Paper4.setOnAppendOnclickListener(PaperSimpleContent);
        at_Material1.setOnAppendOnclickListener(PaperSimpleContent);


        at_Material3.setOnAppendOnclickListener(DownloadSimpleContent);
        at_Material4.setOnAppendOnclickListener(DownloadSimpleContent);


        at_Logistics1.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null) {
                    if ( 0!=bean.getPermissionState()) {
                        IntentHelper.openWaitSendActivity(getContext(), bean);
                    }
                }
            }
        });
        at_Logistics2.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null) {
                    if ( 0!=bean.getPermissionState()) {
                        IntentHelper.openSentActivity(getContext(), bean);
                    }
                }
            }
        });

        at_Logistics3.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null) {
                    if ( "0".equals(bean.getIsfinish())) {
                        IntentHelper.openWaitingConstructActivity(getContext(), bean);
                    }
                }
            }
        });


        at_Logistics4.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null ) {
                    IntentHelper.openPrepareConstructCompleteActivity(getContext(), bean);
                }
            }

        });


        at_Material6.setOnAppendOnclickListener(new AppendItem.OnAppendOnclickListener() {
            @Override
            public void onClick(ProgressBean bean) {
                if (!isLink){
                    return;
                }
                if (0==bean.getPermissionState()){
                    return;
                }
                ProgressBean beforeFinish = beforeItemIsFinish(bean);
                if (beforeFinish!=null && "0".equals(bean.getIsfinish())) {
                    IntentHelper.openFinishSteelHookActivity(getContext(), bean);
                }
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
    //拦截oncreate之后的第一次启动
    private boolean isPe;
    @Override
    public void onResume() {
        super.onResume();
        if (!isPe){
            isPe=true;
            return;
        }
        Log.e("@#","stare update");
        mShopModel.getProjectProgress(mInfo.getProjectId(), new ShopModel.OnProgressResultListener() {
            @Override
            public void Result(boolean isSuccess, Exception e, List<ProgressBean> shopBean) {
                if (isSuccess){
                    isIndex=false;
                    progressBeanList = shopBean;
                    setProgress(progressBeanList);
                }
            }
        });
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
