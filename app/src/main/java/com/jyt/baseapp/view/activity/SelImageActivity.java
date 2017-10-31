package com.jyt.baseapp.view.activity;

/**
 * Created by chenweiqi on 2017/10/31.
 */

import android.os.Bundle;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.bean.LocalMedia;
import com.jyt.baseapp.bean.LocalMediaFolder;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.LocalMediaLoader;
import com.jyt.baseapp.util.ScreenUtils;
import com.jyt.baseapp.view.fragment.ImgFolderFragment;
import com.jyt.baseapp.view.fragment.ImgListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 选择图片activity
 */
public class SelImageActivity extends BaseActivity {


    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;

    FragmentViewPagerAdapter adapter;

    ImgFolderFragment imgFolderFragment;
    ImgListFragment imgListFragment;

    List<String> selImages = new ArrayList<>();

    int currentSelCount = 0;
    int maxSelCount = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.layout_only_viewpager;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        vViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));
        adapter.addFragment(imgFolderFragment = new ImgFolderFragment());
        adapter.addFragment(imgListFragment = new ImgListFragment());

        adapter.notifyDataSetChanged();

        loadMedia();

        imgFolderFragment.setOnFolderClickListener(new ImgFolderFragment.OnFolderClickListener() {
            @Override
            public void onClick(List<LocalMedia> medias) {
                imgListFragment.setData(medias,selImages);
                vViewPager.setCurrentItem(1);
            }
        });

    }

    public void loadMedia(){
        new LocalMediaLoader(this, LocalMediaLoader.TYPE_IMAGE).loadAllImage(new LocalMediaLoader.LocalMediaLoadListener() {
            @Override
            public void loadComplete(List<LocalMediaFolder> folders) {
                if (folders!=null){
                    imgFolderFragment.setDataList(folders);
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (vViewPager.getCurrentItem()==0){
            super.onBackPressed();

        }else {
            vViewPager.setCurrentItem(0);
        }
    }
}
