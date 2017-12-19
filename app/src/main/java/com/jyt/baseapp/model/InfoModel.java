package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * @author LinWei on 2017/12/19 14:11
 */
public interface InfoModel {
    /**
     * 获取最新的项目进度消息
     * @param callback
     */
    void getLatProgress(Callback callback);

    /**
     * 获取最新的工作提示消息
     * @param callback
     */
    void getLastHint(Callback callback);

    /**
     * 获取最新的店主评价消息
     * @param callback
     */
    void getLastEvaluate(Callback callback);
}
