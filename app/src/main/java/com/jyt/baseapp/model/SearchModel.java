package com.jyt.baseapp.model;

import android.util.Log;

import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.List;

import okhttp3.Call;

/**
 * @author LinWei on 2017/11/23 15:57
 */
public class SearchModel {

    public void getSearchData(String condition, final OnSearchResultListener listener){
        OkHttpUtils
                .get()
                .url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getProjectList")
                .addParams("page","1")
                .addParams("keyWord",BaseUtil.getSpString(Const.PositionID))
                .addParams("searchValue",condition)
                .build()
                .execute(new BeanCallback<BaseJson<List<SearchBean>>>() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        if (listener!=null){
                            listener.Result(false,null);
                            Log.e("@#","model_map "+e.getMessage());
                        }
                    }

                    @Override
                    public void onResponse(BaseJson<List<SearchBean>> response, int id) {
                        if (listener!=null){
                            if (response.ret ){
                                listener.Result(true,response.data);
                            }else {
                                Log.e("@#","model_map "+response.forUser);
                            }
                        }
                    }
                });
    }

    public interface OnSearchResultListener{
        void Result(boolean isSuccess,List<SearchBean> data);
    }
}
