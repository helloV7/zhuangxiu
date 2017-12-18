package com.jyt.baseapp.view.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jyt.baseapp.R;
import com.jyt.baseapp.adapter.BrandAdapter;
import com.jyt.baseapp.bean.BrandBean;
import com.jyt.baseapp.itemDecoration.SpacesItemDecoration;
import com.jyt.baseapp.view.viewholder.SingleTextViewHolder;

import java.util.List;

/**
 * @author LinWei on 2017/11/2 10:24
 */
public class SingleSelector extends LinearLayout {
    RecyclerView rv_left;
    RecyclerView rv_right;
    LinearLayout ll_parent;
    ImageView iv_back;
    private List<BrandBean> sonlist;
    private BrandAdapter LeftAdapter;
    private BrandAdapter RightAdapter;

    public SingleSelector(Context context) {
        super(context);
        init(context);
    }

    public SingleSelector(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleSelector(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View.inflate(context, R.layout.layout_mapselector,this);
        ll_parent= (LinearLayout) this.findViewById(R.id.ll_parent);
        rv_left= (RecyclerView) this.findViewById(R.id.rv_select_left);
        rv_right= (RecyclerView) this.findViewById(R.id.rv_select_right);
        iv_back= (ImageView) this.findViewById(R.id.iv_select_back);
        iv_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onClickBack();
                }
            }
        });
    }

    public void setLeftAdapter(Context context, final List<BrandBean> data){
        if (LeftAdapter==null){
            LeftAdapter=new BrandAdapter(context,data,0);
            LeftAdapter.setCentenr(true);
        }
        rv_left.setAdapter(LeftAdapter);
        rv_left.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rv_left.addItemDecoration(new SpacesItemDecoration(0,80));
        LeftAdapter.notifyDataSetChanged();
        LeftAdapter.setOnSingleClickListener(new BrandAdapter.OnSingleClickListener() {
            @Override
            public void onClick(String BrandSonID, SingleTextViewHolder holder,int position) {
                for (int i = 0; i < data.size(); i++) {
                    data.get(i).setCheck(false);
                    if (i==position){
                        data.get(position).setCheck(true);
                        if (listener!=null){
                            listener.onClickBrand(data.get(i).getBrandId(),data.get(i).getBrandName());
                        }
                    }
                }
                LeftAdapter.notifyDataSetChanged();
            }
        });
        LeftAdapter.notifyDataSetChanged();
    }

    public void setRightAdapter(Context context, final List<BrandBean> data){
        if (RightAdapter==null){
            RightAdapter=new BrandAdapter(context,data,0);
            RightAdapter.setCentenr(false);
        }
        sonlist=data;
        rv_right.setAdapter(RightAdapter);
        rv_right.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        rv_right.addItemDecoration(new SpacesItemDecoration(0,60));
        RightAdapter.setOnSingleClickListener(new BrandAdapter.OnSingleClickListener() {
            @Override
            public void onClick(String BrandSonID, SingleTextViewHolder holder,int position) {
                for (int i = 0; i < sonlist.size(); i++) {
                    sonlist.get(i).setCheck(false);
                    if (i==position){
                        sonlist.get(position).setCheck(true);
                    }
                }
                if (listener!=null){
                    listener.onClickDetail(sonlist.get(position).getSubClassId(),sonlist.get(position).getSubClassName());
                }
                RightAdapter.notifyDataSetChanged();
            }
        });
        RightAdapter.notifyDataSetChanged();

    }

    public void notifyRightData(final List<BrandBean> data){
        if (RightAdapter!=null && data!=null){
            sonlist=data;
            RightAdapter.notifyData(data);
        }
    }

    public void notifyLeftData(final List<BrandBean> data){
        if (LeftAdapter!=null && data!=null && data.size()>0){
            LeftAdapter.notifyData(data);
        }
    }




    public interface OnSingleClickListener{
        void onClickBrand(String BrandID,String BrandName);
        void onClickDetail(String BrandSonID,String BrandSonName);
        void onClickBack();
    }
    private OnSingleClickListener listener;
    public void setOnSingleClickListener(OnSingleClickListener listener){
        this.listener=listener;
    }

    public void setHideDeleteIV(boolean isHide){
        if (isHide){
            iv_back.setVisibility(GONE);
        }else {
            iv_back.setVisibility(VISIBLE);
        }
    }
}
