package com.jaysonss.meizhilook.mvp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

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
import com.jaysonss.meizhilook.mvp.ui.layoutmanager.RvLinearStaggedLayoutManager;
import com.jaysonss.meizhilook.utils.ValueUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;

public class MainActivity extends BaseActivity implements MainActivityContract.View, SwipeRefreshLayout.OnRefreshListener {

    private static final int PAGE_COUNT = 20;

    private static final int BACK_TO_TOP_POSITION = 20;

    private static final String TAG = "MainActivity";

    @Inject
    MainActivityPresenter mPresenter;

    @BindView(R.id.activity_main_swl)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.activity_main_rv)
    RecyclerView mRecyclerView;

    @BindView(R.id.activity_main_back_to_top_iv)
    ImageView backToTopIv;

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

    @OnClick({R.id.activity_main_back_to_top_iv})
    @Override
    protected void onClickCallback(View view) {
        switch (view.getId()) {
            case R.id.activity_main_back_to_top_iv:
                mRecyclerView.stopNestedScroll();
                mRecyclerView.scrollToPosition(0);
                backToTopIv.setVisibility(View.GONE);
                break;
        }
    }

    private void setupRecyclerView() {
        RvLinearStaggedLayoutManager staggeredGridLayoutManager = new RvLinearStaggedLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !recyclerView.canScrollVertically(1)
                        && !mIsLoadingMore && !mIsRefreshing) {
                    mIsLoadingMore = true;
                    mRefreshLayout.setRefreshing(true);
                    mPresenter.loadMeizhi(PAGE_COUNT, mCurrentPageIndex, false);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] into = staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(null);
                Arrays.sort(into);
                boolean isBackToTop = !ValueUtil.isEmpty(into) && into[into.length - 1] > BACK_TO_TOP_POSITION;
                backToTopIv.setVisibility(isBackToTop ? View.VISIBLE : View.GONE);
            }
        });
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    @Override
    public void onLoadMeizhi(boolean isRefresh, Meizhi meizhi) {
        mRefreshLayout.setRefreshing(false);
        Observable.just(meizhi)
                .filter(data -> !ValueUtil.isEmpty(meizhi.getResults()))
                .subscribe(data -> {
                    mCurrentPageIndex++;
                    if (isRefresh) {
                        mIsRefreshing = false;
                        mDataInfoList.clear();
                    } else {
                        mIsLoadingMore = false;
                    }
                    mDataInfoList.addAll(data.getResults());
                    mRecyclerViewAdapter.notifyDataSetChanged();
                }, throwable -> Log.e(TAG, throwable.toString()));
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
