package com.jyt.baseapp.util;

import android.content.Context;

/**
 * Created by chenweiqi on 2017/1/4.
 */

public class DensityUtil {

    private DensityUtil() {
        throw new UnsupportedOperationException("DensityUtil cannot be instantiated");
    }

    public static int pxToDp(Context context,int px) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//        return dp;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int dpToPx(Context context,int dp) {
//        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
//        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
//        return px;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
