package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.widget.Button;

import com.jyt.baseapp.R;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/8 10:26
 */
public class PicFragment extends BaseFragment {
    @BindView(R.id.button)
    Button btn;
    @BindView(R.id.design_bottom_sheet)
    View bottomView;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_pic;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final BottomSheetBehavior behavior=BottomSheetBehavior.from(bottomView);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet(behavior);
            }
        });

    }

    private void showBottomSheet(BottomSheetBehavior behavior) {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }


}
