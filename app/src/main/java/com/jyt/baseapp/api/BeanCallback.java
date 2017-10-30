package com.jyt.baseapp.api;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jyt.baseapp.view.widget.LoadingDialog;
import com.zhy.http.okhttp.callback.Callback;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by chenweiqi on 2017/1/18.
 */
public abstract class BeanCallback<T> extends Callback<T> {

    LoadingDialog dialog ;

    Context context;

    public BeanCallback() {
        this(null,false,null);
    }
    public BeanCallback(Context context, boolean cancelable) {
        this(context,cancelable,null);
    }
    public BeanCallback(Context context, String  message) {
        this(context,true,message);
    }
    public BeanCallback(Context context, boolean cancelable, String message) {
        if (context!=null) {
            this.context = context;
            dialog = new LoadingDialog(context);
            dialog.setCancelable(cancelable);
            if (!TextUtils.isEmpty(message)) {
                dialog.setText(message);
            }
        }
    }

    @Override
    public void onBefore(Request request, int id) {
        super.onBefore(request, id);
        if (dialog!=null&&!dialog.isShowing())
            dialog.show();

    }

    @Override
    public void onAfter(int id) {
        super.onAfter(id);
        if (dialog!=null&&dialog.isShowing())
            dialog.dismiss();
    }
    @Override
    public T parseNetworkResponse(Response response, int id) throws Exception {
        Type type = this.getClass().getGenericSuperclass();
        String bodyString = response.body().string() ;
            Log.e("http",bodyString);
            if (type instanceof ParameterizedType) {
                //如果用户写了泛型，就会进入这里，否者不会执行
                ParameterizedType parameterizedType = (ParameterizedType) type;
                Type beanType = parameterizedType.getActualTypeArguments()[0];
                try {
                    if (beanType == String.class) {
                        //如果是String类型，直接返回字符串
                        return (T) bodyString;
                    } else {
                        //如果是 Bean List Map ，则解析完后返回
                        return new Gson().fromJson(bodyString, beanType);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    return (T)beanType.getClass().newInstance();
                }
            } else {
                //默认返回字符串
                return (T) bodyString;
            }

    }


}