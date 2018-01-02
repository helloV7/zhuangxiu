package com.jyt.baseapp.view.viewholder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.SearchBean;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/3 15:42
 */
public class ProjectHolder extends BaseViewHolder<SearchBean> {

    @BindView(R.id.tv_shopName)
    TextView mTvShopName;
    @BindView(R.id.tv_shopMsg)
    TextView mTvShopMsg;
    @BindView(R.id.tv_shopStation)
    TextView mTvShopStation;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_shopLocation)
    TextView mTvShopLocation;

    public ProjectHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false));
    }

    @Override
    public void setData(SearchBean data) {
        super.setData(data);
        mTvShopName.setText(data.getProjectName());
        mTvShopMsg.setText(data.getBrandName()+" "+data.getSubClassName());
        mTvShopLocation.setText(data.getAddress());
        if ("1".equals(data.getIsfrozen())){
            mTvShopStation.setText("暂停中");
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.color_stop));
        }else {
            Log.e("@#",data.getSchedule());
            mTvShopStation.setTextColor(itemView.getResources().getColor(R.color.map_text2));
            switch (data.getSchedule()) {
                case "0":
                    mTvShopStation.setText("未开始");
                    break;
                case "1":
                    mTvShopStation.setText("测量中");
                    break;
                case "2":
                    mTvShopStation.setText("测量完毕");
                    break;
                case "100":
                    mTvShopStation.setText("设计报价");
                    break;
                case "101":
                    mTvShopStation.setText("待设计");
                    break;
                case "102":
                    mTvShopStation.setText("设计中");
                    break;
                case "103":
                    mTvShopStation.setText("设计完毕");
                    break;
                case "104":
                    mTvShopStation.setText("待报价");
                    break;
                case "105":
                    mTvShopStation.setText("报价完毕");
                    break;
                case "200":
                    mTvShopStation.setText("客户审批");
                    break;
                case "201":
                    mTvShopStation.setText("待客户审批");
                    break;
                case "202":
                    mTvShopStation.setText("客户已审批");
                    break;
                case "300":
                    mTvShopStation.setText("店主确认中");
                    break;
                case "301":
                    mTvShopStation.setText("待店主确认");
                    break;
                case "302":
                    mTvShopStation.setText("店主已确认");
                    break;
                case "400":
                    mTvShopStation.setText("备货中");
                    break;
                case "401":
                    mTvShopStation.setText("预算确认下单");
                    break;
                case "402":
                    mTvShopStation.setText("待下图纸");
                    break;
                case "403":
                    mTvShopStation.setText("图纸下单");
                    break;
                case "404":
                    mTvShopStation.setText("待审图纸");
                    break;
                case "405":
                    mTvShopStation.setText("已审图纸");
                    break;
                case "406":
                    mTvShopStation.setText("待预算复核图纸");
                    break;
                case "407":
                    mTvShopStation.setText("预算已复核图纸");
                    break;
                case "408":
                    mTvShopStation.setText("待生产招牌");
                    break;
                case "409":
                    mTvShopStation.setText("待下材料单");
                    break;
                case "410":
                    mTvShopStation.setText("已审材料单");
                    break;
                case "411":
                    mTvShopStation.setText("待备料");
                    break;
                case "412":
                    mTvShopStation.setText("钢挂已完成");
                    break;
                case "413":
                    mTvShopStation.setText("待设计");
                    break;
                case "414":
                    mTvShopStation.setText("所有材料已打包");
                    break;
                case "500":
                    mTvShopStation.setText("物流中");
                    break;
                case "501":
                    mTvShopStation.setText("待发货");
                    break;
                case "502":
                    mTvShopStation.setText("已发货");
                    break;
                case "503":
                    mTvShopStation.setText("货到待施工");
                    break;
                case "504":
                    mTvShopStation.setText("岸炮施工人员完毕");
                    break;
                case "600":
                    mTvShopStation.setText("进场施工");
                    break;
                case "601":
                    mTvShopStation.setText("施工中");
                    break;
                case "700":
                    mTvShopStation.setText("完成施工");
                    break;
                case "701":
                    mTvShopStation.setText("施工完毕");
                    break;
                case "800":
                    mTvShopStation.setText("结算阶段");
                    break;
                case "801":
                    mTvShopStation.setText("预算审核照片已回访");
                    break;
                case "802":
                    mTvShopStation.setText("待寄报销材料");
                    break;
                case "803":
                    mTvShopStation.setText("已寄报销材料");
                    break;
                case "804":
                    mTvShopStation.setText("已收款");
                    break;
                case "900":
                    mTvShopStation.setText("已完成");
                    break;
                default:
                    break;
            }
        }

    }
}
