package com.jyt.baseapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.jyt.baseapp.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenweiqi on 2017/6/1.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    List<String> titles;

    public FragmentViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void addFragment(Fragment fragment){
        getFragments().add(fragment);
    }
    public void addFragment(Fragment fragment,String title){
        getFragments().add(fragment);
        getTitles().add(title);
    }


    public List getFragments() {
        if (fragments==null){
            fragments = new ArrayList<>();
        }
        return fragments;
    }

    public void setFragments(List fragments) {
        this.fragments = fragments;
    }

    public List<String> getTitles() {
        if (titles == null){
            titles = new ArrayList<>();
        }
        return titles;
    }

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles!=null){
            if (titles.size()>position
                    &&position>=0){
                return titles.get(position);
            }
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        if (fragments!=null){
            return fragments.size();
        }
        return 0;
    }

    @Override
    public void finishUpdate(View container) {
        super.finishUpdate(container);
    }
}
