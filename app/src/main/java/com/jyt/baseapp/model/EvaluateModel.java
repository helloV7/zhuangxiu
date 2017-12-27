package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.EvaluateBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/27 11:18
 */
public class EvaluateModel {
    public void getEvaluateData(String PositionId,String ProjectId,final OngetEvaluateDataListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getEvalForInline")
                .addParams("page","0")
                .addParams("keyWord",PositionId)
                .addParams("searchValue",ProjectId)
                .build()
                .execute(new BeanCallback<BaseJson<List<EvaluateBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        BaseUtil.LogE(getClass());

                    }

                    @Override
                    public void onResponse(BaseJson<List<EvaluateBean>> response, int id) {
                        if (listener!=null){
                            listener.Result(true,response.data);
                        }else {
                            listener.Result(false,null);
                        }
                    }
                });
    }

    /**
     * 店主品牌方
     * @param state
     * @param projectId
     * @param listener
     */
    public void getEvaluateShop(int state,String projectId , final OngetEvaluateSBListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_EVALUATE_SHOP)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("evaltype",state+"")
                .addParams("projectId",projectId)
                .build()
                .execute(new BeanCallback<BaseJson<EvaluateBean>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        BaseUtil.LogE(getClass());

                    }

                    @Override
                    public void onResponse(BaseJson<EvaluateBean> response, int id) {
                        if (listener!=null){
                            listener.Result(true,response.data);
                        }else {
                            listener.Result(false,null);
                        }
                    }
                });
    }


    public interface OngetEvaluateSBListener{
        void Result(boolean isSuccess,EvaluateBean data);
    }


    public interface OngetEvaluateDataListener{
        void Result(boolean isSuccess,List<EvaluateBean> data);
    }

    /**
     * 获取店主是否已评价
     * @param ProjectId
     * @param callback
     */
    public void getKEvalIsFinish(String ProjectId , Callback callback){
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getKEvalIsFinish")
                .addParams("page","0")
                .addParams("searchValue",ProjectId)
                .build()
                .execute(callback);
    }


    /**
     * 获取品牌方是否已评价
     * @param ProjectId
     * @param callback
     */
    public void getBEvalIsFinish(String ProjectId , Callback callback){
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getBEvalIsFinish")
                .addParams("page","0")
                .addParams("searchValue",ProjectId)
                .build()
                .execute(callback);
    }



    /**
     * 发布评论-店主
     * @param evaltype
     * @param eval
     * @param projectId
     * @param callback
     */
    public void SendEvaluate1(int evaltype , String eval, int star , String projectId , Callback callback){
        OkHttpUtils.post().url(Path.URL_SEND_EVALUATE1)
                .addParams("token",Const.gettokenSession())
                .addParams("evaltype",evaltype+"")
                .addParams("star",star+"")
                .addParams("eval",eval)
                .addParams("projectId",projectId)
                .build()
                .execute(callback);
    }

    /**
     * 发布评论-店主
     * @param evaltype
     * @param eval
     * @param projectId
     * @param callback
     */
    public void SendEvaluate2(int evaltype , String eval , String projectId , Callback callback){
        OkHttpUtils.post().url(Path.URL_SEND_EVALUATE2)
                .addParams("token",Const.gettokenSession())
                .addParams("evaltype",evaltype+"")
                .addParams("eval",eval)
                .addParams("projectId",projectId)
                .build()
                .execute(callback);
    }



}
