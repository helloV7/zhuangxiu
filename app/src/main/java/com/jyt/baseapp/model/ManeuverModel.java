package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.ManeuverBean;
import com.jyt.baseapp.bean.OssBean;
import com.jyt.baseapp.bean.WorkBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/27 16:43
 */
public class ManeuverModel {

    /**
     * 获取全部的工种
     * @param listener
     */
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

    /**
     * 获取指定条件的工人
     * @param condition
     * @param page
     * @param listener
     */
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
                        if (listener!=null){
                            listener.Result(false,null);
                        }
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

    public void addManeuver(String image,String name ,String tel ,String workId ,String province ,String city ,String area,final OnaddManeuverListener listener){
        OkHttpUtils
                .post()
                .url(Path.URL_UploadData)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("personalname",name)
                .addParams("tel",tel)
                .addParams("province",province)
                .addParams("city",city)
                .addParams("area",area)
                .addParams("workTypeId",workId)
                .addParams("image",image)
                .build()
                .execute(new BeanCallback<BaseJson<String>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(BaseJson<String> response, int id) {
                        if (response.ret){
                            if (listener!=null){
                                listener.Result(true);
                            }
                        }
                    }
                });

    }

    public interface OnaddManeuverListener{
        void Result(boolean isSuccess);
    }

    public void getOssAliyunKey( final OngetOssAliyunListener listener){
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

    public interface OngetOssAliyunListener{
        void Result(boolean isSuccess , OssBean bean);
    }


}
