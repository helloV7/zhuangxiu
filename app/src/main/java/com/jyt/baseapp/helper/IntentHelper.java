package com.jyt.baseapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.activity.BaseActivity;
import com.jyt.baseapp.view.activity.BrowseImagesActivity;
import com.jyt.baseapp.view.activity.CommonProgressActivity;
import com.jyt.baseapp.view.activity.DeliverGoodsActivity;
import com.jyt.baseapp.view.activity.SelImageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class IntentHelper {

    //region 选择图片
    public static void openSelImageActivityForResult(Object context, int maxSelCount){
        Intent intent = getIntent((Context) context, SelImageActivity.class);
        intent.putExtra(IntentKey.MAX_COUNT,maxSelCount);
        if (context instanceof  Activity){
            ((Activity) context).startActivityForResult(intent,IntentRequestCode.CODE_SEL_IMAGES);
        }else if (context instanceof Fragment){
            ((Fragment) context).startActivityForResult(intent,IntentRequestCode.CODE_SEL_IMAGES);
        }
    }
    public static void openSelImageActivityForResult(Object context, int maxSelCount,List images){
        Intent intent = getIntent((Context) context, SelImageActivity.class);
        intent.putExtra(IntentKey.MAX_COUNT,maxSelCount);
        intent.putStringArrayListExtra(IntentKey.IMAGES, (ArrayList<String>) images);
        if (context instanceof  Activity){
            ((Activity) context).startActivityForResult(intent,IntentRequestCode.CODE_SEL_IMAGES);
        }else if (context instanceof Fragment){
            ((Fragment) context).startActivityForResult(intent,IntentRequestCode.CODE_SEL_IMAGES);
        }
    }

    /**
     *
     * @param intent
     * @return int maxCount,List images
     */
    public static Tuple SelImageActivityGetPara(Intent intent){
        int maxCount = intent.getIntExtra(IntentKey.MAX_COUNT,0);
        List images = intent.getStringArrayListExtra(IntentKey.IMAGES);
        return new Tuple(maxCount,images);
    }

    /**
     *
     * @param intent
     * @return List images
     */
    public static Tuple SelImageActivityGetResult(Intent intent){
        List images = intent.getStringArrayListExtra(IntentKey.IMAGES);
        return new Tuple(images);
    }
    public static void SelImageActivitySetResult(Activity activity,int resultCode,List images){
        Intent intent = getIntent();
        intent.putStringArrayListExtra(IntentKey.IMAGES, (ArrayList<String>) images);
        activity.setResult(resultCode,intent);
        activity.finish();
    }

    //endregion


    //region 浏览图片
    public static void openBrowseImagesActivity(Context context ,String image){
        List images = new ArrayList();
        images.add(image);
        openBrowseImagesActivity(context,images,0);
    }

    public static void openBrowseImagesActivity(Context context, List<String> images, int startIndex){
        Intent intent = getIntent(context, BrowseImagesActivity.class);
        intent.putExtra(IntentKey.START_INDEX,startIndex);
        intent.putStringArrayListExtra(IntentKey.IMAGES, (ArrayList<String>) images);

        context.startActivity(intent);
    }
    /**
     *
     * @param intent
     * @return images list  , startIndex int
     */
    public static Tuple BrowseImagesActivityGetPara(Intent intent){
        List list = intent.getStringArrayListExtra(IntentKey.IMAGES);
        int startIndex = intent.getIntExtra(IntentKey.START_INDEX,0);
        return new Tuple(list,startIndex);
    }
    //endregion


// region 上传图片
    public static void openUploadImagesActivityForResult(Object context,List images,int maxCount){
        Intent intent = getIntent((Context) context, SelImageActivity.class);
        intent.putExtra(IntentKey.MAX_COUNT,maxCount);
        intent.putStringArrayListExtra(IntentKey.IMAGES, (ArrayList<String>) images);
        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, IntentRequestCode.CODE_UPLOAD_IMAGES);
        }else if (context instanceof Fragment){
            ((Fragment) context).startActivityForResult(intent, IntentRequestCode.CODE_UPLOAD_IMAGES);
        }
    }

// endregion

    //region 测量完毕 设计完毕 客户已审批 待店主确认 店主已确认 待审图纸 已审图纸 待生产招牌 待审材料 已审材料

    /**
     * 测量完毕
     * @param context
     * @param project
     */
    public static void openMeasureFinishActivity(Context context,Parcelable project){
            Intent intent = getIntent(context, CommonProgressActivity.class);
            intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_MEASURE_FINISH);
            intent.putExtra(IntentKey.DATA,project);
            context.startActivity(intent);
    }

    /**
     * 设计完毕
     * @param context
     * @param project
     */
    public static void openDesignFinishActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_DESIGN_FINISH);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 客户已审批
     * @param context
     * @param project
     */
    public static void openCustomerConfirmedActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_CUSTOMER_VERIFIED);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 待店主确认
     * @param context
     * @param project
     */
    public static void openWaitShopkeeperConfirmActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_WAIT_SHOPKEEPER_CONFIRM);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 店主已确认
     * @param context
     * @param project
     */
    public static void openShopkeeperConfirmedActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_SHOPKEEPER_CONFIRMED);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 待审图纸
     * @param context
     * @param project
     */
    public static void openWaitVerifiedDrawingActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_WAIT_VERIFIED_DRAWING);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 已审图纸
     * @param context
     * @param project
     */
    public static void openVerifiedDrawingActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_VERIFIED_DRAWING);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 待生产招牌
     * @param context
     * @param project
     */
    public static void openWaitProduceSignActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_WAIT_PRODUCE_SIGN);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }
    /**
     *待审材料
     * @param context
     * @param project
     */
    public static void openWaitVerifyMaterialActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_WAIT_VERIFY_MATERIAL);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * 已审材料
     * @param context
     * @param project
     */
    public static void openVerifiedMaterialActivity(Context context,Parcelable project){
        Intent intent = getIntent(context, CommonProgressActivity.class);
        intent.putExtra(IntentKey.TYPE,CommonProgressActivity.TYPE_VERIFIED_MATERIAL);
        intent.putExtra(IntentKey.DATA,project);
        context.startActivity(intent);
    }

    /**
     * CommonProgressActivity 读取数据
     * @param intent
     * @return int type , Parcelable data
     */
    public static Tuple CommonProgressActivityGetPara(Intent intent){
        int type = intent.getIntExtra(IntentKey.TYPE,0);
        Parcelable data = intent.getParcelableExtra(IntentKey.DATA);
        return new Tuple(type,data);
    }
    //endregion

    //region 待发货 已发货
    public static void openWaitSendActivity(Context context){
        Intent intent = getIntent(context, DeliverGoodsActivity.class);
        intent.putExtra(IntentKey.TYPE,DeliverGoodsActivity.TYPE_WAITE_SEND);
        context.startActivity(intent);
    }

    public static void openSentActivity(Context context){
        Intent intent = getIntent(context, DeliverGoodsActivity.class);
        intent.putExtra(IntentKey.TYPE,DeliverGoodsActivity.TYPE_SENT);
        context.startActivity(intent);
    }

    public static Tuple DeliverGoodsActivityGetPara(Intent intent){
        int type = getIntent().getIntExtra(IntentKey.TYPE,0);
        return new Tuple(type);
    }
    //endregion


    public static Intent getIntent(){
        return new Intent();
    }

    public static Intent getIntent(Context context, Class activityClass){
        return new Intent(context,activityClass);
    }
}
