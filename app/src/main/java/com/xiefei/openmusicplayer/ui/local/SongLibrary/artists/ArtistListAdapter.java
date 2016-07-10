package com.xiefei.openmusicplayer.ui.local.SongLibrary.artists;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Artist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistListViewHolder>{
    private Context context;
    private ArrayList<Artist> artists = new ArrayList<>(0);
    public ArtistListAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }
    public void addSongs(List<Artist> artistsList){
        artists.addAll(artistsList);
    }

    @Override
    public ArtistListAdapter.ArtistListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArtistListViewHolder(LayoutInflater.from(context).inflate(R.layout.artist_list_item,parent,false));
    }

    @Override
    public void onBindViewHolder(ArtistListViewHolder holder, int position) {
        Artist artist = artists.get(position);
//        holder.songTitle.setText(songInfo.getTitle());
//        holder.songArtist.setText(songInfo.getArtist());
        holder.primaryTitle.setText(artist.getAstist());
        holder.secondaryTitle.setText("歌曲数量:"+artist.getNumberOfTracks());
        holder.image.setImageResource(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }
    static class ArtistListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.primary_title)
        TextView primaryTitle;
        @BindView(R.id.secondary_title)
        TextView secondaryTitle;
        @BindView(R.id.image)
        ImageView image;
        public ArtistListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }
}
