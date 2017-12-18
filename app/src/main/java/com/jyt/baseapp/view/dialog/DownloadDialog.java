package com.jyt.baseapp.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.jyt.baseapp.R;

/**
 * @author LinWei on 2017/12/18 15:43
 */
public class DownloadDialog extends Dialog {
    private Context context;
    private View mView;
    private int width;
    private int height;
    private TextView tv_download;
    private TextView tv_cancel;


    public DownloadDialog(@NonNull Context context) {
        super(context,R.style.IPhoneDialog);
        this.context=context;
        mView= View.inflate(context, R.layout.dialog_download,null);
        mView.measure(0,0);
        height=mView.getMeasuredHeight();
        width=mView.getMeasuredWidth();
        tv_download = (TextView) mView.findViewById(R.id.tv_download);
        tv_cancel = (TextView) mView.findViewById(R.id.tv_cancel);
    }

    public DownloadDialog(@NonNull Context context, int layoutID) {
        super(context, R.style.IPhoneDialog);
        this.context=context;

    }

    protected DownloadDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.horizontalMargin=0;//设置水平方向的Margin
        params.verticalMargin=0;//设置竖直方向的Margin
        params.height=height;//设置高度
        params.dimAmount=0.7f;//背景黑暗度
        params.width= WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        getWindow().setGravity(Gravity.BOTTOM);//对齐
        getWindow().setAttributes(params);//加入设置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//背景黑暗
        getWindow().setWindowAnimations(R.style.MoveDialog);//加入动画style
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadDialog.this.dismiss();
            }
        });

        tv_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.OnClick();
                }
                DownloadDialog.this.dismiss();
            }
        });


    }

    public View getView(){
        return mView;
    }

    private OnDownloadListener listener;
    public void setOnDownloadListener(OnDownloadListener listener){
        this.listener = listener;
    }
    public interface OnDownloadListener{
        void OnClick();
    }

}
