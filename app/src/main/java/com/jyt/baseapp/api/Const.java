package com.jyt.baseapp.api;

import android.content.Context;
import android.os.Environment;

import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.BaseUtil;

import java.io.File;

/**
 * @author LinWei on 2017/11/14 10:03
 */
public class Const {
    public final static String DepartmentId="departmentId";
    public final static String Tel ="tel";
    public final static String NickName="nickName";
    public final static String PositionID ="positionId";
    public final static String UserToken="token";
    public final static String UserLoginState="LoginState";
    public final static String PositionName="positionName";
    public final static String DepartmentName="departmentName";
    public static final String USERID = "userId";

    public final static String Tag_LocationShop ="location";
    public final static String Tag_LocationWorker="worker";
    public final static String Tag_Download="download";
    public final static String endpoint ="http://oss-cn-shenzhen.aliyuncs.com";
    public final static String BucketName="mingya-oss";
    public final static String AccessKeyId="LTAIfx2tgz94DBbX";
    public final static String SecretKeyId="oKrkinHaJf3CbIrgQIrPhcK3ejgCcW";
    public final static String mMainFile = Environment.getExternalStorageDirectory().getPath() + File.separator + "mingya";


    public static void KeepLoginState(String departmentId
            ,String nickName
            ,String positionId
            ,String userToken
            ,String userId
            ,String departmentName
            ,String positionName){
        BaseUtil.setSpString(DepartmentId,departmentId);
        BaseUtil.setSpString(NickName,nickName);
        BaseUtil.setSpString(PositionID,positionId);
        BaseUtil.setSpString(UserToken,userToken);
        BaseUtil.setSpBoolean(UserLoginState,true);
        BaseUtil.setSpString(USERID,userId);
        BaseUtil.setSpString(DepartmentName,departmentName);
        BaseUtil.setSpString(PositionName,positionName);
    }

    public static void Logout(Context context){
        BaseUtil.setSpString(DepartmentId,null);
        BaseUtil.setSpString(NickName,null);
        BaseUtil.setSpString(PositionID,null);
        BaseUtil.setSpString(UserToken,null);
        BaseUtil.setSpBoolean(UserLoginState,false);
        BaseUtil.setSpString(USERID,null);
        IntentHelper.DoLogout(context);
    }



    public static void createFileMkdirs(){
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            File destDir = new File(mMainFile);
            if (!destDir.exists()) {
                destDir.mkdirs();//在根创建了文件夹hello
            }
            destDir.mkdirs();
        }
    }

    public static String gettokenSession(){
        return BaseUtil.getSpString(UserToken);
    }

    public static String getDepartmentName(){
        return BaseUtil.getSpString(DepartmentName);
    }

    public static String getPositionName(){
        return BaseUtil.getSpString(PositionName);
    }

    public static String getUserid(){
        return BaseUtil.getSpString(USERID);
    }
}
