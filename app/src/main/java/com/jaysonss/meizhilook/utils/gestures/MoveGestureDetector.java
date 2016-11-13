package com.jaysonss.meizhilook.utils.gestures;

import android.content.Context;
import android.graphics.PointF;
import android.view.MotionEvent;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class MoveGestureDetector extends BaseGestureDetector {

    private OnMoveGestureDetectorListener onMoveGestureDetectorListener;

    private PointF mPrePointer;

    private PointF mCurrentPointer;

    private PointF mResultPointer = new PointF();

    public MoveGestureDetector(Context context, OnMoveGestureDetectorListener onMoveGestureDetectorListener) {
        super(context);
        this.onMoveGestureDetectorListener = onMoveGestureDetectorListener;
    }

    @Override
    protected void handlePreMotionEvent(MotionEvent event) {
        int actionMode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionMode) {
            case MotionEvent.ACTION_DOWN:
                resetState();
                mPreMotionEvent = MotionEvent.obtain(event);
                updateStateByEvent(event);
                break;

            case MotionEvent.ACTION_MOVE:
                //第一次Move
                isGestureInProgress = onMoveGestureDetectorListener.onMoveStart(this);
                break;
        }
    }

    @Override
    protected void handleInProgressMotionEvent(MotionEvent event) {
        int actionMode = event.getAction() & MotionEvent.ACTION_MASK;
        switch (actionMode) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                onMoveGestureDetectorListener.onMoveEnd(this);
                resetState();
                break;

            case MotionEvent.ACTION_MOVE:
                updateStateByEvent(event);
                boolean isUpdate = onMoveGestureDetectorListener.onMove(this);
                if (isUpdate) {
                    mPreMotionEvent.recycle();
                    mPreMotionEvent = MotionEvent.obtain(event);
                }
                break;
        }
    }

    @Override
    protected void updateStateByEvent(MotionEvent event) {
        mPrePointer = calculatePointerByEvent(mPreMotionEvent);
        mCurrentPointer = calculatePointerByEvent(event);

        boolean isIgnored = mPreMotionEvent.getPointerCount() != event.getPointerCount();
        mResultPointer.x = isIgnored ? 0 : mCurrentPointer.x - mPrePointer.x;
        mResultPointer.y = isIgnored ? 0 : mCurrentPointer.y - mPrePointer.y;
    }

    private PointF calculatePointerByEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
        float x = 0, y = 0;
        for (int i = 0; i < pointerCount; i++) {
            x += event.getX(i);
            y += event.getY(i);
        }
        x /= pointerCount;
        y /= pointerCount;
        return new PointF(x, y);
    }

    public float getMoveX() {
        return mResultPointer.x;
    }

    public float getMoveY() {
        return mResultPointer.y;
    }

    public interface OnMoveGestureDetectorListener {

        boolean onMoveStart(MoveGestureDetector moveGestureDetector);

        boolean onMove(MoveGestureDetector moveGestureDetector);

        void onMoveEnd(MoveGestureDetector moveGestureDetector);

    }

    public static class SimpleMoveGestureDetectorListener implements OnMoveGestureDetectorListener {

        @Override
        public boolean onMoveStart(MoveGestureDetector moveGestureDetector) {
            return true;
        }

        @Override
        public boolean onMove(MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(MoveGestureDetector moveGestureDetector) {

        }

    }

}
