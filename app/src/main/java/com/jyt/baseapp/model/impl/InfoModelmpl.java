package com.jyt.baseapp.model.impl;

import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.model.InfoModel;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * 最新消息及消息列表
 * @author LinWei on 2017/12/19 14:56
 */
public class InfoModelmpl implements InfoModel {

    @Override
    public void getLatOneProgress(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getPorjectSpeedMsgLast")
                .addParams("page","0")
                .addParams("searchValue","1")//1内部人员2品牌方
                .build()
                .execute(callback);
    }

    @Override
    public void getLastOneHint(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getworktiplast")
                .addParams("page","0")
                .build()
                .execute(callback);
    }

    @Override
    public void getLastOneEvaluate(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getKeeperEvalLastList")
                .addParams("page","0")
                .build()
                .execute(callback);
    }

    @Override
    public void getLatProgress(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getProjectSpeedMsg")
                .addParams("page","0")
                .addParams("searchValue","1")//1内部人员2品牌方
                .build()
                .execute(callback);
    }

    @Override
    public void getLastHint(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getworktip")
                .addParams("page","0")
                .build()
                .execute(callback);
    }

    @Override
    public void getLastEvaluate(Callback callback) {
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token",Const.gettokenSession())
                .addParams("method","getKeeperEvalList")
                .addParams("page","0")
                .build()
                .execute(callback);
    }
}
