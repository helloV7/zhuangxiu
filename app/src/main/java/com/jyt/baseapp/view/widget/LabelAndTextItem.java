package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.DensityUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by chenweiqi on 2017/11/2.
 */

public class LabelAndTextItem extends LinearLayout {
    @BindView(R.id.text_label)
    TextView textLabel;
    @BindView(R.id.text_value)
    TextView textValue;
    @BindView(R.id.img_arrow)
    ImageView imgArrow;
    @BindView(R.id.v_showValue)
    LinearLayout vShowValue;
    @BindView(R.id.input_value)
    EditText inputValue;

    public LabelAndTextItem(Context context) {
        this(context, null);
    }

    public LabelAndTextItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_label_and_text, this, true);
        ButterKnife.bind(this);
        ReadAttrs(context, attrs);

    }

    private void ReadAttrs(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.LabelAndTextItem);
        setLabelText(attributes.getString(R.styleable.LabelAndTextItem_labelText));
        setValueText(attributes.getString(R.styleable.LabelAndTextItem_valueText));

        setLabelTextSize(attributes.getDimension(R.styleable.LabelAndTextItem_labelTextSize,  13));
        setValueTextSize(attributes.getDimension(R.styleable.LabelAndTextItem_valueTextSize,  13));
        setLabelTextColor(attributes.getColor(R.styleable.LabelAndTextItem_labelTextColor, Color.parseColor("#949494")));
        setValueTextColor(attributes.getColor(R.styleable.LabelAndTextItem_labelTextColor, Color.parseColor("#1C1C1C")));

        isShowRightArrow(attributes.getBoolean(R.styleable.LabelAndTextItem_showRightArrow, false));

        needInput(attributes.getBoolean(R.styleable.LabelAndTextItem_needInput, false));

        setHintText(attributes.getString(R.styleable.LabelAndTextItem_hintText));
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        inputValue.setEnabled(enabled);
        imgArrow.setVisibility(enabled?VISIBLE:GONE);
    }

    public void setHintTextColor(@ColorInt int color){
        inputValue.setHintTextColor(color);

    }

    public void setHintText(String text){
        inputValue.setHint(text);
    }

    public void needInput(boolean showInput) {
        vShowValue.setVisibility(!showInput?VISIBLE:GONE);
        inputValue.setVisibility(showInput?VISIBLE:GONE);
    }

    public void setLabelText(String text) {
        textLabel.setText(text);
    }

    public void setValueText(String text) {
        textValue.setText(text);
    }

    public void setLabelTextColor(@ColorInt int color) {
        textLabel.setTextColor(color);
    }

    public void setValueTextColor(@ColorInt int color) {
        textValue.setTextColor(color);
    }

    public void setLabelTextSize(@Px float textSize) {
        textLabel.setTextSize(textSize);
    }

    public void setValueTextSize(@Px float textSize) {
        textValue.setTextSize(textSize);

    }

    public void isShowRightArrow(boolean isShow) {
        imgArrow.setVisibility(isShow ? VISIBLE : GONE);
    }

    public String getInputText(){
        return inputValue.getText().toString();
    }

    public void setInputText(String text){
        inputValue.setText(text);
    }

    public String getValueText(){
        return textValue.getText().toString();
    }

}
