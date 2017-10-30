package com.jyt.baseapp.util;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;

/**
 * Created by chenweiqi on 2017/1/17.
 */

public class VibratorUtil {
    public static void vibratorShort(Context context){
        /**震动服务*/
        Vibrator vib = (Vibrator)context.getSystemService(Service.VIBRATOR_SERVICE);
//          vibrator.vibrate(1000);//只震动一秒，一次
        long[] pattern = {100,100};
        //两个参数，一个是自定义震动模式，
        //数组中数字的含义依次是静止的时长，震动时长，静止时长，震动时长。。。时长的单位是毫秒
        //第二个是“是否反复震动”,-1 不重复震动
        //第二个参数必须小于pattern的长度，不然会抛ArrayIndexOutOfBoundsException
        vib.vibrate(pattern, 1);

        vib = null;

    }

}
