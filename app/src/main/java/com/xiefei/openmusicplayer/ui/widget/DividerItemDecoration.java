package com.xiefei.openmusicplayer.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiefei on 16/7/10.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration{
    private int color;
    private int divideHeight;
    private Paint paint;
    public DividerItemDecoration(int color,int divideHeight){
        this.color = color;
        this.divideHeight = divideHeight;
        paint = new Paint();
        paint.setColor(color);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        for(int i = 0;i < parent.getChildCount();i++){
            View v = parent.getChildAt(i);
            c.drawRect(v.getLeft(),v.getTop(),v.getRight(),v.getTop()+divideHeight,paint);
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(0,0,0,divideHeight);
    }
}
