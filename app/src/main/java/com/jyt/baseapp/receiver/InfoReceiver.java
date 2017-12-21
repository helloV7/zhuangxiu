package com.jyt.baseapp.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jyt.baseapp.helper.IntentKey;
import com.jyt.baseapp.view.activity.ShopActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author LinWei on 2017/12/20 18:23
 */
public class InfoReceiver extends BroadcastReceiver {
    private static final String TAG = "@#";
    private Context mContext;
    private boolean isFirst;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (!isFirst){
            mContext = context;
            isFirst=true;
        }
        Bundle bundle = intent.getExtras();
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.i(TAG, "JPush用户注册成功");
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            Log.i(TAG, "接受到推送下来的自定义消息");
            String message = bundle.getString("ee");
            Log.i(TAG,"收到了自定义消息。消息内容是：message=" + message);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG,"收到了自定义消息。消息内容是：" + extra);

            //            receivingNotification(context, bundle);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            Log.i(TAG, "接受到推送下来的通知");
            String message = bundle.getString(JPushInterface.EXTRA_TITLE);
            Log.i(TAG,"收到了通知。消息内容是：title=" + message);
            String extra = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Log.i(TAG,"收到了通知。extra是：" + extra);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            Log.i(TAG, "用户点击打开了通知");
            openNotification(context, bundle);
        }
    }

    private void openNotification(Context context , Bundle bundle) {
        String projectId =null;
        String projectName =null;
        try {
            JSONObject job = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
            projectId = job.getString("projectId");
            projectName = job.getString("projectName");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(mContext,ShopActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(IntentKey.PROJECTID,projectId);
        intent.putExtra(IntentKey.SHOPNAME,projectName);
        mContext.startActivity(intent);

    }
}
