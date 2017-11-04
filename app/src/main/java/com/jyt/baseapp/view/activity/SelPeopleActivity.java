package com.jyt.baseapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.SelPersonAdapter;
import com.jyt.baseapp.bean.Person;
import com.jyt.baseapp.helper.IntentHelper;
import com.jyt.baseapp.view.viewholder.BaseViewHolder;
import com.jyt.baseapp.view.widget.RefreshRecyclerView;
import com.jyt.baseapp.view.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by chenweiqi on 2017/11/3.
 */

public class SelPeopleActivity extends BaseActivity {
    @BindView(R.id.v_searchView)
    SearchView vSearchView;
    @BindView(R.id.v_refreshRecyclerView)
    RefreshRecyclerView vRefreshRecyclerView;

    SelPersonAdapter adapter;
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

        vRefreshRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        vRefreshRecyclerView.setAdapter(adapter = new SelPersonAdapter());

        List<Person> people = new ArrayList<>();

        for (int i=0;i<10;i++){
            Person person = new Person();
            person.setName("zhangsan"+i);
            people.add(person);
        }

        vRefreshRecyclerView.setDataList(people);


        adapter.setOnViewHolderClickListener(new BaseViewHolder.OnViewHolderClickListener() {
            @Override
            public void onClick(BaseViewHolder holder) {
                Person person = (Person) holder.getData();
                Intent intent = new Intent();
                IntentHelper.SelSinglePersonSetResult(SelPeopleActivity.this,person);
                onBackPressed();
            }
        });
    }
}
