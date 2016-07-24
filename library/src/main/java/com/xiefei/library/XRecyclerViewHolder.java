package com.xiefei.library;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xiefei on 16/7/11.
 */
public class XRecyclerViewHolder extends RecyclerView.ViewHolder{
    private XViewHolderHelper holderHelper;
    public XRecyclerViewHolder(View itemView) {
        super(itemView);
        holderHelper = new XViewHolderHelper(itemView);

    }
    public XViewHolderHelper getHolderHelper() {
        return holderHelper;
    }
}
