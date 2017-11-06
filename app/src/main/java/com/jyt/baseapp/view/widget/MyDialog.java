package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.jyt.baseapp.R;
import com.jyt.baseapp.util.BaseUtil;

/**
 * @author LinWei on 2017/9/7 14:41
 */
public class MyDialog extends AlertDialog {

    private Context context;
    private View mView;

    public MyDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public MyDialog(@NonNull Context context, int layoutID) {
        super(context, R.style.customDialog);
        this.context=context;
        mView= View.inflate(context, layoutID,null);
    }

    protected MyDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.gravity= Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM;//设置对齐方式
        params.horizontalMargin=0;//设置水平方向的Margin
        params.verticalMargin=0;//设置竖直方向的Margin
        params.y=30;
        params.dimAmount=0.7f;//背景黑暗度
        params.height=WindowManager.LayoutParams.WRAP_CONTENT;
        params.width= WindowManager.LayoutParams.WRAP_CONTENT;//设置宽度自适应，需要在布局的每一个控件都明确标识了宽度才有效，不能在width中输入WRAP_CONTENT
        getWindow().setGravity(Gravity.CENTER_HORIZONTAL);//对齐
        getWindow().setAttributes(params);//加入设置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//背景黑暗
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        getWindow().setWindowAnimations(R.style.MoveDialog);//加入动画style
    }

    public View getView(){
        return mView;
    }

    public int height;
    public void setHeight(int height){
        height= BaseUtil.dip2px(height);
    }


}
