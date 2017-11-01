package com.jyt.baseapp.itemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author LinWei on 2017/8/16 16:32
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int spaceW;//左右间隔
    private int spaceH;//上下间隔

    public SpacesItemDecoration(int spaceW, int spaceH){
        this.spaceW=spaceW;
        this.spaceH=spaceH;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left=spaceW;
        outRect.right=spaceW;
        outRect.bottom=spaceH;
        if (parent.getChildPosition(view)==0){

        }
    }
}
