package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.api.Const;
import com.jyt.baseapp.bean.InfoBean;
import com.jyt.baseapp.util.BaseUtil;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/7 13:58
 */
public class InfoDetailViewHolder extends BaseViewHolder<InfoBean> {
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_shopName)
    TextView mTvShopName;
    @BindView(R.id.tv_msg)
    TextView mTvMsg;

    public InfoDetailViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false));
    }

    @Override
    public void setData(InfoBean data) {
        super.setData(data);
        String msg = "";
        String lastTime = "";
        String shopName = "";
        switch (data.getState()) {
            case 0:
                shopName = data.getProjectName();
                msg = "该项目已进入"+data.getSpeedName()+"阶段，点击查看详情。";
                lastTime = BaseUtil.getTime(data.getUpdateDate());
                break;
            case 1:
                shopName = data.getBliu();
                long finishTime = Long.valueOf(data.getFinishDate()) - Long.valueOf(data.getBshiba());
                msg = "距离"+data.getTabletimeName()+"完工时间剩下"+(finishTime/(1000*60*60))+"小时，请尽快完工。";
                lastTime = BaseUtil.getTime(data.getAsishiba());
                break;
            case 2:
                shopName = data.getProjectName();
                msg = "店主已进行评价，点击查看详情。";
                switch (Const.getPositionName()) {
                    case "预算员":
                        lastTime = BaseUtil.getTime(data.getKtobtime());
                        break;
                    case "设计师":
                        lastTime = BaseUtil.getTime(data.getKtodtime());
                        break;
                    case "测量人员":
                        lastTime = BaseUtil.getTime(data.getKtomtime());
                        break;
                    case "项目经理":
                        lastTime = BaseUtil.getTime(data.getKtoptime());
                        break;

                    default:
                        break;
                }
                break;
            default:
                break;
        }
        mTvMsg.setText(msg);
        mTvShopName.setText(shopName);
        mTvTime.setText(lastTime);


    }
}
