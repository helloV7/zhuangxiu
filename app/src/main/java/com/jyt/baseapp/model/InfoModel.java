package com.jyt.baseapp.model;

import com.zhy.http.okhttp.callback.Callback;

/**
 * @author LinWei on 2017/12/19 14:11
 */
public interface InfoModel {

    /**
     * 获取最新的一条项目进度消息
     * @param callback
     */
    void getLatOneProgress(Callback callback);

    /**
     * 获取最新的一条工作提示消息
     * @param callback
     */
    void getLastOneHint(Callback callback);

    /**
     * 获取最新的一条店主评价消息
     * @param callback
     */
    void getLastOneEvaluate(Callback callback);



    /**
     * 获取项目进度消息List
     * @param callback
     */
    void getLatProgress(Callback callback);

    /**
     * 获取工作提示消息List
     * @param callback
     */
    void getLastHint(Callback callback);

    /**
     * 获取店主评价消息List
     * @param callback
     */
    void getLastEvaluate(Callback callback);
}
