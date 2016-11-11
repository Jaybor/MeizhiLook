package com.jaysonss.meizhilook.mvp.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by jaybor on 2016/11/11.
 */
public class MeizhiImageView extends ImageView {

    private int originWidth;

    private int originHeight;

    public MeizhiImageView(Context context) {
        super(context);
    }

    public MeizhiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MeizhiImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeizhiImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setOriginSize(int originWidth, int originHeight) {
        this.originWidth = originWidth;
        this.originHeight = originHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originHeight > 0 && originWidth > 0) {
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);
            float ratio = 1.0f * originWidth / originHeight;
            if (width > 0) {
                height = (int) (width / ratio);
            } else if (height > 0) {
                width = (int) (height * ratio);
            }
            setMeasuredDimension(width, height);
        } else {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
