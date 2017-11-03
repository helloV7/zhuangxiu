package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.widget.ProgressLine;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author LinWei on 2017/11/2 13:54
 */
public class ShopProgressFragment extends BaseFragment {
    @BindView(R.id.progress_zhangliang)
    ProgressLine ll_append;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shopprogress;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<String> list =new ArrayList<>();
        list.add("@");
        list.add("@1");
        list.add("@2");
        ll_append.setAppendData(list);
        ll_append.setStation(true);
    }
}
