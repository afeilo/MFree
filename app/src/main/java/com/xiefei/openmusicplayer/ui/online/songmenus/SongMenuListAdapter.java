package com.xiefei.openmusicplayer.ui.online.songmenus;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiefei.library.XRecyclerAdapter;
import com.xiefei.library.XViewHolderHelper;
import com.xiefei.openmusicplayer.R;
import com.xiefei.openmusicplayer.entity.SongMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xiefei on 16/7/10.
 */
public class SongMenuListAdapter extends XRecyclerAdapter<SongMenu.ContentBean>{

    public SongMenuListAdapter(Context context, @LayoutRes int layoutId) {
        super(context, layoutId);
    }

    @Override
    public void bindItemView(XViewHolderHelper holderHelper, SongMenu.ContentBean data, int position) {
        ((TextView)holderHelper.getViewById(R.id.primary_title)).setText(data.getTitle());
        ((TextView)holderHelper.getViewById(R.id.secondary_title)).setText(data.getDesc());
        Glide.with(context.getApplicationContext()).load(data.getPic_300()).into((ImageView) holderHelper.getViewById(R.id.image));
    }
}

