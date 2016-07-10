package com.xiefei.openmusicplayer.ui.custom;

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
        if(parent.getChildLayoutPosition(view)%horizontalCount == 0){
            outRect.set(0,0,divideHeight/2,divideHeight);
        }else if(parent.getChildLayoutPosition(view)%horizontalCount == divideHeight-1){
            outRect.set(divideHeight/2,0,0,divideHeight);
        }else {
            outRect.set(divideHeight/2,0,divideHeight/2,divideHeight);
        }


    }
}
