package com.xiefei.openmusicplayer.ui.widget;

import android.content.Context;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by xiefei on 16/8/16.
 */
public class CompatCollapsingToolbarLayout extends CollapsingToolbarLayout {
    private boolean mLayoutReady;
    public CompatCollapsingToolbarLayout(Context context) {
        super(context);
    }

    public CompatCollapsingToolbarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompatCollapsingToolbarLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!mLayoutReady) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
                if ((getWindowSystemUiVisibility() &
                        (SYSTEM_UI_FLAG_LAYOUT_STABLE|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) ==
                        (SYSTEM_UI_FLAG_LAYOUT_STABLE|SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)) {
                    try {
                        Field mLastInsets = CollapsingToolbarLayout.class.getDeclaredField("mLastInsets");
                        mLastInsets.setAccessible(true);
                        mLastInsets.set(this, null);
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            mLayoutReady = true;
        }

        super.onLayout(changed, left, top, right, bottom);
    }
}
