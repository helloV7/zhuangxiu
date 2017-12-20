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
import com.jyt.baseapp.bean.FileBean;
import com.jyt.baseapp.model.ShareModel;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;

/**
 * 文件下载/浏览
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
    private FileBean mFileBean;
    private File mFile;
    private boolean isDownload;//能否是否已经下载
    private boolean CanDownload;//能否下载

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
        mFileBean= (FileBean) getIntent().getSerializableExtra("ShareFile");
        setTextTitle(mFileBean.getShareName());
        mTvFileName.setText(mFileBean.getShareName());
        String type ;
        int dian = mFileBean.getShareUrl().lastIndexOf(".");
        if (dian!=-1){
            type = mFileBean.getShareUrl().substring(dian+1);
        }else {
            type = mFileBean.getShareSuffix();
        }

        switch (type){
            case "ai":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ai));
                break;
            case "doc":
            case "docx":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_word));
                break;
            case "ppt":
            case "pptx":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_power));
                break;
            case "pdf":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pdf));
                break;
            case "xls":
            case "xlsx":
            case "xlsm":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_excel));
                break;
            case "psd":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ps));
            case "rar":
            case "zip":
            case "arj":
            case "z":
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_rar));
                break;
            default:
                mIvFileLogo.setImageDrawable(getResources().getDrawable(R.mipmap.icon_unknow));
                break;
        }

        int xiegang = mFileBean.getShareUrl().lastIndexOf("/");
        String fileNameAndType = mFileBean.getShareUrl().substring(xiegang+1);
        mFile = new File(Const.mMainFile+File.separator+fileNameAndType);
        //0 判断目录是否存在
        File files = new File(Const.mMainFile);
        if (!files.exists()){
            //目录不存在
            files.mkdirs();
        }

        //1 判断文件是否已下载
        if (mFile.exists()){
            //已下载，隐藏下载进度框
            Log.e("@#","exit");
            mBtnDownload.setText("用其他应用打开");
            mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
            mLlProgress.setVisibility(View.INVISIBLE);
            isDownload=true;
        }else {
            Log.e("@#","unexit");
            //未下载，显示下载进度框
            mBtnDownload.setText("开始下载");
            mBtnDownload.setBackground(getResources().getDrawable(R.drawable.bg_corner_blue2));
            mLlProgress.setVisibility(View.INVISIBLE);
            isDownload=false;
            //2 判断内存是否足够
            getFileSize(mFileBean.getShareUrl());
        }


    }

    private void initListener(){
        mBtnDownload.setOnClickListener(this);
        mIvPause.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_download:
                if (isDownload){
                    //4 已下载状态
                    showAttachment(mFileBean.getShareSuffix(),mFile.getAbsolutePath());
                }else {
                    //3 未下载状态
                    if (CanDownload){
                        mBtnDownload.setVisibility(View.INVISIBLE);
                        downLoadFile(mFileBean.getShareUrl(),mFile.getName());
                        mBtnDownload.setEnabled(false);
                    }else {
                        BaseUtil.makeText("内存不足");
                    }
                }
                break;
            case R.id.iv_pause:
                //取消下载
                OkHttpUtils.getInstance().cancelTag(Const.Tag_Download);
                mPbDownload.setProgress(0);
                mIvPause.setVisibility(View.GONE);
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
                mIvPause.setVisibility(View.VISIBLE);
                mLlProgress.setVisibility(View.VISIBLE);
            }

            @Override
            public void Progress(int progress) {
                mTvProgress.setText(progress+"/100");
                mPbDownload.setProgress(progress);
            }

            @Override
            public void Result(boolean isSuccess) {
                mBtnDownload.setEnabled(true);
                mBtnDownload.setVisibility(View.VISIBLE);
                if (isSuccess){
                    isDownload=true;
                    mIvPause.setVisibility(View.GONE);
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
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.fromFile(new File(filepath)));
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            startActivity(intent);
        }
    }

}
