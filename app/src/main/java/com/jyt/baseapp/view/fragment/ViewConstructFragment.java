package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.jyt.baseapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 查看施工
 * Created by v7 on 2017/11/4.
 */

public class ViewConstructFragment extends BaseFragment {
    @BindView(R.id.v_content)
    LinearLayout vContent;
    @BindView(R.id.v_scrollView)
    ScrollView vScrollView;
    @BindView(R.id.v_emptyMsg)
    RelativeLayout vEmptyMsg;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_view_construct;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
