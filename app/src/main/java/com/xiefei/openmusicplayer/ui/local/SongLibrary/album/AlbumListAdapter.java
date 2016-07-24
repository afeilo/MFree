package com.xiefei.openmusicplayer.ui.local.SongLibrary.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Album;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class AlbumListAdapter extends RecyclerView.Adapter<AlbumListAdapter.AlbumListViewHolder>{
    private Context context;
    private ArrayList<Album> albums = new ArrayList<>(0);
    public AlbumListAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }
    public void addSongs(List<Album> albumList){
        albums.addAll(albumList);
        notifyDataSetChanged();
    }

    @Override
    public AlbumListAdapter.AlbumListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AlbumListViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(AlbumListViewHolder holder, int position) {
        Album album = albums.get(position);
//        holder.songTitle.setText(songInfo.getTitle());
//        holder.songArtist.setText(songInfo.getArtist());
        holder.primaryTitle.setText(album.getAlbum());
        holder.secondaryTitle.setText("歌曲数量:"+album.getNumSongs());
        holder.image.setImageResource(R.mipmap.ic_launcher);
        Glide.with(context.getApplicationContext())
                .load(OpenMusicPlayerUtils.getAlbumArtUri(album.getArtistId()))
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return albums.size();
    }
    static class AlbumListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.primary_title)
        TextView primaryTitle;
        @BindView(R.id.secondary_title)
        TextView secondaryTitle;
        @BindView(R.id.image)
        ImageView image;
        public AlbumListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
