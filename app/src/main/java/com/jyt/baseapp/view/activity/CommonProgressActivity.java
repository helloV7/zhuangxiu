package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jyt.baseapp.R;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.FileBean;
import com.jyt.baseapp.bean.FinishBean;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.ProjectFileBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.bean.UserBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.model.impl.ProjectDetailModelImpl;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.widget.ProjectFileInfo;
import com.jyt.baseapp.view.widget.WorkerNameAndDateTime;
import com.nex3z.flowlayout.FlowLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * 测量完毕 设计完毕 客户已审批 待店主确认 店主已确认 待审图纸 已审图纸 待生产招牌 待审材料 已审材料 施工完毕
 * Created by chenweiqi on 2017/11/1.
 */

public class CommonProgressActivity extends BaseActivity {
    @BindView(R.id.text_confirm)
    TextView textConfirm;
    @BindView(R.id.v_workerAndTime)
    WorkerNameAndDateTime vWorkerAndTime;
    @BindView(R.id.v_fileLayout)
    LinearLayout vFileLayout;
    @BindView(R.id.v_imageLayout)
    FlowLayout vImageLayout;


    public static final int TYPE_MEASURE_FINISH = 1;
    public static final int TYPE_DESIGN_FINISH = 2;
    public static final int TYPE_CUSTOMER_VERIFIED = 3;
    public static final int TYPE_WAIT_SHOPKEEPER_CONFIRM = 4;
    public static final int TYPE_SHOPKEEPER_CONFIRMED = 5;
    public static final int TYPE_WAIT_VERIFIED_DRAWING = 6;
    public static final int TYPE_VERIFIED_DRAWING = 7;
    public static final int TYPE_WAIT_PRODUCE_SIGN = 8;
    public static final int TYPE_WAIT_VERIFY_MATERIAL = 9;
    public static final int TYPE_VERIFIED_MATERIAL = 10;
    public static final int TYPE_CONSTRUCTION_COMPLETE = 11;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.ll_parent)
    LinearLayout mLlParent;

    private int type;
    private ProgressBean project;
    private ProgressBean beforeProject;
    private List<String> mImageList;

    ProjectDetailModel projectDetailModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_common_progress;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tuple data = IntentHelper.CommonProgressActivityGetPara(getIntent());
        type = (int) data.getItem1();
        mImageList = new ArrayList<>();
        project = (ProgressBean) data.getItem2();
        beforeProject = (ProgressBean) data.getItem3();
        setTextTitle(project.getSpeedName());

        if (beforeProject == null) {
            beforeProject = project;
        }

        //用于店主已确认
        if (getIntent().getBooleanExtra(IntentKey.Confirm,false)){
            textConfirm.setVisibility(View.VISIBLE);
        }else {
            textConfirm.setVisibility(View.GONE);
        }


        projectDetailModel = new ProjectDetailModelImpl();
        projectDetailModel.onCreate(getContext());
        //region 根据type 设置标题
        //        switch (type){
        //            case TYPE_MEASURE_FINISH:
        //                setTextTitle("测量完毕");
        //                break;
        //            case TYPE_DESIGN_FINISH :
        //                setTextTitle("设计完毕");
        //                break;
        //            case TYPE_CUSTOMER_VERIFIED :
        //                setTextTitle("客户已审批");
        //                break;
        //            case TYPE_WAIT_SHOPKEEPER_CONFIRM:
        //                setTextTitle("待店主确认");
        //                break;
        //            case TYPE_SHOPKEEPER_CONFIRMED:
        //                setTextTitle("店主已确认");
        //                break;
        //            case TYPE_WAIT_VERIFIED_DRAWING:
        //                setTextTitle("待审图纸");
        //                break;
        //            case TYPE_VERIFIED_DRAWING:
        //                setTextTitle("已审图纸");
        //
        //                break;
        //            case TYPE_WAIT_PRODUCE_SIGN:
        //                setTextTitle("待生产招牌");
        //
        //                break;
        //            case TYPE_WAIT_VERIFY_MATERIAL:
        //                setTextTitle("待审材料");
        //                break;
        //            case TYPE_VERIFIED_MATERIAL:
        //                setTextTitle("已审材料");
        //                break;
        //            case TYPE_CONSTRUCTION_COMPLETE:
        //                setTextTitle("施工完毕");
        //                break;
        //
        //            default:
        //                finish();
        //
        //        }
        //endregion

        if (beforeProject.getFinishTime() != null) {
            vWorkerAndTime.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(beforeProject.getFinishTime()))));
        }
        if (type == 0) {

            projectDetailModel.getProgressDetail(beforeProject.getSpeedId(), new BeanCallback<BaseJson<List<ProjectFileBean>>>() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    T.showShort(getContext(), e.getMessage());

                }

                @Override
                public void onResponse(BaseJson<List<ProjectFileBean>> response, int id) {
                    if (response != null && response.ret) {
                        mLlParent.setVisibility(View.VISIBLE);
                        mLlEmpty.setVisibility(View.GONE);
                        createView(response.data);
                        if (response.data != null && response.data.size() != 0) {
                            Log.e("@#","size="+response.data.size());
                            ProjectFileBean lastObj = response.data.get(response.data.size() - 1);
                            projectDetailModel.getPersonById(lastObj.getUserId(), new BeanCallback<BaseJson<UserBean>>() {
                                @Override
                                public void onError(Call call, Exception e, int id) {

                                }

                                @Override
                                public void onResponse(BaseJson<UserBean> response, int id) {
                                    if (response.data!=null){
                                        vWorkerAndTime.setWorkerText(response.data.getStationName() + " " + response.data.getNickName());
                                    }
                                }
                            });
                        }else {
                            mLlEmpty.setVisibility(View.VISIBLE);
                            mLlParent.setVisibility(View.GONE);
                        }

                    }
                }
            });
        } else {
            //施工完毕
            projectDetailModel.getFinishList(beforeProject.getProjectId(), new BeanCallback<BaseJson<FinishBean>>() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    T.showShort(getContext(), e.getMessage());
                }

                @Override
                public void onResponse(BaseJson<FinishBean> response, int id) {
                    if (response.data.getLolist() != null && response.ret) {
                        mLlParent.setVisibility(View.VISIBLE);
                        mLlEmpty.setVisibility(View.GONE);
                        List<ProjectFileBean> data = new ArrayList<ProjectFileBean>();
                        for (int i = 0; i < response.data.getLolist().size(); i++) {
                            FinishBean.Data finishFile = response.data.getLolist().get(i);
                            ProjectFileBean bean = new ProjectFileBean();
                            bean.setContentId(finishFile.getConstructionId());
                            bean.setContentName(finishFile.getConstructionName());
                            bean.setContentSuffix(finishFile.getConstructionSuffix());
                            bean.setContentUrl(finishFile.getConstructionUrl());
                            bean.setIsdel(finishFile.getIsdel());
                            data.add(bean);
                        }
                        if (data.size() != 0) {
                            vWorkerAndTime.setWorkerText(response.data.getLolist().get(0).getConstructionNickName());
                            createView(data);
                        }
                    }else {
                        mLlEmpty.setVisibility(View.VISIBLE);
                        mLlParent.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        projectDetailModel.onDestroy();

    }


    private void createView(List<ProjectFileBean> files) {
        vFileLayout.removeAllViews();
        vImageLayout.removeAllViews();

        int windowWidth = ScreenUtils.getScreenWidth(getContext());
        int columnCount = 3;
        float marginPercent = 0.01f;


        int imageMargin = (int) (windowWidth * marginPercent);
        int imageWidth = (int) ((windowWidth - (imageMargin * (columnCount + 1))) * 1.0 / columnCount);

        vImageLayout.setPadding(imageMargin, imageMargin, 0, imageMargin);
        vImageLayout.setChildSpacing(imageMargin);
        vImageLayout.setRowSpacing(0);
        vImageLayout.setRowSpacing(imageMargin);

        View.OnClickListener onImageClickToBrowserListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper.openBrowseImagesActivity(getContext(),mImageList , (int) v.getTag(R.id.tag_imgUrl));
            }
        };

        View.OnClickListener onFileClickToOpenFileDetailActivityListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentHelper.openFileDetailActivity(getContext(), (FileBean) v.getTag(R.id.tag_data));
            }
        };

        //        files.addAll(files);
        int index=0;
        for (ProjectFileBean projectFileBean : files) {
            if (projectFileBean.getContentSuffix().toLowerCase().equals("jpg") || projectFileBean.getContentSuffix().toLowerCase().equals("png") || projectFileBean.getContentSuffix().toLowerCase().equals("jpeg")) {
                ImageView img = new ImageView(getContext());
                FlowLayout.LayoutParams param = new FlowLayout.LayoutParams(imageWidth, imageWidth);
                img.setLayoutParams(param);
                Glide.with(getContext()).load(projectFileBean.getContentUrl()).centerCrop().into(img);
                img.setTag(R.id.tag_imgUrl, index);
                img.setOnClickListener(onImageClickToBrowserListener);
                vImageLayout.addView(img);
                vImageLayout.setVisibility(View.VISIBLE);
                mImageList.add(projectFileBean.getContentUrl());
                index++;
            } else {
                if (TextUtils.isEmpty(projectFileBean.getContentUrl())){
                    continue;
                }
                ProjectFileInfo projectFile = new ProjectFileInfo(getContext());
                projectFile.setFileUrl(projectFileBean.getContentUrl());
                projectFile.setTag(R.id.tag_data, projectFileBean.toFileBean());
                projectFile.setOnClickListener(onFileClickToOpenFileDetailActivityListener);
                vFileLayout.addView(projectFile);
                vFileLayout.setVisibility(View.VISIBLE);
            }
        }

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vImageLayout.getLayoutParams();
        if (params == null) {
            params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        params.height = (int) (vImageLayout.getChildCount() * 1f / columnCount + 0.9f) * (imageWidth + imageMargin) + imageMargin * 2;
        vImageLayout.setLayoutParams(params);
    }


}
