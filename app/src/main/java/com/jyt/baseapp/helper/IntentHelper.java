package com.jyt.baseapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.activity.BaseActivity;
import com.jyt.baseapp.view.activity.BrowseImagesActivity;
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




    public static Intent getIntent(){
        return new Intent();
    }

    public static Intent getIntent(Context context, Class activityClass){
        return new Intent(context,activityClass);
    }
}
