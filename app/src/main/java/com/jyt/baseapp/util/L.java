package com.jyt.baseapp.util;

import android.util.Log;

/**
 * Created by chenweiqi on 2017/1/20.
 */

public class L {
    static String TAG = "LOG";
    public static void e(String message){
        e(TAG,message);
    }
    public static void e(String key,Object value){
        if (value instanceof Object[]){
            printArray((Object[]) value);
        }
        Log.e(TAG,"key\t"+key+"\nvalue\t"+value.toString()+"\n");
    }

    private static void printArray(Object[] objects){
        int i=0;
        for (Object o:
             objects) {
            Log.e(TAG,o.toString());
        }
    }
}
