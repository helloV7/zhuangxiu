package com.jyt.baseapp.view.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.SelPersonAdapter;
import com.jyt.baseapp.api.BeanCallback;
import com.jyt.baseapp.bean.BaseJson;
import com.jyt.baseapp.bean.Manager;
import com.jyt.baseapp.bean.Monitor;
import com.jyt.baseapp.bean.Person;
import com.jyt.baseapp.bean.Tuple;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.itemDecoration.RecycleViewDivider;
import com.jyt.baseapp.model.UserModel;
import com.jyt.baseapp.model.impl.UserModelImpl;
import com.jyt.baseapp.util.T;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.SearchView;

import java.util.List;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by chenweiqi on 2017/11/3.
 */

public class SelPeopleActivity extends BaseActivity {
    @BindView(R.id.v_searchView)
    SearchView vSearchView;
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;
    SelPersonAdapter adapter;

    List<Person> people;

    public static final int TYPE_PROJECT_MANAGER = 1;
    public static final int TYPE_PROJECT_MONITOR = 2;
    int type;

    String parentId;

    UserModel userModel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sel_people;
    }

    @Override
    protected View getContentView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vSearchView.setVisibility(View.GONE);
        Tuple tuple = IntentHelper.SelSingleWorkerGetPara(getIntent());
        type = (int) tuple.getItem1();
        parentId = (String) tuple.getItem2();

        userModel = new UserModelImpl();
        userModel.onCreate(getContext());


        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        vRefreshRecyclerView.setAdapter(adapter = new SelPersonAdapter());

        vRefreshRecyclerView.getRecyclerView().addItemDecoration(new RecycleViewDivider(getContext(),LinearLayoutManager.VERTICAL));
        vRefreshRecyclerView.setRefreshable(false);
//        vRefreshRecyclerView.setRefreshListener(new RefreshListenerAdapter() {
//            @Override
//            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
//                super.onRefresh(refreshLayout);
//            }
//
//            @Override
//            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
//                super.onLoadMore(refreshLayout);
//            }
//        });
//        List<Person> people = new ArrayList<>();

//        for (int i=0;i<10;i++){
//            Person person = new Person();
//            person.setName("zhangsan"+i);
//            people.add(person);
//        }



        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Parcelable person = (Parcelable) holder.getData();
                IntentHelper.SelSinglePersonSetResult(SelPeopleActivity.this,person);
                onBackPressed();
            }
        });

        switch (type){
            case TYPE_PROJECT_MANAGER:
                getManagerList();
                break;
            case TYPE_PROJECT_MONITOR:
                getMonitor();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userModel.onDestroy();

    }

    private void getManagerList(){
        userModel.getManagerList(new BeanCallback<BaseJson<List<Manager>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson<List<Manager>> response, int id) {
                if (response.ret){
                    vRefreshRecyclerView.setDataList(response.data);
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });
    }

    private void getMonitor(){
        userModel.getMonitorList(parentId, new BeanCallback<BaseJson<List<Monitor>>>() {
            @Override
            public void onError(Call call, Exception e, int id) {
                T.showShort(getContext(),e.getMessage());
            }

            @Override
            public void onResponse(BaseJson<List<Monitor>> response, int id) {
                if (response.ret){
                    vRefreshRecyclerView.setDataList(response.data);
                }else {
                    T.showShort(getContext(),response.forUser);
                }
            }
        });
    }


}
