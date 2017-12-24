package com.jyt.baseapp.view.activity;

/**
 * Created by chenweiqi on 2017/10/30.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ShowImageAdapter;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.ProgressBean;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.helper.IntentRequestCode;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.model.ProjectDetailModel;
import com.jyt.baseapp.model.impl.ProjectDetailModelImpl;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.util.UpLoadUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 上传测量图片  所有材料已打包
 */
@ActivityAnnotation(showBack = true)
public class UpLoadImageActivity extends BaseActivity {

    @BindView(R.id.v_rcv)
    RecyclerView vRcv;
    @BindView(R.id.text_imageCount)
    TextView textImageCount;
    @BindView(R.id.btn_submit)
    LinearLayout btnSubmit;

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

    ShowImageAdapter adapter;

    ProjectDetailModel model;

    ProgressBean progressBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_up_load_image;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int windowWidth = ScreenUtils.getScreenWidth(getContext());

        Intent intent = getIntent();
        maxCount = intent.getIntExtra(IntentKey.MAX_COUNT,20);
        if (maxCount==9){
            setTextTitle("所有材料已打包");
        }
        imageList = intent.getStringArrayListExtra(IntentKey.IMAGES);
        progressBean = intent.getParcelableExtra(IntentKey.PROJECT);
//        maxCount = intent.getIntExtra(IntentKey.MAX_COUNT,0);

        if (1!=progressBean.getPermissionState()){
            //无操作权限
            btnSubmit.setVisibility(View.GONE);

            if ( "1".equals(progressBean.getIsfinish())){
                //已经操作过了，获取数据
            }

            return;
        }



        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(columnCount, StaggeredGridLayoutManager.VERTICAL);
        vRcv.setLayoutManager(gridLayoutManager);
        vRcv.setAdapter(adapter = new ShowImageAdapter());

        int margin_image_percent = (int) (windowWidth * imageMarginPercent);
        vRcv.addItemDecoration(new RcvGridSpaceItemDecoration(columnCount, margin_image_percent, true));

        adapter.setOnViewHolderLongClickListener(new BaseViewHolder.OnViewHolderLongClickListener() {
            @Override
            public void onLongClick(BaseViewHolder holder) {
                adapter.getDataList().remove(holder.getData());
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                if (holder.getData() instanceof Integer) {
                    List images = new ArrayList(Arrays.asList(new Integer[imageList.size()]));
                    Collections.copy(images, imageList);
                    L.e("size" + images.size());
                    images.remove(images.size() - 1);
                    int selCount = maxCount - currentCount;
                    if (selCount > 0) {
                        IntentHelper.openSelImageActivityForResult(getContext(), selCount);

                    } else {
                        T.showShort(getContext(), "已达到限制，无法继续添加");
                    }
                } else {
                    IntentHelper.openBrowseImagesActivity(getContext(), holder.getData().toString());
                }
            }
        });

        if (imageList == null) {
            imageList = new ArrayList();
        }
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
//        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");

        imageList.add(imageList.size(), new Integer(0));

        adapter.setDataList(imageList);
        upDateView();

        model = new ProjectDetailModelImpl();
        model.onCreate(getContext());
    }

    public void upDateView() {
        currentCount = imageList.size() - 1;
        textImageCount.setText(getString(R.string.uploadImages_finish_text, imageList.size() - 1 + "", maxCount + ""));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IntentRequestCode.CODE_SEL_IMAGES && resultCode == RESULT_OK) {
            Tuple result = IntentHelper.SelImageActivityGetResult(data);
            imageList = (List) result.getItem1();
            imageList.add(imageList.size(), new Integer(0));
            adapter.setDataList(imageList);
            adapter.notifyDataSetChanged();
            upDateView();
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (imageList!=null && imageList.size()!=1){
            final UpLoadUtil upLoadUtil = new UpLoadUtil();
            upLoadUtil.setOnUpLoadProgressChangedListener(new UpLoadUtil.OnUpLoadProgressChangedListener() {
                @Override
                public void onProgress(float percent) {
                    if (percent==1){
                        model.uploadImage(progressBean.getSpeedId(), progressBean.getProjectId(), progressBean.getSpeedCode()+"", upLoadUtil.getRemoteUrls(), new BeanCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                BaseUtil.makeText("上传失败");
                            }

                            @Override
                            public void onResponse(Object response, int id) {
                                BaseUtil.makeText("上传成功");
                                finish();
                            }
                        });
                    }else {
                        T.showShort(getContext(), new DecimalFormat("0").format(percent*100)+"%");
                    }
                }
            });

            if (imageList.get(imageList.size()-1) instanceof  Integer){
                imageList.remove(imageList.size()-1);
            }
            upLoadUtil.upload(getContext(),imageList);

        }
    }
}
