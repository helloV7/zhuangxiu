package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.model.ShareModel;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/28 11:29
 */
public class FileDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.iv_fileLogo)
    ImageView mIvFileLogo;
    @BindView(R.id.tv_fileName)
    TextView mTvFileName;
    @BindView(R.id.pb_download)
    ProgressBar mPbDownload;
    @BindView(R.id.iv_pause)
    ImageView mIvPause;
    @BindView(R.id.ll_progress)
    LinearLayout mLlProgress;
    @BindView(R.id.tv_progress)
    TextView mTvProgress;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    private ShareModel mShareModel;
    private String mFileName="http://mingya-oss.oss-cn-shenzhen.aliyuncs.com/upload-file//%E9%98%BF%E9%87%8C%E7%9F%AD%E4%BF%A1demo%E5%8F%8Asdk20171128191132zip";
    private File mFile;
    private String mFileType;
    private boolean isDownload;//能否下载
    private boolean CanDownload;//是否已经下载

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
        initListener();
    }

    private void init() {
        mShareModel = new ShareModel();
//        mFileName = getIntent().getStringExtra("FileName");
        File files = new File(Const.mMainFile);
        if (!files.exists()){
            //目录不存在
            files.mkdirs();
        }
        //判断文件是否已下载
        File[] list = files.listFiles();
        if (list.length==0){
            mBtnDownload.setText("开始下载");
            mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
            mLlProgress.setVisibility(View.VISIBLE);
            isDownload=false;
            getFileSize(mFileName);
        }
        for (File f:files.listFiles()) {
            if ("A.rar".equals(f.getName())){
                //已下载，隐藏下载进度框
                Log.e("@#","exit");
                mFile=f;
                isDownload=true;
                mBtnDownload.setText("用其他应用打开");
                mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
                mLlProgress.setVisibility(View.INVISIBLE);
                break;
            }else {
                Log.e("@#","unexit");
                //未下载，显示下载进度框
                mBtnDownload.setText("开始下载");
                mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
                mLlProgress.setVisibility(View.VISIBLE);
                isDownload=false;
                getFileSize(mFileName);
            }

        }

    }

    private void initListener(){
        mBtnDownload.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                if (isDownload){
                    //已下载状态
                    showAttachment("rar",Const.mMainFile+File.separator+"A.rar");
                }else {
                    //未下载状态
                    if (CanDownload){
                        downLoadFile(mFileName,"A.rar");
                        mBtnDownload.setEnabled(false);
                    }else {
                        BaseUtil.makeText("内存不足");
                    }
                }
                break;
            case R.id.iv_pause:
                OkHttpUtils.getInstance().cancelTag(Const.Tag_Download);
                break;

            default:
                break;
        }
    }

    /**
     * 下载文件
     * @param url 文件地址
     * @param FileName 文件名
     */
    private void downLoadFile(final String url, String FileName){
        mShareModel.DownloadFile(url,FileName , new ShareModel.OnDownloadFileListener() {
            @Override
            public void Before(  String tag) {
//
            }

            @Override
            public void Progress(int progress) {
                mTvProgress.setText(progress+"/100");
                mPbDownload.setProgress(progress);
            }

            @Override
            public void Result(boolean isSuccess) {
                mBtnDownload.setEnabled(true);
                if (isSuccess){
                    isDownload=true;
                    mBtnDownload.setText("用其他应用打开");
                    mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
                }
            }


        });
    }

    /**
     * 判断内存是否足够下载文件
     * @param fileUrl
     */
    private void getFileSize(final String fileUrl){

        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(fileUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    int fileSize = urlConnection.getContentLength();
                    Log.e("@#",""+BaseUtil.getRomAvailableSizeNum());
                    if (BaseUtil.getRomAvailableSizeNum()>fileSize){
                        //内存足够
                        CanDownload=true;
                    }else {
                        //内存不足
                        CanDownload=false;
                        BaseUtil.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mBtnDownload.setBackground(getResources().getDrawable(R.drawable.btn_add_off));
                            }
                        });
                    }
                    urlConnection.disconnect();


                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }.start();

    }

    private void showAttachment(String fileType, String filepath) {
        if (fileType.equalsIgnoreCase("jpg") || fileType.equalsIgnoreCase("JPG")
                || fileType.equalsIgnoreCase("jpeg")
                || fileType.equalsIgnoreCase("JPEG")
                || fileType.equalsIgnoreCase("png")
                || fileType.equalsIgnoreCase("PNG")
                || fileType.equalsIgnoreCase("gif")
                || fileType.equalsIgnoreCase("GIF")) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filepath)), "image/*");
            startActivity(intent);
        }else if (fileType.equalsIgnoreCase("doc") || fileType.equalsIgnoreCase("docx")
                || fileType.equalsIgnoreCase("xls")
                || fileType.equalsIgnoreCase("ppt")
                || fileType.equalsIgnoreCase("DOC")
                || fileType.equalsIgnoreCase("DOCX")
                || fileType.equalsIgnoreCase("XLS")
                || fileType.equalsIgnoreCase("PPT")) {
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filepath)), "application/msword");
            startActivity(intent);
        }else if (fileType.equalsIgnoreCase("rar")
                || fileType.equalsIgnoreCase("RAR")
                || fileType.equalsIgnoreCase("zip")
                || fileType.equalsIgnoreCase("ZIP")){
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(filepath)),  "application/x-gzip");
            startActivity(intent);
        }
    }

}
