package com.jaysonss.meizhilook.mvp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.jaysonss.meizhilook.MeizhiApplication;
import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.dagger.component.DaggerMainActivityComponent;
import com.jaysonss.meizhilook.dagger.module.MainActivityModule;
import com.jaysonss.meizhilook.entities.DataInfo;
import com.jaysonss.meizhilook.entities.Meizhi;
import com.jaysonss.meizhilook.mvp.base.BaseActivity;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;
import com.jaysonss.meizhilook.mvp.presenter.MainActivityPresenter;
import com.jaysonss.meizhilook.mvp.ui.adapter.MeizhiListRvAdapter;
import com.jaysonss.meizhilook.utils.ValueUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final int PAGE_COUNT = 20;

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.activity_main_swl)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.activity_main_rv)
    RecyclerView mRecyclerView;

    @Inject
    MeizhiListRvAdapter mRecyclerViewAdapter;

    private List<DataInfo> mDataInfoList = new ArrayList<>();

    //api请求分页
    private int mCurrentPageIndex = 1;

    private boolean mIsRefreshing;

    private boolean mIsLoadingMore;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainActivityComponent.builder()
                .applicationComponent(MeizhiApplication.getInstance().getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this, this, mDataInfoList))
                .build()
                .inject(this);

        setupRecyclerView();

        mRecyclerView.post(() -> {
            mIsRefreshing = true;
            mRefreshLayout.setRefreshing(true);
            mPresenter.loadMeizhi(PAGE_COUNT, mCurrentPageIndex, true);
        });
    }

    private void setupRecyclerView() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] into = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                Arrays.sort(into);
                int lastPosition = mRecyclerViewAdapter.getItemCount() - 1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        !ValueUtil.isEmpty(into) && lastPosition == into[into.length - 1] && !mIsRefreshing && !mIsLoadingMore) {
                    mRefreshLayout.setRefreshing(true);
                    mIsLoadingMore = true;
                    mPresenter.loadMeizhi(PAGE_COUNT, mCurrentPageIndex, false);
                }
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public void onLoadMeizhi(boolean isRefresh, Meizhi meizhi) {
        mRefreshLayout.setRefreshing(false);
        List<DataInfo> results = meizhi.getResults();
        if (!ValueUtil.isEmpty(results)) {
            mCurrentPageIndex++;
            if (isRefresh) {
                mIsRefreshing = false;
                mDataInfoList.clear();
            } else {
                mIsLoadingMore = false;
            }
            mDataInfoList.addAll(results);
            mRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRefresh() {
        if (!mIsLoadingMore && !mIsRefreshing) {
            mIsRefreshing = true;
            mCurrentPageIndex = 1;
            mPresenter.loadMeizhi(PAGE_COUNT, mCurrentPageIndex, true);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onViewDetached();
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

}
