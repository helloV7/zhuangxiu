package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.BaseRcvAdapter;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by v7 on 2017/11/4.
 */

public class RefreshRecyclerView extends LinearLayout {
    @BindView(R.id.v_recyclerView)
    RecyclerView vRecyclerView;
    @BindView(R.id.v_refreshLayout)
    TwinklingRefreshLayout vRefreshLayout;
    @BindView(R.id.v_emptyView)
    RelativeLayout vEmptyView;
    @BindView(R.id.text_emptyHint)
    TextView textEmptyHint;

    private BaseRcvAdapter adapter;

    public boolean showEmptyHint = true;


    public boolean refreshable = true;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_refresh_recyclerview, this, true);
        ButterKnife.bind(this);

    }

    public RefreshRecyclerView setEmptyHintText(String text){
        textEmptyHint.setText(text);
        return this;
    }

    public RefreshRecyclerView setLayoutManager(RecyclerView.LayoutManager layoutManager){
        vRecyclerView.setLayoutManager(layoutManager);
        return this;
    }

    public RefreshRecyclerView setAdapter(BaseRcvAdapter adapter){
        this.adapter = adapter;
        vRecyclerView.setAdapter(adapter);
        return this;
    }

    public RefreshRecyclerView setRefreshListener(final RefreshListenerAdapter refreshListener){
        vRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onPullingDown(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullingDown(refreshLayout, fraction);
                if (refreshListener!=null)
                refreshListener.onPullingDown(refreshLayout,fraction);
            }

            @Override
            public void onPullingUp(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullingUp(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullingUp(refreshLayout,fraction);
            }

            @Override
            public void onPullDownReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullDownReleasing(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullDownReleasing(refreshLayout, fraction);

            }

            @Override
            public void onPullUpReleasing(TwinklingRefreshLayout refreshLayout, float fraction) {
                super.onPullUpReleasing(refreshLayout, fraction);
                if (refreshListener!=null)

                    refreshListener.onPullUpReleasing(refreshLayout, fraction);

            }

            @Override
            public void onFinishRefresh() {
                super.onFinishRefresh();
                if (refreshListener!=null)

                    refreshListener.onFinishRefresh();

            }

            @Override
            public void onFinishLoadMore() {
                super.onFinishLoadMore();
                if (refreshListener!=null)

                    refreshListener.onFinishLoadMore();
                showEmptyViewWhenListEmpty();
            }

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                if (refreshListener==null){
                    finishRefreshing();
                }else {
                    refreshListener.onRefresh(refreshLayout);
                }
                showEmptyViewWhenListEmpty();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                if (refreshListener==null){
                    refreshListener.onFinishLoadMore();
                }else {
                    refreshListener.onLoadMore(refreshLayout);
                }
            }
        });
        return this;
    }

    private void showEmptyViewWhenListEmpty(){
        if (!isShowEmptyHint()){
            vEmptyView.setVisibility(GONE);
            return;
        }
        if (adapter==null||adapter.getDataList()==null||adapter.getDataList().size()==0){
            vEmptyView.setVisibility(VISIBLE);
        }else {
            vEmptyView.setVisibility(GONE);
        }
    }

    public BaseRcvAdapter getAdapter() {
        return adapter;
    }

    public boolean isShowEmptyHint() {
        return showEmptyHint;
    }

    public void setShowEmptyHint(boolean showEmptyHint) {
        this.showEmptyHint = showEmptyHint;
    }
    public boolean isRefreshable() {
        return refreshable;
    }

    public void setRefreshable(boolean refreshable) {
        this.refreshable = refreshable;
        vRefreshLayout.setEnableRefresh(refreshable);
    }

    public RecyclerView getRecyclerView(){
        return vRecyclerView;
    }

    public void setDataList(List dataList){
        adapter.setDataList(dataList);
        adapter.notifyDataSetChanged();
        showEmptyViewWhenListEmpty();
    }

    public void finishLoadMore(){
        vRefreshLayout.finishLoadmore();
    }
    public void finishRefreshing(){
        vRefreshLayout.finishRefreshing();
    }

}
