package com.jyt.baseapp.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.ImageFolderAdapter;
import com.jyt.baseapp.bean.LocalMedia;
import com.jyt.baseapp.bean.LocalMediaFolder;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/1/9.
 */

public class ImgFolderFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.emptyMsg)
    TextView emptyMsg;




    OnFolderClickListener onFolderClickListener;
    ImageFolderAdapter adapter;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_img_folder;
    }

    public void setOnFolderClickListener(OnFolderClickListener onFolderClickListener) {
        this.onFolderClickListener = onFolderClickListener;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter = new ImageFolderAdapter());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));


        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                if (onFolderClickListener!=null){
                    onFolderClickListener.onClick(((LocalMediaFolder) holder.getData()).getImages());
                }
            }
        });
    }

    public void setDataList(List<LocalMediaFolder> folders){
        boolean haveContent = (folders!=null&&folders.size()!=0);
        emptyMsg.setVisibility(haveContent?View.GONE:View.VISIBLE);

        adapter.setDataList(folders);
        adapter.notifyDataSetChanged();
    }

    public interface OnFolderClickListener{
        void onClick(List<LocalMedia> medias);
    }
}
