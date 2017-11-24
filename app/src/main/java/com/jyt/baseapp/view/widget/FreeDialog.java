package com.jyt.baseapp.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/9/7 14:41
 */
public class FreeDialog extends Dialog {

    private Context context;
    private View mView;
    int layoutId;
    public FreeDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    public FreeDialog(@NonNull Context context, int layoutID) {
        super(context, R.style.IPhoneDialog);
        this.context=context;
        layoutId = layoutID;
        mView= View.inflate(context, layoutID,null);
    }

    protected FreeDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);

//        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
//        params.gravity= Gravity.LEFT;//设置对齐方式
//        params.horizontalMargin=0;//设置水平方向的Margin
//        params.verticalMargin=0;//设置竖直方向的Margin
//        params.height=getWindow().getWindowManager().getDefaultDisplay().getHeight();//设置高度
//        params.width= WindowManager.LayoutParams.WRAP_CONTENT;//设置宽度自适应，需要在布局的每一个控件都明确标识了宽度才有效，不能在width中输入WRAP_CONTENT
//        getWindow().setGravity(Gravity.LEFT);//对齐
//        getWindow().setAttributes(params);//加入设置
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

    }

    public View getView(){
        return mView;
    }


}
