package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 项目内 文件 item
 * Created by chenweiqi on 2017/11/1.
 */

public class ProjectFileInfo extends LinearLayout {
    @BindView(R.id.img_typeImg)
    ImageView imgTypeImg;
    @BindView(R.id.text_fileName)
    TextView textFileName;
    @BindView(R.id.text_size)
    TextView textSize;
    @BindView(R.id.text_isHad)
    TextView textIsHad;


    Thread getSizeThread;
    String fileUrl;
    String fileType;
    public ProjectFileInfo(Context context) {
        this(context, null);
    }

    public ProjectFileInfo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_project_file_info, this, true);
        ButterKnife.bind(this);
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (getSizeThread !=null && !hasWindowFocus){
            try {
                getSizeThread.interrupt();
                getSizeThread.stop();
                getSizeThread = null;
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }


    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
        getFileSize();
        setUrlType();
        textIsHad.setText(isFileExist()?"已下载":"未下载");
    }

    private void setUrlType(){
        int lastSlashIndex = fileUrl.lastIndexOf("/");
        if (lastSlashIndex==-1){
            return;
        }
        String fileName = fileUrl.substring(lastSlashIndex+1,fileUrl.length());
        int fileTypeIndex = fileName.lastIndexOf(".");
        fileType = fileName.substring(fileTypeIndex+1,fileName.length()).toLowerCase();
        switch (fileType){
            case "ai":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ai2));
                break;
            case "xls":
            case "xlsx":
            case "xlsm":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_excel2));
                break;
            case "pdf":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_pdf2));
                break;
            case "ppt":
            case "pptx":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_power2));
                break;
            case "doc":
            case "docx":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_word2));
                break;
            case "psd":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_ps2));
                break;
            case "rar":
            case "zip":
            case "arj":
            case "z":
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_rar2));
                break;
            default:
                imgTypeImg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_unknow2));
        }
        textFileName.setText(fileName);

    }

    private boolean isFileExist(){
        int xiegang = fileUrl.lastIndexOf("/");
//        int dian =fileUrl.lastIndexOf(".");
        String fileNameAndType = fileUrl.substring(xiegang+1);
        File file = new File(Const.mMainFile+"/"+fileNameAndType);
        return file.exists();
    }


    private void getFileSize(){
        getSizeThread = new Thread(){
                @Override
                public void run() {
                    super.run();
                    try {
                    String[] unitArray = new String[]{"Byte","KB","M","G"};
                    URL url = new URL(fileUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    int fileSize = urlConnection.getContentLength();
                    float displaySize = fileSize;
                    int unit=0;
                    while (displaySize>10000){
                        unit++;
                        displaySize = displaySize/1024;
                    }
                    urlConnection.disconnect();

                        setFileSizeText(new DecimalFormat("0.00").format(displaySize)+unitArray[unit]);
                }catch (Exception e){
                    e.printStackTrace();
                        setFileSizeText("未知大小");
                    }
                }
            };
        getSizeThread.start();

    }


    public void setFileSizeText(final String text){
        new Handler(getContext().getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                textSize.setText(text);
            }
        });
    }




}
