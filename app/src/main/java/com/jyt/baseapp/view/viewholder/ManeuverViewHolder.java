package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.WorkBean;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/24 11:02
 */
public class ManeuverViewHolder extends BaseViewHolder<WorkBean> {
    @BindView(R.id.tv_maneuver)
    TextView tv_work;
    public ManeuverViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_maneuver,parent,false));
    }

    @Override
    public void setData(WorkBean data) {
        super.setData(data);
        if (data.isCheck()){
            tv_work.setTextColor(itemView.getResources().getColor(R.color.map_text2));
        }else {
            tv_work.setTextColor(itemView.getResources().getColor(R.color.text_color1));
        }
        tv_work.setText(data.getType());
    }
}
