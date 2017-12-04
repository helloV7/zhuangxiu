package com.jyt.baseapp.util;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.api.ProgressCallback;
import com.jyt.baseapp.api.PutObjectSamples;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.OssBean;
import com.jyt.baseapp.model.ManeuverModel;
import com.zhy.http.okhttp.OkHttpUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public class UpLoadUtil {
    OnUpLoadProgressChangedListener onUpLoadProgressChangedListener;
    private OSS mOSS;

    private List<String> remoteUrls;
    public void upload(final Context context, final List<String> images){
        if (images==null||images.size()==0){
            return;
        }
        getOssAliyunKey(new ManeuverModel.OngetOssAliyunListener(){

            @Override
            public void Result(boolean isSuccess, OssBean bean) {
                String AccessKeyId = bean.getAccessKeyId();
                String SecretId = bean.getAccessKeySecret();
                String token = bean.getSecurityToken();
//                    OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(mOssBean.getAccessKeyId().trim(),mOssBean.getAccessKeySecret().trim(),mOssBean.getAccessKeySecret().trim());
                OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(AccessKeyId,SecretId,token);
                ClientConfiguration conf = new ClientConfiguration();
                conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
                conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
                conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
                conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
                mOSS = new OSSClient(context, Const.endpoint,credentialProvider,conf);
                remoteUrls = new ArrayList<String>();

                for (int i=0;i<images.size();i++){
                    String remoteUrl = upload(images.get(i));
                    remoteUrls.add(remoteUrl);
                    onUpLoadProgressChangedListener.onProgress(i+1/images.size());
                }

            }
        });
    }

    public List<String> getRemoteUrls() {
        return remoteUrls;
    }

    private String upload(String imagePath){
        File file = new File(imagePath);
        PutObjectSamples putObjectSamples = new PutObjectSamples(mOSS,Const.BucketName,file.getName(),file.getAbsolutePath());
        PutObjectRequest request = putObjectSamples.putObjectFromLocalFile();
        String remotePath = Path.URL_Ayiyun+request.getObjectKey();
        return remotePath;
    }

    private void uploadSync(final String imagePath , final OnUpLoadFinish onUpLoadSuccess){
        File file = new File(imagePath);
        PutObjectSamples putObjectSamples = new PutObjectSamples(mOSS,Const.BucketName,file.getName(),file.getAbsolutePath());


        putObjectSamples.asyncPutObjectFromLocalFile(new ProgressCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onProgress(PutObjectRequest putObjectRequest, long currentSize, long totalSize) {

            }

            @Override
            public void onSuccess(PutObjectRequest putObjectRequest, PutObjectResult putObjectResult) {
                Log.e("@#","Success");
                String remotePath = Path.URL_Ayiyun+putObjectRequest.getObjectKey();
                onUpLoadSuccess.result(imagePath,remotePath);
            }

            @Override
            public void onFailure(PutObjectRequest putObjectRequest, ClientException clientException, ServiceException serviceException) {
                if (clientException != null) {
                    // 本地异常如网络异常等
                    clientException.printStackTrace();
                    Log.e("clientMsg",clientException.getMessage());
                }
                if (serviceException != null) {
                    // 服务异常
                    Log.e("StatusCode",serviceException.getStatusCode()+"");
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

    }

    public void setOnUpLoadProgressChangedListener(OnUpLoadProgressChangedListener onUpLoadProgressChangedListener) {
        this.onUpLoadProgressChangedListener = onUpLoadProgressChangedListener;
    }

    public void getOssAliyunKey(final ManeuverModel.OngetOssAliyunListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_StsKey)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .build()
                .execute(new BeanCallback<BaseJson<OssBean>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<OssBean> response, int id) {
                        if(listener!=null && response.ret){
                            listener.Result(true,response.data);
                        }
                    }
                });
    }


    public interface OnUpLoadProgressChangedListener{
        void onProgress(float percent);
    }

    public interface OnUpLoadFinish {
        void result(String localPath,String remotePath);
    }


}
