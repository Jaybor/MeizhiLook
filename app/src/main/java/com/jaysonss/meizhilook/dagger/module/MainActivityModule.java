package com.jaysonss.meizhilook.dagger.module;

import com.jaysonss.meizhilook.ActivityScope;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jaybor on 2016/11/10.
 */
@Module
public class MainActivityModule {

    private MainActivityContract.View mView;

    public MainActivityModule(MainActivityContract.View view) {
        this.mView = view;
    }

    @Provides
    public MainActivityContract.View provideMainActivityCOntractView() {
        return mView;
    }

}
