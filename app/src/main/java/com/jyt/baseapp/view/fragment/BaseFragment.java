package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by chenweiqi on 2017/5/10.
 */

public abstract class BaseFragment extends Fragment {

    protected View rootView;

    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            rootView = inflater.from(getContext()).inflate(getLayoutId(),container,false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        return rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder!=null)
            unbinder.unbind();
    }

    protected abstract int getLayoutId();
}
