package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.EvaluateAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EvaluateDetailActivity extends BaseActivity {

    @BindView(R.id.et_evaluate)
    EditText mEtEvaluate;
    @BindView(R.id.rb_evaluate)
    RatingBar mRbEvaluate;
    @BindView(R.id.rv_evaluates)
    RecyclerView mRvEvaluates;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_evaluate_detail;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTextTitle("评价详情");
        List<String> list=new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        EvaluateAdapter adapter=new EvaluateAdapter();
        adapter.setDataList(list);
        mRvEvaluates.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mRvEvaluates.setAdapter(adapter);
    }
}
