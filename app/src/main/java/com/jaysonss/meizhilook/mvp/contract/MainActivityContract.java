package com.jaysonss.meizhilook.mvp.contract;

import com.jaysonss.meizhilook.mvp.base.BaseContract;

/**
 * Created by Jaybor on 2016/11/10.
 */

public interface MainActivityContract extends BaseContract {

    interface Presenter {
        void loadTestStr();
    }

    interface View {
        void onLoadTestStr(String testStr);
    }

}
