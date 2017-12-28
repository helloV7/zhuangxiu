package com.jyt.baseapp.view.activity;

/**
 * Created by chenweiqi on 2017/10/31.
 */

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FragmentViewPagerAdapter;
import com.jyt.baseapp.bean.LocalMedia;
import com.jyt.baseapp.bean.LocalMediaFolder;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.util.LocalMediaLoader;
import com.jyt.baseapp.view.fragment.ImgFolderFragment;
import com.jyt.baseapp.view.fragment.ImgListFragment;
import com.jyt.baseapp.view.widget.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 */
public class SelImageActivity extends BaseActivity {

    @BindView(R.id.v_viewPager)
    NoScrollViewPager vViewPager;

    FragmentViewPagerAdapter adapter;

    ImgFolderFragment imgFolderFragment;
    ImgListFragment imgListFragment;

    List<String> selImages;

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

        Tuple para = IntentHelper.SelImageActivityGetPara(getIntent());

        maxSelCount = (int) para.getItem1();
        selImages = (List<String>) para.getItem2();
        if (selImages==null){
            selImages = new ArrayList<>();
            currentSelCount = 0;
        }else {
            currentSelCount = selImages.size();
        }

        setFunctionText(getResources().getString(R.string.selImages_finish_text,currentSelCount+"",maxSelCount+""));

        vViewPager.setAdapter(adapter = new FragmentViewPagerAdapter(getSupportFragmentManager()));
        adapter.addFragment(imgFolderFragment = new ImgFolderFragment());
        adapter.addFragment(imgListFragment = new ImgListFragment());

        adapter.notifyDataSetChanged();

        loadMedia();

        imgFolderFragment.setOnFolderClickListener(new ImgFolderFragment.OnFolderClickListener() {
            @Override
            public void onClick(List<LocalMedia> medias) {


                for (int i = 0; i < selImages.size(); i++) {
                    Log.e("@#","@#1111");
                    for (int j = 0; j < medias.size(); j++) {
                        Log.e("@#","@#");
                        if (selImages.get(i).equals(medias.get(j).getPath())){
                            Log.e("@#","success");
                            medias.get(j).setChecked(true);
                        }
                    }
                }

                imgListFragment.setData(medias,selImages);
                vViewPager.setCurrentItem(1,true);
            }
        });

        imgListFragment.setOnImageCheckChanged(new ImgListFragment.OnImageCheckChanged() {
            @Override
            public void onChanged(boolean checked, String path) {
                if (checked){
                    selImages.add(path);
                }else {
                    selImages.remove(path);
                }
                currentSelCount = selImages.size();
                setFunctionText(getResources().getString(R.string.selImages_finish_text,currentSelCount+"",maxSelCount+""));

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
    public void onFunctionClick() {
        if (currentSelCount>0 && currentSelCount<=maxSelCount){
            IntentHelper.SelImageActivitySetResult(this,RESULT_OK,selImages);
        }else {
            BaseUtil.makeText("超出最大上传数");
        }
    }

    @Override
    public void onBackPressed() {
        if (vViewPager.getCurrentItem()==0){
            super.onBackPressed();

        }else {
            vViewPager.setCurrentItem(0,true);
        }
    }
}
