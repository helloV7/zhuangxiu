package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ManeuverBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/27 16:43
 */
public class ManeuverModel {

    public void getAllWorkType(final OngetAllWorkTypeListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAllWorkType")
                .addParams("page","0")
                .build()
                .execute(new BeanCallback<BaseJson<List<WorkBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        BaseUtil.LogE(getClass());
                    }

                    @Override
                    public void onResponse(BaseJson<List<WorkBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,response.data);
                            }else {
                                listener.Result(false,null);
                            }
                        }
                    }
                });
    }
    public interface OngetAllWorkTypeListener{
        void Result(boolean isSuccess, List<WorkBean> data);
    }

    public void getAllPersonal(String condition,
                               int page,
                               final OngetAllPersonalListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAllPersonal")
                .addParams("page",String.valueOf(page))
                .addParams("searchValue",condition)
                .build()
                .execute(new BeanCallback<BaseJson<List<ManeuverBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<List<ManeuverBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret){
                                listener.Result(true,response.data);
                            }else {
                                listener.Result(false,null);
                            }
                        }
                    }
                });
    }

    public interface OngetAllPersonalListener{
        void Result(boolean isSuccess, List<ManeuverBean> data);
    }
}
