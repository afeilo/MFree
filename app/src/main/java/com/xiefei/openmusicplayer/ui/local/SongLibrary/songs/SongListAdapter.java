package com.xiefei.openmusicplayer.ui.local.SongLibrary.songs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongListAdapter extends RecyclerView.Adapter<SongListAdapter.SongListViewHolder>{
    private Context context;
    private ArrayList<SongInfo> songInfos = new ArrayList<>(0);
    public SongListAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }
    public void addSongs(List<SongInfo> songInfoList){
        songInfos.addAll(songInfoList);
    }

    @Override
    public SongListAdapter.SongListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongListViewHolder(LayoutInflater.from(context).inflate(R.layout.song_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(SongListViewHolder holder, int position) {
        SongInfo songInfo = songInfos.get(position);
        holder.songTitle.setText(songInfo.getTitle());
        holder.songArtist.setText(songInfo.getArtist());
        Glide.with(context).load(OpenMusicPlayerUtils.getAlbumArtUri(songInfo.getAlbumId()))
                .placeholder(R.mipmap.ic_launcher)
                .crossFade(100)
                .into(holder.albumImage);
    }

    @Override
    public int getItemCount() {
        return songInfos.size();
    }
    static class SongListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.song_title)
        TextView songTitle;
        @BindView(R.id.song_artist)
        TextView songArtist;
        @BindView(R.id.album_image)
        ImageView albumImage;
        public SongListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
