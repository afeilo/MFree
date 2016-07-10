package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListAdapter extends RecyclerView.Adapter<SongMenuListAdapter.SongMenuListViewHolder>{
    private Context context;
    private ArrayList<SongMenu.ContentBean> SongMenus = new ArrayList<>(0);
    public SongMenuListAdapter(Context context) {
        this.context = context;
    }

    public void addSongMenus(List<SongMenu.ContentBean> data) {
        SongMenus.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public SongMenuListAdapter.SongMenuListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongMenuListViewHolder(LayoutInflater.from(context).inflate(R.layout.songmenu_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(SongMenuListViewHolder holder, int position) {
        SongMenu.ContentBean songMenu = SongMenus.get(position);
//        holder.songTitle.setText(songInfo.getTitle());
//        holder.songSongMenu.setText(songInfo.getSongMenu());
//        holder.primaryTitle.setText(SongMenu.getAstist());
//        holder.secondaryTitle.setText("歌曲数量:"+SongMenu.getNumberOfTracks());
//        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.primaryTitle.setText(songMenu.getTitle());
        holder.secondaryTitle.setText(songMenu.getDesc());
        Glide.with(context).load(songMenu.getPic_300()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return SongMenus.size();
    }



    static class SongMenuListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.primary_title)
        TextView primaryTitle;
        @BindView(R.id.secondary_title)
        TextView secondaryTitle;
        @BindView(R.id.image)
        ImageView image;
        public SongMenuListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
