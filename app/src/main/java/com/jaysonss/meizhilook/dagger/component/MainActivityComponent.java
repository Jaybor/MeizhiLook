package com.jaysonss.meizhilook.dagger.component;

import com.jaysonss.meizhilook.ActivityScope;
import com.jaysonss.meizhilook.dagger.module.MainActivityModule;
import com.jaysonss.meizhilook.dagger.module.component.ApplicationComponent;
import com.jaysonss.meizhilook.mvp.ui.MainActivity;

import dagger.Component;

/**
 * Created by Jaybor on 2016/11/10.
 */
@ActivityScope
@Component(dependencies = {ApplicationComponent.class}, modules = {MainActivityModule.class})
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

}
