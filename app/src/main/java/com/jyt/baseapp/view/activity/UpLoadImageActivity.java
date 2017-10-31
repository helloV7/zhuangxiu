package com.jyt.baseapp.view.activity;

/**
 * Created by chenweiqi on 2017/10/30.
 */

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ShowImageAdapter;
import com.jyt.baseapp.annotation.ActivityAnnotation;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RcvGridSpaceItemDecoration;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.lcodecore.tkrefreshlayout.utils.DensityUtil;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 上传测量图片
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
    int maxCount;
    //图片链接
    List imageList;
    //rcv显示列数
    int columnCount = 4;
    //底部按钮margin百分比
    float bottomBtnMarginPercent = 0.12f;
    //图片边距百分比
    float imageMarginPercent = 0.011f;

    ShowImageAdapter adapter;

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

        int margin_btn_percent = (int) (windowWidth*bottomBtnMarginPercent);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btnSubmit.getLayoutParams();
        params.leftMargin = margin_btn_percent;
        params.rightMargin = margin_btn_percent;

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(columnCount,StaggeredGridLayoutManager.VERTICAL);
        vRcv.setLayoutManager(gridLayoutManager);
        vRcv.setAdapter(adapter = new ShowImageAdapter());

        int margin_image_percent = (int) (windowWidth*imageMarginPercent);
        vRcv.addItemDecoration(new RcvGridSpaceItemDecoration(columnCount,margin_image_percent,true));

        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                if (holder.getData() instanceof Integer){
                    int selCount = maxCount-currentCount;
                    if (selCount==0){
                        IntentHelper.openSelImageActivityForResult(getContext(),selCount);

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
        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");
        imageList.add("http://img1.imgtn.bdimg.com/it/u=3191256922,1392369155&fm=214&gp=0.jpg");

        imageList.add(imageList.size(),new Integer(0));

        adapter.setDataList(imageList);

    }
}
