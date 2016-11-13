package com.jaysonss.meizhilook.utils;

import android.content.Context;

/**
 * Created by Jaybor on 2016/11/13.
 */

public class AppUtil {

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(identifier);
    }

}
