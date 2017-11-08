package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.InfoDetailAdapter;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class InfoDetailActivity extends BaseActivity {

    @BindView(R.id.rv_container)
    RecyclerView mRvContainer;
    @BindView(R.id.iv_bottombg)
    ImageView mIvBottombg;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_info_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initRv();
    }

    private void init() {
        int state = getIntent().getIntExtra("state", 0);
        switch (state) {
            case 0:
                setTextTitle("项目进度");
                mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_blue2));
                break;
            case 1:
                setTextTitle("工作提示");
                mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_yellow2));
                break;
            case 2:
                setTextTitle("店主评价");
                mIvBottombg.setImageDrawable(getResources().getDrawable(R.mipmap.icon_green2));
                break;
        }
    }

    private void initRv(){
        InfoDetailAdapter adapter=new InfoDetailAdapter();
        List<String> list=new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        adapter.setDataList(list);
        mRvContainer.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvContainer.setAdapter(adapter);
        mRvContainer.addItemDecoration(new SpacesItemDecoration(0,30));
    }
}
