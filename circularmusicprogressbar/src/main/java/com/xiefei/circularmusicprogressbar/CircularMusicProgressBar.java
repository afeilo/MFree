package com.xiefei.circularmusicprogressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;



public class CircularMusicProgressBar extends ImageView {

    private static final ScaleType SCALE_TYPE = ScaleType.CENTER_CROP;

    private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    private static final int COLORDRAWABLE_DIMENSION = 2;

    private static final int DEFAULT_ProgressBar_WIDTH = 0;
    private static final int DEFAULT_ProgressBar_COLOR = Color.BLACK;
    private static final int DEFAULT_FILL_COLOR = Color.TRANSPARENT;
    private static final boolean DEFAULT_ProgressBar_OVERLAY = false;

    private final RectF mDrawableRect = new RectF();
    private final RectF mProgressBarRect = new RectF();

    private final Matrix mShaderMatrix = new Matrix();
    private final Paint mBitmapPaint = new Paint();
    private final Paint mProgressBarPaint = new Paint();
    private final Paint mFillPaint = new Paint();
    private final Paint mSecondProgressBarPaint = new Paint();

    private int mProgressBarColor = DEFAULT_ProgressBar_COLOR;
    private int mSecondProgressBarColor = DEFAULT_ProgressBar_COLOR;
    private int mProgressBarWidth = DEFAULT_ProgressBar_WIDTH;
    private int mFillColor = DEFAULT_FILL_COLOR;
    private float mContentScale = 1;
    private float mProgress;

    private Bitmap mBitmap;
    private BitmapShader mBitmapShader;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private float mDrawableRadius;
    private float mProgressBarRadius;

    private ColorFilter mColorFilter;

    private boolean mReady;
    private boolean mSetupPending;
    private boolean mProgressBarOverlay;

    public CircularMusicProgressBar(Context context) {
        super(context);

        init();
    }
    public CircularMusicProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public CircularMusicProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircularMusicProgressBar, defStyle, 0);

        mProgressBarWidth = a.getDimensionPixelSize(R.styleable.CircularMusicProgressBar_progress_bar_width, DEFAULT_ProgressBar_WIDTH);
        mProgressBarColor = a.getColor(R.styleable.CircularMusicProgressBar_progress_bar_color, DEFAULT_ProgressBar_COLOR);
        mSecondProgressBarColor = a.getColor(R.styleable.CircularMusicProgressBar_progress_bar_second_color, DEFAULT_ProgressBar_COLOR);
        mProgressBarOverlay = a.getBoolean(R.styleable.CircularMusicProgressBar_civ_border_overlay, DEFAULT_ProgressBar_OVERLAY);
        mFillColor = a.getColor(R.styleable.CircularMusicProgressBar_civ_fill_color, DEFAULT_FILL_COLOR);
        mContentScale = a.getFloat(R.styleable.CircularMusicProgressBar_mContentScale,mContentScale);
        a.recycle();

        init();
    }

    private void init() {
        super.setScaleType(SCALE_TYPE);
        mReady = true;

        if (mSetupPending) {
            setup();
            mSetupPending = false;
        }
    }

    @Override
    public ScaleType getScaleType() {
        return SCALE_TYPE;
    }

    @Override
    public void setScaleType(ScaleType scaleType) {
        if (scaleType != SCALE_TYPE) {
            throw new IllegalArgumentException(String.format("ScaleType %s not supported.", scaleType));
        }
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        if (adjustViewBounds) {
            throw new IllegalArgumentException("adjustViewBounds not supported.");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mBitmap == null) {
            return;
        }

        if (mFillColor != Color.TRANSPARENT) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mDrawableRadius, mFillPaint);
        }
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mDrawableRadius-mProgressBarWidth, mBitmapPaint);
        if (mProgressBarWidth != 0) {
            canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, mDrawableRadius, mProgressBarPaint);
            canvas.drawArc(mDrawableRect,0,180,false, mSecondProgressBarPaint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setup();
    }
    

    public void setProgressBarColor(@ColorInt int ProgressBarColor) {
        if (ProgressBarColor == mProgressBarColor) {
            return;
        }

        mProgressBarColor = ProgressBarColor;
        mProgressBarPaint.setColor(mProgressBarColor);
        invalidate();
    }

    public void setProgressBarColorResource(@ColorRes int ProgressBarColorRes) {
        setProgressBarColor(getContext().getResources().getColor(ProgressBarColorRes));
    }

    public int getFillColor() {
        return mFillColor;
    }

    public void setFillColor(@ColorInt int fillColor) {
        if (fillColor == mFillColor) {
            return;
        }
        mFillColor = fillColor;
        mFillPaint.setColor(fillColor);
        invalidate();
    }

    public void setFillColorResource(@ColorRes int fillColorRes) {
        setFillColor(getContext().getResources().getColor(fillColorRes));
    }

    public int getProgressBarWidth() {
        return mProgressBarWidth;
    }

    public void setProgressBarWidth(int ProgressBarWidth) {
        if (ProgressBarWidth == mProgressBarWidth) {
            return;
        }

        mProgressBarWidth = ProgressBarWidth;
        setup();
    }

    public boolean isProgressBarOverlay() {
        return mProgressBarOverlay;
    }

    public void setProgressBarOverlay(boolean ProgressBarOverlay) {
        if (ProgressBarOverlay == mProgressBarOverlay) {
            return;
        }

        mProgressBarOverlay = ProgressBarOverlay;
        setup();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        mBitmap = bm;
        setup();
    }

    @Override
    public void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        mBitmap = getBitmapFromDrawable(drawable);
        setup();
    }

    @Override
    public void setImageResource(@DrawableRes int resId) {
        super.setImageResource(resId);
        mBitmap = getBitmapFromDrawable(getDrawable());
        setup();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        mBitmap = uri != null ? getBitmapFromDrawable(getDrawable()) : null;
        setup();
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        if (cf == mColorFilter) {
            return;
        }
        mColorFilter = cf;
        mBitmapPaint.setColorFilter(mColorFilter);
        invalidate();
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }

        if (drawable instanceof BitmapDrawable) {
            Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                    : Bitmap.Config.RGB_565;
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(), config);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }

        try {
            Bitmap bitmap;

            if (drawable instanceof ColorDrawable) {
                bitmap = Bitmap.createBitmap(COLORDRAWABLE_DIMENSION, COLORDRAWABLE_DIMENSION, BITMAP_CONFIG);
            } else {
                bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), BITMAP_CONFIG);
            }

            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void setup() {
        if (!mReady) {
            mSetupPending = true;
            return;
        }

        if (getWidth() == 0 && getHeight() == 0) {
            return;
        }

        if (mBitmap == null) {
            invalidate();
            return;
        }

        mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        mBitmapPaint.setAntiAlias(true);
        mBitmapPaint.setShader(mBitmapShader);
        mProgressBarPaint.setStyle(Paint.Style.STROKE);
        mProgressBarPaint.setAntiAlias(true);
        mProgressBarPaint.setColor(mProgressBarColor);
        mProgressBarPaint.setStrokeWidth(mProgressBarWidth);
        mSecondProgressBarPaint.setStyle(Paint.Style.STROKE);
        mSecondProgressBarPaint.setAntiAlias(true);
        mSecondProgressBarPaint.setColor(mSecondProgressBarColor);
        mSecondProgressBarPaint.setStrokeWidth(mProgressBarWidth);
        mFillPaint.setStyle(Paint.Style.FILL);
        mFillPaint.setAntiAlias(true);
        mFillPaint.setColor(mFillColor);

        mBitmapHeight = mBitmap.getHeight();
        mBitmapWidth = mBitmap.getWidth();

        mProgressBarRect.set(0, 0, getWidth(), getHeight());
        mProgressBarRadius = Math.min((mProgressBarRect.height() - mProgressBarWidth) / 2.0f, (mProgressBarRect.width() - mProgressBarWidth) / 2.0f);

        mDrawableRect.set(mProgressBarRect);
        if (!mProgressBarOverlay) {
            mDrawableRect.inset(mProgressBarWidth, mProgressBarWidth);
        }
        mDrawableRadius = Math.min(mDrawableRect.height() / 2.0f, mDrawableRect.width() / 2.0f);

        updateShaderMatrix();
        invalidate();
    }

    private void updateShaderMatrix() {
        float scale;
        float dx = 0;
        float dy = 0;

        mShaderMatrix.set(null);

        if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
            scale = (mDrawableRect.height() / (float) mBitmapHeight*mContentScale);
        } else {
            scale = (mDrawableRect.width() / (float) mBitmapWidth*mContentScale);
        }
        dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
        dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;

        mShaderMatrix.setScale(scale, scale);
        mShaderMatrix.postTranslate((int) (dx + 0.5f) + mDrawableRect.left, (int) (dy + 0.5f) + mDrawableRect.top);

        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

}