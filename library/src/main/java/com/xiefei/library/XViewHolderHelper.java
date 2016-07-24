package com.xiefei.library;

import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.widget.TextView;

/**
 * Created by xiefei on 16/7/11.
 */
public class XViewHolderHelper{
    private View holdView;
    private SparseArrayCompat<View> views;
    public XViewHolderHelper(View view) {
        holdView = view;
        views = new SparseArrayCompat<>();
    }

    public <V extends View>V getViewById(@IdRes int viewId){
        V v = (V) holdView.findViewById(viewId);
        if(v==null){
            v = (V) holdView.findViewById(viewId);
            views.put(viewId,v);
        }
        return v;
    }

    public View getHoldView() {
        return holdView;
    }
}
