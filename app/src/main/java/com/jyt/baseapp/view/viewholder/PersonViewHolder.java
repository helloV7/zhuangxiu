package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.Manager;
import com.jyt.baseapp.bean.Monitor;

import butterknife.BindView;

/**
 * Created by v7 on 2017/11/4.
 */

public class PersonViewHolder extends BaseViewHolder {
    @BindView(R.id.text_name)
    TextView textName;

    public PersonViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_person, parent, false));

    }

    @Override
    public void setData(Object data) {
        super.setData(data);
        if (data instanceof Monitor){
            textName.setText(((Monitor) data).getMonitorName());
        }
        if (data instanceof Manager){
            textName.setText(((Manager) data).getNickName());
        }
    }
}
