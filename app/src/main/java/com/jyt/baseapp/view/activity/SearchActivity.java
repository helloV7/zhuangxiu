package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.SearchAfterAdapter;
import com.jyt.baseapp.adapter.SearchBeforAdapter;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.view.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity implements View.OnClickListener {

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
    private List<String> mList;

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
        mBeforAdapter=new SearchBeforAdapter();
        mAfterAdapter=new SearchAfterAdapter();
        mList = new ArrayList<>();
        mList.add("青花陶瓷");
        mList.add("景德窑");
        mList.add("粗陶");
        mSvSearch.setOnSearchViewTextChangedListener(new SearchView.OnSearchViewTextChangedListener() {
            @Override
            public void onTextChanged(String text) {
                if (text.length()>0){
                    mLlBefor.setVisibility(View.GONE);
                    mRvAfter.setVisibility(View.VISIBLE);
                    Log.e("@#","length="+text.length());
                }else {
                    mLlBefor.setVisibility(View.VISIBLE);
                    mRvAfter.setVisibility(View.GONE);
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
        mTvCancel.setOnClickListener(this);
        mBtnClaer.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.btn_claer:
                List<String> l=new ArrayList<>();
                mBeforAdapter.notifyData(l);
                break;

            default:
                break;
        }
    }
}
