package com.jaysonss.meizhilook.mvp.contract;

import com.jaysonss.meizhilook.entities.Meizhi;
import com.jaysonss.meizhilook.mvp.base.BaseContract;

/**
 * Created by Jaybor on 2016/11/10.
 */

public interface MainActivityContract {

    interface Presenter extends BaseContract.Presenter {

        void loadMeizhi(int pageCount, int pageIndex);

    }

    interface View extends BaseContract.View {
        void onLoadMeizhi(Meizhi meizhi);
    }

}
