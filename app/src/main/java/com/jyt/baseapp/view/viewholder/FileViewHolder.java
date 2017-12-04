package com.jyt.baseapp.view.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.bean.FileBean;
import com.jyt.baseapp.util.BaseUtil;
import com.jyt.baseapp.view.widget.CircleImageView;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/9 13:58
 */
public class FileViewHolder extends BaseViewHolder<FileBean> {

    @BindView(R.id.iv_logo)
    CircleImageView mIvLogo;
    @BindView(R.id.tv_msg1)
    TextView mTvMsg1;
    @BindView(R.id.tv_msg2)
    TextView mTvMsg2;
    @BindView(R.id.iv_next)
    ImageView mIvNext;
    @BindView(R.id.tv_tel)
    TextView mTvTel;

    public FileViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false));
    }

    @Override
    public void setData(FileBean data) {
        super.setData(data);
        mTvMsg1.setText(data.getShareName());
        mTvMsg2.setText(BaseUtil.getTime(data.getShareDate()));
        mTvMsg2.setVisibility(View.VISIBLE);
        switch (data.getShareSuffix()){
            case "ai":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_ai2));
                break;
            case "doc":
            case "docx":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_word2));
                break;
            case "ppt":
            case "pptx":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_power2));
                break;
            case "pdf":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_pdf2));
                break;
            case "xls":
            case "xlsx":
            case "xlsm":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_excel2));
                break;
            case "psd":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_ps2));
            case "rar":
            case "zip":
            case "arj":
            case "z":
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_rar2));
                break;
            default:
                mIvLogo.setImageDrawable(itemView.getResources().getDrawable(R.mipmap.icon_unknow2));
                break;
        }

    }
}
