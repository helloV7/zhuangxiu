package com.jyt.baseapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.util.L;
import com.jyt.baseapp.view.activity.BaseActivity;
import com.jyt.baseapp.view.activity.BrowseImagesActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenweiqi on 2017/10/30.
 */

public class IntentHelper {

    public static void openSelImageActivityForResult(Context context, int selCount){

    }


    public static void openBrowseImagesActivity(Context context ,String image){
        List images = new ArrayList();
        images.add(image);
        openBrowseImagesActivity(context,images);
    }

    public static void openBrowseImagesActivity(Context context , List<String> images){
        Intent intent = getIntent(context, BrowseImagesActivity.class);
        intent.putStringArrayListExtra(IntentKey.IMAGES, (ArrayList<String>) images);

        context.startActivity(intent);
    }

    public static Tuple BrowseImagesActivityGetPara(Intent intent){
        List list = intent.getStringArrayListExtra(IntentKey.IMAGES);
        int startIndex = intent.getIntExtra(IntentKey.START_INDEX,0);
        return new Tuple(list,startIndex);
    }




    public static Intent getIntent(Context context, Class activityClass){
        return new Intent(context,activityClass);
    }
}
