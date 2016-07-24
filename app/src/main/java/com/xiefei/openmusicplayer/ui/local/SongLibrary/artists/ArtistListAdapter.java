package com.xiefei.openmusicplayer.ui.local.SongLibrary.artists;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.library.XViewHolderHelper;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.Artist;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class ArtistListAdapter extends XRecyclerAdapter<Artist>{

    public ArtistListAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }
    @Override
    public void bindItemView(XViewHolderHelper holderHelper, Artist data, int position) {
        ((TextView)holderHelper.getViewById(R.id.primary_title)).setText(data.getAstist());
        ((TextView)holderHelper.getViewById(R.id.secondary_title)).setText("歌曲数量:"+data.getNumberOfTracks());
        ((ImageView)holderHelper.getViewById(R.id.image)).setImageResource(R.mipmap.logo_icon);
    }
}
