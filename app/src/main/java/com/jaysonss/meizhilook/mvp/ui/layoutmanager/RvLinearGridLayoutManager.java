package com.jaysonss.meizhilook.mvp.ui.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class RvLinearGridLayoutManager extends GridLayoutManager {

    private static final String TAG = "RvLinearGridLayoutManager";

    public RvLinearGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public RvLinearGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public RvLinearGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
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
