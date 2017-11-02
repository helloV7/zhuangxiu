package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.ArabicToChineseUtils;
import com.jyt.baseapp.util.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 发货组件
 * Created by chenweiqi on 2017/11/2.
 */

public class DeliverGoodsItem extends LinearLayout {
    @BindView(R.id.text_batch)
    TextView textBatch;
    @BindView(R.id.img_del)
    ImageView imgDel;
    @BindView(R.id.input_goodsName)
    EditText inputGoodsName;
    @BindView(R.id.LT_companyName)
    LabelAndTextItem LTCompanyName;
    @BindView(R.id.LT_companyTel)
    LabelAndTextItem LTCompanyTel;
    @BindView(R.id.LT_oddNumbers)
    LabelAndTextItem LTOddNumbers;
    @BindView(R.id.LT_beginTime)
    LabelAndTextItem LTBeginTime;
    @BindView(R.id.LT_endTime)
    LabelAndTextItem LTEndTime;

    OnSelBeginTimeClick onSelBeginTimeClick;
    OnSelEndTimeClick onSelEndTimeClick;
    OnSelDelClick onSelDelClick;

    public DeliverGoodsItem(Context context) {
        this(context, null);
    }

    public DeliverGoodsItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_deliver_goods_item, this, true);
        ButterKnife.bind(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int batch  = getIndexInParent()+ 1;
        textBatch.setText(getResources().getString(R.string.batch, ArabicToChineseUtils.formatInteger(batch)));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        imgDel.setVisibility(enabled?VISIBLE:GONE);
        inputGoodsName.setEnabled(enabled);
        LTCompanyName.setEnabled(enabled);
        LTCompanyTel.setEnabled(enabled);
        LTBeginTime.setEnabled(enabled);
        LTEndTime.setEnabled(enabled);
        LTOddNumbers.setEnabled(enabled);
    }

    public String getGoodsName(){
        return inputGoodsName.getText().toString();
    }

    public void setGoodsName(String text){
        inputGoodsName.setText(text);
    }

    public String getOddNumbers(){
        return LTOddNumbers.getInputText();
    }

    public void setOddNumbers(String text){
        LTOddNumbers.setInputText(text);
    }

    public void setCompanyTel(String text){
        LTCompanyTel.setInputText(text);
    }

    public String getCompanyTel(){
        return LTCompanyTel.getInputText();
    }

    public String getCompanyName(){
        return LTCompanyName.getInputText();
    }

    public void setCompanyName(String text){
        LTCompanyName.setInputText(text);
    }

    public void setBeginTime(String text){
        LTBeginTime.setValueText(text);
    }

    public String getBeginTime(){
        return LTBeginTime.getValueText();
    }

    public void setEndTime(String text){
         LTEndTime.setValueText(text);
    }

    public String getEndTime(){
        return LTEndTime.getValueText();
    }

    @OnClick(R.id.img_del)
    public void onDelClick(){
        if (onSelDelClick!=null)
            onSelDelClick.onClick(getIndexInParent());
    }

    @OnClick(R.id.LT_beginTime)
    public void onBeginTimeClick(){
        if (onSelBeginTimeClick!=null)
            onSelBeginTimeClick.onClick(getIndexInParent());
    }

    @OnClick(R.id.LT_endTime)
    public void onEndTimeClick(){
        if (onSelEndTimeClick!=null)
            onSelEndTimeClick.onClick(getIndexInParent());
    }

    public int getIndexInParent(){
        return ((ViewGroup) getParent()).indexOfChild(this) ;
    }

    public void setOnSelBeginTimeClick(OnSelBeginTimeClick onSelBeginTimeClick) {
        this.onSelBeginTimeClick = onSelBeginTimeClick;
    }

    public void setOnSelEndTimeClick(OnSelEndTimeClick onSelEndTimeClick) {
        this.onSelEndTimeClick = onSelEndTimeClick;
    }

    public void setOnSelDelClick(OnSelDelClick onSelDelClick) {
        this.onSelDelClick = onSelDelClick;
    }

    public interface OnSelBeginTimeClick{
        void onClick(int indexInParent);
    }
    public interface OnSelEndTimeClick{
        void onClick(int indexInParent);
    }

    public interface OnSelDelClick{
        void onClick(int indexInParent);
    }
}

