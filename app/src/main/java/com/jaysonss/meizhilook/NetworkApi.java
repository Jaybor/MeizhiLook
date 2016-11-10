package com.jaysonss.meizhilook;

import android.database.Observable;

import com.jaysonss.meizhilook.entities.Day;
import com.jaysonss.meizhilook.entities.Meizhi;

import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by jaybor on 2016/11/10.
 */
public interface NetworkApi {

    @SuppressWarnings("SameParameterValue")
    @GET("api/data/福利/{amount}/{page}")
    Observable<Meizhi> getMeizhi(@Path("amount") int meizhiCount, @Path("page") int page);

    @GET("api/day/{date}")
    Observable<Day> getDay(@Path("date") String date);

}
