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

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ShowImageAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.helper.IntentRequestCode;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    private ProgressBean mBean;

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
                        L.e("size"+images.size());
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
                Log.e("@#",response.data.toString());
                int max = Integer.valueOf(response.data.toString().trim());
                if (max>=3){
                    isMax=true;
                    BaseUtil.makeText("已超出当天最大上传次数");
                }
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
