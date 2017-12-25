package com.jyt.baseapp.model.impl;

import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.api.Path;
import com.jyt.baseapp.util.BaseUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

/**
 * @author LinWei on 2017/12/25 16:32
 */
public class MoreModel {

    public void getRole(Callback callback){
        OkHttpUtils.get().url(Path.URL_MapDatas)
                .addParams("token", BaseUtil.getSpString(Const.UserToken))
                .addParams("method","getRoleAny")
                .addParams("page","0")
                .build()
                .execute(callback);
    }
}
