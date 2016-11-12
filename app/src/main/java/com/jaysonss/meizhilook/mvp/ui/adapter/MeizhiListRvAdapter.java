package com.jaysonss.meizhilook.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.jaysonss.meizhilook.entities.DataInfo;
import com.jaysonss.meizhilook.mvp.ui.vh.MeizhiListViewHolder;
import com.jaysonss.meizhilook.mvp.ui.widget.MeizhiImageView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by jaybor on 2016/11/11.
 */
public class MeizhiListRvAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataInfo> dataInfoList;

    private Context context;

    @Inject
    public MeizhiListRvAdapter(Context context, List<DataInfo> dataInfoList) {
        this.context = context;
        this.dataInfoList = dataInfoList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cardView = new CardView(context);
        MeizhiImageView meizhiImageView = new MeizhiImageView(context);
        meizhiImageView.setOriginSize(50, 50);
        cardView.addView(meizhiImageView);
        return new MeizhiListViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CardView cardView = (CardView) holder.itemView;
        MeizhiImageView meizhiImageView = (MeizhiImageView) cardView.getChildAt(0);
        Glide.with(context)
                .load(dataInfoList.get(position).getUrl())
                .fitCenter()
                .into(meizhiImageView);
    }

    @Override
    public int getItemCount() {
        return dataInfoList == null ? 0 : dataInfoList.size();
    }

}
