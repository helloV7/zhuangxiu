package com.jyt.baseapp.view.viewholder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.nex3z.flowlayout.FlowLayout;


/**
 * @author LinWei on 2017/11/1 11:28
 */
public class AreaViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_city;
    public FlowLayout fl_area;
    public View line;
    public AreaViewHolder(View itemView) {
        super(itemView);
        tv_city= (TextView) itemView.findViewById(R.id.tv_selector_city);
        fl_area= (FlowLayout) itemView.findViewById(R.id.flow_area);
        line=itemView.findViewById(R.id.line_last);

    }
}
