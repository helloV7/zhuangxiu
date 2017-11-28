package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.model.ShareModel;

/**
 * @author LinWei on 2017/11/28 11:29
 */
public class FileDetailActivity extends BaseActivity {

    private ShareModel mShareModel;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_filedetail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mShareModel = new ShareModel();
        final ProgressBar pb = (ProgressBar) findViewById(R.id.pb_download);
        final TextView tv= (TextView) findViewById(R.id.tv_progress);
        mShareModel.DownloadFile(new ShareModel.OnDownloadFileListener() {
            @Override
            public void Result(boolean isSuccess, int progress) {
                if (isSuccess){
                    pb.setProgress(progress);
                    tv.setText(progress+"/100");
                }
            }
        });
    }
}
