package com.xiefei.openmusicplayer.ui.local.SongLibrary.filterSong;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.library.XViewHolderHelper;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongInfo;
import com.xiefei.openmusicplayer.entity.SongMenuInfo;
import com.xiefei.openmusicplayer.utils.OpenMusicPlayerUtils;

/**
 * Created by xiefei on 16/7/10.
 */
public class FilterSongListAdapter extends XRecyclerAdapter<SongInfo>{

    public FilterSongListAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindItemView(XViewHolderHelper holderHelper, SongInfo data, int position) {
        ((TextView)holderHelper.getViewById(R.id.primary_title)).setText(data.getTitle());
        ((TextView)holderHelper.getViewById(R.id.secondary_title)).setText(data.getArtist());
        Glide.with(context.getApplicationContext()).load(OpenMusicPlayerUtils.getAlbumArtUri(data.getAlbumId())).into((ImageView) holderHelper.getViewById(R.id.image));
    }

}
