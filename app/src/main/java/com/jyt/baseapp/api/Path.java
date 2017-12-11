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

    //上传项目节点内容
    public static final String URL_ADD_PROJECT_CONTENT = "project/detailSpeed";

    //读取项目节点内容
    public static final String URL_GET_PROJECT_CONTENT = "getList";

    //根据用户id获取用户详细
    public static final String URL_GET_USER_SUB_INFO = "user/getUserDetailA";

    //项目进度直接+1
    public static final String URL_PROGRESS_NEXT = "content/clickNext";

    //新增物流信息
    public static final String URL_ADD_DELIVER_GOODS_INFO="logist/ioeApp";

    //提交货到待施工
    public static final String URL_ADD_CONSTRICTION = "logist/con";


}
