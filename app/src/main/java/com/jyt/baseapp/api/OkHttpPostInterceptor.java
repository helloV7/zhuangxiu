package com.jyt.baseapp.api;

import android.util.Log;

import com.zhy.http.okhttp.request.CountingRequestBody;

import java.io.IOException;
import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by chenweiqi on 2017/11/16.
 */

public class OkHttpPostInterceptor implements Interceptor {
    private String TAG = "OkHttpPostInterceptor";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.d(TAG,"\n");
        Log.d(TAG,"----------Start----------------");
        Log.d(TAG, "| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof CountingRequestBody) {
                CountingRequestBody body = (CountingRequestBody) request.body();
                try {
                    Field field = body.getClass().getDeclaredField("delegate");
                    field.setAccessible(true);
                    RequestBody requestBody = (RequestBody) field.get(body);
                    if (requestBody instanceof FormBody){
                        FormBody formBody = (FormBody) requestBody;
                        for (int i = 0; i < formBody.size(); i++) {
                            sb.append(formBody.encodedName(i) + "=" + formBody.encodedValue(i) + ",");
                        }
                    }
                sb.delete(sb.length() - 1, sb.length());
                Log.d(TAG, "| RequestParams:{"+sb.toString()+"}");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
        Log.d(TAG, "| Response:" + content);
        Log.d(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();

    }
}
