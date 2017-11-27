package com.jyt.baseapp.model;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.EvaluateBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

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

    public interface OngetEvaluateDataListener{
        void Result(boolean isSuccess,List<EvaluateBean> data);
    }
}
