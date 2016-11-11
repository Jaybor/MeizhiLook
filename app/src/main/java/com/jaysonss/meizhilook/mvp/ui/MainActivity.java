package com.jaysonss.meizhilook.mvp.ui;

import android.app.Application;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.jaysonss.meizhilook.MeizhiApplication;
import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.dagger.component.DaggerMainActivityComponent;
import com.jaysonss.meizhilook.dagger.component.MainActivityComponent;
import com.jaysonss.meizhilook.dagger.module.MainActivityModule;
import com.jaysonss.meizhilook.entities.Meizhi;
import com.jaysonss.meizhilook.mvp.base.BaseActivity;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;
import com.jaysonss.meizhilook.mvp.presenter.MainActivityPresenter;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private static final int PAGE_COUNT = 20;

    private MainActivityComponent mMainActivityComponent;

    @Inject
    MainActivityPresenter mPresenter;

    @Inject
    Application application;

    @BindView(R.id.activity_main_swl)
    SwipeRefreshLayout mRefreshLayout;

    @BindView(R.id.activity_main_rv)
    RecyclerView mRecyclerView;

    //api请求分页
    private int mCurrentPageIndex = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivityComponent = DaggerMainActivityComponent.builder()
                .applicationComponent(((MeizhiApplication) application).getApplicationComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build();
        mMainActivityComponent.inject(this);
        mPresenter.loadMeizhi(PAGE_COUNT, mCurrentPageIndex);
    }

    @Override
    public void onLoadMeizhi(Meizhi meizhi) {
        mCurrentPageIndex++;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onViewDetached();
    }
}
