package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.TouchImageAdapter;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.viewholder.TouchImageView;
import com.jyt.baseapp.view.widget.ExtendedViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/10/30.
 */
//查看图片
public class BrowseImagesActivity extends BaseActivity {
    @BindView(R.id.v_viewPager)
    ExtendedViewPager vViewPager;


    TouchImageAdapter adapter;
    //数据源
    List images = new ArrayList();
    //开始查看index
    int startIndex = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_browse_images;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Tuple tuple = IntentHelper.BrowseImagesActivityGetPara(getIntent());
        images = (List) tuple.getItem1();
        startIndex = (int) tuple.getItem2();

        vViewPager.setAdapter(adapter = new TouchImageAdapter());
        adapter.setImages(images);
        adapter.notifyDataSetChanged();

        vViewPager.setCurrentItem(startIndex);

    }
}
