package com.jyt.baseapp.api;

import com.jyt.baseapp.util.BaseUtil;

/**
 * @author LinWei on 2017/11/14 10:03
 */
public class Const {
    public final static String DepartmentId="departmentId";
    public final static String NickName="nickName";
    public final static String PositionID ="positionId";
    public final static String UserToken="token";
    public final static String UserLoginState="LoginState";

    public static void KeepLoginState(String departmentId,String nickName,String positionId,String userToken){
        BaseUtil.setSpString(DepartmentId,departmentId);
        BaseUtil.setSpString(NickName,nickName);
        BaseUtil.setSpString(PositionID,positionId);
        BaseUtil.setSpString(UserToken,userToken);
        BaseUtil.setSpBoolean(UserLoginState,true);
    }

    public static void Logout(){
        BaseUtil.setSpString(DepartmentId,null);
        BaseUtil.setSpString(NickName,null);
        BaseUtil.setSpString(PositionID,null);
        BaseUtil.setSpString(UserToken,null);
        BaseUtil.setSpBoolean(UserLoginState,false);
    }
}
