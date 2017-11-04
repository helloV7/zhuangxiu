package com.jyt.baseapp.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by chenweiqi on 2017/6/20.
 */

public class SoftInputUtil {
    public static void showSoftKeyboard(Context context, View inputView){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (!imm.isActive())
            inputView.requestFocus();
            imm.showSoftInput(inputView,InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void hideSoftKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }
}
