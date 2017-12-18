package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.TouchImageAdapter;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.ShareModel;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.dialog.DownloadDialog;
import com.jyt.baseapp.view.widget.ExtendedViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
    private DownloadDialog mDownloadDialog;
    private ShareModel mShareModel;

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
        //0 判断目录是否存在
        File files = new File(Const.mMainFile);
        if (!files.exists()){
            //目录不存在
            files.mkdirs();
        }
        mDownloadDialog = new DownloadDialog(this);
        mShareModel = new ShareModel();
        Tuple tuple = IntentHelper.BrowseImagesActivityGetPara(getIntent());
        images = (List) tuple.getItem1();
        startIndex = (int) tuple.getItem2();
        mDownloadDialog.setOnDownloadListener(new DownloadDialog.OnDownloadListener() {
            @Override
            public void OnClick() {
                String filePath = (String) images.get(startIndex);
                int xiegang = filePath.lastIndexOf("/");
                String fileNameAndType = filePath.substring(xiegang+1);
                Log.e("@#",filePath);
                mShareModel.DownloadFile("https://timgsa.baidu.com/timg?image&amp;quality=80&amp;size=b9999_10000&amp;sec=1513596725872&amp;di=1b82f44d085583b05822dc6ebfdc498d&amp;imgtype=0&amp;src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F4ec2d5628535e5ddbf06b6377cc6a7efce1b622b.jpg", fileNameAndType, new ShareModel.OnDownloadFileListener() {
                    @Override
                    public void Before(String tag) {

                    }

                    @Override
                    public void Progress(int progress) {
                    }

                    @Override
                    public void Result(boolean isSuccess) {
                        if (isSuccess){
                            BaseUtil.makeText("下载成功");
                        }else {
                            BaseUtil.makeText("下载失败");
                        }
                    }
                });
            }
        });

        vViewPager.setAdapter(adapter = new TouchImageAdapter());
        adapter.setImages(images);
        adapter.notifyDataSetChanged();
        vViewPager.setCurrentItem(startIndex);
        adapter.setOnLongClickListener(new TouchImageAdapter.OnLongClickListener() {
            @Override
            public void OnLongClick(String data) {
                mDownloadDialog.show();
            }
        });


    }
}
