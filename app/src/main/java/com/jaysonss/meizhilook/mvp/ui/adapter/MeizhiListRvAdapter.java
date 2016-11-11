package com.jaysonss.meizhilook.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jaysonss.meizhilook.entities.DataInfo;
import com.jaysonss.meizhilook.mvp.ui.vh.MeizhiListViewHolder;
import com.jaysonss.meizhilook.mvp.ui.widget.MeizhiImageView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jaybor on 2016/11/11.
 */
public class MeizhiListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<DataInfo> dataInfoList;

    Context context;

    @Inject
    public MeizhiListRvAdapter(Context context, List<DataInfo> dataInfoList) {
        this.context = context;
        this.dataInfoList = dataInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MeizhiImageView meizhiImageView = new MeizhiImageView(context);
        return new MeizhiListViewHolder(meizhiImageView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return dataInfoList == null ? 0 : dataInfoList.size();
    }
}
