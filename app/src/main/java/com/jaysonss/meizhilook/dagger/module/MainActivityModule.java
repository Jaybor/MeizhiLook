package com.jaysonss.meizhilook.dagger.module;

import android.content.Context;

import com.jaysonss.meizhilook.entities.DataInfo;
import com.jaysonss.meizhilook.mvp.contract.MainActivityContract;
import com.jaysonss.meizhilook.mvp.ui.adapter.MeizhiListRvAdapter;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jaybor on 2016/11/10.
 */
@Module
public class MainActivityModule {

    MainActivityContract.View mView;

    Context mContext;

    List<DataInfo> mDataInfoList;

    public MainActivityModule(MainActivityContract.View view, Context context, List<DataInfo> dataInfoList) {
        this.mContext = context;
        this.mView = view;
        this.mDataInfoList = dataInfoList;
    }

    @Provides
    public MainActivityContract.View provideMainActivityCOntractView() {
        return mView;
    }

    @Provides
    public MeizhiListRvAdapter provideMeizhiListRvAdapter() {
        return new MeizhiListRvAdapter(mContext, mDataInfoList);
    }

}
