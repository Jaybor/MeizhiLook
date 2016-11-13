package com.jaysonss.meizhilook.utils;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.jaysonss.meizhilook.R;

import java.lang.ref.WeakReference;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class StatusBarCompat {

    public static void compat(WeakReference<Activity> activityRef) {
        int sdkVersion = Build.VERSION.SDK_INT;
        Activity activity = activityRef.get();
        if (sdkVersion < Build.VERSION_CODES.KITKAT || activity == null) {
            return;
        }

        if (sdkVersion < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, AppUtil.getStatusBarHeight(activity)));
            view.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
            decorView.addView(view);
        } else {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }

}
