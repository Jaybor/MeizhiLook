package com.jaysonss.meizhilook.mvp.ui;

import android.app.Application;
import android.os.Bundle;
import android.widget.Toast;

import com.jaysonss.meizhilook.MeizhiApplication;
import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.dagger.component.DaggerMainActivityComponent;
import com.jaysonss.meizhilook.dagger.component.MainActivityComponent;
import com.jaysonss.meizhilook.dagger.module.MainActivityModule;
import com.jaysonss.meizhilook.mvp.base.BaseActivity;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;
import com.jaysonss.meizhilook.mvp.presenter.MainActivityPresenter;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class MainActivity extends BaseActivity implements MainActivityContract.View {

    private MainActivityComponent mMainActivityComponent;

    @Inject
    MainActivityPresenter mPresenter;

    @Inject
    Application application;

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

        mPresenter.loadTestStr();
    }

    @Override
    public void onLoadTestStr(String testStr) {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Toast.makeText(getContext(), testStr, Toast.LENGTH_SHORT).show();
                });
    }
}
