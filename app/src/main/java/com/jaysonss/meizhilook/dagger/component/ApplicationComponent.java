package com.jaysonss.meizhilook.dagger.module.component;

import android.app.Application;

import com.jaysonss.meizhilook.NetworkApi;
import com.jaysonss.meizhilook.dagger.module.ApplicationModule;
import com.jaysonss.meizhilook.dagger.module.NetWorkModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by jaybor on 2016/11/10.
 */
@Singleton
@Component(modules = {ApplicationModule.class, NetWorkModule.class})
public interface ApplicationComponent {

    Application getApplication();

    NetworkApi getNetworkApi();

}
