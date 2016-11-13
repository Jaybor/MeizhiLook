package com.jaysonss.meizhilook.utils.gestures;

import android.content.Context;
import android.view.MotionEvent;

/**
 * Created by Jaybor on 2016/11/13.
 */

public abstract class BaseGestureDetector {

    protected boolean isGestureInProgress;

    protected MotionEvent mPreMotionEvent;

    protected MotionEvent mCurrentMotionEvent;

    protected Context mContext;

    public BaseGestureDetector(Context context) {
        this.mContext = context;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isGestureInProgress) {
            handleInProgressMotionEvent(event);
        } else {
            handlePreMotionEvent(event);
        }
        return true;
    }

    protected abstract void handlePreMotionEvent(MotionEvent event);

    protected abstract void handleInProgressMotionEvent(MotionEvent event);

    protected abstract void updateStateByEvent(MotionEvent event);

    public void resetState() {
        if (mPreMotionEvent != null) {
            mPreMotionEvent.recycle();
            mPreMotionEvent = null;
        }

        if (mCurrentMotionEvent != null) {
            mCurrentMotionEvent.recycle();
            mCurrentMotionEvent = null;
        }

        isGestureInProgress = false;
    }

}
