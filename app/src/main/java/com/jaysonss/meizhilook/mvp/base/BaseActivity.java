package com.jaysonss.meizhilook.mvp.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jaysonss.meizhilook.utils.StatusBarCompat;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by jaybor on 2016/11/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseContract.View, Toolbar.OnMenuItemClickListener {

    protected abstract int getContentViewId();

    private Unbinder mButterKnifeUnBinder;

    protected CompositeSubscription mAsyncTasks = new CompositeSubscription();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.compat(new WeakReference<>(this));
        setContentView(getContentViewId());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mButterKnifeUnBinder = ButterKnife.bind(this);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return false;
    }

    protected void setUpToolbar(int id) {
        Toolbar toolbar = (Toolbar) findViewById(id);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    protected void onClickCallback(View view) {

    }

    @Override
    public CompositeSubscription getAsyncTasks() {
        return mAsyncTasks;
    }

    @Override
    protected void onDestroy() {
        if (mButterKnifeUnBinder != null) {
            mButterKnifeUnBinder.unbind();
        }
        super.onDestroy();
    }

}
