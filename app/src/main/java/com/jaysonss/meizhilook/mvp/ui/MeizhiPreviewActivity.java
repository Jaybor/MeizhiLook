package com.jaysonss.meizhilook.mvp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.mvp.base.BaseActivity;
import com.jaysonss.meizhilook.mvp.ui.widget.PreviewImageView;
import com.orhanobut.logger.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jaybor on 2016/11/14.
 */

public class MeizhiPreviewActivity extends BaseActivity {

    private static final String TAG = "MeizhiPreviewActivity";
    private static final String IMG_URL = "img_url";

    @BindView(R.id.activity_meizhi_preview_iv)
    PreviewImageView mIv;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_meizhi_preview;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String imgUrl = getIntent().getStringExtra(IMG_URL);
        if (TextUtils.isEmpty(imgUrl)) {
            finish();
            return;
        }
        Observable.create(new Observable.OnSubscribe<InputStream>() {
            @Override
            public void call(Subscriber<? super InputStream> subscriber) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(imgUrl)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    InputStream inputStream = response.body().byteStream();
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    int length;
                    byte[] buf = new byte[1024];
                    while ((length = inputStream.read(buf)) != -1) {
                        bos.write(buf, 0, length);
                    }

                    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());

                    bos.close();
                    inputStream.close();
                    subscriber.onNext(bis);
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(inputStream -> mIv.setInputStream(inputStream), throwable -> {
                    Logger.e(TAG, throwable);
                });
    }

    public static Intent newIntent(Context context, String url) {
        Intent intent = new Intent(context, MeizhiPreviewActivity.class);
        intent.putExtra(IMG_URL, url);
        return intent;
    }
}
