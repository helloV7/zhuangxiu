package com.jyt.baseapp.model;

import android.content.Context;

import com.jyt.baseapp.bean.DeliverGoods;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

/**
 * Created by chenweiqi on 2017/12/1.
 */

public interface ProjectDetailModel {
    public void onCreate(Context context);

    public void onDestroy();

    public void uploadImage(String detailId, String projectId,String sc,List<String> imageList, Callback callback);

    public void getProgressDetail(String detailId,Callback callback);

    void  getPersonById(String userId,Callback callback);

    void clickToNextStep(String detailId,String projectId,String sc,Callback callback);


    void addDeliverGoodsInfo(String projectId, List<DeliverGoods> logList, Callback callback);

    void getAllDeliverGoodsInfo(String projectId, Callback callback);

    /**
     * 提交 货到待施工
     * @param constructionTime 预计到货时间
     * @param constructionArr 实际到货时间
     * @param constructionStart 预计到店时间
     * @param projectManId 项目经理id
     * @param mpersonalId 班长id
     * @param projectId 项目id
     * @param callback
     */
    void addConstriction(String constructionTime,String constructionArr,String constructionStart,String projectManId,String mpersonalId,String projectId,Callback callback);

    /**
     * 
     * @param projectId
     * @param callback
     */
    void getConstrictionComplete(String projectId,Callback callback);
}

