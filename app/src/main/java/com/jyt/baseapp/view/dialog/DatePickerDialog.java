package com.jyt.baseapp.view.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.L;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.aigestudio.datepicker.bizs.themes.DPBaseTheme;
import cn.aigestudio.datepicker.bizs.themes.DPTManager;
import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * Created by chenweiqi on 2017/11/2.
 */

public class DatePickerDialog extends AlertDialog {

    OnDateSelectedListener onDateSelectedListener;

    public DatePickerDialog(Context context) {
        super(context, R.style.customDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DPTManager dptManager = DPTManager.getInstance();
        dptManager.initCalendar(new DPBaseTheme(){
            @Override
            public int colorTitleBG() {
                return getContext().getResources().getColor(R.color.enableColor);
            }

        });

        DatePicker datePicker = new DatePicker(getContext());
        Calendar calendar = Calendar.getInstance();

        L.e(calendar.toString());
        datePicker.setDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH));
        datePicker.setMode(DPMode.SINGLE);
        datePicker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                if (onDateSelectedListener!=null){
                    onDateSelectedListener.onSelected(date);
                }
                dismiss();
            }
        });


        setContentView(datePicker);

        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.dialogBottomInOutAnim);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void setOnDateSelectedListener(OnDateSelectedListener onDateSelectedListener) {
        this.onDateSelectedListener = onDateSelectedListener;
    }

    public interface OnDateSelectedListener{
        void onSelected(String date);
    }
}
