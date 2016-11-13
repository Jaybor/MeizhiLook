package com.jaysonss.meizhilook.mvp.presenter;

import com.jaysonss.meizhilook.DefaultSubscriber;
import com.jaysonss.meizhilook.NetworkApi;
import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.entities.Meizhi;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jaybor on 2016/11/10.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {

    @Inject
    NetworkApi networkApi;

    private MainActivityContract.View mView;

    private boolean isRefresh;

    @Inject
    public MainActivityPresenter(MainActivityContract.View view) {
        this.mView = view;
    }

    @Override
    public void loadMeizhi(int pageCount, int pageIndex, boolean isRefresh) {
        this.isRefresh = isRefresh;
        mView.getAsyncTasks().add(
                networkApi.getMeizhi(pageCount, pageIndex)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MainSubscriber())
        );
    }

    @Override
    public void onViewDetached() {
        if (!mView.getAsyncTasks().isUnsubscribed()) {
            mView.getAsyncTasks().clear();
        }
    }

    public class MainSubscriber extends DefaultSubscriber<Meizhi> {

        @Override
        public void onCompleted() {
            super.onCompleted();
        }

        @Override
        public void onNext(Meizhi meizhi) {
            if (meizhi.isError()) {
                mView.showToastMessage(mView.getContext().getString(R.string.common_error));
            } else {
                mView.onLoadMeizhi(isRefresh, meizhi);
            }
        }

        @Override
        public void onError(Throwable e) {
            mView.showToastMessage(mView.getContext().getString(R.string.network_exception));
        }

    }
}
