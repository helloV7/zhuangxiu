package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.SearchAfterAdapter;
import com.jyt.baseapp.adapter.SearchBeforAdapter;
import com.jyt.baseapp.bean.SearchBean;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.model.SearchModel;
import com.jyt.baseapp.util.CacheUtil;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.SearchView;

import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity  {

    @BindView(R.id.sv_search)
    SearchView mSvSearch;
    @BindView(R.id.tv_cancel)
    TextView mTvCancel;
    @BindView(R.id.rv_after)
    RecyclerView mRvAfter;
    @BindView(R.id.rv_before)
    RecyclerView mRvBefore;
    @BindView(R.id.btn_claer)
    Button mBtnClaer;
    @BindView(R.id.ll_befor)
    LinearLayout mLlBefor;
    private SearchBeforAdapter mBeforAdapter;
    private SearchAfterAdapter mAfterAdapter;
    private List<SearchBean> mList;
    private SearchModel mSearchModel;
    private CacheUtil mCacheUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
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
        initListener();
    }
    private void init(){
        HideActionBar();
        mCacheUtil = new CacheUtil();
        mSearchModel = new SearchModel();
        mBeforAdapter=new SearchBeforAdapter();
        mAfterAdapter=new SearchAfterAdapter();
        mList=mCacheUtil.getPsd();
        mSvSearch.setOnSearchViewTextChangedListener(new SearchView.OnSearchViewTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length()>0){
                    mLlBefor.setVisibility(View.GONE);
                    mRvAfter.setVisibility(View.VISIBLE);
                    String condition=text+",null,null,null,null,null,null";
                    mSearchModel.getSearchData(condition, new SearchModel.OnSearchResultListener() {
                        @Override
                        public void Result(boolean isSuccess, List<SearchBean> data) {
                            if (isSuccess){
                                mList = data;
                                mAfterAdapter.notifyData(data);
                            }
                        }
                    });
                }else {
                    mLlBefor.setVisibility(View.VISIBLE);
                    mRvAfter.setVisibility(View.GONE);
                    mAfterAdapter.notifyData(null);
                }
            }
        });

    }

    private void initRv(){
        mBeforAdapter.setDataList(mList);
        mAfterAdapter.setDataList(mList);
        mRvBefore.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvAfter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvBefore.setAdapter(mBeforAdapter);
        mRvAfter.setAdapter(mAfterAdapter);
        mRvBefore.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRvAfter.addItemDecoration(new SpacesItemDecoration(0,2));
    }

    private void initListener(){
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtnClaer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCacheUtil.clearData();
                mBeforAdapter.notifyData(mCacheUtil.getPsd());
            }
        });

        mBeforAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                SearchBean bean = (SearchBean) holder.getData();
                IntentHelper.openShopActivity(SearchActivity.this,bean);
                finish();
            }
        });

        mAfterAdapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                SearchBean bean =(SearchBean) holder.getData();
                boolean isNoContain=true;
                for (SearchBean shop:mCacheUtil.getPsd()) {
                    if (shop.getProjectId().equals(bean.getProjectId())){
                        isNoContain=false;
                    }
                }
                if (isNoContain){
                    mCacheUtil.setPsd(bean);
                }
                mBeforAdapter.notifyData(mCacheUtil.getPsd());
                IntentHelper.openShopActivity(SearchActivity.this,(SearchBean) holder.getData());
                finish();
            }
        });
    }





}
