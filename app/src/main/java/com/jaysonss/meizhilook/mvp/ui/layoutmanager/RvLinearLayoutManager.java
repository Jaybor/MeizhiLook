package com.jaysonss.meizhilook.mvp.ui.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class RvLinearLayoutManager extends LinearLayoutManager {

    private static final String TAG = "RvLinearLayoutManager";

    public RvLinearLayoutManager(Context context) {
        super(context);
    }

    public RvLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public RvLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
