package com.jaysonss.meizhilook.mvp.base;

import android.content.Context;

/**
 * Created by jaybor on 2016/11/10.
 */

public interface BaseContract {

    interface View {
        Context getContext();
    }

    interface Presenter {
        /**
         * activity's onDestroy or fragment's onDestroyView,
         * to stop all async tasks
         */
        void onViewDetached();
    }

}
