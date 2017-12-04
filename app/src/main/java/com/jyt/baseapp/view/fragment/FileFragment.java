package com.jyt.baseapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.FileAdapter;
import com.jyt.baseapp.bean.FileBean;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.ShareModel;
import com.jyt.baseapp.view.activity.FileDetailActivity;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.LoadingDialog;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LinWei on 2017/11/8 10:25
 */
public class FileFragment extends BaseFragment {

    @BindView(R.id.rv_container)
    RecyclerView mRvContainer;
    Unbinder unbinder;
    @BindView(R.id.trl_lore)
    TwinklingRefreshLayout mTrlLore;

    private ShareModel mShareModel;
    private FileAdapter mFileAdapter;
    private LoadingDialog mDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_file;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        initData();
        initListener();
    }

    private void init() {
        mShareModel = new ShareModel();
        mFileAdapter = new FileAdapter();
        mDialog = new LoadingDialog(getActivity());
        mDialog.show();

    }

    private void initData(){
        mShareModel.getShareFile(new ShareModel.OngetFileListener() {
            @Override
            public void Result(boolean isSuccess, List<FileBean> data) {
                mDialog.dismiss();
                if (isSuccess){
                    if (data!=null && data.size()>0){
                        mFileAdapter.notifyData(data);
                    }
                }
            }
        });
        mRvContainer.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        mRvContainer.addItemDecoration(new SpacesItemDecoration(0,5));
        mRvContainer.setAdapter(mFileAdapter);
    }

    private void initListener(){
        mFileAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Intent intent =new Intent(getActivity(), FileDetailActivity.class);
                FileBean bean = (FileBean) holder.getData();
                intent.putExtra("ShareFile",bean);
                startActivity(intent);
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
