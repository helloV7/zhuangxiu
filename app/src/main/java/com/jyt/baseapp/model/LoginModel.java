package com.jyt.baseapp.model;

import android.util.Log;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.UserBean;
import com.zhy.http.okhttp.OkHttpUtils;

import okhttp3.Call;

/**
 * @author LinWei on 2017/10/30 14:05
 */
public class LoginModel {

    public void ToLogin(String tel, String pwd, final LoginResultListener listener){
        OkHttpUtils.post()
                .url(Path.URL_LOGIN)
                .addParams("tel",tel)
                .addParams("pwd",pwd)
                .build()
                .execute(new BeanCallback<BaseJson<UserBean>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model---login "+ e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<UserBean> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model---login "+response.forUser);
                            }
                        }

                    }
                });
    }

    public interface LoginResultListener{
        void Result(boolean isSuccess,UserBean user);
    }
}
