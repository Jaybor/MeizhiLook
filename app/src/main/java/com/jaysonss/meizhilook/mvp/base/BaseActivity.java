package com.jaysonss.meizhilook.mvp.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jaybor on 2016/11/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View {

    protected abstract int getContentViewId();

    private Unbinder mButterKnifeUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        mButterKnifeUnBinder = ButterKnife.bind(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        if (mButterKnifeUnBinder != null) {
            mButterKnifeUnBinder.unbind();
        }
        super.onDestroy();
    }
}
