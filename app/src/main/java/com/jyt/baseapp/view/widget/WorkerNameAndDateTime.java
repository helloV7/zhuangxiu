package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/10/31.
 */

public class WorkerNameAndDateTime extends FrameLayout {
    @BindView(R.id.text_workerInfo)
    TextView textWorkerInfo;
    @BindView(R.id.text_dateTime)
    TextView textDateTime;

    public WorkerNameAndDateTime(@NonNull Context context) {
        super(context, null);
    }

    public WorkerNameAndDateTime(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_worker_name_and_update_time, this, true);
    }

    public void setWorkerText(String text){
        textWorkerInfo.setText(text);
    }

    public void setUpdateTime(String text){
        textDateTime.setText(text);
    }
}
