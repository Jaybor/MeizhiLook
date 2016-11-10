package com.jaysonss.meizhilook;

import android.app.Application;

import com.jaysonss.meizhilook.dagger.module.ApplicationModule;
import com.jaysonss.meizhilook.dagger.module.NetWorkModule;
import com.jaysonss.meizhilook.dagger.module.component.ApplicationComponent;
import com.jaysonss.meizhilook.dagger.module.component.DaggerApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

import butterknife.ButterKnife;

/**
 * Created by jaybor on 2016/11/10.
 */
public class MeizhiApplication extends Application {

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        initializeMemoryLeakChecker();
        initializeButterKnifeLogger();
    }

    private void initializeInjector() {
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .netWorkModule(new NetWorkModule())
                .build();
    }

    private void initializeMemoryLeakChecker() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

    private void initializeButterKnifeLogger() {
        if (BuildConfig.DEBUG) {
            ButterKnife.setDebug(true);
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return mAppComponent;
    }

}
