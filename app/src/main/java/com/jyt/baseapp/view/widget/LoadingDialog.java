package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.jyt.baseapp.R;


/**
 * Created by chenweiqi on 2017/1/18.
 */

public class LoadingDialog extends AlertDialog {
    private TextView tips_loading_msg;

    private String message = null;

    public LoadingDialog(Context context) {
        super(context, R.style.customDialog);
        message = "请等待";
    }

    public LoadingDialog(Context context, String message) {
        super(context, R.style.customDialog);
        this.message = message;
        this.setCancelable(true);
    }

    public LoadingDialog(Context context, int theme, String message) {
        super(context, R.style.customDialog);
        this.message = message;
        this.setCancelable(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_tips_loading);
        tips_loading_msg = (TextView) findViewById(R.id.tips_loading_msg);
        tips_loading_msg.setText(this.message);
    }

    public void setText(String message) {
        this.message = message;
        tips_loading_msg.setText(this.message);
    }

    public void setText(int resId) {
        setText(getContext().getResources().getString(resId));
    }
}
