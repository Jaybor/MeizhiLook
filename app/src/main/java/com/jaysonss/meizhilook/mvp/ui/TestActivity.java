package com.jaysonss.meizhilook.mvp.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.mvp.base.BaseActivity;
import com.jaysonss.meizhilook.mvp.ui.widget.ScaleTypeImageView;

import butterknife.BindView;

/**
 * Created by jaybor on 2016/11/16.
 */

public class TestActivity extends BaseActivity {

    @BindView(R.id.activity_test_stiv)
    ScaleTypeImageView srcIv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_test;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        srcIv.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            srcIv.setImageRes(R.drawable.leak_canary_icon);
        });
    }

}

