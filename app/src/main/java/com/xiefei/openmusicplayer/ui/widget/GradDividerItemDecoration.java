package com.xiefei.openmusicplayer.ui.widget;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiefei on 16/7/10.
 */
public class GradDividerItemDecoration extends RecyclerView.ItemDecoration{
    private int divideHeight;
    private Paint paint;
    private int horizontalCount;
    public GradDividerItemDecoration(int color, int divideHeight,int horizontalCount){
        this.divideHeight = divideHeight;
        this.horizontalCount = horizontalCount;
        paint = new Paint();
        paint.setColor(color);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        c.drawRect(0,0,parent.getWidth(),divideHeight,paint);
        c.drawRect(0,0,parent.getWidth(),divideHeight,paint);
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int left = 0,top = 0,right = 0,bottom = 0;
//        if(parent.getChildLayoutPosition(view)<horizontalCount)
//            top = 0;
        if(parent.getChildLayoutPosition(view)%horizontalCount == 0){
//            left = divideHeight;
            right = divideHeight/2;
            bottom = divideHeight;
        }else if(parent.getChildLayoutPosition(view)%horizontalCount == horizontalCount-1){
            left = divideHeight/2;
//            right = divideHeight;
            bottom = divideHeight;
        }else {
            left = divideHeight /2;
            right = divideHeight /2;
//            bottom = divideHeight;
        }
        outRect.set(left,top,right,bottom);
    }
}
