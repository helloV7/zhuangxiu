package com.jyt.baseapp.api;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.util.BaseUtil;

import java.io.File;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author LinWei on 2017/11/14 10:03
 */
public class Const {
    private final static String TAG="@#";
    public static int NUM;
    public final static String ISFIRST="ISFIRST";
    public final static String DepartmentId="departmentId";
    public final static String Tel ="tel";
    public final static String NickName="nickName";
    public final static String PositionID ="positionId";
    public final static String UserToken="token";
    public final static String UserLoginState="LoginState";
    public final static String PositionName="positionName";
    public final static String DepartmentName="departmentName";
    public static final String USERID = "userId";

    //店主
    public static final String PROJECTNAME="shop_projectName";
    public static final String PROJECTID="shop_projectId";

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
            ,String stateName
            ,String tel){
        BaseUtil.setSpString(DepartmentId,departmentId);
        BaseUtil.setSpString(Tel,tel);
        BaseUtil.setSpString(NickName,nickName);
        BaseUtil.setSpString(PositionID,positionId);
        BaseUtil.setSpString(UserToken,userToken);
        BaseUtil.setSpBoolean(UserLoginState,true);
        BaseUtil.setSpString(USERID,userId);
        BaseUtil.setSpString(DepartmentName,departmentName);
        BaseUtil.setSpString(PositionName,stateName);
        JPushInterface.setAlias(BaseUtil.getContext(),userToken,mAliasCallback);
    }

    public static void KeepLoginStateShop(String name,String id,String token){
        BaseUtil.setSpBoolean(UserLoginState,true);
        BaseUtil.setSpString(NickName,name);
        BaseUtil.setSpString(USERID,id);
        BaseUtil.setSpString(UserToken,token);
        BaseUtil.setSpString(PositionName,"");
    }

    public static void KeepLoginStateBrand(String name ,String token,String tel){
        BaseUtil.setSpBoolean(UserLoginState,true);
        BaseUtil.setSpString(NickName,name);
        BaseUtil.setSpString(UserToken,token);
        BaseUtil.setSpString(Tel,tel);
        BaseUtil.setSpString(PositionName,"");
        JPushInterface.setAlias(BaseUtil.getContext(),token,mAliasCallback);
    }

    public static void Logout(Context context){
        BaseUtil.setSpString(DepartmentId,null);
        BaseUtil.setSpString(PositionName,null);
        BaseUtil.setSpString(NickName,null);
        BaseUtil.setSpString(PositionID,null);
        BaseUtil.setSpString(UserToken,null);
        BaseUtil.setSpBoolean(UserLoginState,false);
        BaseUtil.setSpString(USERID,null);
        BaseUtil.setSpString(Tel,null);
        IntentHelper.DoLogout(context);
        JPushInterface.clearAllNotifications(context);
        JPushInterface.cleanTags(BaseUtil.getContext(),0);
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

    public static String getUserName(){
        return BaseUtil.getSpString(NickName);
    }

    public static String getTel(){
        return BaseUtil.getSpString(Tel);
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

    public static boolean getIsfirst(){
        return BaseUtil.getSpBoolean(ISFIRST);
    }

    public static String getProjectname(){
        return BaseUtil.getSpString(PROJECTNAME);
    }

    public static String getProjectid(){
        return BaseUtil.getSpString(PROJECTID);
    }

    private  final static TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    //注册失败时，需要再次操作
                    BaseUtil.getHandle().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            JPushInterface.setAlias(BaseUtil.getContext(),gettokenSession(),mAliasCallback);
                        }
                    },3000*60);
                    // 延迟 60 秒来调用 Handler 设置别名
                    //                    mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }

        }
    };
}
