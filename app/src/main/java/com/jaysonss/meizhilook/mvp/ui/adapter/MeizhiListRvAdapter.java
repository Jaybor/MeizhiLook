package com.jaysonss.meizhilook.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaysonss.meizhilook.R;
import com.jaysonss.meizhilook.entities.DataInfo;
import com.jaysonss.meizhilook.mvp.ui.MeizhiPreviewActivity;
import com.jaysonss.meizhilook.mvp.ui.widget.MeizhiImageView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        return new MeizhiListViewHolder(LayoutInflater.from(context).inflate(R.layout.view_meizhi_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MeizhiListViewHolder meizhiListViewHolder = (MeizhiListViewHolder) holder;
        DataInfo dataInfo = dataInfoList.get(position);
        Glide.with(context)
                .load(dataInfo.getUrl())
                .fitCenter()
                .into(meizhiListViewHolder.imageView);
        meizhiListViewHolder.descTv.setText(dataInfo.getDesc());
        meizhiListViewHolder.imageView.setOnClickListener(v -> {
            context.startActivity(MeizhiPreviewActivity.newIntent(context, dataInfo.getUrl()));
        });
    }

    @Override
    public int getItemCount() {
        return dataInfoList == null ? 0 : dataInfoList.size();
    }

    class MeizhiListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.view_meizhi_list_item_iv)
        MeizhiImageView imageView;

        @BindView(R.id.view_meizhi_list_item_tv)
        TextView descTv;

        public MeizhiListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (imageView != null) {
                imageView.setOriginSize(50, 50);
            }
        }
    }

}
