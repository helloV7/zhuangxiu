package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.PicAdapter;
import com.jyt.baseapp.bean.PicBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.model.ShareModel;
import com.jyt.baseapp.view.widget.TitleAndFlowImages;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LinWei on 2017/11/8 10:26
 */
public class PicFragment extends BaseFragment {

    @BindView(R.id.rv_container)
    RecyclerView mRvContainer;
    Unbinder unbinder;

    private ShareModel mShareModel;
    private PicAdapter mPicAdapter;

    protected int getLayoutId() {
        return R.layout.fragment_pic;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initData();
        initListener();
    }

    private void init(){
        mShareModel = new ShareModel();
        mPicAdapter = new PicAdapter();
        mRvContainer.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
//        mRvContainer.addItemDecoration(new SpacesItemDecoration(0,10));
        mRvContainer.setAdapter(mPicAdapter);
    }

    private void initData(){
        mShareModel.getSharePic(new ShareModel.OngetPicListener() {
            @Override
            public void Result(boolean isSuccess, List<PicBean> data) {
                if (isSuccess){
                    if (data!=null && data.size()>0){
//                        data.addAll(data);
//                        data.addAll(data);
                        mPicAdapter.notifyData(data);
                    }
                }
            }
        });
    }

    private void initListener(){
        mPicAdapter.setOnImageClickListener(new TitleAndFlowImages.OnImageClickListener() {
            @Override
            public void onImaegClick(String imagePath) {
                IntentHelper.openBrowseImagesActivity(getContext(),imagePath);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
