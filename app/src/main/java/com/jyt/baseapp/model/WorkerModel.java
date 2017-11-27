package com.jyt.baseapp.model;

import com.amap.api.maps.model.LatLng;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.LocationBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/24 14:43
 */
public class WorkerModel {

    public void LocationWork(LatLng l1, LatLng l2, final OngetWorkerListener listener){
        OkHttpUtils.getInstance().cancelTag(Const.Tag_LocationWorker);
        OkHttpUtils
                .get()
                .tag(Const.Tag_LocationWorker)
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getAreaUser")
                .addParams("page","0")
                .addParams("searchValue",l1.latitude+","+l2.latitude+","+l1.longitude+","+l2.longitude)
                .build()
                .execute(new BeanCallback<BaseJson<List<LocationBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);

                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<LocationBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                            }
                        }
                    }
                });
    }

    public interface OngetWorkerListener{
        void Result(boolean isSuccess,List<LocationBean> workers);
    }
}
