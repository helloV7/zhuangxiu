package com.jyt.baseapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jyt.baseapp.R;
import com.jyt.baseapp.view.activity.AboutUsActivity;
import com.jyt.baseapp.view.activity.LocationActivity;
import com.jyt.baseapp.view.activity.ManeuverActivity;
import com.jyt.baseapp.view.activity.PersonInfoActivity;
import com.jyt.baseapp.view.activity.ShareActivity;
import com.jyt.baseapp.view.widget.JumpItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author LinWei on 2017/10/30 15:05
 */
public class MoreFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.item_info)
    JumpItem mItemInfo;
    @BindView(R.id.item_maneuver)
    JumpItem mItemManeuver;
    @BindView(R.id.item_location)
    JumpItem mItemLocation;
    @BindView(R.id.item_share)
    JumpItem mItemShare;
    @BindView(R.id.item_about)
    JumpItem mItemAbout;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void initListener() {
        mItemInfo.setOnClickListener(this);
        mItemManeuver.setOnClickListener(this);
        mItemLocation.setOnClickListener(this);
        mItemShare.setOnClickListener(this);
        mItemAbout.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_info:
                getActivity().startActivity(new Intent(getActivity(), PersonInfoActivity.class));
                break;
            case R.id.item_maneuver:
                getActivity().startActivity(new Intent(getActivity(), ManeuverActivity.class));
                break;
            case R.id.item_location:
                getActivity().startActivity(new Intent(getActivity(), LocationActivity.class));
                break;
            case R.id.item_share:
                getActivity().startActivity(new Intent(getActivity(), ShareActivity.class));
                break;
            case R.id.item_about:
                getActivity().startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;

            default:
                break;
        }
    }
}
