package com.jyt.baseapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ShowImageAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.api.PutObjectSamples;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.OssBean;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.ProgressFileBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.helper.IntentRequestCode;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.model.ManeuverModel;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.LoadingDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

import static android.app.Activity.RESULT_OK;

/**
 * 施工中
 * Created by chenweiqi on 2017/11/6.
 */

public class UnderConstructionFragment extends BaseFragment {
    @BindView(R.id.v_rcv)
    RecyclerView vRcv;
    @BindView(R.id.text_imageCount)
    TextView textImageCount;
    @BindView(R.id.btn_submit)
    LinearLayout btnSubmit;
    @BindView(R.id.v_setEndTime)
    RelativeLayout vSetEndTime;
    @BindView(R.id.v_uploadImg)
    FrameLayout vUploadImg;
    @BindView(R.id.btn_submitDate)
    TextView btnSubmitDate;

    ShowImageAdapter adapter;
    //当前数量
    int currentCount;
    //上传最大数量
    int maxCount = 20;
    //图片链接
    List imageList;
    //rcv显示列数
    final int columnCount = 4;
    //图片边距百分比
    final float imageMarginPercent = 0.011f;
    //是否超出最大限制
    boolean isMax;

    ProjectDetailModel projectDetailModel;
    private ManeuverModel mManeuverModel;
    private ProgressBean mBean;
    private OSS mOSS;
    private List<ProgressFileBean> mUploadList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_under_construction;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hadSetEndTime(true);
        init();

    }

    private void init() {
        mBean = getArguments().getParcelable(IntentKey.PROGRESS);
        mManeuverModel=new ManeuverModel();
        mUploadList = new ArrayList<>();
        //初始化OSS
        mManeuverModel.getOssAliyunKey(new ManeuverModel.OngetOssAliyunListener() {
            @Override
            public void Result(boolean isSuccess, OssBean bean) {
                if (isSuccess){
                    Log.e("@#",bean.getAccessKeyId());
                    Log.e("@#",bean.getAccessKeySecret());
                    Log.e("@#",bean.getSecurityToken());
                    String AccessKeyId = bean.getAccessKeyId();
                    String SecretId = bean.getAccessKeySecret();
                    String token = bean.getSecurityToken();
                    OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId,SecretId,token);
                    ClientConfiguration conf = new ClientConfiguration();
                    conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                    conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                    conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                    conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                    mOSS = new OSSClient(BaseUtil.getContext(), Const.endpoint,credentialProvider,conf);
                }
            }
        });
    }

    /**
     * 是否已经设置了预期完工时间
     *
     * @param set
     */
    private void hadSetEndTime(boolean set) {
        vUploadImg.setVisibility(set ? View.VISIBLE : View.GONE);
        vSetEndTime.setVisibility(!set ? View.VISIBLE : View.GONE);

        if (set) {
            int windowWidth = ScreenUtils.getScreenWidth(getContext());

            StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(columnCount,StaggeredGridLayoutManager.VERTICAL);
            vRcv.setLayoutManager(gridLayoutManager);
            vRcv.setAdapter(adapter = new ShowImageAdapter());

            int margin_image_percent = (int) (windowWidth*imageMarginPercent);
            vRcv.addItemDecoration(new RcvGridSpaceItemDecoration(columnCount,margin_image_percent,true));

            adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
                @Override
                public void onClick(BaseViewHolder holder) {
                    if (holder.getData() instanceof Integer){
                        List images = new  ArrayList(Arrays.asList( new Integer[imageList.size()]));
                        Collections.copy(images,imageList);
                        images.remove(images.size()-1);
                        int selCount = maxCount-currentCount;
                        if (selCount>0){
                            IntentHelper.openSelImageActivityForResult(UnderConstructionFragment.this,selCount);

                        }else {
                            T.showShort(getContext(),"已达到限制，无法继续添加");
                        }
                    }else {
                        IntentHelper.openBrowseImagesActivity(getContext(),holder.getData().toString());
                    }
                }
            });

            if (imageList==null){
                imageList = new ArrayList();
            }
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");

            imageList.add(imageList.size(),new Integer(0));

            adapter.setDataList(imageList);
            upDateView();
        }
    }

    public void upDateView(){
        currentCount = imageList.size()-1;
        textImageCount.setText(getString(R.string.uploadImages_finish_text,imageList.size()-1+"",maxCount+""));
    }

    //上传图片
    @OnClick(R.id.btn_submit)
    public void onSubmitImageClick() {
        if (isMax){
            BaseUtil.makeText("已超出当天最大上传次数");
            return;
        }
        if (imageList.size()<=1){
            BaseUtil.makeText("请添加上传的工程图片");
            return;
        }
        projectDetailModel.getStatus(mBean.getProjectId(), "3", new BeanCallback<BaseJson<String>>() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(BaseJson<String> response, int id) {
                Log.e("@#","max="+response.data.toString());
                int max = Integer.valueOf(response.data.toString().trim());
                if (max>=3){
                    isMax=true;
                    BaseUtil.makeText("已超出当天最大上传次数");
                    return;
                }
                final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                loadingDialog.show();
                //提交aliyun
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < imageList.size()-1; i++) {
                            String value = (String)(imageList.get(i));
                            int lastIndex = value.lastIndexOf("/");
                            PutObjectSamples putObjectSamples = new PutObjectSamples(mOSS,Const.BucketName,new Date().getTime()+value.substring(lastIndex+1),value);
                            PutObjectRequest request = putObjectSamples.putObjectFromLocalFile();
                            String remotePath = Path.URL_Ayiyun+request.getObjectKey();
                            Log.e("@#",remotePath);
                            mUploadList.add(new ProgressFileBean(remotePath,"1"));
                            //达到最后一张时上传文件名到后台
                            if (i==imageList.size()-2){
                                //返回数据,提交后台
                                projectDetailModel.pushFileList(mBean.getProjectId(), mUploadList, new BeanCallback<BaseJson>() {
                                    @Override
                                    public void onError(Call call, Exception e, int id) {
                                        loadingDialog.dismiss();
                                        BaseUtil.makeText("上传失败，请重试");
                                    }

                                    @Override
                                    public void onResponse(BaseJson response, int id) {
                                        loadingDialog.dismiss();
                                        if (response.ret){
                                            imageList.clear();
                                            imageList.add(imageList.size(),new Integer(0));
                                            adapter.notifyData(imageList);
                                            BaseUtil.makeText("上传完成");
                                            //通知查看施工界面刷新
                                            Intent intent = new Intent();
                                            intent.setAction(IntentKey.ActionConstruct);
                                            getActivity().sendBroadcast(intent);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }).start();
            }
        });
    }

    //提交时间
    @OnClick(R.id.btn_submitDate)
    public void onSubmitDateClick(){

    }

    public void setProjectDetailModel(ProjectDetailModel projectDetailModel) {
        this.projectDetailModel = projectDetailModel;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IntentRequestCode.CODE_SEL_IMAGES && resultCode == RESULT_OK){
            Tuple result = IntentHelper.SelImageActivityGetResult(data);
            imageList = (List) result.getItem1();
            Log.e("@#",imageList.size()+"");
            imageList.add(imageList.size(),new Integer(0));
            adapter.setDataList(imageList);
            adapter.notifyDataSetChanged();
            upDateView();
        }
    }
}
