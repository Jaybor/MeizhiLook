package com.jaysonss.meizhilook.mvp.presenter;

import android.app.Application;
import android.util.Log;

import com.jaysonss.meizhilook.NetworkApi;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;

import javax.inject.Inject;

/**
 * Created by Jaybor on 2016/11/10.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {

    @Inject
    NetworkApi networkApi;

    private MainActivityContract.View mView;

    @Inject
    public MainActivityPresenter(MainActivityContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadTestStr() {
        String testStr = "hello world";
        mView.onLoadTestStr(testStr);
    }
}
