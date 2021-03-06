package com.jaysonss.meizhilook.mvp.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

import com.jaysonss.meizhilook.utils.gestures.MoveGestureDetector;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Jaybor on 2016/11/13.
 * 可用于加载大图
 */

public class PreviewImageView extends ImageView {

    private static final String TAG = "PreviewImageView";

    private MoveGestureDetector mMoveGestureDetector;

    private ScaleGestureDetector mScaleGestureDetector;

    private BitmapRegionDecoder mDecoder;

    private Rect mRect;

    private int mImageWidth;

    private int mImageHeight;

    private BitmapFactory.Options mOptions;

    private Matrix mMatrix;

    private float mScaleFactor = 1.0f;

    public PreviewImageView(Context context) {
        super(context);
    }

    public PreviewImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();
    }

    public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PreviewImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initial() {
        mOptions = new BitmapFactory.Options();
        mRect = new Rect();
        mMatrix = new Matrix();
        mMatrix.postScale(mScaleFactor, mScaleFactor);
        mScaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                mScaleFactor *= detector.getScaleFactor();
                if (mScaleFactor > 3 || mScaleFactor < 0.5) {
                    return false;
                }
                mMatrix.setScale(mScaleFactor, mScaleFactor);
                mImageWidth *= mScaleFactor;
                mImageHeight *= mScaleFactor;
                invalidate();
                return true;
            }
        });

        mMoveGestureDetector = new MoveGestureDetector(getContext(), new MoveGestureDetector.SimpleMoveGestureDetectorListener() {
            @Override
            public boolean onMove(MoveGestureDetector moveGestureDetector) {
                int moveX = (int) moveGestureDetector.getMoveX();
                int moveY = (int) moveGestureDetector.getMoveY();

                if (mImageWidth > getWidth()) {
                    mRect.offset(-moveX, 0);
                    checkWidth();
                    invalidate();
                }

                if (mImageHeight > getHeight()) {
                    mRect.offset(0, -moveY);
                    checkHeight();
                    invalidate();
                }

                return true;
            }
        });
    }

    private void checkWidth() {
        Rect rect = mRect;
        if (rect.left < 0) {
            rect.left = 0;
            rect.right = getWidth();
            invalidate();
        }

        if (rect.right > mImageWidth) {
            rect.right = mImageWidth;
            rect.left = mImageWidth - getWidth();
        }
    }

    private void checkHeight() {
        Rect rect = mRect;
        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = getHeight();
            invalidate();
        }

        if (rect.bottom > mImageHeight) {
            rect.bottom = mImageHeight;
            rect.top = mImageHeight - getHeight();
        }
    }

    public void setInputStream(@NonNull InputStream inputStream) {
        try {
            BitmapFactory.Options tmpOptions = new BitmapFactory.Options();
            tmpOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, tmpOptions);
            mImageWidth = tmpOptions.outWidth;
            mImageHeight = tmpOptions.outHeight;

            inputStream.reset();
            mDecoder = BitmapRegionDecoder.newInstance(inputStream, false);
            requestLayout();
            invalidate();
        } catch (IOException e) {
            Logger.e(TAG, e);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //根据图片大小调整显示在屏幕上的图片
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        int imageWidth = mImageWidth;
        int imageHeight = mImageHeight;

        mRect.left = imageWidth / 2 - width / 2;
        mRect.top = imageHeight / 2 - height / 2;
        mRect.right = mRect.left + width;
        mRect.bottom = mRect.top + height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDecoder != null) {
            Bitmap bitmap = mDecoder.decodeRegion(mRect, mOptions);
            canvas.drawBitmap(bitmap, mMatrix, null);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mMoveGestureDetector.onTouchEvent(event);
        mScaleGestureDetector.onTouchEvent(event);
        return true;
    }
}
