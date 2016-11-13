package com.jaysonss.meizhilook.mvp.ui.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class RvLinearStaggedLayoutManager extends StaggeredGridLayoutManager {

    private static final String TAG = "RvLinearStaggedLayoutManager";

    public RvLinearStaggedLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RvLinearStaggedLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            Log.e(TAG, e.toString());
        }
    }
}
