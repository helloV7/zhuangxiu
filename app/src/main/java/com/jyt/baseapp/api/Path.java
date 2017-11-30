package com.jyt.baseapp.api;

/**
 * @author LinWei on 2017/11/1 15:51
 */
public class Path {

    public final static String BasePath="http://192.168.3.182:8080/mingya/";
    /**
     * 获取省份
     */
    public final static String URL_PROVINCE="https://way.jd.com/JDCloud/getProvince";
    /**
     * 获取城市
     */
    public final static String URL_CITY="https://way.jd.com/JDCloud/getCity";
    /**
     * 获取地区
     */
    public final static String URL_AREA="https://way.jd.com/JDCloud/getCountry";
    /**
     * 登录
     */
    public final static String URL_LOGIN=BasePath+"admin/login";
    /**
     * 获取品牌信息
     */
    public final static String URL_MapDatas = BasePath+"getList";
    /**
     * 上传新增人员信息
     */
    public final static String URL_UploadData=BasePath+"typework/personalIOE";

    public final static String URL_ChangeState=BasePath+"project/push";

    public final static String URL_StsKey=BasePath+"typework/getsts";

    public final static String URL_Ayiyun="http://mingya-oss.oss-cn-shenzhen.aliyuncs.com/";

}
