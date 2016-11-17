package com.jaysonss.meizhilook.mvp.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.jaysonss.meizhilook.R;

/**
 * Created by jaybor on 2016/11/17.
 */
public class ScaleTypeImageView extends View {

    public static final int INVALID_SCALE_TYPE = -1;
    private Matrix mDrawMatrix;

    private Drawable mDrawable;

    private int mScaleType;

    private int mDrawableWidth;

    private int mDrawableHeight;

    public ScaleTypeImageView(Context context) {
        this(context, null);
    }

    public ScaleTypeImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScaleTypeImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScaleTypeImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ScaleTypeImageView);
        mScaleType = a.getInt(R.styleable.ScaleTypeImageView_scaleTypee, INVALID_SCALE_TYPE);
        a.recycle();
        mDrawMatrix = new Matrix();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDrawable != null) {
            BitmapDrawable bmd = (BitmapDrawable) mDrawable;
            Bitmap srcBmp = bmd.getBitmap();
            Bitmap dstBmp = Bitmap.createBitmap(mDrawableWidth,mDrawableHeight,srcBmp.getConfig());
            if (mDrawMatrix != null) {
                mDrawMatrix.setTranslate(getPaddingLeft(), getPaddingTop());
                canvas.concat(mDrawMatrix);
            }
            canvas.drawBitmap();
        }
    }

    public void setImageRes(int resId) {
        mDrawable = getResources().getDrawable(resId);
        mDrawableWidth = mDrawable.getIntrinsicWidth();
        mDrawableHeight = mDrawable.getIntrinsicHeight();
        if (mScaleType != INVALID_SCALE_TYPE) {
            float dx = 0f, dy = 0f, scale = 1.0f;
            int drawableWidth = mDrawable.getIntrinsicWidth();
            int drawableHeight = mDrawable.getIntrinsicHeight();
            int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int viewHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
            switch (mScaleType) {
                case 0:
                    //center
                    dx = (viewWidth - drawableWidth) * 0.5f;
                    dy = (viewHeight - drawableHeight) * 0.5f;
                    mDrawMatrix.setTranslate(dx, dy);
                    break;

                case 1:
                    //centerInside
                    if (drawableWidth < viewWidth && drawableHeight < viewHeight) {
                        dx = (viewWidth - drawableWidth) * 0.5f;
                        dy = (viewHeight - drawableHeight) * 0.5f;
                    } else {
                        scale = Math.min(1.0f * viewWidth / drawableWidth, 1.0f * viewHeight / drawableHeight);
                        dx = (float) ((viewWidth - drawableWidth * scale) * 0.5);
                        dy = (float) ((viewHeight - drawableHeight * scale) * 0.5);
                    }
                    mDrawMatrix.setScale(scale, scale);
                    mDrawableWidth *= scale;
                    mDrawableHeight *= scale;
                    mDrawMatrix.postTranslate(dx, dy);
                    break;

                case 2:
                    //centerCrop
                    if (drawableWidth > viewWidth && drawableHeight > viewHeight) {
                        dx = (viewWidth - drawableWidth) * 0.5f;
                        dy = (viewHeight - drawableHeight) * 0.5f;
                    } else {
                        scale = Math.max(1.0f * viewWidth / drawableWidth, 1.0f * viewHeight / drawableHeight);
                        dx = (float) ((viewWidth - drawableWidth * scale) * 0.5);
                        dy = (float) ((viewHeight - drawableHeight * scale) * 0.5);
                    }
                    mDrawableWidth *= scale;
                    mDrawableHeight *= scale;
                    mDrawMatrix.setScale(scale, scale);
                    mDrawMatrix.postTranslate(dx, dy);
                    break;
            }
            invalidate();
        }
    }

}
