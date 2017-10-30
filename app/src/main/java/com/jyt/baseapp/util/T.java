package com.jyt.baseapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenweiqi on 2017/1/6.
 */

public class T {
    public static void showShort(Context context , String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }
}
